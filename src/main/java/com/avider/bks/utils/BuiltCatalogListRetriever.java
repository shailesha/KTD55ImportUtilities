package com.avider.bks.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class BuiltCatalogListRetriever {
    BufferedReader fileReader = null;
    File file = null;


    ////book number, isbn13, isbn_10, titleid, title, subtitle,language, edition, Dewey_decimal, Authors, Publishers, location, shelf location, Overview,Synopsys, excerpt, Notes, Publish Date,category, subjects, Times rented, Status, Num Pages,Reviews, cover image, Openlib URL, good reads id, Lccn, library thing id
    public Set<String> getAllBuiltCatalogBookNumbers(String filePath)
    {
        Set<String> bookNumSet = new HashSet(1000);
        try {
            file = new File(filePath);
            fileReader = new BufferedReader(new FileReader(file));
            //String line = null;

            String recievedLine = "";

            while ((recievedLine = fileReader.readLine()) != null) {

                String[] bookContentLine = recievedLine.split(",");
                if(bookContentLine[0].contains("\"B")) {
                    bookNumSet.add(bookContentLine[0]);
                }

            }
            fileReader.close();

        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return bookNumSet;

    }

    public Set<String> loadIsbnSetFromFile(String filePath) {

        Set<String> isbnSet = new HashSet(1000);
        try {
            file = new File(filePath);
            fileReader = new BufferedReader(new FileReader(file));
            //String line = null;

            String recievedLine = "";

            while ((recievedLine = fileReader.readLine()) != null) {

                String[] bookContentLine = recievedLine.split(",");
                //if(bookContentLine[0].contains("\"B")) {
                isbnSet.add(bookContentLine[0]);
                //}

            }
            fileReader.close();

        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return isbnSet;

    }
}
