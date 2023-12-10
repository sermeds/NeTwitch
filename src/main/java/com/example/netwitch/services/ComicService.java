package com.example.netwitch.services;

import com.example.netwitch.models.Comic;
import com.example.netwitch.repositories.ComicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComicService {
    private final ComicRepo comicRepo;

    public Comic getComicById(Integer id) {
        return comicRepo.findComicById(id);
    }

    public List<Comic> getAll() {
        return comicRepo.findAll();
    }

    public void save(Comic comic) {
        comicRepo.save(comic);
    }

    public void delete(Integer id) {
        comicRepo.delete(getComicById(id));
    }
}
