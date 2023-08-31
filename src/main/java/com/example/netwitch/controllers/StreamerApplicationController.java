package com.example.netwitch.controllers;

import com.example.netwitch.models.StreamerApplication;
import com.example.netwitch.models.User;
import com.example.netwitch.services.StreamerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StreamerApplicationController {
    private final StreamerApplicationService applicationService;

    @GetMapping("/application")
    public String createApplication(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("exist", applicationService.applicationExist(user));
        return "createStreamerApplication";
    }

    @PostMapping("/application")
    public String createApplication(StreamerApplication application) {
        applicationService.createApplication(application);
        return "redirect:/";
    }

    @GetMapping("/applicationList")
    public String applicationList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("pending", applicationService.findPending());
        model.addAttribute("applications", applicationService.findNotPending());
        return "StreamerApplicationList";
    }


    @PostMapping("/approve")
    public String approve(@RequestParam("application") StreamerApplication application) {
        applicationService.approveApplication(application);
        return "redirect:/applicationList";
    }

    @PostMapping("/reject")
    public String reject(@RequestParam("application") StreamerApplication application) {
        applicationService.rejectApplication(application);
        return "redirect:/applicationList";
    }
}
