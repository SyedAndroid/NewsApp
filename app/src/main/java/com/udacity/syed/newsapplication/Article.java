package com.udacity.syed.newsapplication;

/**
 * Created by shoiab on 2017-09-12.
 */

public class Article {
    private String author;
    private String title;
    private String description;
    private String articleURL;
    private String imgURL;
    private String publishedTime;

    public Article(String author, String title, String description, String articleURL, String imgURL, String publishedTime) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleURL = articleURL;
        this.imgURL = imgURL;
        this.publishedTime = publishedTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getPublishedTime() {
        return publishedTime;
    }
}
