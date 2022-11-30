package create_new_customer;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class test5 
{

	@Test
	public void tc005()
  {
	RestAssured.baseURI="https://reqres.in";
	 
	RequestSpecification httpRequest=RestAssured.given();
	
	JSONObject request=new JSONObject();
	request.put("name","morpheus");
	request.put("job", "zion resident");
	
	httpRequest.body(request.toJSONString());
	httpRequest.header("Content-Type","application/json");
	
	Response response=httpRequest.request(Method.PATCH, "/api/users/2");
	
	Assert.assertEquals( response.getStatusCode(),200);
	 Assert.assertTrue(response.getBody().asString().contains("morpheus"));
	 Assert.assertTrue(response.getBody().asString().contains("zion resident"));
	 Assert.assertTrue(response.getBody().asString().contains("id"));
	 Assert.assertTrue(response.getBody().asString().contains("updatedAt"));

  }
}
