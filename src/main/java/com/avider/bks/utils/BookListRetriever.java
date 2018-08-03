package com.avider.bks.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class BookListRetriever {
    BufferedReader fileReader = null;
    File file = null;


    //file format assumed to be Title	Title ID	ISBN	Booknumber	Category	Location	Author	Shelf Location	Times Rented	Status	Language
    public List<ImportBookDataDto> retrieveBooksInLibrary(String isbnFilePath)
    {
        List<ImportBookDataDto> isbnSet = new ArrayList<>(1000);
        try {
            file = new File(isbnFilePath);
            fileReader = new BufferedReader(new FileReader(file));
            String line = null;

            String recievedLine = "";
            fileReader.readLine();
            fileReader.readLine();
            fileReader.readLine();
            while ((recievedLine = fileReader.readLine()) != null) {
               // System.out.println(recievedLine);
                line = Isbn13Isbn10Converter.getEscapedCsvLine(recievedLine);
               // System.out.println(line);
                String[] bookContentLine = line.split(",");

                if(bookContentLine.length > 5) {
                  //  System.out.println(bookContentLine[0] + ":" + bookContentLine[1] + ":" + bookContentLine[3]);
                    ImportBookDataDto importBookDataDto = new ImportBookDataDto();
                    importBookDataDto.setBookNum(Long.parseLong(bookContentLine[3]));
                    if(bookContentLine[2] != null && !bookContentLine[2].equals("") && !bookContentLine[2].equals("NOISBN")) {
                        try {
                            importBookDataDto.setIsbn13(Long.parseLong(bookContentLine[2]));
                        } catch (Exception ex) {
                            System.out.println("Invalid ISBN : " + bookContentLine[2]);
                        }
                    }
                    importBookDataDto.setTitleId(Integer.parseInt(bookContentLine[1]));
                    importBookDataDto.setTitle(bookContentLine[0]);
                    importBookDataDto.setAuthors(bookContentLine[6]);
                    importBookDataDto.setCategory(bookContentLine[4]);
                    if(bookContentLine.length >=11) {
                        importBookDataDto.setLanguage(bookContentLine[10]);
                    }

                    importBookDataDto.setLocation(bookContentLine[7]);
                    importBookDataDto.setTimesRented(Integer.parseInt(bookContentLine[8]));
                    importBookDataDto.setStatus(bookContentLine[9]);
                    importBookDataDto.setLibLocation(bookContentLine[5]);

                    isbnSet.add(importBookDataDto);



                }
            }
            fileReader.close();

        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return isbnSet;

    }
}
