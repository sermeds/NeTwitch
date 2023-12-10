package com.example.netwitch.repositories;

import com.example.netwitch.models.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicRepo extends JpaRepository<Comic, Integer> {
    Comic findComicById(Integer id);
    List<Comic> findAll();
}
