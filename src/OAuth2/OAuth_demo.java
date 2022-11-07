package OAuth2;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.Courses;
import pojo.GetCourse;
import pojo.WebAutomation;

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

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJrOd-mi67YyBQbCF2QPsrYZBRZMAHcTKx2Avmb9ygSa0yYSMC6Dep17CEg4y0-Nww&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
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
		/*String Response = given().contentType("application/json").queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(Response);*/
		
		//Parsing the response into POJO
		GetCourse gc = given().contentType("application/json").queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON).
				when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		//Deserialization getting response from java object
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
	System.out.println(gc.getCourses().getWebAutomation().get(0).getCourseTitle());	
	
	//finding price of api SoapUI Webservices testing course 
	List<Api> apielements=gc.getCourses().getApi();
	for(int i=0;i<apielements.size();i++) {
		 
		if(apielements.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
			System.out.println(apielements.get(i).getPrice());
		}
		
	}
	
//Finding course title all web automation courses
	List<WebAutomation> webcourse=gc.getCourses().getWebAutomation();
	
	for(int j=0;j<webcourse.size();j++) {

	System.out.println(webcourse.get(j).getCourseTitle());
	
		
	}

}
}