package com.example.netwitch.repositories;

import com.example.netwitch.models.StreamerApplication;
import com.example.netwitch.models.User;
import com.example.netwitch.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamerApplicationRepo extends JpaRepository<StreamerApplication, Integer> {
    List<StreamerApplication> findAllByStatusIsNotLike(Status status);

    List<StreamerApplication> findAllByStatusIsLike(Status status);

    StreamerApplication findFirstByUserAndStatusIsNotLike(User user, Status status);
}
