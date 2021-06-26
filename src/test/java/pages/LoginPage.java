package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utilities.CommonFunctions;
import utilities.SiteFactory;

public class LoginPage extends CommonFunctions{
	
	public LoginPage(SiteFactory factory, WebDriver driver) {		
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'username')]")
	private WebElement username;
	
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'password')]")
	private WebElement password;
	
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'submit')]")
	private WebElement loginBtn;
	
	public void enterUsername(String user) {
		username.sendKeys(user);
	}
	
	public void enterPassword(String passwrd) {
		password.sendKeys(passwrd);
	}
	
	public void clickOnLoginBtn() {
		loginBtn.click();
	}

}
