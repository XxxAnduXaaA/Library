package com.example.Library.controller;//package com.example.Library.controller;

import com.example.Library.entity.Book;
import com.example.Library.entity.Genre;
import com.example.Library.repository.GenreRepository;
import com.example.Library.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    @PostMapping
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre){
        LOGGER.info("Creating genre: {}", genre);
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres(){
        LOGGER.info("Getting all genres");
        List<Genre> genres = genreService.getAllGenres();

        if(genres.isEmpty()){
            LOGGER.warn("No genres found");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Found {} genres", genres.size());
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id){
        LOGGER.info("Getting genre by ID: {}", id);
        Genre genre = genreService.getGenreById(id);

        if(genre != null){
            LOGGER.info("Genre found: {}", genre);
            return ResponseEntity.ok(genre);
        }
        else {
            LOGGER.warn("Genre not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @Valid @RequestBody Genre updatedGenre){
        Genre genre = genreService.updateGenre(id, updatedGenre);

        if(genre != null){
            return ResponseEntity.ok(genre);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genre> updatePartialGenre(@PathVariable Long id, @Valid @RequestBody Genre updatedGenre) {
        LOGGER.info("Updating genre with ID: {}", id);
        Optional<Genre> genreOptional = Optional.ofNullable(genreService.getGenreById(id));

        if (genreOptional.isPresent()) {
            LOGGER.info("Updated genre: {}", genreOptional.get());
            Genre existingGenre = genreOptional.get();

            if (updatedGenre.getName() != null) {
                existingGenre.setName(updatedGenre.getName());
            }

            Genre updatedGenreEntity = genreService.updateGenre(id, existingGenre);
            return ResponseEntity.ok(updatedGenreEntity);
        } else {
            LOGGER.warn("Genre not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
