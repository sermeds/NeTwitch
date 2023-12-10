package com.example.netwitch.services;

import com.example.netwitch.models.User;
import com.example.netwitch.models.enums.Role;
import com.example.netwitch.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        if (userRepo.findByLogin("sa") == null) {
            user.setLogin("sa");
            user.setName("sa");
            user.setActive(true);
            user.setPassword(passwordEncoder.encode("sa"));
            user.getRoles().add(Role.ROLE_USER);
            user.getRoles().add(Role.ROLE_ADMIN);
            user.getRoles().add(Role.ROLE_STREAMER);
            userRepo.save(user);
            return true;
        }
        if (userRepo.findByLogin(user.getLogin()) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepo.save(user);
        return true;
    }

    public List<User> showUsers() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void saveAdminEdit(String name, Map<String, String> form, User user) {
        user.setName(name);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) user.getRoles().add(Role.valueOf(key));
        }

        save(user);
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        currentUser.getSubscriptions().add(user);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        currentUser.getSubscriptions().remove(user);
        userRepo.save(user);
    }

    public void updateBalance(User user, int amount) {
        user.setBalance(user.getBalance() + amount);
        userRepo.save(user);
    }

    public void addRole(User user, Role role) {
        user.getRoles().add(role);
        userRepo.save(user);
    }

}
