package com.movie.moviecatalog.models;

public class CatalogOutput {

    private String movieId;
    private String rating;
    private String movieDesc;

    public CatalogOutput(String movieId, String rating, String movieDesc) {
        this.movieId = movieId;
        this.rating = rating;
        this.movieDesc = movieDesc;
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

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }
}
