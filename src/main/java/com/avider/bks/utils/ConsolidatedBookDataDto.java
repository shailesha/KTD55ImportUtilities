package com.avider.bks.utils;

/**
 * Created by ruchiagarwal on 7/26/18.
 */
public class ConsolidatedBookDataDto {

    private String isbn;
    private String isbn_10;
    private String lccn;
    private String libraryThingId;
    private String openlibUrl;
    private String openlibNotes;
    private int numPages;
    private String coverImgUrl;
    private String[] subjectNames;
    private String publishDate;
    private String[] authors;
    private String title;
    private String subtitle;
    private String goodreadsId;
    private String[] publishers;
    private String overview;
    private String edition;

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

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
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
}
