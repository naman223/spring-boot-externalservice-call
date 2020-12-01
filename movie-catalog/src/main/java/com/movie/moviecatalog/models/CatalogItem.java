package com.movie.moviecatalog.models;

public class CatalogItem {

    private String userId;
    private String movieId;
    private String rating;

    public CatalogItem(String name, String movieId, String rating) {
        this.movieId = movieId;
        this.userId = name;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
