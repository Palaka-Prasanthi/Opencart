package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{	
	
	//constructor
	public HomePage(WebDriver driver)		//child class constructor
	{
		super(driver);			//passing this driver to the parent class constructor
	}
	
	//locators 
	@FindBy(xpath="//span[text()='My Account']") 
	WebElement lnkMyaccount;
	
	@FindBy(xpath="//a[normalize-space()='Register']") 
	WebElement lnkRegister;
	
	@FindBy(xpath="//a[text()='Login']")
	WebElement linkLogin;
	
	//Methods
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	public void cliCkLogin()
	{
		linkLogin.click();
	}
	
	
	
	

}
