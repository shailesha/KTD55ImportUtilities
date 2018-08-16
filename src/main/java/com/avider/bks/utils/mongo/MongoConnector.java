package com.avider.bks.utils.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Created by root on 16/8/18.
 */
public class MongoConnector {

    public static DB connect() {
        DB mongoDb = null;

        try {
            //mongodb://<dbuser>:<dbpassword>@ds121382.mlab.com:21382/avidreaders
            MongoClientURI mongoClientURI = new MongoClientURI("mongodb://aviduser1:aviduser1@ds121382.mlab.com:21382/avidreaders");
            MongoClient mongoClient = new MongoClient(mongoClientURI);

            mongoDb = mongoClient.getDB("avidreaders");

        } catch ( Exception ex) {
            throw new RuntimeException(ex);
        }

        return mongoDb;
        //mongodb://<dbuser>:<dbpassword>@ds033607.mlab.com:33607/ct_party_db
    }
}
