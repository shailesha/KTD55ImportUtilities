package com.avider.bks.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ruchiagarwal on 7/23/18.
 */
public class IsbnFileWriter {
    FileWriter writer;
    File file;
    public void initialize(String outputFilePath)  {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            file = new File(outputFilePath);
            writer = new FileWriter(file,true);
            writer.append("\n\n\n\n\n\n-------,-----------,next run,---------,Date & Time," + formatter.format(date) +",-------------\n");
            writer.append("isbn13,isbn_10, title, subtitle,edition, Dewey_decimal, Authors, Publishers, Overview,Synopsys, excerpt, Notes, Publish Date,subjects, Num Pages,Reviews, cover image, Openlib URL, good reads id, Lccn, library thing id\n");
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

    public void writeIsbnData(IsbnDataDto isdnDataDto) {
        try {
            System.out.println("isbn = " + isdnDataDto.getIsbn13());
            writer.append(isdnDataDto.getIsbn13() + ",");
            writer.append(isdnDataDto.getIsbn_10() + ",");
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
            writer.append(isdnDataDto.getLibraryThingId() + "\n");
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
