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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by ruchiagarwal on 7/22/18.
 */
public class BookDataExternalAdaptor {
    private static final String openLibUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" ;
    private static final String isbnDbUrl = "https://api.isbndb.com/book/" ;

    public static String getIsbnDbData(String isbn) {
        HashMap<String, String> returnMap = new HashMap<String, String>();

        String isbnDbResult = null;
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(isbnDbUrl+isbn);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("X-API-Key", "ec0J1CSbXu3N5b0Zh9eZ5E5KNrFwNtJ2lQAKCrO9");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            isbnDbResult = EntityUtils.toString(response1.getEntity());

        } catch(Exception ex) {
            throw new RuntimeException(ex);

        }

        return isbnDbResult;

    }
    public static OpenLibDataDto getOpenLibData(String isbn) {
        HashMap<String, String> returnMap = new HashMap<String, String>();

        String openLibResult = null;
        OpenLibDataDto openLibDataDto = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(openLibUrl+isbn+"&format=json&jscmd=data");

            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            String result = EntityUtils.toString(response1.getEntity());
            if(result.length()<10 && isbn.length()==13) {
                String isbn10 = new Isbn13Isbn10Converter().convertFromIsbn13(isbn);
                //System.out.println("data not found for isbn13 = " + isbn + ". Retrieving for isbn10 = " + isbn10);
                httpclient = HttpClients.createDefault();
                httpGet = new HttpGet(openLibUrl+isbn10+"&format=json&jscmd=data");

                response1 = httpclient.execute(httpGet);
                result = EntityUtils.toString(response1.getEntity());
            }

           // ObjectMapper mapper = new ObjectMapper()
             //       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List chainrSpecJSON = JsonUtils.classpathToList("/openLibSpec.json");
            Chainr chainr = Chainr.fromSpec( chainrSpecJSON );

            Object inputJSON = JsonUtils.jsonToObject(result);

            Object transformedOutput = chainr.transform( inputJSON );

System.out.println(JsonUtils.toJsonString(transformedOutput));

            JsonFactory factory = new JsonFactory();
            JsonParser  parser  = factory.createParser(JsonUtils.toJsonString(transformedOutput));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            openLibDataDto = mapper.readValue(parser, OpenLibDataDto.class);

//System.out.println(openLibDataDto.getIsbn());


        } catch(Exception ex) {
            throw new RuntimeException(ex);

        }

        return openLibDataDto;




    }
    public static String getConsolidatedData(String isbn) {
        HashMap<String, String> returnMap = new HashMap<String, String>();
        OpenLibDataDto openLibResult = getOpenLibData(isbn);
        String isbnDbResult = getIsbnDbData(isbn);


        return isbnDbResult;




    }

    /*public static void main(String[] args) {

        String isbn = "9781451648539";
        String result = BookDataExternalAdaptor.getIsbnDbData(isbn);
        System.out.println(result);

    }*/

    public static void main(String[] args) {
        //String isbn = "9201558025";
        //file format assumed to be Title	Title ID	ISBN	Booknumber	Category	Location	Author	Shelf Location	Times Rented	Status	Language
        String isbnListFilePath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration.csv";

        String outputFilePath = "/Users/ruchiagarwal/avidreaders/IsbnData.csv";

        IsbnListRetriever isbnListRetriever = new IsbnListRetriever();
        Set<String> isbnSet = isbnListRetriever.retrieveIsbnsInLibrary(isbnListFilePath);
        IsbnFileWriter isbnFileWriter = new IsbnFileWriter();
        isbnFileWriter.initialize(outputFilePath);
        Iterator<String> iter = isbnSet.iterator();
int i=0;
        while(iter.hasNext() && i <= 50) {

i++;
            String isbn = iter.next();
            OpenLibDataDto openLibData = BookDataExternalAdaptor.getOpenLibData(isbn);

            if (openLibData != null) {
                //System.out.println("writing valid isbn data in file" + openLibData.getIsbn());
                //isbnFileWriter.writeIsbnData(openLibData);
            } else {
                //System.out.println("writing missing data in file" + isbn);
                isbnFileWriter.writeMissingData(isbn);
            }
        }
        isbnFileWriter.closeWriting();





//        System.out.println(openLibData);

    }



}
