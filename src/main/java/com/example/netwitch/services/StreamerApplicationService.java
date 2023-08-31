package com.example.netwitch.services;

import com.example.netwitch.models.StreamerApplication;
import com.example.netwitch.models.User;
import com.example.netwitch.models.enums.Role;
import com.example.netwitch.models.enums.Status;
import com.example.netwitch.repositories.StreamerApplicationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamerApplicationService {

    private final StreamerApplicationRepo applicationRepo;
    private final UserService userService;

    public void createApplication(StreamerApplication application) {
        application.setStatus(Status.PENDING);

        applicationRepo.save(application);
    }

    public List<StreamerApplication> findAll() {
        return applicationRepo.findAll();
    }

    public List<StreamerApplication> findPending() {
        return applicationRepo.findAllByStatusIsLike(Status.PENDING);
    }


    public List<StreamerApplication> findNotPending() {
        return applicationRepo.findAllByStatusIsNotLike(Status.PENDING);
    }

    public void approveApplication(StreamerApplication application) {
        application.setStatus(Status.APPROVED);
        userService.addRole(application.getUser(), Role.ROLE_STREAMER);
        applicationRepo.save(application);
    }

    public void rejectApplication(StreamerApplication application) {
        application.setStatus(Status.REJECTED);
        applicationRepo.save(application);
    }

    public boolean applicationExist (User user) {
        StreamerApplication application = applicationRepo.findFirstByUserAndStatusIsNotLike(user, Status.REJECTED);
        return application != null;
    }

}
