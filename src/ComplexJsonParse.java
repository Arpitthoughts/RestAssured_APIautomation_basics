import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.CoursePrice());
		// total courses
		int totalCourse = js.getInt("courses.size()");
		System.out.println("total courses = " + totalCourse);

		// total amount spent on courses

		int totalCourseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Amount spent on courses = " + totalCourseAmount);

		// title of first course

		String courseTitle = js.getString("courses[0].title");
		System.out.println("title of first course = " + courseTitle);

		// Print all course title and respective titles
		for (int i = 0; i < totalCourse; i++) {
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getString("courses["+i+"].price"));
		}
		
		
		
		//printing copies of RPA course
		for(int i=0; i< totalCourse;i++) {
			
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				System.out.println("copies of RPA course = "+js.getString("courses["+i+"].copies"));
				break;
			}
		}
		
		
		//Verify sum of each course is equal to the total purchased amount
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
		
	}

}
