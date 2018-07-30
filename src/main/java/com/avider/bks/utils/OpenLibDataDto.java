package com.avider.bks.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class OpenLibDataDto {
    @JsonProperty("ISBN")
    private String isbn;

    @JsonProperty("isbn_10")
    private String isbn_10;

    @JsonProperty("lccn")
    private String lccn;

    @JsonProperty("libraryThingId")
    private String libraryThingId;

    @JsonProperty("openlibUrl")
    private String openlibUrl;

    @JsonProperty("openlibNotes")
    private String openlibNotes;

    @JsonProperty("numPages")
    private int numPages;

    @JsonProperty("coverImgUrl")
    private String coverImgUrl;

    @JsonProperty("subjectNames")
    private String[] subjectNames;

    @JsonProperty("publishDate")
    private String publishDate;

    @JsonProperty("authors")
    private String[] authors;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("goodreadsId")
    private String goodreadsId;

    @JsonProperty("publishers")
    private String[] publishers;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public String getLccn() {
        return lccn;
    }

    public void setLccn(String lccn) {
        this.lccn = lccn;
    }

    public String getLibraryThingId() {
        return libraryThingId;
    }

    public void setLibraryThingId(String libraryThingId) {
        this.libraryThingId = libraryThingId;
    }

    public String getOpenlibUrl() {
        return openlibUrl;
    }

    public void setOpenlibUrl(String openlibUrl) {
        this.openlibUrl = openlibUrl;
    }

    public String getOpenlibNotes() {
        return openlibNotes;
    }

    public void setOpenlibNotes(String openlibNotes) {
        this.openlibNotes = openlibNotes;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }



    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getGoodreadsId() {
        return goodreadsId;
    }

    public void setGoodreadsId(String goodreadsId) {
        this.goodreadsId = goodreadsId;
    }

    public String[] getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(String[] subjectNames) {
        this.subjectNames = subjectNames;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }
}
