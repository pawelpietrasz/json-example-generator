package pl.jsongen.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonEntity {
	private Map<String, Object> properties;
	private Map<String, Object> parentProperties;
	private List<String> enums;
	private String regexPattern;
	
	public List<String> getEnums() {
		return enums;
	}

	public void setEnums(List<String> enums) {
		this.enums = enums;
	}

	public Map<String, Object> getParentProperties() {
		return parentProperties;
	}

	public void setParentProperties(Map<String, Object> parentProperties) {
		this.parentProperties = parentProperties;
	}

	public String getRegexPattern() {
		return regexPattern;
	}

	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}

	private List<Map<String, Object>> allOf;
	
	private String entityName;

	@SuppressWarnings("unchecked")
	public JsonEntity(String entityName) {
		super();
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}


	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setAllOf(List<Map<String, Object>> allOf) {
		this.allOf = allOf;
	}


	


	@Override
	public String toString() {
		return "JsonEntity [properties=" + properties + ", parentProperties=" + parentProperties + ", enums=" + enums
				+ ", regexPattern=" + regexPattern + ", allOf=" + allOf + ", entityName=" + entityName + "]";
	}

	public boolean isExtended() {
		//lista ref ktore dziedziczymy	
		return allOf!=null ? true:false;
	}
	
	public Iterator<Map.Entry<String, Object>> getAllElements(){
		//zawiera wszytskie pola z encji glownej oraz te z ktorej dziedziczymy
		Map<String, Object> fullEntity = new HashMap<>();
		
		//encja po ktorej dziedziczymy ma parenta
		if (parentProperties != null)
			fullEntity.putAll(parentProperties);
		
		fullEntity.putAll(properties);
		return fullEntity.entrySet().iterator();
	}
}
