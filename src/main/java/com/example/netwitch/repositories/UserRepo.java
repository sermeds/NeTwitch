package com.example.netwitch.repositories;

import com.example.netwitch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
