package OAuth2;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth_demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		WebDriver  driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("arjaria.arpit55@gmail.com");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("sujekhankhidki");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
		// String url=driver.getCurrentUrl();

		// RestAssured.baseURI = "";

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJqL6k7T0ac13KYepDiRg91de-nKAe07NFcK-k5PEYge1chm2i3YyOrjfLxvVVBRfg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String code = url.split("code=")[1].split("&scope")[0];
		System.out.println(code);

		// getting access_token

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)

				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code")
				.queryParams("scope", "https://www.googleapis.com/auth/userinfo.email")
				.queryParams("response_type", "code")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println(accessToken);

		//Hitting the API with access token to get available books
		String Response = given().contentType("application/json").queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(Response);
	}

}
