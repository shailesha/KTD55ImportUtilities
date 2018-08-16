package com.avider.bks.utils.mongo;

import com.mongodb.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by root on 16/8/18.
 */

public class MongoWriter {
    public static final String GOOGLE = "GOOGLE";
    public static final String ISBNDB = "ISBNDB";

    public void writeMissedIsbns(Set<String> isbnSet, String bookApiName) {
        if(!isbnSet.isEmpty() && (bookApiName != null && (bookApiName.equals(GOOGLE) || bookApiName.equals(ISBNDB)))) {
            DB avidRdrDb = MongoConnector.connect();
            DBCollection booksMissSet = null;
            if(bookApiName.equals(GOOGLE)) {
                booksMissSet = avidRdrDb.getCollection("GoogleBooksMissSet");
            } else {
                booksMissSet = avidRdrDb.getCollection("IsbnDbBooksMissSet");
            }
            Iterator iter = isbnSet.iterator();
            while(iter.hasNext()) {
                BasicDBObject document = new BasicDBObject();
                document.put("isbn", iter.next());
                booksMissSet.insert(document);
            }



        }

    }


    public Set<String> retrieveMissedIsbns(String bookApiName) {
       // if(!isbnSet.isEmpty()) {
        Set<String> isbnSet = new HashSet<>();
        if(bookApiName != null && (bookApiName.equals(GOOGLE) || bookApiName.equals(ISBNDB))) {

            DB avidRdrDb = MongoConnector.connect();
            DBCollection booksMissSet = null;


            if (bookApiName.equals(GOOGLE)) {
                booksMissSet = avidRdrDb.getCollection("GoogleBooksMissSet");
            } else {
                booksMissSet = avidRdrDb.getCollection("IsbnDbBooksMissSet");
            }
            avidRdrDb.getCollection("GoogleBooksMissSet");
            DBCursor cursor = booksMissSet.find();
            while (cursor.hasNext()) {
                DBObject document = cursor.next();
                isbnSet.add((String) document.get("isbn"));

            }
        }
        return isbnSet;

       // }

    }





}
