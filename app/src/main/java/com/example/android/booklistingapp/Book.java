package com.example.android.booklistingapp;

import android.media.Rating;

public class Book {

    private  String title;
    private String author;
    private float rating;
    private float price;

    public Book(String title, String author, float rating, float price) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public float getRating() {
        return rating;
    }

    public float getPrice() {
        return price;
    }
}
