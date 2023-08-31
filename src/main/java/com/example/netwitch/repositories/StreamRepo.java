package com.example.netwitch.repositories;

import com.example.netwitch.models.Stream;
import com.example.netwitch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamRepo extends JpaRepository<Stream, Integer> {
    List<Stream> findAllByOrderByActiveDesc();
    List<Stream> findAllByActiveIsAndStreamer(Boolean active, User streamer);

    List<Stream> findAllByCategoryOrderByActiveDesc(String category);

    List<Stream> findAllByStreamerOrderByActiveDesc(User user);

    Stream findFirstByActiveAndCategory(Boolean active, String category);
}
