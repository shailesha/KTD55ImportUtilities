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

    private String mailtoshailesh26_GoogleApiKey = "AIzaSyCMMh_ojN8AwiNa7M4KpJyrhZVWCgjxetk";
    private String ruchiavidreaders_GoogleApiKey = "AIzaSyAcwS8AgUzEgD48HUtOmjsB2wOKdZVPZos";

    public static final int MAX_GOOGLE_CNT = 1000;
    public static final int MAX_ISBNDB_CNT = 5000;

    public int currentGoogleCnt = 0;
    public int currentIsbndbCnt = 0;

    public Set<String> noGoogleSet = new HashSet();
    public Set<String> noIsbnDbSet = new HashSet();

    public  IsbnDataDto getIsbnDbData(long isbn) {
        //HashMap<String, String> returnMap = new HashMap<String, String>();

        String isbnDbResult = null;
        IsbnDataDto isbnDataDto = null;
        if(noIsbnDbSet.contains(String.valueOf(isbn))) {
            return null;
        }
        try {

            currentIsbndbCnt ++;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(isbnDbUrl+isbn);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("X-API-Key", "ec0J1CSbXu3N5b0Zh9eZ5E5KNrFwNtJ2lQAKCrO9");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            isbnDbResult = EntityUtils.toString(response1.getEntity());

            //System.out.println("isbnDbResult : " + isbnDbResult);
            //if there wasa valid result
            if(isbnDbResult.length() >50) {
                List chainrSpecJSON = JsonUtils.classpathToList("/isbnDbSpec.json");
                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(isbnDbResult);

                Object transformedOutput = chainr.transform(inputJSON);

                //System.out.println(JsonUtils.toJsonString(transformedOutput));

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(JsonUtils.toJsonString(transformedOutput));

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                isbnDataDto = mapper.readValue(parser, IsbnDataDto.class);
            } else {
                noIsbnDbSet.add(String.valueOf(isbn));
            }

//System.out.println(openLibDataDto.getIsbn());



        } catch(Exception ex) {

            ex.printStackTrace();

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

          //  System.out.println("openlib result : " + result);
           // ObjectMapper mapper = new ObjectMapper()
             //       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            if(result.length() >50) {

                List chainrSpecJSON = JsonUtils.classpathToList("/openLibSpec.json");

                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(result);

                Object transformedOutput = chainr.transform(inputJSON);

                //System.out.println(JsonUtils.toJsonString(transformedOutput));

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
        //System.out.println("isbn13 : " + isbn13);

        String isbnGoogleResult = null;
        IsbnDataDto isbnDataDto = null;
        if(noGoogleSet.contains(String.valueOf(isbn13))) {
            return null;
        }
        try {

            currentGoogleCnt ++;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String uri = googleBooksUrl + "isbn:" + isbn13 + "&key=" + ruchiavidreaders_GoogleApiKey;
            HttpGet httpGet = new HttpGet(uri);
            //httpGet.addHeader("Content-Type", "application/json");
            //httpGet.addHeader("X-API-Key", );
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            isbnGoogleResult = EntityUtils.toString(response1.getEntity());
           // System.out.println("isbnGoogleResult : " + isbnGoogleResult);



            // ObjectMapper mapper = new ObjectMapper()
            //       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            if(isbnGoogleResult.length() >50) {

                List chainrSpecJSON = JsonUtils.classpathToList("/GoogleSpec.json");

                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

                Object inputJSON = JsonUtils.jsonToObject(isbnGoogleResult);

                Object transformedOutput = chainr.transform(inputJSON);

                //System.out.println(JsonUtils.toJsonString(transformedOutput));

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(JsonUtils.toJsonString(transformedOutput));

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                isbnDataDto = mapper.readValue(parser, IsbnDataDto.class);
            } else {

                noGoogleSet.add(String.valueOf(isbn13));


            }


        } catch(Exception ex) {

            ex.printStackTrace();

        }
        return isbnDataDto;

    }
    public IsbnDataDto getConsolidatedData(ImportBookDataDto importBookDataDto) {


        IsbnDataDto consolidatedIsbnDataDto = new IsbnDataDto();

        System.out.println("isbn being processed : " + importBookDataDto.getIsbn13());

        if (importBookDataDto.getIsbn13() > 1000000) {  // if the book has a valid isbn

            if (isbnDataMap.get(String.valueOf(importBookDataDto.getIsbn13())) == null) { // if it has not been fetched before

                if (!noGoogleSet.contains(String.valueOf(importBookDataDto.getIsbn13()))) {// if it hasnt been tried before in google

                    IsbnDataDto googleBookDto = getDataFromGoogle(importBookDataDto.getIsbn13());
                    if (googleBookDto != null) {
                        //System.out.println("google data found for isbn");
                        consolidatedIsbnDataDto = googleBookDto;
                        consolidatedIsbnDataDto.setGoogleDataComments("Google found data for this ISBN");


                    } else if (noGoogleSet.contains(String.valueOf(importBookDataDto.getIsbn13()))) {
                        {
                            consolidatedIsbnDataDto.setGoogleDataComments("Google does not hold data for this ISBN");
                        }
                    }
                } else {
                    consolidatedIsbnDataDto.setGoogleDataComments("Google does not hold data for this ISBN");
                }


                // if data was not found in google or few important attributes were not found in google
                if (consolidatedIsbnDataDto.getPublishDate() == null || consolidatedIsbnDataDto.getPublishers() == null
                        || consolidatedIsbnDataDto.getSubjectNames() == null || consolidatedIsbnDataDto.getEdition() == null || consolidatedIsbnDataDto.getNumPages() == 0) {

                    IsbnDataDto IsbnDbDto = null;
                    IsbnDataDto openLibIsbnData = getOpenLibData(importBookDataDto.getIsbn13());
                    if (noIsbnDbSet.contains(String.valueOf(importBookDataDto.getIsbn13()))) {// if isbndb does not contain data for this isbn , then dont try again and also set the comments
                        consolidatedIsbnDataDto.setIsbndbComments("IsbnDB does not hold data for this ISBN");
                    } else if (currentIsbndbCnt >= MAX_ISBNDB_CNT) {  // data not found in google, openlib and isbndb qiota is expired for today, then we should discard the entry
                        consolidatedIsbnDataDto.setIsbndbComments("ISBNDB quota expired and hence not tried for this ISBN");
                        //return consolidatedIsbnDataDto;
                    } else if (openLibIsbnData == null || openLibIsbnData.getPublishDate() == null || openLibIsbnData.getPublishers() == null
                            || openLibIsbnData.getSubjectNames() == null || openLibIsbnData.getNumPages() == 0) {
                        IsbnDbDto = getIsbnDbData(importBookDataDto.getIsbn13());
                        if (noIsbnDbSet.contains(String.valueOf(importBookDataDto.getIsbn13()))) { // after calling isbndb we might realize that isbndb does not contain this data
                            consolidatedIsbnDataDto.setIsbndbComments("IsbnDB does not hold data for this ISBN");
                        } else if (IsbnDbDto != null) {
                            consolidatedIsbnDataDto.setIsbndbComments("IsbnDB found data for this ISBN");
                        }

                    }


                    // null return will be the case only when the entry needs to be discarded for a retry tomorrow and hence should not be written into output
                    if (openLibIsbnData == null) {
                        openLibIsbnData = new IsbnDataDto();
                        consolidatedIsbnDataDto.setOpenLibComments("Openlib does not hold data for this ISBN"); // assuming openlib was always hit
                    } else {
                        consolidatedIsbnDataDto.setOpenLibComments("Openlib found data for this ISBN");
                    }
                    if (IsbnDbDto == null) {
                        IsbnDbDto = new IsbnDataDto();
                    }
                    if (consolidatedIsbnDataDto.getPublishDate() == null) {
                        consolidatedIsbnDataDto.setPublishDate((IsbnDbDto.getPublishDate() == null) ? openLibIsbnData.getPublishDate() : IsbnDbDto.getPublishDate());
                    }
                    if (consolidatedIsbnDataDto.getPublishers() == null) {
                        consolidatedIsbnDataDto.setPublishers((IsbnDbDto.getPublishers() == null) ? openLibIsbnData.getPublishers() : IsbnDbDto.getPublishers());
                    }
                    if (consolidatedIsbnDataDto.getSubjectNames() == null) {
                        consolidatedIsbnDataDto.setSubjectNames((IsbnDbDto.getSubjectNames() == null) ? openLibIsbnData.getSubjectNames() : IsbnDbDto.getSubjectNames());
                    }
                    //if(consolidatedIsbnDataDto.get() == null ) {consolidatedIsbnDataDto.setPublishers((isbnDataDto.getPublishers() == null)? openLibIsbnData.getPublishers(): isbnDataDto.getPublishers());}
                    if (consolidatedIsbnDataDto.getNumPages() == 0) {
                        consolidatedIsbnDataDto.setNumPages((IsbnDbDto.getNumPages() == 0) ? openLibIsbnData.getNumPages() : IsbnDbDto.getNumPages());
                    }
                    if (consolidatedIsbnDataDto.getCoverImgUrl() == null) {
                        consolidatedIsbnDataDto.setCoverImgUrl((IsbnDbDto.getCoverImgUrl() == null) ? openLibIsbnData.getCoverImgUrl() : IsbnDbDto.getCoverImgUrl());
                    }

                    if (consolidatedIsbnDataDto.getEdition() == null) {
                        consolidatedIsbnDataDto.setEdition((IsbnDbDto.getEdition() == null) ? openLibIsbnData.getEdition() : IsbnDbDto.getEdition());
                    }
                    if (consolidatedIsbnDataDto.getTitle() == null) {
                        consolidatedIsbnDataDto.setTitle((IsbnDbDto.getTitle() == null) ? openLibIsbnData.getTitle() : IsbnDbDto.getTitle());
                    }
                    if (consolidatedIsbnDataDto.getSubtitle() == null) {
                        consolidatedIsbnDataDto.setSubtitle((IsbnDbDto.getSubtitle() == null) ? openLibIsbnData.getSubtitle() : IsbnDbDto.getSubtitle());
                    }
                    if (consolidatedIsbnDataDto.getAuthors() == null) {
                        consolidatedIsbnDataDto.setAuthors((IsbnDbDto.getAuthors() == null) ? openLibIsbnData.getAuthors() : IsbnDbDto.getAuthors());
                    }
                    if (consolidatedIsbnDataDto.getIsbn_10() == null) {
                        consolidatedIsbnDataDto.setIsbn_10((IsbnDbDto.getIsbn_10() == null) ? openLibIsbnData.getIsbn_10() : IsbnDbDto.getIsbn_10());
                    }
                    if (consolidatedIsbnDataDto.getIsbn13() == null) {
                        consolidatedIsbnDataDto.setIsbn13((IsbnDbDto.getIsbn13() == null) ? openLibIsbnData.getIsbn13() : IsbnDbDto.getIsbn13());
                    }
                    if (consolidatedIsbnDataDto.getOverview() == null) {
                        consolidatedIsbnDataDto.setOverview((IsbnDbDto.getOverview() == null) ? openLibIsbnData.getOverview() : IsbnDbDto.getOverview());
                    }
                    consolidatedIsbnDataDto.setExcerpt((IsbnDbDto.getExcerpt() == null) ? openLibIsbnData.getExcerpt() : IsbnDbDto.getExcerpt());
                    consolidatedIsbnDataDto.setReviews((IsbnDbDto.getReviews() == null) ? openLibIsbnData.getReviews() : IsbnDbDto.getReviews());
                    consolidatedIsbnDataDto.setOpenlibNotes(openLibIsbnData.getOpenlibNotes());

                    isbnDataMap.put(String.valueOf(importBookDataDto.getIsbn13()), consolidatedIsbnDataDto);

                }
            } else {
                consolidatedIsbnDataDto = isbnDataMap.get(String.valueOf(importBookDataDto.getIsbn13()));
            }


        }
        if (consolidatedIsbnDataDto.getTitle() == null || consolidatedIsbnDataDto.getTitle().equals("")) {
            consolidatedIsbnDataDto.setTitle(importBookDataDto.getTitle());
        }
        if (consolidatedIsbnDataDto.getAuthors() == null || consolidatedIsbnDataDto.getAuthors().length == 0) {
            String[] authors = new String[1];
            authors[0] = importBookDataDto.getAuthors();
            consolidatedIsbnDataDto.setAuthors(authors);
        }

        if (importBookDataDto.getIsbn13() > 1000000000000L) {
            consolidatedIsbnDataDto.setIsbn13(String.valueOf(importBookDataDto.getIsbn13()));
        }
        if (consolidatedIsbnDataDto.getIsbn_10() == null) {
            consolidatedIsbnDataDto.setIsbn_10(Isbn13Isbn10Converter.convertFromIsbn13(consolidatedIsbnDataDto.getIsbn13()));
        }
        consolidatedIsbnDataDto.setTitleId(importBookDataDto.getTitleId());
        consolidatedIsbnDataDto.setBookNum(Isbn13Isbn10Converter.prepareBookNumber(importBookDataDto.getBookNum()));


        consolidatedIsbnDataDto.setCategory(importBookDataDto.getCategory());

        // consolidatedIsbnDataDto.setAuthorsFromImport(importBookDataDto.getAuthors());
        consolidatedIsbnDataDto.setTimesRented(importBookDataDto.getTimesRented());
        consolidatedIsbnDataDto.setLibLocation(importBookDataDto.getLibLocation());
        consolidatedIsbnDataDto.setShelfLocation(importBookDataDto.getLocation());
        consolidatedIsbnDataDto.setStatus(importBookDataDto.getStatus());
        if (consolidatedIsbnDataDto.getStatus().equals("T")) {
            consolidatedIsbnDataDto.setStatus("A");
        }
        consolidatedIsbnDataDto.setLanguage(importBookDataDto.getLanguage());
        if (consolidatedIsbnDataDto.getSubtitle() == null) {
            consolidatedIsbnDataDto.setSubtitle(consolidatedIsbnDataDto.getTitle());
        }


        return consolidatedIsbnDataDto;


    }


    /*public static void main(String[] args) {

        String isbn = "9781451648539";
        String result = BookDataExternalAdaptor.getIsbnDbData(isbn);
        System.out.println(result);

    }*/

    public static void main(String[] args) {
        //String isbn = "9201558025";
        Set<String> noGoogleSetBeforeIteration = null;
        Set<String> noIsbndbSetBeforeIteration = null;


        //file format assumed to be Title	Title ID	ISBN	Booknumber	Category	Location	Author	Shelf Location	Times Rented	Status	Language
        String isbnListFilePath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration.csv";

        String outputFilePath = "/Users/ruchiagarwal/avidreaders/IsbnData.csv";

        String noGoogleSetPath = "/Users/ruchiagarwal/avidreaders/noGoogleSet.csv";

        String noIsbnDbSetPath = "/Users/ruchiagarwal/avidreaders/noIsbndbSet.csv";

       // String isbnListFilePath = "/home/shailesh/avidreader/catalog_report_for_migration.csv";

        //String outputFilePath = "/home/shailesh/avidreader/IsbnData.csv";

       // String noGoogleSetPath = "/home/shailesh/avidreader/noGoogleSet.csv";

        //String noIsbnDbSetPath = "/home/shailesh/avidreader/noIsbnDbSet.csv";

        BookDataExternalAdaptor bookDataExternalAdaptor = new BookDataExternalAdaptor();
        IsbnFileWriter isbnFileWriter = new IsbnFileWriter();
        isbnFileWriter.initialize(outputFilePath);

        IsbnFileWriter noGoogleIsbnFileWriter = new IsbnFileWriter();
        noGoogleIsbnFileWriter.initialize(noGoogleSetPath);

        IsbnFileWriter noIsbndbIsbnFileWriter = new IsbnFileWriter();
        noIsbndbIsbnFileWriter.initialize(noIsbnDbSetPath);

        bookDataExternalAdaptor.currentGoogleCnt = 320;
        bookDataExternalAdaptor.currentIsbndbCnt = 0;


        Set<String> alreadyCatalogedBookNums = new BuiltCatalogListRetriever().getAllBuiltCatalogBookNumbers(outputFilePath);




        bookDataExternalAdaptor.noGoogleSet = new BuiltCatalogListRetriever().loadIsbnSetFromFile(noGoogleSetPath);

        bookDataExternalAdaptor.noIsbnDbSet = new BuiltCatalogListRetriever().loadIsbnSetFromFile(noIsbnDbSetPath);
        List<ImportBookDataDto> bookImportDtoList = new BookListRetriever().retrieveBooksInLibrary(isbnListFilePath);


         Iterator<ImportBookDataDto> iter = bookImportDtoList.iterator();
        int i=0;
        ImportBookDataDto importBookDataDto = null;
        while(iter.hasNext() //&& i<1000
               // && bookDataExternalAdaptor.currentIsbndbCnt < bookDataExternalAdaptor.MAX_ISBNDB_CNT
                && bookDataExternalAdaptor.currentGoogleCnt < bookDataExternalAdaptor.MAX_GOOGLE_CNT) {

            i++;
            noGoogleSetBeforeIteration = new HashSet<>(bookDataExternalAdaptor.noGoogleSet);
            noIsbndbSetBeforeIteration = new HashSet<>(bookDataExternalAdaptor.noIsbnDbSet);
            importBookDataDto = iter.next();
            if(!alreadyCatalogedBookNums.contains("\""+Isbn13Isbn10Converter.prepareBookNumber(importBookDataDto.getBookNum())+ "\"") ) {
                IsbnDataDto isbnDataDto = bookDataExternalAdaptor.getConsolidatedData(importBookDataDto);
                //if(isbnDataDto != null) {// write to output if we dont want to retry this isbn later (due t expiry of daily api quota)
                    isbnFileWriter.writeIsbnData(isbnDataDto);
               // }
            }
            if(bookDataExternalAdaptor.noGoogleSet.size() > noGoogleSetBeforeIteration.size()) {
                bookDataExternalAdaptor.noGoogleSet.removeAll(noGoogleSetBeforeIteration);
                noGoogleIsbnFileWriter.writeIsbnOnlyData(bookDataExternalAdaptor.noGoogleSet);
                bookDataExternalAdaptor.noGoogleSet.addAll(noGoogleSetBeforeIteration);
            }
            if(bookDataExternalAdaptor.noIsbnDbSet.size() > noIsbndbSetBeforeIteration.size()) {
                bookDataExternalAdaptor.noIsbnDbSet.removeAll(noIsbndbSetBeforeIteration);

                noIsbndbIsbnFileWriter.writeIsbnOnlyData(bookDataExternalAdaptor.noIsbnDbSet);

                bookDataExternalAdaptor.noIsbnDbSet.addAll(noIsbndbSetBeforeIteration);

            }


            System.out.println("record number being processed :" + i);

        }
        System.out.println("Number of records processed = " + i);
        System.out.println("Last Record processed = " + importBookDataDto.getIsbn13());

        noGoogleIsbnFileWriter.closeWriting();
        noIsbndbIsbnFileWriter.closeWriting();
        isbnFileWriter.closeWriting();

    }



}
