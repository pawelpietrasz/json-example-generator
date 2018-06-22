package pl.jsongen.json;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.fluttercode.datafactory.impl.DataFactory;

import pl.jsongen.value.ValueGenerator;

public class JsonExampleBuilder {
	private StringBuilder jsonExample = new StringBuilder();

	private String entityName;
	private int depth = 1;
	private JsonSchema schema;
	
	
	public StringBuilder getJsonExample() {
		return jsonExample;
	}

	public JsonExampleBuilder(JsonSchema schema, String entityName, int depth) {
		super();
		this.entityName = entityName;
		this.depth = depth;
		this.schema = schema;
	}
	
	public void generateExample() {
		JsonEntity entityDefinition = schema.getDefinition(entityName);
		System.out.println("generate:   " + entityDefinition);
		
		jsonExample.append("{");
		iterateFields(entityDefinition.getAllElements(), 0);
		jsonExample.append("}");
	}
	
	private void iterateFields(Iterator<Map.Entry<String, Object>> iterator, int counter) {
		while (iterator.hasNext() && counter < depth) {
			//pojedyncze pole z properties
			//np. PaymentMethod={$ref=#/definitions/payment-method}
			Entry<String, Object> singleProperty = iterator.next();
			
			JsonProperty property = new JsonProperty(singleProperty);	
			
			System.out.println("iterator counter: " + counter);
			System.out.println("iterator elem:   " + property);
			
			printProperty(property, counter, iterator.hasNext());
			
		}
	}
	
	private void printProperty(JsonProperty property,int currentDepth, boolean hasNextElem) {
		if(JsonConstance.JSON_SIMPLE_TYPE.contains(property.getType())) {
			printSimpleType(property, hasNextElem);
		}
		if (property.getRefId() != null) {
			printRefIdType(property, currentDepth, hasNextElem);
		}
		//jesli pole jest zwyklym obiektem
		if (JsonConstance.JSON_OBJECT_TYPE.equals(property.getType())) {
			printObjectField(property, currentDepth, hasNextElem);
		}	
		if (JsonConstance.JSON_ARRAY_TYPE.equals(property.getType())) {
			printArrayField(property, currentDepth, hasNextElem);
		}		
	}
	
	private void printSimpleType(JsonProperty property, boolean hasNextElem) {
		switch(property.getType()) {
		case JsonConstance.JSON_STRING_TYPE:
			jsonExample.append("\""+property.getName()+"\": \"" + ValueGenerator.getSimpleText(12)+"\"");
			break;
		case JsonConstance.JSON_NUMBER_TYPE:
			jsonExample.append("\""+property.getName()+"\": " + ValueGenerator.getSimpleNumber(0, 100));
			break;
		case JsonConstance.JSON_BOOLEAN_TYPE:
			jsonExample.append("\""+property.getName()+"\": " + ValueGenerator.getSimpleBoolean());	
			break;						
		}
		
		if(hasNextElem)
			jsonExample.append(",");
	}
	
	private void printRefIdType(JsonProperty property,int currentDepth, boolean hasNextElem) {
		if ( currentDepth<depth) {
			
			JsonEntity entityDefinition = schema.getDefinition(property.getRefId());
			
			if (entityDefinition.getProperties() == null) {
				jsonExample.append("\""+property.getName()+"\": \"" + ValueGenerator.getSimpleLov(entityDefinition)+"\"");
			}else {
				jsonExample.append("\""+property.getName()+"\": {");
				iterateFields(entityDefinition.getAllElements(), ++currentDepth);
				jsonExample.append("}");
			}
			if(hasNextElem)
				jsonExample.append(",");				
		}		
	}
	public void printObjectField(JsonProperty property,int currentDepth, boolean hasNextElem) {
		jsonExample.append("\""+property.getName()+"\": {");
		Iterator<Map.Entry<String, Object>> iter = 	property.getProperties().entrySet().iterator();
		iterateFields(iter, ++currentDepth);		
		jsonExample.append("}");	
		if(hasNextElem)
			jsonExample.append(",");	
	}
	
	public void printArrayField(JsonProperty property,int currentDepth, boolean hasNextElem) {
		if ( currentDepth<depth) {

			JsonEntity entityDefinition = schema.getDefinition(property.getItemRefId());
			Iterator<Map.Entry<String, Object>> iter = 	entityDefinition.getAllElements();		
			jsonExample.append("\""+property.getName()+"\": [");	
			jsonExample.append("{");
			iterateFields(iter, ++currentDepth);	
			jsonExample.append("}");
			jsonExample.append("]");
			
			if(hasNextElem)
				jsonExample.append(",");			
		}		
	}
	
	public String getExample() {
		return jsonExample.toString();
	}
}
