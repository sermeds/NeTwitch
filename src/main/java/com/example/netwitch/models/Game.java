package com.example.netwitch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.json.JSONPropertyIgnore;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private int id;
    private String title;
    private int year;
    private String genre;
    private double rating;
    private String description;
    private String gameCoverUrl;
}
