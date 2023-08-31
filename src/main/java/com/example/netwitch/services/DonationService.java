package com.example.netwitch.services;

import com.example.netwitch.models.Donation;
import com.example.netwitch.models.Stream;
import com.example.netwitch.models.User;
import com.example.netwitch.repositories.DonationRepo;
import com.example.netwitch.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepo donationRepo;
    private final UserService userService;

    public void donate(User sender, User receiver, int amount, Stream stream, String message) {
        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setMessage(message);
        donation.setSender(sender);
        donation.setStream(stream);
        donation.setReceiver(receiver);

        userService.updateBalance(receiver, amount);
        userService.updateBalance(sender, amount * -1);

        donationRepo.save(donation);
    }
}
