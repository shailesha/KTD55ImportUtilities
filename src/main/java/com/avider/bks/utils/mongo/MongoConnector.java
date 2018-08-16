package com.avider.bks.utils.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * Created by root on 16/8/18.
 */
public class MongoConnector {

    public static DB connect() {
        DB mongoDb = null;

        try {
            MongoClient mongoClient = new MongoClient("mongodb://aviduser:pwd2610@ds033607.mlab.com:33607/ct_party_db");

            mongoDb = mongoClient.getDB("avidreaders");

        } catch ( Exception ex) {
            throw new RuntimeException(ex);
        }

        return mongoDb;
        //mongodb://<dbuser>:<dbpassword>@ds033607.mlab.com:33607/ct_party_db
    }
}
