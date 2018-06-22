package pl.jsongen.value;

import org.fluttercode.datafactory.impl.DataFactory;

import com.mifmif.common.regex.Generex;

import pl.jsongen.json.JsonEntity;

public class ValueGenerator {
	private static DataFactory df = new DataFactory();
	private final static int  DEFAULT_STRING_LENGTH = 10;
	
	public static String getSimpleText(int characterNumber) {
		return df.getRandomText(characterNumber);
	}
	
	public static Integer getSimpleNumber(int rangeLeft, int rangeRight) {
		return df.getNumberBetween(rangeLeft, rangeRight);
	}
	
	public static Boolean getSimpleBoolean() {
		return df.getItem(new Boolean[] {true,false});
	}
	
	public static String getSimpleLov(JsonEntity entity) {
		if (entity.getEnums() != null) {
			return entity.getEnums().get(0);
		}else {
			if (entity.getRegexPattern() != null) {
				String regex = entity.getRegexPattern().replace("^", "").replace("$", "");
				Generex generex = new Generex(regex);
				return generex.random();
			}else {
				return df.getRandomText(DEFAULT_STRING_LENGTH);
			}
		}
	}
}
