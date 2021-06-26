package utilities;

import org.openqa.selenium.WebDriver;

import bindings.Balance;
import bindings.Login;
import pages.BalancePage;
import pages.LoginPage;

public class SiteFactory {

	public CommonFunctions commonfunction() {
		return new CommonFunctions();
	}

	public WebDriver driverOject() {
		return CommonFunctions.driver;
	}

	public Login login() {
		return new Login(this);
	}

	public Balance balance() {

		return new Balance(this);
	}
	
	public BalancePage balancepage() {

		return new BalancePage(this, driverOject());
	}
	
	public LoginPage loginpage() {

		return new LoginPage(this, driverOject());
	}

}


