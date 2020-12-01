package com.movie.moviecatalog.resource;

import com.movie.moviecatalog.models.CatalogItem;
import com.movie.moviecatalog.models.CatalogOutput;
import com.movie.moviecatalog.models.MovieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogOutput> getCatalog(@PathVariable("userId") String userId) {

        CatalogItem catalogItem = new CatalogItem(userId,"550","5");
        String movieId = catalogItem.getMovieId();

        /*
        //If movie info list is going to return.
        MovieInfo[] movieInfo = restTemplate.getForObject("http://movie-info-service/movie/"+movieId, MovieInfo[].class);

        return Arrays.asList(movieInfo).stream().map(movie -> {
            return new CatalogOutput(movie.getMovieId(),catalogItem.getRating(),movie.getMovieDesc());
        }).collect(Collectors.toList());*/

        MovieInfo movieInfo = restTemplate.getForObject("http://localhost:8082/movie/"+movieId, MovieInfo.class);

        List<CatalogOutput> catalogOutputs = new ArrayList<>();
        catalogOutputs.add(new CatalogOutput(movieInfo.getMovieId(),catalogItem.getRating(),movieInfo.getMovieDesc()));
        return catalogOutputs;
    }

    @RequestMapping("/new/{userId}")
    public List<CatalogOutput> getCatalogWebClient(@PathVariable("userId") String userId) {

        CatalogItem catalogItem = new CatalogItem(userId,"1","5");
        String movieId = catalogItem.getMovieId();

        MovieInfo[] movieInfo = webClientBuilder.build().get()
                .uri("http://localhost:8082/movie/"+movieId)
                .retrieve()
                .bodyToMono(MovieInfo[].class)
                .block();

        return Arrays.asList(movieInfo).stream().map(movie -> {
            return new CatalogOutput(movie.getMovieId(),catalogItem.getRating(),movie.getMovieDesc());
        }).collect(Collectors.toList());
    }
}
