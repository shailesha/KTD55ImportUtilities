package com.avider.bks.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruchiagarwal on 7/26/18.
 */
public class IsbnDbDto {

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("Overview")
    private String overview;

    @JsonProperty("coverImgUrl")
    private String coverImage;

    @JsonProperty("subtitle")
    private String subTitle;

    @JsonProperty("title")
    private String title;

    @JsonProperty("edition")
    private String edition;

    @JsonProperty("publishDate")
    private String publishDate;

    @JsonProperty("authors")
    private String[] authors;

    @JsonProperty("subjectNames")
    private String[] subjectNames;

    @JsonProperty("isbn_10")
    private String isbn_10;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("numPages")
    private int numPages;


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String[] getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(String[] subjectNames) {
        this.subjectNames = subjectNames;
    }

    public String getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }


}
