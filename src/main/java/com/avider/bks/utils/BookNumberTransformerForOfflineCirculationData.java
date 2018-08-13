package com.avider.bks.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ruchiagarwal on 8/7/18.
 */
public class BookNumberTransformerForOfflineCirculationData {



    public static void main(String[] args) {

        String circulationInputFilePath = "/Users/ruchiagarwal/avidreaders/Workbook24.csv";
        String circulationOutputFilePath = "/Users/ruchiagarwal/avidreaders/circulation with booknum.koc";

        IsbnFileWriter fileWriter = null;
        File file = null;
        BufferedReader fileReader = null;
        try {

            file = new File(circulationInputFilePath);
            fileReader = new BufferedReader(new FileReader(file));
            fileWriter = new IsbnFileWriter(circulationOutputFilePath);


            String line = null;

            String recievedLine = "";
            //fileReader.readLine();

            while ((recievedLine = fileReader.readLine()) != null) {
                String[] circulationLine = recievedLine.split(",");
                //if(circulationLine[2] != null && !circulationLine[2].equals("")) {
                    //String bookNum = Isbn13Isbn10Converter.prepareBookNumber(Long.parseLong(circulationLine[2]));
                    //circulationLine[2] = bookNum;
                //}
                fileWriter.writeCirculationData(circulationLine);

            }
            fileReader.close();


        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {

            fileWriter.closeWriting();
        }


    }
}
