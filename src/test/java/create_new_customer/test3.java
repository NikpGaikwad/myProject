package create_new_customer;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class test3

{

	@Test
	public void tc003() {
		RestAssured.baseURI = "https://register-dev.wowfinstack.com";

		RequestSpecification httprequest = RestAssured.given();

		JSONObject requestParamas = new JSONObject();

		requestParamas.put("mobileno", "19840000");
		requestParamas.put("pin", "123456");

		httprequest.body(requestParamas.toJSONString());
		httprequest.header("ReqDatetime", "13072022095257");
		httprequest.header("Content-Type", "application/json");

		Response response = httprequest.request(Method.POST, "/customer-registration/loginUser");

		Assert.assertEquals(response.getStatusCode(), 200);
		// Assert.assertTrue(response.getBody().asString().contains("NIK"));
		// Assert.assertTrue(response.getBody().asString().contains("QA"));
		// Assert.assertTrue(response.getBody().asString().contains("id"));
		// Assert.assertTrue(response.getBody().asString().contains("createdAt"));

	}
}
