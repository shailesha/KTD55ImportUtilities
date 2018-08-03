package com.avider.bks.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by root on 25/7/18.
 */
public class IsbnDataDto {
    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
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

    public String[] getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(String[] subjectNames) {
        this.subjectNames = subjectNames;
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

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getDeweyDecimal() {
        return deweyDecimal;
    }

    public void setDeweyDecimal(String deweyDecimal) {
        this.deweyDecimal = deweyDecimal;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getSynopsys() {
        return synopsys;
    }

    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    @JsonProperty("ISBN")
    private String isbn13;

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

    @JsonProperty("publisher")
    private String publishers;

    @JsonProperty("Overview")
    private String overview;

    @JsonProperty("edition")
    private String edition;


    @JsonProperty("dewey_decimal")
    private String deweyDecimal;

    @JsonProperty("excerpt")
    private String excerpt;

    @JsonProperty("synopsys")
    private String synopsys;

    @JsonProperty("reviews")
    private String[] reviews;

    private String category;

    private String shelfLocation;

    private String libLocation;

    private String bookNum;

    private String language;

    private long titleId;

    private int timesRented;

    private String status;

    private String authorsFromImport;

    public String getAuthorsFromImport() {
        return authorsFromImport;
    }

    public void setAuthorsFromImport(String authorsFromImport) {
        this.authorsFromImport = authorsFromImport;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getLibLocation() {
        return libLocation;
    }

    public void setLibLocation(String libLocation) {
        this.libLocation = libLocation;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getTitleId() {
        return titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }

    public int getTimesRented() {
        return timesRented;
    }

    public void setTimesRented(int timesRented) {
        this.timesRented = timesRented;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
