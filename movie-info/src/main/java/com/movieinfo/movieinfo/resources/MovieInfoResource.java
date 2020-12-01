package com.movieinfo.movieinfo.resources;

import com.movieinfo.movieinfo.models.MovieInfo;
import com.movieinfo.movieinfo.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieInfoResource {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public MovieInfo getMovies(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject("http://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
        return new MovieInfo(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
