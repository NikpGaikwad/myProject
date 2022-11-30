package create_new_customer;


import org.testng.Assert;
import org.testng.annotations.Test;

import  io.restassured.RestAssured;
import io.restassured.response.Response;

public class test1
{
 
	@Test
	public void tc001()
	{
		RestAssured.baseURI="https://reqres.in";
		Response response=RestAssured.get("/api/users?page=2");
		
		System.out.println( "Status code"+response.getStatusCode());
		System.out.println("time for resonse in millisecond"+response.getTime());
		System.out.println("response body"+response.getBody().asString());
		System.out.println("status line is"+response.getStatusLine());
		
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		String headerValue=response.header("content-type");
		System.out.println(headerValue);
		Assert.assertEquals(headerValue,"application/json; charset=utf-8");
		
	}
	
	
}
