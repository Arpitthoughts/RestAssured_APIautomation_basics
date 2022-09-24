import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// validating add place API of google maps

		// given - all the input details
		// when - submit API response with resource and http method
		// then - validate the response

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlaceBody()).when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract()
				.response().asString();

		System.out.println(Response);
		JsonPath js = new JsonPath(Response);

		String placeId = js.getString("place_id");
		System.out.println("place is added with placeid->" + placeId);

		// now automating update place API
		String newAddress = "70 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + " \r\n" + "")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// now automation get place API

		String responseGetPlace = given()

				.log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)

				.when()

				.get("maps/api/place/get/json")

				.then()

				.log().all().assertThat().statusCode(200)

				.extract()

				.response().asString();

		JsonPath js1 = new JsonPath(responseGetPlace);
		String updatedaddress = js1.getString("address");
		System.out.println(updatedaddress);
		
		Assert.assertEquals(updatedaddress, newAddress);
	}

}
