package bindings;

import cucumber.api.java.en.And;
import utilities.SiteFactory;

public class Login {
	
	private SiteFactory factory;
	public Login(SiteFactory factory) {
		this.factory=factory;
	}

	/**
	 * @author  Srinivas
	 * @feature Balance
	 * This method is to enter the username in Login screen
	 */
	@And("^On Login page, enter the username ('.*')$")
	public void enterTheUsername(String username) {
		
		factory.loginpage().enterUsername(username);

	}
	
	/**
	 * @author  Srinivas
	 * @feature Balance
	 * This method is to enter the password in Login screen
	 */
	@And("^On Login page, enter the password ('.*')$")
	public void enterThePassword(String password) {
		
		factory.loginpage().enterPassword(password);
	}
	
	/**
	 * @author  Srinivas
	 * @feature Balance
	 * This method is to enter the password in Login screen
	 */
	@And("^On Login page, click on the login button$")
	public void clickOnLoginButton(String password) {
		factory.loginpage().clickOnLoginBtn();

	}
	
	/**
	 * @author  Srinivas
	 * @throws Exception 
	 * @feature Balance
	 * This method is to enter the password in Login screen
	 */
	@And("^the user should be able to navigate to the application$")
	public void navigateToUrl() throws Exception {
		factory.commonfunction().gotoHomePage();

	}
	
	
}
