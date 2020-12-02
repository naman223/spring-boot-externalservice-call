package com.movie.moviecatalog.service;

import com.movie.moviecatalog.models.CatalogItem;
import com.movie.moviecatalog.models.CatalogOutput;
import com.movie.moviecatalog.models.MovieInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieHystrixInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalog",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "100")
    },
    threadPoolKey = "moviePool",
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
    })
    public List<CatalogOutput> getCatalog(@PathVariable("userId") String userId) {

        CatalogItem catalogItem = new CatalogItem(userId,"550","5");
        String movieId = catalogItem.getMovieId();

        /*
        //If movie info list is going to return.
        MovieInfo[] movieInfo = restTemplate.getForObject("http://movie-info-service/movie/"+movieId, MovieInfo[].class);

        return Arrays.asList(movieInfo).stream().map(movie -> {
            return new CatalogOutput(movie.getMovieId(),catalogItem.getRating(),movie.getMovieDesc());
        }).collect(Collectors.toList());*/

        MovieInfo movieInfo = restTemplate.getForObject("http://movie-info-service/movie/"+movieId, MovieInfo.class);

        List<CatalogOutput> catalogOutputs = new ArrayList<>();
        catalogOutputs.add(new CatalogOutput(movieInfo.getMovieId(),catalogItem.getRating(),movieInfo.getMovieDesc()));
        return catalogOutputs;
    }

    public List<CatalogOutput> getFallbackCatalog(@PathVariable("userId") String userId) {
        List<CatalogOutput> catalogOutputs = new ArrayList<>();
        catalogOutputs.add(new CatalogOutput("550","0","Fall Back Method"));
        return catalogOutputs;
    }
}
