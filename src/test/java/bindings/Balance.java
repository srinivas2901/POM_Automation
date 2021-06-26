package bindings;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import utilities.CommonFunctions;
import utilities.SiteFactory;

public class Balance extends CommonFunctions {

	private SiteFactory factory;
	static Logger log = Logger.getLogger(Balance.class.getName());

	public Balance(SiteFactory factory) {
		this.factory = factory;
	}

	/**
	 * @author Srinivas
	 * @feature Balance This method is to validate the user is in Value screen
	 */
	@And("^The user is in the values screen$")
	public void validateTheUserInValueScreen() {

		Assert.assertTrue(factory.balancepage().validateUserIsInValueScreen(), "User is not navigated to value screen");

	}

	/**
	 * @author Srinivas
	 * @throws Exception
	 * @feature Balance This method is to validate the user is in Value screen
	 */
	@Then("^Validate the right count of values appear on the screen are greater than '(.*)' and the values are formatted as '(.*)'$")
	public void validateTheCountValue(String count, String formatvalue) throws Exception {
		SoftAssert softAssertion = new SoftAssert();
		boolean flag1 = factory.balancepage().Values(workbookLocation, BalanceSheetName, 1);
		boolean flag2 = factory.balancepage().validateTheFormatValue(formatvalue);
		try {
			if (flag1 == true && flag2 == true) {

				log.info("The count of screen values are  greater than 0");
				log.info("The values are formmatted as currencies");

			} else {

				log.info("The count of screen values are not greater than 0");
				softAssertion.assertFalse(flag1, "The count of screen values are not greater than 0");
				log.info("The values are not formmatted as currencies");

				softAssertion.assertFalse(flag2, "The values are not formmatted as currencies");
			}

		} catch (Exception e) {
			System.out.println("FAIL");
		} finally {

			softAssertion.assertAll();
		}

	}

	/**
	 * @author Srinivas
	 * @throws Exception
	 * @feature Balance This method is to validate the total balance matches the sum
	 *          of the values in the screen
	 */
	@Then("^Validate the total balance matches the sum of the '(.*)' values in the screen$")
	public void validateTheTotalBalanceValue(String count) throws Exception {
		double actualSum = factory.balancepage().sumOfValues(workbookLocation, BalanceSheetName, 1);
		double totalsum = factory.balancepage().totalBalance(workbookLocation, BalanceSheetName, 1);

		SoftAssert softAssertion = new SoftAssert();

		try {
			if (actualSum == totalsum) {
				log.info("Total balance matches the sum of values");

			} else {

				log.info("The total balances doesnt matches sum of the values");
				softAssertion.assertFalse(true, "The total balances doesnt matches sum of the values");

			}
		} catch (Exception e) {

			log.info("FAIL");
		} finally {

			softAssertion.assertAll();
		}
	}

}
