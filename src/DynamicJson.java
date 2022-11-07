//Parameterization of API using @DataProvider annotation
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utilities.createBookData;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class DynamicJson {
	
	@Test(dataProvider="getExcelData",enabled=true)
	public void addBook(String isbn,String aisle ) {
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().header("Content-Type","text/plain").body(Payload.addBookPayload(isbn,aisle))
		.when().post("Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
	
		JsonPath js= ReusableMethods.rawtoJson(response);
		String bookID=js.get("ID");
		System.out.println(bookID);
	}
	
	
	//@Test(dataProvider="getData")
	public void deleteBook(String isbn,String aisle ) {
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().header("Content-Type","text/plain").body(Payload.deleteBookPayload(isbn,aisle))
		.when().delete("Library/DeleteBook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
	
		JsonPath js= ReusableMethods.rawtoJson(response);
		String msgBookDelete=js.getString("msg");
		System.out.println(msgBookDelete);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] getData() {
		
		return new Object[][] {{"Book1","111"},{"Book2","112"},{"Book3","113"}};

	}
	
	
	@DataProvider(name="getExcelData")
	public Object[][] getExcelData() throws IOException {
		
		Object[][] data= createBookData.BookData();
		return data;
		
	}
	
	
	
}
