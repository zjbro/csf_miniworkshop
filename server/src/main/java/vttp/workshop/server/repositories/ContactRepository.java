package vttp.workshop.server.repositories;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.workshop.server.models.Contact;

@Repository
public class ContactRepository {

	@Autowired
	@Qualifier("games")
	private RedisTemplate<String, Object> redisTemplate;

	public void delete(String contactId) {
		redisTemplate.opsForHash().delete(contactId, "address");
	}

	public void save(Contact contact) {
		String cId = contact.getContactId();
		redisTemplate.opsForHash().put(
	        "%s".formatted(cId), 
			"address", 
			contact.toJson().toString());
	}

	public List<String> findKeys(String pattern) {
		List<String> contactIds = new ArrayList<>(redisTemplate.keys(pattern));
		System.out.println(">>>>>>contactIds: "+ contactIds);
		return contactIds;
	}

	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

	public JsonArray contactList(List<String> contactIds){
		
		
		for (int i = 0; i < contactIds.size(); i++){
			System.out.println(">>>>>>>Size of contact Id list: " + contactIds.size());
			// System.out.println(">>>>>>>contact from redis: "+ redisTemplate.opsForHash().get(contactIds.get(i), "address").toString());
			String jsonObjectStr = redisTemplate.opsForHash().get(contactIds.get(i), "address").toString().toString();
			JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();
			
			arrayBuilder.add(object);
		}
		

		JsonArray jsonArray = arrayBuilder.build();
		System.out.println(">>>>>json array :" + jsonArray);
		return jsonArray;
	}
}
