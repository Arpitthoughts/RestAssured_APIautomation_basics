package specbuilder;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.AddPlacePojo;
import serialization.location;;

public class SerializeSpecBuilder {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlacePojo p = new AddPlacePojo();
		p.setAccuracy(50);
		p.setName("Manik chowk");
		p.setAddress("Sindhi tiraha manik chowk");
		p.setPhone_number("+91 563 999 8293");
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");

		List<String> al = new ArrayList<String>();
		al.add("shoe park");
		al.add("shop");
		p.setTypes(al);

		location l = new location();
		l.setLat(-38.383334);
		l.setLng(33.423362);
		p.setLocation(l);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		RequestSpecification request = given().spec(req).body(p);
		String response = request.when().post("/maps/api/place/add/json").then().spec(res).extract().response()
				.asString();

		System.out.println(response);

	}

}
