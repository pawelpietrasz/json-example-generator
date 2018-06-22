package pl.jsongen.json;

import java.util.Arrays;
import java.util.List;

public class JsonConstance {
	
	public static final String ENTITY_PROPERTIES="properties";
	
	public static final String JSON_STRING_TYPE="string";
	public static final String JSON_NUMBER_TYPE="number";
	public static final String JSON_BOOLEAN_TYPE="boolean";
	public static final String JSON_OBJECT_TYPE="object";
	public static final String JSON_ARRAY_TYPE="array";
	public static final List<String> JSON_SIMPLE_TYPE = Arrays.asList(JSON_STRING_TYPE, JSON_NUMBER_TYPE, JSON_BOOLEAN_TYPE);
	
	public static final String ENTITY_ENUM="enum";
	
	private JsonConstance() {}
}
