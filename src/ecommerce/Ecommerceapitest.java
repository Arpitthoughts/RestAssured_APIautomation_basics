package ecommerce;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ecommercepojo.LoginRequest;
import ecommercepojo.LoginResponse;
import ecommercepojo.OrderDetail;
import ecommercepojo.OrdersRequest;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;

public class Ecommerceapitest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		LoginRequest loginrequest = new LoginRequest();

		loginrequest.setUserEmail("arjaria.arpit55@gmail.com");
		loginrequest.setUserPassword("Arpit!12345");

		RequestSpecification request = given().log().all().spec(reqspec).body(loginrequest);

		LoginResponse loginresponse = request.when().post("/api/ecom/auth/login").then().log().all().spec(resspec)
				.extract().response().as(LoginResponse.class);

		String token = loginresponse.getToken();
		String userID = loginresponse.getUserId();

		System.out.println(loginresponse.getToken());
		System.out.println(loginresponse.getMessage());

		// Add product

		RequestSpecification addProdSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification addProductRequest = given().log().all().spec(addProdSpec).param("productName", "Laptop")
				.param("productAddedBy", userID).param("productCategory", "Electronics")
				.param("productSubCategory", "Electronics").param("productPrice", "70000")
				.param("productDescription", "Lenovo 10th gen laptop").param("productFor", "All")
				.multiPart("productImage", new File("C:\\Users\\91945\\OneDrive\\Pictures\\Laptop.jpg"));

		String addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(addProductResponse);
		String productId=js.get("productId");

		
		//Create Order
		
		RequestSpecification createOrderSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderdetail= new OrderDetail();
		orderdetail.setProductOrderedId(productId);
		orderdetail.setCountry("India");
		
		List<OrderDetail> od = new ArrayList<OrderDetail>();
		od.add(orderdetail);
		
		OrdersRequest order= new OrdersRequest();
		order.setOrders(od);
		
		RequestSpecification createOrderReq=given().log().all().spec(createOrderSpec).body(order);
		Response createOrderRes=createOrderReq.when().post("/api/ecom/order/create-order");
		createOrderRes.then().log().all();
		
		
		//Delete order
		
	RequestSpecification deleteProductBase=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
		.setContentType(ContentType.JSON).build();
	
	RequestSpecification deleteProductReq=given().log().all().spec(deleteProductBase).pathParam("productId", productId);
	
	String deleteProductRes=deleteProductReq.when().log().all().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract()
	.response().asString();
	
	JsonPath js1= new JsonPath(deleteProductRes);
System.out.println(js1.getString("message"));

	
		
		
		
	}

}
