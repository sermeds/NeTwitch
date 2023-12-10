package com.example.netwitch.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор")
    private int id;
    @Column(name = "name")
    @Schema(description = "Название комикса", example = "Удивительный Человек-Паук")
    private String name;
    @Column(name = "author")
    @Schema(description = "Автор комикса", example = "Стен Ли")
    private String author;
    @Column(name = "publisher")
    @Schema(description = "Издатель комикса", example = "Марвел")
    private String publisher;
    @Schema(description = "Количество страниц", minimum = "1")
    @Column(name = "pages")
    private int pages;
    @Column(name = "remained")
    @Schema(description = "Количество комиксов, которые есть в наличии", minimum = "0")
    private int remained;
    @Column(name = "sales")
    @Schema(description = "Количество проданных комиксов", minimum = "0")
    private int sales;
    @Column(name = "price")
    @Schema(description = "Цена", minimum = "0")
    private int price;
}
