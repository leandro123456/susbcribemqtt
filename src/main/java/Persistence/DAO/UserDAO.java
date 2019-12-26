package Persistence.DAO;


import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.conversions.Bson;

import Persistence.Model.User;
import Persistence.Mongo.MongoDBClient;



public class UserDAO extends MongoDBClient<User>{

	
	public UserDAO() {
		super(User.class);
	}

	public List<User> retrieveAllUsers() {
		return this.retrieveAll();
	}
	
	public User retrieveById(String deviceId) {
		Bson filter = eq("id", deviceId);
		return this.retrieveByFilter(filter);
	}
	
	public User retrieveByMail(String email) {
		Bson filter = eq("email", email);
		return this.retrieveByFilter(filter);
	}
	
	public User retrieveByCookie(String cookie) {
		Bson filter = eq("cookie", cookie);
		return this.retrieveByFilter(filter);
	}
	
    public void deleteUser(User user) {
		this.delete(user);
	}
	
	@Override
	protected String getDatabaseName() {
		return "MQTT-Manager";
	}
}
