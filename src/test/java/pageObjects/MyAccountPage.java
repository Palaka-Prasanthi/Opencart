package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
	
	//Constructor
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}

	//Locators
	@FindBy(xpath="//h2[text()='My Account']")		//My Account page heading
	WebElement msgHeading;
	
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
	WebElement linkLogout;
	
	
	//Methods  -Verification
	public boolean isMyAccountPageExists()
	{
		try
		{
		return(msgHeading.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void clickLogout()
	{
		linkLogout.click();
	}
}
