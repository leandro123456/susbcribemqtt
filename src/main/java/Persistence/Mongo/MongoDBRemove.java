package Persistence.Mongo;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;



public class MongoDBRemove{
		

	public static void removeByQuery() throws UnknownHostException {

		// Get a new connection to the db assuming that it is running
		MongoClient m1 = new MongoClient();

		// use test as a database,use your database here
		DB db = m1.getDB("test");

		// fetch the collection object ,car is used here,use your own
		DBCollection coll = db.getCollection("car");

		// builds query for car whose speed is less than 45
		BasicDBObject b1 = new BasicDBObject("speed", new BasicDBObject("$lt",
				45));

		// invoke remove method
		WriteResult c1 = coll.remove(b1);

		// print the number of documents using getN method
		System.out.println("Number of documents removed:" + c1.getN());
	}

	public static void removeSingleDoc(String dbname, String collection, String parameter1,
			String value1) throws UnknownHostException {

		MongoClient m1 = new MongoClient();

		DB db = m1.getDB(dbname);

		DBCollection coll = db.getCollection(collection);

		DBObject findDoc = new BasicDBObject(parameter1, value1);
		coll.remove(findDoc);
	}

	public static void removeAllDocs() throws UnknownHostException {

		MongoClient m1 = new MongoClient();

		DB db = m1.getDB("test");

		DBCollection coll = db.getCollection("car");

		// remove all documents in the collection with empty object
		WriteResult r1 = coll.remove(new BasicDBObject());

		System.out.println("------------------------------------");

		System.out.println("Number of documents removed:" + r1.getN());
	}
	
	
	//nuevos metodos

}