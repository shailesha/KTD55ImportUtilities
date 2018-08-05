package com.avider.bks.utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ruchiagarwal on 8/4/18.
 */
public class BookNumberAppender {

    public static void main(String[] args) {

        String libBookListPath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration.csv";
        String bookNumAppenderOutputPath = "/Users/ruchiagarwal/avidreaders/catalog_report_for_migration_withBooknum.csv";

        IsbnFileWriter isbnFileWriter = null;
        try {
            BookListRetriever bookListRetriever = new BookListRetriever();
            List<ImportBookDataDto> importBookDataDtos = bookListRetriever.retrieveBooksInLibrary(libBookListPath);

            isbnFileWriter = new IsbnFileWriter(bookNumAppenderOutputPath);
            isbnFileWriter.initializeForBookNumberAppender();


            Iterator<ImportBookDataDto> iter = importBookDataDtos.iterator();
            while (iter.hasNext()) {
                ImportBookDataDto importBookDataDto = iter.next();
                importBookDataDto.setConvertedBookNum(Isbn13Isbn10Converter.prepareBookNumber(importBookDataDto.getBookNum()));
                importBookDataDto.setIsbn_10(Isbn13Isbn10Converter.convertFromIsbn13(String.valueOf(importBookDataDto.getIsbn13())));
                isbnFileWriter.writeBookNumAppender(importBookDataDto);

            }
        } finally {
            isbnFileWriter.closeWriting();
        }


    }
}
