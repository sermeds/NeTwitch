package com.example.netwitch.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "donations")
@Data
@ToString(exclude = {"sender", "receiver", "stream"})
@EqualsAndHashCode(of = {"id"})
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;
}
