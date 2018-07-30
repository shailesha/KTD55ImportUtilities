package com.avider.bks.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 27/7/18.
 */
public class BookAndCatalogMerger {
    File catalogFile = null;

    public BookAndCatalogMerger() {

    }


    public Map loadCatalog(String catalogFileNameWithPath) {
        if(catalogFileNameWithPath != null) {
            catalogFile = new File(catalogFileNameWithPath);
        }
        Map catalogMap = new HashMap(1000);
        if(catalogFile == null) {
            throw new RuntimeException("catalog file not initialized");
        }
        try {

            BufferedReader fileReader = new BufferedReader(new FileReader(catalogFile));
            String line = null;
            while ((line = fileReader.readLine()) != null) {

                String[] bookContentLine = line.split(",");

                if(bookContentLine.length>5) {
                    catalogMap.put(bookContentLine[0], line);
                }
            }
            fileReader.close();
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return catalogMap;





    }

    public void mergeCatalogWithBooks(String booksDataFilePath, Map catalogMap, String outputFilePath) {
        try {
            File readfile = new File(booksDataFilePath);
            BufferedReader fileReader = new BufferedReader(new FileReader(readfile));
            File writefile = new File(outputFilePath);
            FileWriter writer = new FileWriter(writefile,true);
            String line = null;
            while ((line = fileReader.readLine()) != null) {

                String[] bookContentLine = line.split(",");

                if(bookContentLine.length > 5) {
                    line = line + "," + catalogMap.get(bookContentLine[2]);
                    writer.append(line + "\n");
                }
            }
            fileReader.close();
            writer.flush();
            writer.close();
        }catch(IOException ex) {

            throw new RuntimeException(ex);
        }

    }


    public static void main(String[] args) {


        String catalogFilePath = "/Users/ruchiagarwal/avidreaders/isbnData.csv";
        String booksExportFilePath = "/Users/ruchiagarwal/avidreaders/book_inventory_circulation_for_migration.csv";
        String outputFilePath = "/Users/ruchiagarwal/avidreaders/booksAndIsbnData.csv";

        BookAndCatalogMerger bookAndCatalogMerger = new BookAndCatalogMerger();
        Map catalog = bookAndCatalogMerger.loadCatalog(catalogFilePath);
        bookAndCatalogMerger.mergeCatalogWithBooks(booksExportFilePath,catalog,outputFilePath);
    }


}
