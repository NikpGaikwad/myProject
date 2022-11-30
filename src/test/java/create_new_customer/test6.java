package create_new_customer;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class test6 

{   
	
	@Test
  public void tc006()
  {
	  
	  RestAssured.baseURI="https://reqres.in";
	  
	  RequestSpecification httpRequest=RestAssured.given();
	  
	  Response response=httpRequest.request(Method.DELETE,"/api/users/2");
	  
	  Assert.assertEquals( response.getStatusCode(),204);
	  
  }
}
