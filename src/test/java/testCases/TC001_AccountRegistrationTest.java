package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass{	//BaseClass is a parent class 
	
	
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration()
	{
		logger.info("***Starting TC001_AccountRegistrationTest Test case***" );
		
		try
		{
		HomePage hp=new HomePage(driver);		//pass driver as a constructor
		
		hp.clickMyAccount();
		logger.info("Clicked on My Account...");
		
		hp.clickRegister();
		logger.info("Clicked on Register link...");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details...");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmai.com");		//randomly generated the email
		regpage.setTelePhone(randomeNumber());
		
		String pwd=randomeAlphaNumeric();
		regpage.setPassword(pwd);
		regpage.setConfirmpwd(pwd);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		
		//validation
		logger.info("Validating Expected message...");
		String confmsmsg=regpage.getConfirmationmsg();
		if(confmsmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed...");
			logger.debug("Debug logs...");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Finished TC001_AccountRegistrationTest Test case***" );
	}
	
	
}
