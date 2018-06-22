package pl.jsongen;

import pl.jsongen.json.JsonExampleBuilder;
import pl.jsongen.json.JsonSchema;
import pl.jsongen.json.JsonSchemaException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JsonSchemaException
    {
        String path="C:\\Development\\Provident\\develop\\configuration\\raml\\types\\api-cdm-schemas.json";
        JsonSchema jsonBuilder = new JsonSchema(path);
        
        jsonBuilder.init();
        
        JsonExampleBuilder example = new JsonExampleBuilder(jsonBuilder, "application", 4);
        
        example.generateExample();
        
        System.out.println("\n------------------\n");
        System.out.println(example.getExample());
    }
}
