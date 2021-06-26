package bindings;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import utilities.CommonFunctions;
import utilities.ReadWrite;
import utilities.SiteFactory;

public class Balance extends CommonFunctions {

	private SiteFactory factory;

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

				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment1", "The count of screen values are  greater than 0", 1);
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment3", "The values are formmatted as currencies", 1);
				
			} else {
				softAssertion.assertFalse(flag1, "The count of screen values are not greater than 0");
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment1", "The count of screen values are not greater than 0", 1);
				
				softAssertion.assertFalse(flag2, "The values are not formmatted as currencies");
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment3", "The values are not formmatted as currencies", 1);
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
	@SuppressWarnings("static-access")
	@Then("^Validate the total balance matches the sum of the '(.*)' values in the screen$")
	public void validateTheTotalBalanceValue(String count) throws Exception {
		double actualSum = factory.balancepage().sumOfValues(workbookLocation, BalanceSheetName, 1);
		double totalsum = factory.balancepage().totalBalance(workbookLocation, BalanceSheetName, 1);

		SoftAssert softAssertion = new SoftAssert();

		try {
			if (actualSum == totalsum) {
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment2", "Total balance matches the sum of values", 1);
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Status2", "Pass", 1);
				System.out.println("Pass");

			} else {

				softAssertion.assertFalse(true, "The total balances doesnt matches sum of the values");
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Comment2", "The total balances doesnt matches sum of the values", 1);
				ReadWrite.write_Cell_Data_Specific_Column(workbookLocation,
						BalanceSheetName, "Status2", "Fail", 1);
				System.out.println("Fail");

			}
		} catch (Exception e) {

			System.out.println("FAIL");
		} finally {

			softAssertion.assertAll();
		}
	}

}
