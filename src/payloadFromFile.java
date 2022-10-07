
//to convert json into string
//json -> bytes -> String


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class payloadFromFile {
	
	
	
	@Test
	public void addPlace() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("D:\\RestAssuredAPI_Automation\\DemoProject\\addPlaceJsonBody.json")))).when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract()
				.response().asString();
		
	}

}
