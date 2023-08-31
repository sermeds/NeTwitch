package com.example.netwitch.controllers;

import com.example.netwitch.models.Game;
import com.example.netwitch.services.StreamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.weaver.ArrayReferenceType;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class Test {
    private final StreamService streamService;

    @SneakyThrows
    @GetMapping("/get")
    public ResponseEntity<List<String>> get() {
        String url = "http://localhost:9090/api/getTitles";
        RestTemplate restTemplate = new RestTemplate();
        String[] res = restTemplate.getForObject(url, String[].class);
        List<String> list = Arrays.asList(res);
        System.out.println(list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
