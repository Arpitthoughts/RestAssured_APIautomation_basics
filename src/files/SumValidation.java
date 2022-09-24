package files;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void verify_course_price(){
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		int totalCourseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Amount spent on courses = " + totalCourseAmount);
		
		int totalCourse = js.getInt("courses.size()");
		System.out.println("total courses = " + totalCourse);
		
		int sum=0;
		for(int i=0;i<totalCourse;i++) {
			
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			
			sum=sum+(price*copies);
		}
		if(sum==totalCourseAmount) {
			System.out.println("total course amount is correct = "+sum);
		}
		else {
			System.out.println("total course amount is not correct = "+sum);
		}
		
		Assert.assertEquals(totalCourseAmount, sum);
		
	}
		
	}


