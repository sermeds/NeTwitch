package com.example.netwitch.controllers;

import com.example.netwitch.models.Stream;
import com.example.netwitch.models.enums.Category;
import com.example.netwitch.services.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final StreamService streamService;

    @RequestMapping("/api/stream/{game}")
    public Integer getStreamByGame(@PathVariable String game) {
        Stream stream = streamService.getStreamByCategory(game);
        if (stream != null) return stream.getId();
        return null;
    }

    @GetMapping("/api/stream/{category}")
    public ResponseEntity<Stream> getStreamByCategory(@PathVariable String category) {
        return ResponseEntity.ok(streamService.getStreamByCategory(category));
    }


}
