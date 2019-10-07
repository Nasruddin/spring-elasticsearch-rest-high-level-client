package com.javatab.springelasticdemo.api;

import com.javatab.springelasticdemo.model.Movie;
import com.javatab.springelasticdemo.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<String> createDocument(@RequestBody Movie movie) throws IOException {
        return new ResponseEntity<>(movieService.createMovieDocument(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody Movie movie) throws Exception {
        return new ResponseEntity<>(movieService.updateMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() throws Exception {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Movie>> search(
            @RequestParam(value = "technology") String technology)
            throws Exception {
        return ResponseEntity.ok(movieService.searchByGenre(technology));
    }

}
