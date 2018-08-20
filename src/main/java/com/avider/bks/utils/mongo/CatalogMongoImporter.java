package com.avider.bks.utils.mongo;

import com.avider.bks.utils.ImportBookDataDto;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by ruchiagarwal on 8/19/18.
 *
 * This class assumes book import data in the format : BarCode	BookNum	isbn13	JBTitleId	Title	Language	Authors	Location	Location1	ShelfLocation	Type	Type1	Category	TimesRented	Status 	LostStatus	DamageStatus	CallNum
 * the format which was used to import books in Koha
 */
public class CatalogMongoImporter {
    public static void writeRawBookDataIntoCatalog(ImportBookDataDto importBookDataDto) {
        if (importBookDataDto != null) {

            if(null != findInCatalog(String.valueOf(importBookDataDto.getTitleId()),String.valueOf(importBookDataDto.getIsbn13()),importBookDataDto.getIsbn_10(), null)) {
                DB avidRdrDb = MongoConnector.connect();
                DBCollection catalogColl = avidRdrDb.getCollection("Catalog");
                DBObject record = new BasicDBObject();
                record.put("isbn13", importBookDataDto.getIsbn13());
                record.put("isbn10", importBookDataDto.getIsbn_10());
                record.put("JBTitleId", importBookDataDto.getTitleId());
                record.put("title", importBookDataDto.getTitle());

                record.put("authors", importBookDataDto.getAuthors());
                record.put("language", importBookDataDto.getLanguage());
                record.put("category", importBookDataDto.getCategory());
                record.put("publishers", "");
                record.put("publishDate", "");
                record.put("type", importBookDataDto.getBookType());
                record.put("overview", "");
                record.put("reviews", "");
                record.put("coverImgUrl", "");

                catalogColl.insert(record);
            }

        }
    }

    private static ImportBookDataDto findInRawCatalog(String JBTitleId, String isbn13, String isbn10, String catalogPkId) {
        ImportBookDataDto importBookDataDto = null;
        DB avidReaderDb = MongoConnector.connect();
        DBObject query = new BasicDBObject();
        DBCollection catalogColl = avidReaderDb.getCollection("Catalog");
        if(isbn13 != null && !isbn13.isEmpty()) {
            query.put("isbn13", isbn13);
        } else if(isbn10 != null && !isbn10.isEmpty()) {
            query.put("isbn10", isbn10);
        } else if(JBTitleId != null && !JBTitleId.isEmpty()) {
            query.put("JBTitleId", JBTitleId);
        }else if(catalogPkId != null && !catalogPkId.isEmpty()) {
            query.put("_id", catalogPkId);
        }
        DBObject record = catalogColl.findOne(query);
        if(null != record) {
            importBookDataDto = new ImportBookDataDto();

            importBookDataDto.setIsbn13(record.get("isbn13"));
            record.put("isbn10");
            record.put("JBTitleId");
            record.put("title");
            record.put("JBTitleId");
            record.put("authors");
            record.put("language");
            record.put("category");
            record.put("publishers");
            record.put("publishDate");
            record.put("type");
            record.put("overview");
            record.put("reviews");
            record.put("coverImgUrl");
        }
    }

}
