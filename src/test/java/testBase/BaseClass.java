package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
//BaseClass we have to extend in every test case
//This is the common class required for all the test cases
//for every test case we need to logs
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;			//in Extent Report Manager file we have created new object for that we make static common
	public Logger logger;		//Log4j
	public Properties p;
	
	@BeforeClass(groups={"Sanity", "Regression", "Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String browser) throws IOException
	{
		//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");		//FileReader equals to FileInputStream only and .// represents current project location
		p=new Properties();		//creation object for Properties
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		
		//Grid setUp
		//os
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))		//for executing Selenium grid //if environment is remote do the execution
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();		
		
			if(os.equalsIgnoreCase("Windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No Matching os");
				return;			//return from the if condition
			}
			
			//browser
			
			switch(browser.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome");  break;
			case "edge": capabilities.setBrowserName("edge");  break;
			case "firefox": capabilities.setBrowserName("firefox");  break;
			default: System.out.println("No Matching browsers");  return;		//returns means exit from loop
			}
			
			driver=new RemoteWebDriver(new URL("http://192.168.31.118:4454/wd/hub"), capabilities);		//launch url
		
		}
		
		//local environment
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))		//if environment is local do the execution
		{
			switch(browser.toLowerCase())
			{
			case "chrome": driver=new ChromeDriver(); break;
			case "edge": driver=new EdgeDriver(); break;
			case "firefox": driver=new FirefoxDriver(); break;
			default: System.out.println("Invalid Broswer.."); return;    //return means we exit from the entire execution no need to continue further stuff
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");  //Hard coded value we can write like this
		driver.get(p.getProperty("appURL"));		//reading url from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomeNumber()
	{
		String generatednumber=RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
	
	public String randomeAlphaNumeric()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		String generatednumber=RandomStringUtils.randomNumeric(10);
		return (generatedstring+"@"+generatednumber);
	}
	
	//capture screenshot
	public String captureScreen(String tname)		//it is not a static method so we cannot access from Base class to extent report
	{
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takeScreenshot=(TakesScreenshot) driver;
		File sourceFile=takeScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timeStamp+ ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}

	
}
