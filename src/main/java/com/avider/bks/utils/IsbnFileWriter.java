package com.avider.bks.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class IsbnFileWriter {
    FileWriter writer;
    File file;

    public IsbnFileWriter() {}

    public IsbnFileWriter(String outputFilePath) {
        try {

            file = new File(outputFilePath);
            writer = new FileWriter(file,true);

        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void initialize() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            writer.append("-------,-----------,next run,---------,Date & Time," + formatter.format(date) +",-------------\n");
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void initializeForBookNumberAppender() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            writer.append("-------,-----------,next run,---------,Date & Time," + formatter.format(date) +",-------------\n");
            writer.append("book number, book num, isbn10, isbn13, titleid, title, language,  Authors,  location, shelf location, category, Times rented, Status \n");
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void initializeForEnrichedBookFormat()  {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            //file = new File(outputFilePath);
            //writer = new FileWriter(file,true);
            writer.append("-------,-----------,next run,---------,Date & Time," + formatter.format(date) +",-------------\n");
            writer.append("book number, isbn13, isbn_10, titleid, title, subtitle,language, edition, Dewey_decimal, Authors, Publishers, location, shelf location, Overview,Synopsys, excerpt, Notes, Publish Date,category, subjects, Times rented, Status, Num Pages,Reviews, cover image, isbndb Comments, Google Comments, Openlib Comments \n");
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }


    }
    public void writeMissingData(String isbn) {
        try {
            writer.append(isbn + ", NO DATA FOUND FOR THIS ISBN\n");
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void writeBookNumAppender(ImportBookDataDto importBookDataDto) {
        String[] lineToBeWritten = new String [13];
        try {
            System.out.println("isbn = " + importBookDataDto.getIsbn13());
            lineToBeWritten[0] = importBookDataDto.getConvertedBookNum();
            lineToBeWritten[1] = String.valueOf(importBookDataDto.getBookNum());
            lineToBeWritten[2] = String.valueOf(importBookDataDto.getIsbn_10());
            lineToBeWritten[3] = String.valueOf(importBookDataDto.getIsbn13());
            lineToBeWritten[4] = String.valueOf(importBookDataDto.getTitleId());
            lineToBeWritten[5] = importBookDataDto.getTitle();
            lineToBeWritten[6] = importBookDataDto.getLanguage();
            lineToBeWritten[7] = importBookDataDto.getAuthors();
            lineToBeWritten[8] = "KTD";
            lineToBeWritten[9] = importBookDataDto.getLocation();
            lineToBeWritten[10] = importBookDataDto.getCategory();
            lineToBeWritten[11] = String.valueOf(importBookDataDto.getTimesRented());
            lineToBeWritten[12] = importBookDataDto.getStatus();
System.out.println(Isbn13Isbn10Converter.makeCsvLine(lineToBeWritten));
            writer.append(Isbn13Isbn10Converter.makeCsvLine(lineToBeWritten));

            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeIsbnOnlyData(Set isbnSet) {
        try {


            Iterator iter = isbnSet.iterator();
            while (iter.hasNext()) {
                writer.append(iter.next() + "\n");
            }
            writer.flush();

        } catch(IOException ex) {
            ex.printStackTrace();
        }


    }

    public void writeIsbnData(IsbnDataDto isdnDataDto) {

        //book number, isbn13, isbn_10, titleid, title, subtitle,language, edition, Dewey_decimal, Authors, Publishers, location, shelf location, Overview,Synopsys, excerpt, Notes, Publish Date,category, subjects, Times rented, Status, Num Pages,Reviews, cover image, Openlib URL, good reads id, Lccn, library thing id

        String[] lineToBeWritten = new String [28];
        try {
            System.out.println("isbn = " + isdnDataDto.getIsbn13());
            lineToBeWritten[0] = isdnDataDto.getBookNum();
            lineToBeWritten[1] = isdnDataDto.getIsbn13() ;
            lineToBeWritten[2] = isdnDataDto.getIsbn_10() ;
            lineToBeWritten[3] = String.valueOf(isdnDataDto.getTitleId() );
            lineToBeWritten[4] = isdnDataDto.getTitle() ;
            lineToBeWritten[5] = isdnDataDto.getSubtitle() ;
            lineToBeWritten[6] = isdnDataDto.getLanguage() ;
            lineToBeWritten[7] = isdnDataDto.getEdition() ;
            lineToBeWritten[8] = isdnDataDto.getDeweyDecimal() ;
            lineToBeWritten[9] = convertListToString(isdnDataDto.getAuthors()) ;
            lineToBeWritten[10] = isdnDataDto.getPublishers() ;
            lineToBeWritten[11] = "KTD" ;
            lineToBeWritten[12] = isdnDataDto.getShelfLocation() ;
            lineToBeWritten[13] = isdnDataDto.getOverview() ;
            lineToBeWritten[14] = isdnDataDto.getSynopsys() ;
            lineToBeWritten[15] = isdnDataDto.getExcerpt() ;
            lineToBeWritten[16] = isdnDataDto.getOpenlibNotes() ;
            lineToBeWritten[17] = isdnDataDto.getPublishDate() ;
            lineToBeWritten[18] = isdnDataDto.getCategory() ;
            lineToBeWritten[19] = convertListToString(isdnDataDto.getSubjectNames()) ;
            if(isdnDataDto.getTimesRented() != 0) {
                lineToBeWritten[20] = String.valueOf(isdnDataDto.getTimesRented());
            }
            lineToBeWritten[21] = isdnDataDto.getStatus() ;
            if(isdnDataDto.getNumPages() != 0) {
                lineToBeWritten[22] = String.valueOf(isdnDataDto.getNumPages());
            }
            lineToBeWritten[23] = convertListToString(isdnDataDto.getReviews()) ;
            lineToBeWritten[24] = isdnDataDto.getCoverImgUrl() ;
            lineToBeWritten[25] = isdnDataDto.getIsbndbComments() ;
            lineToBeWritten[26] = isdnDataDto.getGoogleDataComments() ;
            lineToBeWritten[27] = isdnDataDto.getOpenLibComments() ;
            //lineToBeWritten[28] = isdnDataDto.getLibraryThingId() ;

            writer.append(Isbn13Isbn10Converter.makeCsvLine(lineToBeWritten));
            /*writer.append(isdnDataDto.getIsbn_10() + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getTitle()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getSubtitle()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getEdition()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getDeweyDecimal()) + ",");
            writer.append(escapeSpecialCharacters(convertListToString(isdnDataDto.getAuthors()) )+ ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getPublishers() )+ ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getOverview()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getSynopsys()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getExcerpt()) + ",");
            writer.append(escapeSpecialCharacters(isdnDataDto.getOpenlibNotes()) + ",");
            writer.append(isdnDataDto.getPublishDate() + ",");
            writer.append(escapeSpecialCharacters(convertListToString(isdnDataDto.getSubjectNames())) + ",");
            writer.append(isdnDataDto.getNumPages() + ",");
            writer.append(escapeSpecialCharacters(convertListToString(isdnDataDto.getReviews()) )+ ",");
            writer.append(isdnDataDto.getCoverImgUrl() + ",");
            writer.append(isdnDataDto.getOpenlibUrl() + ",");
            writer.append(isdnDataDto.getGoodreadsId() + ",");
            writer.append(isdnDataDto.getLccn() + ",");
            writer.append(isdnDataDto.getLibraryThingId() + "\n");*/
            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }




    }


    public void writeCirculationData(String[] circulationData) {

        //datetime, action, book num, crd no

        String[] lineToBeWritten = new String [3];
        try {

            writer.append(Isbn13Isbn10Converter.makeCsvLine(circulationData));

            writer.flush();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }




    }


    public void closeWriting() {
        try {
            writer.flush();
            writer.close();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    private String escapeSpecialCharacters(String str) {
        if(str != null) {
            return str.replaceAll("\\r", "").replaceAll("\\n","");
        } else {
            return "";
        }
    }
    
    private String convertListToString(String[] list) {
        StringBuffer x= new StringBuffer();
        if(null != list) {
            Arrays.stream(list).forEach(item -> x.append(item + ";"));
            int length = x.length();
            return x.toString().substring(0,length-1);
        }
        return "";

        
    }


}
