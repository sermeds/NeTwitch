package com.example.netwitch.controllers;

import com.example.netwitch.models.CategoryWrap;
import com.example.netwitch.models.Stream;
import com.example.netwitch.models.User;
import com.example.netwitch.services.DonationService;
import com.example.netwitch.services.StreamService;
import jakarta.transaction.Transactional;
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
@Transactional
public class StreamController {
    private final StreamService streamService;
    private final DonationService donationService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        if (user != null) model.addAttribute("isStreamer", user.isStreamer());
        model.addAttribute("streams", streamService.findAll());
        return "hello";
    }

    @GetMapping("/category")
    public String allCategories(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        model.addAttribute("subs", streamService.findBySubscriptions(currentUser));
        model.addAttribute("categories", streamService.findAllCategories());
        model.addAttribute("streams", streamService.findAll());
        return "categories";
    }

    @GetMapping("/category/{type}")
    public String category(@AuthenticationPrincipal User currentUser, Model model, @PathVariable String type) {
        if ("subs" .equals(type)) {
            model.addAttribute("user", currentUser);
            model.addAttribute("category", "Подписки");
            model.addAttribute("streams", streamService.findBySubscriptions(currentUser));
            return "category";
        }
        CategoryWrap cat = streamService.getCategory(Integer.parseInt(type));
        if (cat == null) return "redirect:/category";
        streamService.getGameByName(cat.getTitle());
        model.addAttribute("user", currentUser);
        model.addAttribute("category", cat.getTitle());
        model.addAttribute("streams", streamService.findByCategory(cat.getTitle()));
        model.addAttribute("game", streamService.getGameByName(cat.getTitle()));
        return "category";
    }

    @GetMapping("/start")
    public String showStart(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        model.addAttribute("categories", streamService.getAllCategories());
        return "start";
    }

    @PostMapping("/start")
    public String start(@AuthenticationPrincipal User currentUser, Stream stream) {
        streamService.start(stream, currentUser);
        return "redirect:/live/" + stream.getId();
    }

    @PostMapping("/end/{stream}")
    public String end(@AuthenticationPrincipal User currentUser, @PathVariable Stream stream) {
        if (currentUser.equals(stream.getStreamer())) streamService.end(stream);
        return "redirect:/live/" + stream.getId();
    }

    @GetMapping("/live/{stream}")
    public String stream(@AuthenticationPrincipal User currentUser, Model model, @PathVariable Stream stream) {
        model.addAttribute("user", currentUser);
        model.addAttribute("streamer", stream.getStreamer());
        model.addAttribute("isStreamer", stream.getStreamer().equals(currentUser));
        model.addAttribute("donations", stream.getDonations());
        model.addAttribute("stream", stream);
        model.addAttribute("isActive", stream.getActive());
        return "stream";
    }

    @PostMapping("/donate")
    public String donate(
            @AuthenticationPrincipal User sender,
            @RequestParam User receiver,
            @RequestParam int amount,
            @RequestParam Stream stream,
            @RequestParam String message
    ) {
        if (sender.getBalance() < amount) return "redirect:/updateBalance";

        donationService.donate(sender, receiver, amount, stream, message);
        return "redirect:/live/" + stream.getId();
    }

}
