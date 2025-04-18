package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener 
{
	
	public ExtentSparkReporter sparkReporter;		//UI of the report    //look and color
	public ExtentReports extent; 					//populate common info on the report
	public ExtentTest test;			//creating test case entries in the report and update status of the test methods
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt=new Date();
		 String currentdatetimestamp=df.format(dt);
		 */
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());	//timeStamp
		
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\" + repName);  //specify the location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report");		//Title of the report
		sparkReporter.config().setReportName("opencart Functional Testing");			//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	
	public void onTestSuccess(ITestResult result) {	
		
	    test=extent.createTest(result.getTestClass().getName());		//create a new entry in the Report  //get name of the test case
	    test.assignCategory(result.getMethod().getGroups());		//to display groups in report test case wise & attach groups to a category wise
	    test.log(Status.PASS,result.getName()+" got successfully executed");	//update status p/f/s
	
	}
	
	public void onTestFailure(ITestResult result) {				//onTestFailure method executes whenever test got failed
	    
		 test=extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups());
		 
		 test.log(Status.FAIL,result.getName()+" got failed");
		 test.log(Status.INFO, result.getThrowable().getMessage());		//shows cause of the error
		
		 try
		 {
			 String imgPath = new BaseClass().captureScreen(result.getName());    //capture screenshot
			 test.addScreenCaptureFromPath(imgPath);		//attach screenshot to the report
		 } catch(Exception e1)			//it should be IOException but getting error so I given Exception
		 {
			 e1.printStackTrace();			//display exception message
		 }
	  }
	
	public void onTestSkipped(ITestResult result) {				//onTestSkipped method executes whenever test got skipped
	    
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
	  }
	
	public void onFinish(ITestContext testContext) {				//onTestFinish method executes only once, once it finished all the tests execution
	    
		extent.flush();
		
		String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;			
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());			//automatically the report will be generated
		}
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
	  }

}
