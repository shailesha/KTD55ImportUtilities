package com.avider.bks.utils;

/**
 * Created by ruchiagarwal on 8/1/18.
 */
public class ImportBookDataDto {

    private long titleId;

    private String convertedBookNum;

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    //BK or CR
    private String bookType;

    public String getConvertedBookNum() {
        return convertedBookNum;
    }

    public void setConvertedBookNum(String convertedBookNum) {
        this.convertedBookNum = convertedBookNum;
    }

    private String isbn_10;

    public String getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public long getTitleId() {
        return titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }

    public long getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(long isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBookNum() {
        return bookNum;
    }

    public void setBookNum(long bookNum) {
        this.bookNum = bookNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTimesRented() {
        return timesRented;
    }

    public void setTimesRented(int timesRented) {
        this.timesRented = timesRented;
    }

    private long isbn13;

    private String title;

    private long bookNum;

    private String location;

    private String authors;

    private String category;

    private String status;

    private String language;

    private int timesRented;

    private String libLocation;

    public String getLibLocation() {
        return libLocation;
    }

    public void setLibLocation(String libLocation) {
        this.libLocation = libLocation;
    }
}
