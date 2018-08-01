package com.avider.bks.utils;

import java.io.*;
import java.util.Map;

/**
 * Created by ruchiagarwal on 7/24/18.
 */
public class Isbn13Isbn10Converter {

    public String convertFromIsbn13(String isbn13) {

        System.out.println(isbn13);
        char[] isbn13Chars = isbn13.toCharArray();
        int[] isbn13Ints = new int[13];
        int[] isbn10Ints = new int[10];
        char[] isbn10Chars = new char[10];
        try {
            int weightedSum = 0;

            //taking digits 4 to 12 as they will be part of 10 digit number as well
            for (int i = 3, j = 10; i <= 11; i++, j--) {
                isbn13Ints[i] = Integer.parseInt(String.valueOf(isbn13Chars[i]));
                isbn10Ints[i - 3] = isbn13Ints[i];
                System.out.println((i-3) + "index = " + isbn10Ints[i - 3]);
                weightedSum = weightedSum + isbn13Ints[i] * j;


            }

            int mod11 = weightedSum % 11;

            int distanceFrom11Divisibility = 11 - mod11;
            if (mod11 == 0) {
                distanceFrom11Divisibility = 0;
            }
            isbn10Ints[9] = distanceFrom11Divisibility;
            for (int i = 0; i < 10; i++) {

                isbn10Chars[i] = String.valueOf(isbn10Ints[i]).charAt(0);
                System.out.println((i) + "index = " + isbn10Chars[i]);

            }
            String isbn10 = new String(isbn10Chars);
            System.out.println("isbn10 = " + isbn10);
            return isbn10;
        } catch(Exception ex) {
            System.out.println("data for isbn " + isbn13 + "could not be computed");
        }
        return null;






    }

    public static String prepareBookNumber(long recievedBookNum) {
        String toBeRetrunedBookNum = "B";
        if(recievedBookNum < 10000) {
            toBeRetrunedBookNum = "000" + recievedBookNum;
        } else if(recievedBookNum < 100000) {
            toBeRetrunedBookNum = "00" + recievedBookNum;
        } else if(recievedBookNum < 1000000) {
            toBeRetrunedBookNum = "0" + recievedBookNum;
        } else {
            toBeRetrunedBookNum =  "" + recievedBookNum;
        }
        return toBeRetrunedBookNum;

    }

    public static String getEscapedCsvLine(String recievedLine) {

        String line = "";


        String[] contentLine = recievedLine.split(",");

        boolean continuation = false;
        for ( String data : contentLine ) {

            //System.out.println(":"+data+":" + data.contains("\"") );

            if (data.startsWith("\"") && !continuation) {


                line = line + data;
                continuation = true;
            } else if (continuation) {

                if (data.endsWith("\"")) {
                    //System.out.println(data.charAt(0));
                    line = line + ":::" + data + ",";
                    continuation = false;
                } else {
                    line = line + ":::" + data;
                }
            } else {
                line = line + data + ",";
            }


        }
        return line;
    }

    public static String makeCsvLine(String[] stringsInSeq) {
        String csvLine = "";
        for(String data: stringsInSeq) {
            csvLine = csvLine + "\"" + data.replaceAll(":::",",") + "\"" +  ","  ;
            csvLine.replaceAll("\\r", "").replaceAll("\\n","");
        }
        csvLine = csvLine + "\n";
        return csvLine;

    }

    public static void main(String[] args) {
        /*String isbn13 ="9788172236656";
        String isbn10 = new Isbn13Isbn10Converter().convertFromIsbn13(isbn13);
        System.out.println("\n"+isbn13 + "::::" + isbn10);
*/
        Isbn13Isbn10Converter isbn13Isbn10Converter = new Isbn13Isbn10Converter();
        String catalogFilePath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration.csv";
        String writeFile = "/Users/ruchiagarwal/avidreaders/catalog_report_with_isbn10_for_migration.csv";

        try {
            File readfile = new File(catalogFilePath);
            BufferedReader fileReader = new BufferedReader(new FileReader(readfile));
            File writefile = new File(writeFile);
            FileWriter writer = new FileWriter(writeFile);
            String line = "";
            String recievedLine = "";
            fileReader.readLine();
            fileReader.readLine();
            fileReader.readLine();
            while ((recievedLine = fileReader.readLine()) != null) {


                line =getEscapedCsvLine(recievedLine);
                System.out.println(line);
                String[] bookContentLine = line.split(",");

                if(bookContentLine.length > 5) {
                    line = isbn13Isbn10Converter.prepareBookNumber(Integer.parseInt(bookContentLine[3])) + "," + line;
                    line = "\"" + isbn13Isbn10Converter.convertFromIsbn13(bookContentLine[2]) + "\"" +  "," + line ;

                    System.out.println(line);
                    writer.append(line.replaceAll(":::", ",") + "\n");
                }
            }
            fileReader.close();
            writer.flush();
            writer.close();
        }catch(IOException ex) {

            throw new RuntimeException(ex);
        }


    }
}
