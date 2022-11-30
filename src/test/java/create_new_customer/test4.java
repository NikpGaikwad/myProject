package create_new_customer;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class test4 

{ @Test
  public void tc004()
  {
	 RestAssured.baseURI="https://reqres.in";
	 
	 RequestSpecification httprequest=RestAssured.given();
	 
	 JSONObject requestParamas=new JSONObject();
	 
	 requestParamas.put("name","morpheus" );
	 requestParamas.put("job","zion resident" );
	 
	 httprequest.body(requestParamas.toJSONString());
	 httprequest.header("Content-Type","application/json");
	 
	 Response response=httprequest.request(Method.PUT,"/api/users/2");
	 
	 Assert.assertEquals( response.getStatusCode(),200);
	 Assert.assertTrue(response.getBody().asString().contains("morpheus"));
	 Assert.assertTrue(response.getBody().asString().contains("zion resident"));
	 Assert.assertTrue(response.getBody().asString().contains("id"));
	 Assert.assertTrue(response.getBody().asString().contains("updatedAt"));
  } 
	 
}
