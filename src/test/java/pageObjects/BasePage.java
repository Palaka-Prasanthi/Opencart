package pageObjects;
//BasePage only contains Constructor, which we need to extend in every page object class
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	
	WebDriver driver;
	public BasePage(WebDriver driver)		//parent constructor
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

}
