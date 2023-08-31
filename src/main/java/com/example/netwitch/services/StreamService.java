package com.example.netwitch.services;

import com.example.netwitch.models.CategoryWrap;
import com.example.netwitch.models.Game;
import com.example.netwitch.models.Stream;
import com.example.netwitch.models.User;
import com.example.netwitch.models.enums.Category;
import com.example.netwitch.repositories.StreamRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StreamService {
    private final StreamRepo streamRepo;

    public List<Stream> findAll() {
        return streamRepo.findAllByOrderByActiveDesc();
    }

    public void start(Stream stream, User user) {
        stream.setActive(true);
        stream.setStartTime(LocalDateTime.now());
        stream.setStreamer(user);
        streamRepo.save(stream);
    }

    public void end(Stream stream) {
        stream.setActive(false);
        stream.setEndTime(LocalDateTime.now());

        streamRepo.save(stream);
    }

    public List<Stream> findByCategory(String category) {
        return streamRepo.findAllByCategoryOrderByActiveDesc(category);
    }

    public List<Stream> findByActiveAndStreamer(User user, boolean active) {
        return streamRepo.findAllByActiveIsAndStreamer(active, user);
    }

    public Map<CategoryWrap, List<Stream>> findAllCategories() {
        Map<CategoryWrap, List<Stream>> map = new HashMap<>();
        for (CategoryWrap c : getAllCategories()) {
            map.put(c, findByCategory(c.getTitle()));
        }
        return map;
    }

    public List<Stream> findBySubscriptions(User user) {
        List<Stream> streams = new ArrayList<>();
//        System.out.println("Current User = " + user);
//        System.out.println("His subscriptions = " + user.getSubscriptions());
//        System.out.println("His subscribers = " + user.getSubscribers());
        for (User u : user.getSubscriptions()) {
            streams.addAll(streamRepo.findAllByStreamerOrderByActiveDesc(u));
        }
        return streams;
    }

    public List<CategoryWrap> getAllCategories() {
        List<CategoryWrap> lst = new ArrayList<>();
        int i = 0;
        for (Category c : Category.values()) {
            lst.add(new CategoryWrap(i++, c.getValue()));
        }
        try {
            String url = "http://localhost:9090/api/titles";
            RestTemplate restTemplate = new RestTemplate();
            String[] res = restTemplate.getForObject(url, String[].class);
            for (String str : res) {
                lst.add(new CategoryWrap(i++, str));
            }
        } catch (Exception e){
            return lst;
        }
        return lst;
    }

    public CategoryWrap getCategory(int id) {
        for (CategoryWrap cat : getAllCategories()) {
            if (cat.getId() == id) return cat;
        }
        return null;
    }

    public Game getGameByName(String str) {
        try {
            String url = "http://localhost:9090/api/game/" + str;
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, Game.class);
        } catch (Exception e){
            return null;
        }
    }

    public Stream getStreamByCategory(String category) {
        return streamRepo.findFirstByActiveAndCategory(true, category);
    }


}

