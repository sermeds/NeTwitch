package com.example.netwitch;

import com.example.netwitch.repositories.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NeTwitchApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(NeTwitchApplication.class, args);
//        StaffRepo sr = context.getBean(StaffRepo.class);
//        System.out.println(sr.findAll());
//        sr.findAll().forEach(System.out::println);
        UserRepo userRepo = context.getBean(UserRepo.class);
        System.out.println(userRepo.findAll());
//        DepRepo dr = context.getBean(DepRepo.class);
//        System.out.println(dr.findAll());
    }


}
