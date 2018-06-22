package pl.jsongen.json;

import java.util.Map;
import java.util.Map.Entry;

public class JsonProperty {
	private String type;
	
	private String refId;
	
	private String name;

	private Map<String, Object> properties;
	
	private String itemRefId;
	
	public JsonProperty(Entry<String, Object> singleProperty) {
		this.name = singleProperty.getKey();
		//wczytana definicja do Mapy np {$ref=#/definitions/payment-method}
		Map<String, Object> singeFieldDefinition = (Map<String, Object>)singleProperty.getValue();		
		this.type = (String)singeFieldDefinition.get("type");
		//jezeli istnieje referencja do innej definicji to wczytujemy
		if ((String)singeFieldDefinition.get("$ref") != null)
			this.refId = ((String)singeFieldDefinition.get("$ref")).replaceAll("#/definitions/", "");	
		properties = (Map<String, Object>)singeFieldDefinition.get("properties");
		if (singeFieldDefinition.get("items") != null)
			this.itemRefId = ((String)(((Map<String, Object>)singeFieldDefinition.get("items"))).get("$ref")).replaceAll("#/definitions/", "");
		
	}

	public String getItemRefId() {
		return itemRefId;
	}

	public void setItemRefId(String itemRefId) {
		this.itemRefId = itemRefId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "JsonProperty [type=" + type + ", refId=" + refId + ", name=" + name + ", properties=" + properties
				+ ", itemRefId=" + itemRefId + "]";
	}
		
}
