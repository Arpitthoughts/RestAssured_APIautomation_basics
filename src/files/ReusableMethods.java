package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	public static JsonPath rawtoJson(String response) {
		
		return new JsonPath(response);
		
	}
	
}
