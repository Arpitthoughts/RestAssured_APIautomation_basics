package serialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;;
public class serialize {
	
	public static void main(String [] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlacePojo p = new AddPlacePojo();
		p.setAccuracy(50);
		p.setName("Elite chauraha");
		p.setAddress("Manu vihar Kunwar Residency");
		p.setPhone_number("+91 983 748 8293");
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		
		List<String> al= new ArrayList<String>();
		al.add("shoe park");
		al.add("shop");
		p.setTypes(al);
		
		location l = new location();
		l.setLat(-38.383334);
		l.setLng(33.423362);
		
		p.setLocation(l);
		
		String response=given().log().all().queryParam("key", "qaclick123").body(p)
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	System.out.println(response);
	
	}

}
