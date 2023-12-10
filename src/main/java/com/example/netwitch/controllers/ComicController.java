package com.example.netwitch.controllers;

import com.example.netwitch.models.Comic;
import com.example.netwitch.models.User;
import com.example.netwitch.services.ComicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/comic")
@RequiredArgsConstructor
@Tag(name = "Магазин комиксов", description = "управляет комиксами")
public class ComicController {
    private final ComicService comicService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение всех комиксов",
            description = "Позволяет получить информацию о всех комиксах в системе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Comic.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<List<Comic>> getAll() {
        List<Comic> comics = comicService.getAll();
        return ResponseEntity.ok(comics);
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение комикса",
            description = "Позволяет получить информацию о конкретном комиксе по его id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comic.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<Comic> get(@PathVariable @Parameter(description = "Идентификатор комикса") Integer id) {
        Comic comic = comicService.getComicById(id);
        return ResponseEntity.ok(comic);
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Добавление нового комикса",
            description = "Позволяет добавить новый комикс, задавая все необходимые параметры"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public void create(@RequestBody @Parameter(description = "Комикс") Comic comic) {
        comicService.save(comic);
    }

    @PutMapping(value = "/edit", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Изменение комикса",
            description = "Позволяет изменить уже добавленный комикс"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public void edit(@RequestBody @Parameter(description = "Комикс") Comic comic) {
        comicService.save(comic);
    }

    @DeleteMapping(value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Удаление комикса",
            description = "Позволяет удалить комикс по его id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public void delete(@PathVariable @Parameter(description = "Идентификатор комикса") Integer id) {
        comicService.delete(id);
    }
}
