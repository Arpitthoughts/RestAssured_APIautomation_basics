package coforgetraining;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.*;

import static io.restassured.RestAssured.*;
public class airlineapiTest {
	
	@Test
	public void createairlineTest() {
		
	RequestSpecification resspec= new RequestSpecBuilder().setBaseUri("https://api.instantwebtools.net/").setContentType(ContentType.JSON).build();
		//java faker api to generate random values
		int airlineId=new Faker().number().numberBetween(250, 550);
		
		
		
		RestAssured.given().spec(resspec).log().all().body("{\r\n"
				+ "    \"id\": "+airlineId+",\r\n"
				+ "    \"name\": \"Sri Lankan Airways\",\r\n"
				+ "    \"country\": \"Sri Lanka\",\r\n"
				+ "    \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png\",\r\n"
				+ "    \"slogan\": \"From Sri Lanka\",\r\n"
				+ "    \"head_quaters\": \"Katunayake, Sri Lanka\",\r\n"
				+ "    \"website\": \"www.srilankaairways.com\",\r\n"
				+ "    \"established\": \"1990\"\r\n"
				+ "}")
		.when().post("v1/airlines")
		.then().log().all().statusCode(200).body("slogan",equalTo("From Sri Lanka")).and()
		.body("name", containsString("Airways"));
	}

}
