package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import utilities.CommonFunctions;
import utilities.SiteFactory;

public class BalancePage extends CommonFunctions {

	public BalancePage(SiteFactory factory, WebDriver driver) {
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}

	@FindBy(how = How.XPATH, using = "//*[contains(@id,'screenname')]")
	private WebElement screenName;

	@FindBy(how = How.XPATH, using = "//*[contains(@id,'values')]")
	private List<WebElement> countValues;

	@FindBy(how = How.XPATH, using = "//*[contains(@id,'total')]")
	private WebElement totalSum;

	public boolean validateUserIsInValueScreen() {
		boolean flag = false;
		if (screenName.isDisplayed())
			flag = true;
		return flag;
	}

	/*
	 * public boolean validateTheCountValue(String count) {
	 * 
	 * boolean flag = true; int count1 = 0; for (int i = 0; i < countValues.size();
	 * i++) { if (!(Integer.parseInt(countValues.get(i).getText().replace("$", ""))
	 * > Integer.parseInt(count))) count1 = 0; else count1 = count1 + 1; } if
	 * (count1 > 0) flag = false;
	 * 
	 * return flag; }
	 */

	public boolean validateTheFormatValue(String formatvalue) throws Exception {
		boolean flag = true;
		String V1 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value1", 1);
		String V2 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value2", 1);
		String V3 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value3", 1);
		String V4 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value4", 1);
		String V5 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value5", 1);
		String V6 = get_Cell_Values_Input(workbookLocation, BalanceSheetName, "TotalBalance", 1);

		if (V1.startsWith(formatvalue) && V2.startsWith(formatvalue) && V3.startsWith(formatvalue)
				&& V4.startsWith(formatvalue) && V5.startsWith(formatvalue) && V6.startsWith(formatvalue)) {
			flag= true;
		}
		else {
			flag = false;
		}
		return flag;
	}

	public double sumOfValues(String workbook_Location, String sheet_name, int static_Row) throws Exception {

		double v1 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value1", 1).replaceAll("[$,]+", ""));
		double v2 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value2", 1).replaceAll("[$,]+", ""));
		double v3 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value3", 1).replaceAll("[$,]+", ""));
		double v4 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value4", 1).replaceAll("[$,]+", ""));
		double v5 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value5", 1).replaceAll("[$,]+", ""));

		double sum = sum(v1, v2, v3, v4, v5);
		return sum;
	}

	private double sum(double v1, double v2, double v3, double v4, double v5) {
		return v1 + v2 + v3 + v4 + v5;
	}

	public boolean Values(String workbook_Location, String sheet_name, int static_Row) throws Exception {

		boolean flag = false;
		double v1 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value1", 1).replaceAll("[$,]+", ""));
		double v2 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value2", 1).replaceAll("[$,]+", ""));
		double v3 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value3", 1).replaceAll("[$,]+", ""));
		double v4 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value4", 1).replaceAll("[$,]+", ""));
		double v5 = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "Value5", 1).replaceAll("[$,]+", ""));

		if (v1 > 0 && v2 > 0 && v3 > 0 && v4 > 0 && v5 > 0)
			flag = true;

		return flag;
	}

	public double totalBalance(String workbook_Location, String sheet_name, int static_Row) throws Exception {

		double totalBalance = Double.parseDouble(readWriteExcel
				.get_Cell_Values_Input(workbookLocation, BalanceSheetName, "TotalBalance", 1).replaceAll("[$,]+", ""));
		return totalBalance;
	}

}
