package com.example.netwitch.controllers;

import com.example.netwitch.models.User;
import com.example.netwitch.services.StreamService;
import com.example.netwitch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final StreamService streamService;

    @GetMapping("/profile/{user}")
    public String profile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("active", streamService.findByActiveAndStreamer(user, true));
        model.addAttribute("streams", streamService.findByActiveAndStreamer(user, false));
        model.addAttribute("subsCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        return "profile";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/updateBalance")
    public String updateBalance(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "updateBalance";
    }

    @PostMapping("/updateBalance")
    public String enrollment(@AuthenticationPrincipal User user, @RequestParam int amount) {
        userService.updateBalance(user, amount);
        return "redirect:/";
    }

}
