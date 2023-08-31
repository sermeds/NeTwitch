package com.example.netwitch.models;

import com.example.netwitch.models.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "streamer_applications"
)
@Data
public class StreamerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isApproved() {
        return status.equals(Status.APPROVED);
    }

    public boolean isRejected() {
        return status.equals(Status.REJECTED);
    }

    public boolean isPending() {
        return status.equals(Status.PENDING);
    }
}
