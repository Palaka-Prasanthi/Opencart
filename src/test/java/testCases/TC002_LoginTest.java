package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {		//we cannot execute test case through this page we can execute through xml because Base Class is also depending on xml file if without xml we can execute
	
	//Test Methods
	
	@Test(groups={"Sanity", "Master"})
	public void verify_login()
	{
		logger.info("***Starting TC002_LoginTest***");
		try
		{
		//HomePage
		HomePage hp=new HomePage(driver);		//driver coming from BaseClass
		hp.clickMyAccount();
		hp.cliCkLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));     //we should not put Hard coded value so we call from property file
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//Validation
		//MyAccount
		MyAccountPage maccp=new MyAccountPage(driver);
		boolean targetPage=maccp.isMyAccountPageExists();
		
		Assert.assertTrue(targetPage);		//Assert.assertEquals(targetPage, true, "Login failed");  //this way also we can write
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("***Finished TC002_LoginTest***");
	}

}
