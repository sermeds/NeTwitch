package com.example.netwitch.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@ToString(exclude = {"donations"})
@Table(name="streams")
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    private User streamer;

    @Column(name = "startTime")
    private LocalDateTime startTime;
    @Column(name = "endTime")
    private LocalDateTime endTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stream")
    private List<Donation> donations = new ArrayList<>();

    public List<Donation> getDonations() {
        List<Donation> collect = donations.stream().distinct().collect(Collectors.toList());
        setDonations(collect);
        return donations;
    }
}
