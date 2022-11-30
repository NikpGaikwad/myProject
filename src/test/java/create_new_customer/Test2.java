package create_new_customer;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import static io.restassured.matcher.ResponseAwareMatcher.*;
import io.restassured.response.Response;
import static org.hamcrest.Matcher.*;

public class Test2 

{    
	@Test
	void tc02()
	{
	   given().
	           get("https://reqres.in/api/users?page=2").
	   then().
	          statusCode(200).
	          log().all();
	
		
	}

	
}
