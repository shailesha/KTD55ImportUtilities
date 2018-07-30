package com.avider.bks.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class IsbnListRetriever {
    BufferedReader fileReader = null;
    File file = null;
    Set<String> isbnSet = new HashSet(1000);
    public Set<String> retrieveIsbnsInLibrary(String isbnFilePath)
    {
        try {
            file = new File(isbnFilePath);
            fileReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = fileReader.readLine()) != null) {

                String[] bookContentLine = line.split(",");

                if(bookContentLine.length>8) {
                    isbnSet.add(bookContentLine[2]);
                }
            }
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return isbnSet;

    }
}
