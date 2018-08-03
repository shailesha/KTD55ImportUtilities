package com.avider.bks.utils;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;


/**
 * Created by ruchiagarwal on 7/22/18.
 */
public class BookDataExternalAdaptor {
    private static final String openLibUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" ;
    private static final String isbnDbUrl = "https://api.isbndb.com/book/" ;
    private static final String googleBooksUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private Map<String,IsbnDataDto> isbnDataMap = new HashMap(1000);

    public static final int MAX_GOOGLE_CNT = 1000;
    public static final int MAX_ISBNDB_CNT = 100;

    public int currentGoogleCnt = 0;
    public int currentIsbndbCnt = 0;

    public  IsbnDataDto getIsbnDbData(long isbn) {
        //HashMap<String, String> returnMap = new HashMap<String, String>();

        String isbnDbResult = null;
        IsbnDataDto isbnDataDto = null;
        try {

            currentIsbndbCnt ++;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(isbnDbUrl+isbn);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("X-API-Key", "ec0J1CSbXu3N5b0Zh9eZ5E5KNrFwNtJ2lQAKCrO9");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            isbnDbResult = EntityUtils.toString(response1.getEntity());

            System.out.println("isbnDbResult : " + isbnDbResult);
            //if there wasa valid result
            if(isbnDbResult.length() >50) {
                List chainrSpecJSON = JsonUtils.classpathToList("/isbnDbSpec.json");
                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(isbnDbResult);

                Object transformedOutput = chainr.transform(inputJSON);

                System.out.println(JsonUtils.toJsonString(transformedOutput));

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(JsonUtils.toJsonString(transformedOutput));

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                isbnDataDto = mapper.readValue(parser, IsbnDataDto.class);
            }

//System.out.println(openLibDataDto.getIsbn());



        } catch(Exception ex) {
            throw new RuntimeException(ex);

        }

        return isbnDataDto;

    }
    public  IsbnDataDto getOpenLibData(long isbn) {
        //HashMap<String, String> returnMap = new HashMap<String, String>();

        //String openLibResult = null;
        IsbnDataDto openLibDataDto = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(openLibUrl+isbn+"&format=json&jscmd=data");

            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            String result = EntityUtils.toString(response1.getEntity());
            if(result.length()<10 && isbn > 999999999999L) {
                String isbn10 = new Isbn13Isbn10Converter().convertFromIsbn13(String.valueOf(isbn));
                //System.out.println("data not found for isbn13 = " + isbn + ". Retrieving for isbn10 = " + isbn10);
                httpclient = HttpClients.createDefault();
                httpGet = new HttpGet(openLibUrl+isbn10+"&format=json&jscmd=data");

                response1 = httpclient.execute(httpGet);
                result = EntityUtils.toString(response1.getEntity());
            }

            System.out.println("openlib result : " + result);
           // ObjectMapper mapper = new ObjectMapper()
             //       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            if(result.length() >50) {

                List chainrSpecJSON = JsonUtils.classpathToList("/openLibSpec.json");

                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(result);

                Object transformedOutput = chainr.transform(inputJSON);

                System.out.println(JsonUtils.toJsonString(transformedOutput));

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(JsonUtils.toJsonString(transformedOutput));

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                openLibDataDto = mapper.readValue(parser, IsbnDataDto.class);
            }

//System.out.println(openLibDataDto.getIsbn());


        } catch(Exception ex) {
            throw new RuntimeException(ex);

        }

        return openLibDataDto;




    }

    public  IsbnDataDto getDataFromGoogle(long isbn13) {
        System.out.println("isbn13 : " + isbn13);

        String isbnGoogleResult = null;
        IsbnDataDto isbnDataDto = null;
        try {

            currentGoogleCnt ++;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String uri = googleBooksUrl + "isbn:" + isbn13 + "&key=" + "AIzaSyAcwS8AgUzEgD48HUtOmjsB2wOKdZVPZos";
            HttpGet httpGet = new HttpGet(uri);
            //httpGet.addHeader("Content-Type", "application/json");
            //httpGet.addHeader("X-API-Key", );
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            isbnGoogleResult = EntityUtils.toString(response1.getEntity());
            System.out.println("isbnGoogleResult : " + isbnGoogleResult);



            // ObjectMapper mapper = new ObjectMapper()
            //       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            if(isbnGoogleResult.length() >50) {

                List chainrSpecJSON = JsonUtils.classpathToList("/GoogleSpec.json");

                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(isbnGoogleResult);

                Object transformedOutput = chainr.transform(inputJSON);

                System.out.println(JsonUtils.toJsonString(transformedOutput));

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(JsonUtils.toJsonString(transformedOutput));

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                isbnDataDto = mapper.readValue(parser, IsbnDataDto.class);
            }


        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return isbnDataDto;

    }
    public IsbnDataDto getConsolidatedData(ImportBookDataDto importBookDataDto) {



        IsbnDataDto consolidatedIsbnDataDto = new IsbnDataDto();

        System.out.println(importBookDataDto.getIsbn13());

        if(importBookDataDto.getIsbn13() > 1000000000 ) {  // if the book has a valid isbn

            if (isbnDataMap.get(String.valueOf(importBookDataDto.getIsbn13())) == null) { // if it has not been fetched before

                IsbnDataDto googleBookDto = getDataFromGoogle(importBookDataDto.getIsbn13());
                if(googleBookDto != null ) {
                    System.out.println("google data found for isbn");
                    consolidatedIsbnDataDto = googleBookDto;

                }
                if(consolidatedIsbnDataDto.getPublishDate() == null || consolidatedIsbnDataDto.getPublishers() == null || consolidatedIsbnDataDto.getSubjectNames() == null || consolidatedIsbnDataDto.getEdition() == null) {

                    IsbnDataDto isbnDataDto = getIsbnDbData(importBookDataDto.getIsbn13());
                    if(isbnDataDto == null) {
                        isbnDataDto = new IsbnDataDto();
                    }
                    IsbnDataDto openLibIsbnData = getOpenLibData(importBookDataDto.getIsbn13());
                    if(openLibIsbnData == null) {
                        openLibIsbnData = new IsbnDataDto();
                    }
                    if(consolidatedIsbnDataDto.getPublishDate() == null ) {consolidatedIsbnDataDto.setPublishDate((isbnDataDto.getPublishDate() == null)? openLibIsbnData.getPublishDate(): isbnDataDto.getPublishDate());}
                    if(consolidatedIsbnDataDto.getPublishers() == null ) {consolidatedIsbnDataDto.setPublishers((isbnDataDto.getPublishers() == null)? openLibIsbnData.getPublishers(): isbnDataDto.getPublishers());}
                    if(consolidatedIsbnDataDto.getSubjectNames() == null ) {consolidatedIsbnDataDto.setSubjectNames((isbnDataDto.getSubjectNames() == null)? openLibIsbnData.getSubjectNames(): isbnDataDto.getSubjectNames());}
                    //if(consolidatedIsbnDataDto.get() == null ) {consolidatedIsbnDataDto.setPublishers((isbnDataDto.getPublishers() == null)? openLibIsbnData.getPublishers(): isbnDataDto.getPublishers());}
                    if(consolidatedIsbnDataDto.getNumPages() == 0 ) {consolidatedIsbnDataDto.setNumPages((isbnDataDto.getNumPages() == 0)? openLibIsbnData.getNumPages(): isbnDataDto.getNumPages());}
                    if(consolidatedIsbnDataDto.getCoverImgUrl() == null ) {consolidatedIsbnDataDto.setCoverImgUrl((isbnDataDto.getCoverImgUrl() == null)? openLibIsbnData.getCoverImgUrl(): isbnDataDto.getCoverImgUrl());}

                    if(consolidatedIsbnDataDto.getEdition() == null ) {consolidatedIsbnDataDto.setEdition((isbnDataDto.getEdition() == null)? openLibIsbnData.getEdition(): isbnDataDto.getEdition());}
                    if(consolidatedIsbnDataDto.getTitle() == null ) {consolidatedIsbnDataDto.setTitle((isbnDataDto.getTitle() == null)? openLibIsbnData.getTitle(): isbnDataDto.getTitle());}
                    if(consolidatedIsbnDataDto.getSubtitle() == null ) {consolidatedIsbnDataDto.setSubtitle((isbnDataDto.getSubtitle() == null)? openLibIsbnData.getSubtitle(): isbnDataDto.getSubtitle());}
                    if(consolidatedIsbnDataDto.getAuthors() == null ) {consolidatedIsbnDataDto.setAuthors((isbnDataDto.getAuthors() == null)? openLibIsbnData.getAuthors(): isbnDataDto.getAuthors());}
                    if(consolidatedIsbnDataDto.getIsbn_10() == null ) {consolidatedIsbnDataDto.setIsbn_10((isbnDataDto.getIsbn_10() == null)? openLibIsbnData.getIsbn_10(): isbnDataDto.getIsbn_10());}
                    if(consolidatedIsbnDataDto.getIsbn13() == null ) {consolidatedIsbnDataDto.setIsbn13((isbnDataDto.getIsbn13() == null)? openLibIsbnData.getIsbn13(): isbnDataDto.getIsbn13());}
                    if(consolidatedIsbnDataDto.getOverview() == null ) {consolidatedIsbnDataDto.setOverview((isbnDataDto.getOverview() == null)? openLibIsbnData.getOverview(): isbnDataDto.getOverview());}
                    consolidatedIsbnDataDto.setExcerpt((isbnDataDto.getExcerpt() == null)? openLibIsbnData.getExcerpt(): isbnDataDto.getExcerpt());
                    consolidatedIsbnDataDto.setReviews((isbnDataDto.getReviews() == null)? openLibIsbnData.getReviews(): isbnDataDto.getReviews());
                    consolidatedIsbnDataDto.setOpenlibNotes(openLibIsbnData.getOpenlibNotes());

                    isbnDataMap.put(String.valueOf(importBookDataDto.getIsbn13()),consolidatedIsbnDataDto);

                }
            } else {
                consolidatedIsbnDataDto = isbnDataMap.get(String.valueOf(importBookDataDto.getIsbn13()));
            }


        }
        if(consolidatedIsbnDataDto.getTitle() == null || consolidatedIsbnDataDto.getTitle().equals("")) {
            consolidatedIsbnDataDto.setTitle(importBookDataDto.getTitle());
        }
        if(consolidatedIsbnDataDto.getAuthors() == null || consolidatedIsbnDataDto.getAuthors().length ==0) {
            String[] authors = new String[1];
            authors[0] = importBookDataDto.getAuthors();
            consolidatedIsbnDataDto.setAuthors(authors);
        }

        if(importBookDataDto.getIsbn13() > 1000000000000L) {
            consolidatedIsbnDataDto.setIsbn13(String.valueOf(importBookDataDto.getIsbn13()));
        }
        if(consolidatedIsbnDataDto.getIsbn_10() == null ) {consolidatedIsbnDataDto.setIsbn_10(Isbn13Isbn10Converter.convertFromIsbn13(consolidatedIsbnDataDto.getIsbn13()));}
        consolidatedIsbnDataDto.setTitleId(importBookDataDto.getTitleId());
        consolidatedIsbnDataDto.setBookNum(Isbn13Isbn10Converter.prepareBookNumber(importBookDataDto.getBookNum()));


        consolidatedIsbnDataDto.setCategory(importBookDataDto.getCategory());

       // consolidatedIsbnDataDto.setAuthorsFromImport(importBookDataDto.getAuthors());
        consolidatedIsbnDataDto.setTimesRented(importBookDataDto.getTimesRented());
        consolidatedIsbnDataDto.setLibLocation(importBookDataDto.getLibLocation());
        consolidatedIsbnDataDto.setShelfLocation(importBookDataDto.getLocation());
        consolidatedIsbnDataDto.setStatus(importBookDataDto.getStatus());
        if(consolidatedIsbnDataDto.getStatus().equals("T")) {
            consolidatedIsbnDataDto.setStatus("A");
        }
        consolidatedIsbnDataDto.setLanguage(importBookDataDto.getLanguage());
        if(consolidatedIsbnDataDto.getSubtitle() == null) { consolidatedIsbnDataDto.setSubtitle(consolidatedIsbnDataDto.getTitle());}




        return consolidatedIsbnDataDto;




    }

    /*public static void main(String[] args) {

        String isbn = "9781451648539";
        String result = BookDataExternalAdaptor.getIsbnDbData(isbn);
        System.out.println(result);

    }*/

    public static void main(String[] args) {
        //String isbn = "9201558025";
        //file format assumed to be Title	Title ID	ISBN	Booknumber	Category	Location	Author	Shelf Location	Times Rented	Status	Language
    //    String isbnListFilePath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration.csv";

      //  String outputFilePath = "/Users/ruchiagarwal/avidreaders/IsbnData.csv";

        String isbnListFilePath = "/home/shailesh/avidreader/catalog_report_for_migration.csv";

        String outputFilePath = "/home/shailesh/avidreader/IsbnData.csv";

        BookDataExternalAdaptor bookDataExternalAdaptor = new BookDataExternalAdaptor();
        BookListRetriever isbnListRetriever = new BookListRetriever();
        Set<ImportBookDataDto> isbnSet = new BookListRetriever().retrieveBooksInLibrary(isbnListFilePath);
        IsbnFileWriter isbnFileWriter = new IsbnFileWriter();
        isbnFileWriter.initialize(outputFilePath);
        Iterator<ImportBookDataDto> iter = isbnSet.iterator();
int i=0;
        while(iter.hasNext()
                && bookDataExternalAdaptor.currentIsbndbCnt < bookDataExternalAdaptor.MAX_ISBNDB_CNT
                && bookDataExternalAdaptor.currentGoogleCnt < bookDataExternalAdaptor.MAX_GOOGLE_CNT) {

i++;
            ImportBookDataDto importBookDataDto = iter.next();

            IsbnDataDto isbnDataDto = bookDataExternalAdaptor.getConsolidatedData(importBookDataDto);

            //bookDataExternalAdaptor.getDataFromGoogle(importBookDataDto.getIsbn13());
            //if (isbnDataDto.getIsbn13() != null) {
                //System.out.println("writing valid isbn data in file" + openLibData.getIsbn());
            isbnFileWriter.writeIsbnData(isbnDataDto);
            //}
        }
        isbnFileWriter.closeWriting();





//        System.out.println(openLibData);

    }



}
