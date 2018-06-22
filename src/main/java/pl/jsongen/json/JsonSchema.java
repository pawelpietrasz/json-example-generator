package pl.jsongen.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonSchema {
	private String schemaPath;
	
	//mapa wszystkich obiektow wczytanych z pliku schema
	private Map<String, Map<String, Object>> allDefinitions;

	public JsonSchema(String schemaPath) {
		super();
		this.schemaPath = schemaPath;
	}
	
	public void init() throws JsonSchemaException{
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		try {
			JsonReader reader = new JsonReader(
				    new InputStreamReader(
				        new FileInputStream(schemaPath), 
				        "UTF-8"
				    )
				);
			JsonParser parser = new JsonParser();
			JsonObject parentObject=parser.parse( reader ).getAsJsonObject();
			
			Map<String, Map<String, Map<String, Object>>> map = gson.fromJson(
				    parentObject, 
				    new TypeToken<Map<String, Object>>(){}.getType()
				);		
			allDefinitions = map.get("definitions");
			
			
		} catch (UnsupportedEncodingException|FileNotFoundException e) {
			throw new JsonSchemaException(e);
		}	
	}
	
	public JsonEntity getDefinition(String entityName){
		return buildEntity(entityName);
	}
	
	private JsonEntity buildEntity(String entityName) {
		JsonEntity entity = new JsonEntity(entityName);
		
		//definicja encji ktora chcemy generowac
		Map<String, Object> initialEntity = (Map<String, Object>)allDefinitions.get(entityName);
		
		//wszystkie pola z encji glownej, bez tych z dziedziczenia
		Map<String, Object> properties = (Map<String, Object>)initialEntity.get(JsonConstance.ENTITY_PROPERTIES);	
		
		if (properties != null) {
			entity.setProperties(properties);
		}
		
		//lista ref ktore dziedziczymy
		List<Map<String, Object>> allOf = (List<Map<String, Object>>)initialEntity.get("allOf");		

		if(allOf != null) {
			Map<String, Object> extendedEntity = allOf.get(0);
			String parentEntityName = ((String)extendedEntity.get("$ref")).replaceAll("#/definitions/", "");
			
			//propertisy encji parent
			Map<String, Object> parentEntityProperties = (Map<String, Object>)((Map<String, Object>)allDefinitions.get(parentEntityName)).get(JsonConstance.ENTITY_PROPERTIES);
			
			entity.setParentProperties(parentEntityProperties);
		}
		
		List<String> enums = (List<String>)initialEntity.get(JsonConstance.ENTITY_ENUM);
		
		entity.setEnums(enums);
		return entity;
	}
}
