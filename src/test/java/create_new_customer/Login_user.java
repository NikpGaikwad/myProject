package customer_ManagementAPI;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.util.JSON;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import maven.temple_project1.Base_class;
import maven.temple_project1.UtilityClass;

public class Login_user extends Test_BaseClass

{   
	//ExtentReports er;
	//ExtentTest test;
	//String dirPath;
	String extentRepPath;
	String testCaseID;
	String[] testData;
	String[] headerData;
	String expResponseBody;
	String ExpHTTPStatusCode;
	String ExpResponseCode;
	/*JSONParser jsonParserj;
	Object javaObject;
	JSONObject jsObject;*/
	JSONObject eRd;
	String ercode;
	String etn;
	int id = 0;
	RequestSpecification httpRequest;
	Response response;
	//SoftAssert as;

	/*@BeforeSuite
	public void setExtent() throws IOException {
		dirPath = System.getProperty("user.dir");
		System.out.println(dirPath);
		String extentRepPath = dirPath + "/test-output/ExtentReport/extentReport.html";
		er = new ExtentReports(extentRepPath);

	}

	@AfterSuite
	public void endReport() {

		er.flush();
		er.close();
	}*/

	@BeforeMethod  //
	public void beforeMethod() throws IOException, ParseException {
		
		//initialise variable
		id++;
		testCaseID = "tc" + Integer.toString(id);
		testData = UtilityClass.getDatafromCSVFile(testCaseID);
		headerData = testData[2].split(":");
	    expResponseBody=testData[4];
		ExpResponseCode= testData[5];
		//System.out.println(testData[5]);
		//System.out.println(testData[6]);
	    ExpHTTPStatusCode=testData[6];
	    
	    //convert string expected response into jsonObject for fetching data from key
		/* jsonParserj=new JSONParser();
	     javaObject=jsonParserj.parse(testData[4]);
	     jsObject=(JSONObject)javaObject;
		 ercode= (String) jsObject.get("responseCode");
		 JSONObject eRd= (JSONObject) jsObject.get("responseData");
		 String tt=(String) eRd.get("mobileno");
		  System.out.println(tt);*/
		
		
		
		//give name of test case in extent report
		test=er.startTest((String) testData[1]);
		Reporter.log("Test case Discription  :" +testData[1]);
		
		
		// set base uri
		RestAssured.baseURI = UtilityClass.getDataFromPF("baseuri");
		// request specification
		httpRequest = RestAssured.given();
		as = new SoftAssert();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE)
		{   test.log(LogStatus.INFO,"Status code    :"+response.getStatusCode());
		    Reporter.log("Status code    :"+response.getStatusCode());
		    test.log(LogStatus.INFO,"Staus line     :"+response.getStatusLine());
		    Reporter.log("Staus line     :"+response.getStatusLine());
		  //  test.log(LogStatus.INFO,response.jsonPath().get("errorDetail.errorMessage"));
			test.log(LogStatus.INFO,"Response we get         :"+ response.asString());
			Reporter.log("Response we get         :"+ response.asString());
			test.log(LogStatus.INFO,"Response we expected    :"+ expResponseBody);
			Reporter.log("Response we expected    :"+ expResponseBody);
			test.log(LogStatus.INFO,result.getThrowable());
			
			}
		else if(result.getStatus()==ITestResult.SKIP)
		{ 
			test.log(LogStatus.SKIP,"Test Skipped"+result.getThrowable());
		    Reporter.log("Test Skipped");
		 }
		else 
		{ 
			test.log(LogStatus.PASS,"ALL TEST PASSED");
			Reporter.log("ALL TEST PASSED");
		}
		
		er.endTest(test);
	}

	@Test
	public void tc1() throws IOException, ParseException {
		Reporter.log("Test case Discription  :" +testData[1]);
		// add json body into RequestSpecification variable "httpRequest"
		httpRequest.body(testData[3]);
		System.out.println(testData[3]);
		

		// add header information into request specification variable
		httpRequest.header(headerData[0], headerData[1]);
		httpRequest.header(UtilityClass.getDataFromPF("login_header2_key"),
				UtilityClass.getDataFromPF("login_header2_value"));

		// send request and get response
		response = httpRequest.request(Method.POST, UtilityClass.getDataFromPF("login_posturl"));
		System.out.println(response.asString());
		
		//converting expected string response into jsonObject and initialise variable 
		ercode=(String) UtilityClass.convertDataStringToJsonObject(expResponseBody).get("responseCode");
		System.out.println(ercode);
		eRd=(JSONObject) UtilityClass.convertDataStringToJsonObject(expResponseBody).get("responseData");
		etn=(String) eRd.get("mobileno");
		
		
	
	
		// TESTS
		//1.validate token present in body
		as.assertTrue(response.getBody().asString().contains("token"));

		   // store token into variable
		if (response.getBody().asString().contains("token")) {
			tokenValue = response.jsonPath().get("responseData.token").toString();} 
		else 
			{test.log(LogStatus.FAIL, "Test is fail because Token is not genrated");
			Reporter.log("FAIL :Test is fail because Token is not genrated");}
		
		//2.validate response code
		as.assertEquals(response.jsonPath().get("responseCode"),ExpResponseCode );
		if (ExpResponseCode.equals(response.jsonPath().get("responseCode"))) {
			test.log(LogStatus.PASS,"Test Expected response code and actual response code is same "+response.jsonPath().get("responseCode"));
			Reporter.log("PASS  :Test Expected response code and actual response code is same "+response.jsonPath().get("responseCode"));} 
		else 
			{test.log(LogStatus.FAIL,"Test is fail because we expect RESPONSE CODE is " +ExpResponseCode+ "but we get "+response.jsonPath().get("responseCode"));
			 Reporter.log("FAIL  :Test is fail because we expect RESPONSE CODE is " +ExpResponseCode+ "but we get "+response.jsonPath().get("responseCode"));}
       
		as.assertAll();
		//test.log(LogStatus.INFO,"response code contain body "+response.getBody().asString());

		//System.out.println(tokenValue);*/
	}

	@Test
	public void tc2() throws IOException {
		Reporter.log("Test case Discription  :" +testData[1]);
		// atach body to request specification variable
		httpRequest.body(testData[3]);

		// add header information to requestSpecification
		httpRequest.header(UtilityClass.getDataFromPF("login_header2_key"),
				UtilityClass.getDataFromPF("login_header2_value"));

		// send request and get response
		Response response = httpRequest.request(Method.POST, UtilityClass.getDataFromPF("login_posturl"));
		
		
			

		// TESTS
		//1.validate response body
				as.assertNotEquals(response.asString(),expResponseBody );
				if (response.asString().isEmpty())
				{test.log(LogStatus.PASS, "Test is PASS we not get any response");
				 Reporter.log("PASS  :Test is PASS we not get any response");}
			else
			{test.log(LogStatus.FAIL, "Test is FAIL we not get response"+response.asString());
			Reporter.log("FAIL  :Test is FAIL we not get response"+response.asString());}
				
		//2.validate http status code		
				as.assertEquals(Integer.toString(response.getStatusCode()),ExpHTTPStatusCode);
				if (ExpHTTPStatusCode.equals(Integer.toString(response.getStatusCode())))
				{ 
					test.log(LogStatus.PASS, "Test is PASS because we expect status code is"+ExpHTTPStatusCode +"and actual is "+response.getStatusCode());
				Reporter.log("PASS  :Test is PASS because we expect status code is"+ExpHTTPStatusCode +"and actual is "+response.getStatusCode());
				}
				else
					{
					test.log(LogStatus.FAIL, "Test is FAIL because we expect status code is" +ExpHTTPStatusCode +" but we found "+response.getStatusCode());
					Reporter.log("FAIL  :Test is FAIL because we expect status code is" +ExpHTTPStatusCode +" but we found "+response.getStatusCode());
					}
		
				
		as.assertAll();

	}

	@Test
	public void tc3() throws IOException {
		Reporter.log("Test case Discription  :" +testData[1]);
		
		// atach body to request specification variable
		httpRequest.body(testData[3]);
        
		//add header info
		httpRequest.header(headerData[0],headerData[1]);
		httpRequest.header(UtilityClass.getDataFromPF("login_header2_key"),
				UtilityClass.getDataFromPF("login_header2_value"));

		// send request and get response
		response = httpRequest.request(Method.POST, UtilityClass.getDataFromPF("login_posturl"));

		// TESTS
	    //1.validate httpStatus code
		as.assertEquals(Integer.toString(response.getStatusCode()),ExpHTTPStatusCode);
		if (ExpHTTPStatusCode.equals(Integer.toString(response.getStatusCode())))
		{
			test.log(LogStatus.PASS, "Test is PASS because we expect status code is"+ExpHTTPStatusCode +"and actual is "+response.getStatusCode());
		    Reporter.log("PASS  :Test is PASS because we expect status code is"+ExpHTTPStatusCode +"and actual is "+response.getStatusCode());
		}
		else
			{
			test.log(LogStatus.FAIL, "Test is FAIL because we expect status code is" +ExpHTTPStatusCode +" but we found "+response.getStatusCode());
			Reporter.log("FAIL  :Test is FAIL because we expect status code is" +ExpHTTPStatusCode +" but we found "+response.getStatusCode());
			}
		
		//2.validate response code
				as.assertEquals(response.jsonPath().get("responseCode"),ExpResponseCode );
				if (ExpResponseCode.equals(response.jsonPath().get("responseCode"))) 
				{
					test.log(LogStatus.PASS, "Test Expected response code and actual response code is same "+response.jsonPath().get("responseCode"));
					Reporter.log("PASS  :Test Expected response code and actual response code is same "+response.jsonPath().get("responseCode"));
				} 
				else 
					{
					test.log(LogStatus.FAIL, "Test is fail because we expect RESPONSE CODE is " +ExpResponseCode+ "but we get "+response.jsonPath().get("responseCode"));
					Reporter.log("FAIL  :Test is fail because we expect RESPONSE CODE is " +ExpResponseCode+ "but we get "+response.jsonPath().get("responseCode"));
					}
		
				
		as.assertAll();

	}

}
