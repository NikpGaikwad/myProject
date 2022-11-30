package maven.temple_project;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Base_class 
{
  public static String tokenValue;
  String dirPath;
  public ExtentReports er;
  public ExtentTest test;
 
	@BeforeSuite
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
	} 
 
}