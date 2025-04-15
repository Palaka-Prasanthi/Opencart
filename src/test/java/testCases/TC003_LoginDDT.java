package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")  //getting data provider from different class/object
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		logger.info("***Starting TC003_LoginDDT***");
		try
		{
		//HomePage
		HomePage hp=new HomePage(driver);		//driver coming from BaseClass
		hp.clickMyAccount();
		hp.cliCkLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);     //we should not put Hard coded value so we call from property file
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//Validation
		//MyAccount
		MyAccountPage maccp=new MyAccountPage(driver);
		boolean targetPage=maccp.isMyAccountPageExists();
		
		
		/*Data is Valid - login success - test pass - logout
		 					login failed - test fail
		 Data is InValid - login success - test fail - logout
		 					login failed - test pass				
		 */
		if(exp.equalsIgnoreCase("Valid"))
				{
					if(targetPage==true)
					{
						maccp.clickLogout();
						Assert.assertTrue(true);
					}
					else
					{
						Assert.assertTrue(false);
					}
				}
		if(exp.equalsIgnoreCase("InValid"))
		{
			if(targetPage==true)
			{
				maccp.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("***Finished TC002_LoginTest***");
	}

}
