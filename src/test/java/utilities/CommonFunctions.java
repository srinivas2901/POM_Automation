package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;

public class CommonFunctions extends ReadWrite {

	public static WebDriver driver;
	public static Wait<WebDriver> wait;
	public static String Output_Folder_Name = null;
	public static boolean verifyStatus = true;
	public static boolean verifyStatusFalse = false;
	public static String verifyErrorMessage = "";
	public static Scenario HTML_Report = null;
	public static HashMap<String, String> testDataExcel = new HashMap<String, String>();
	public static String TestDataSheetName = "";
	public static String PageName = "";
	public static String DataType = "";
	public static String environment = null;
	public static Logger logger;
	public static Map<String, String> context = new HashMap<String, String>();
	public static List<String> errorMessagesCollection = new ArrayList<String>();
	public static Integer Errorcount = 0;

	public static Integer pageLoadCount = 0;
	public static int randomStringlength = 10;
	public static String Browser;

	public static final int MOBILE_WIDTH = 200;
	public static final int MOBILE_HEIGHT = 600;

	public static String filepath;
	public static FileInputStream in;

	public static ReadWrite readWriteExcel = new ReadWrite();
	public String workbookLocation = null;
	public static String BalanceSheetName = "Balance";
	public StringBuffer sb;
	public By by_Locator = null;
	public StringBuilder sbuilder;
	public Actions send_Data_Actions;
	public Select send_Data_select;
	public static WebDriverWait send_Data_Wait;
	public DateFormat dateFormatObject = new SimpleDateFormat("dd/MM/YYYY");
	public Date dateObj = new Date();
	public static String methodNameofCallingMethod = null;
	public List<WebElement> list_Of_Elements = null;
	public boolean status_Flag = false;
	public static String RESULT_RUNTIME_COLUMN_NAME = "Runtime Result on";
	protected int gridCountBefore;
	public String workbook_Location_local = null;
	public static JavascriptExecutor executor;
	public static MyProperties propertiesObj = new MyProperties();

	/**
	 * @author AF05830 log4j property setting or config
	 */
	public static void logPropertyConfig() {
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}

	public static void intiateLogger(String Loggerfile) {
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "SIT_environment.properties";
		PropertyConfigurator.configure(log4jConfigFile);

	}

	public static void insertLogMessage(String message) {
		// Sysout(message);
		System.out.println(message);
	}

	public void gotoHomePage() throws Exception {
		logPropertyConfig();
		environment = propertiesObj.getProperty("environment");
		initiateBrowser(propertiesObj.getProperty("browser"));
	}

	public void initiateBrowser(String browserName) throws Exception {
		Browser = browserName;
		DesiredCapabilities capabilities;
		CommonFunctions.insertLogMessage("Initiating to open browser");
		if (browserName.equalsIgnoreCase("IE")) {

			File file = new File("src//test//resources//drivers//IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setPlatform(Platform.ANY);

			capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			if (propertiesObj.getProperty("browser") != null) {
				CommonFunctions.insertLogMessage(propertiesObj.getProperty("browser"));
				driver = new InternetExplorerDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				CommonFunctions.insertLogMessage("Browser opened");
			}
			unconditionalWait(10);
		} else if (browserName.equalsIgnoreCase("Firefox")) {

			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("AnthemDigital");
			capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			capabilities.setPlatform(Platform.ANY);
			capabilities.setCapability("acceptInsecureCerts", true);

			if (propertiesObj.getProperty("url").startsWith("https")) {
				myprofile.setAcceptUntrustedCertificates(true);
				myprofile.setAssumeUntrustedCertificateIssuer(false);
			}

		} else if (browserName.equalsIgnoreCase("Chrome")) {

			try {
				String downloadFilepath = System.getProperty("user.dir");

				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);

				/* Save Chrome Opions */

				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension",
						false); /* Adding the function to remove extension disabled popuup */
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--test-type");
				options.setExperimentalOption("useAutomationExtension", false);

				chromePrefs.put("pdfjs.disabled", true);
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + File.separator + "chromedriver.exe");
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(
						Long.parseLong(propertiesObj.getProperty("Timeout").toString()), TimeUnit.SECONDS);

				driver.navigate().to(propertiesObj.getProperty("url"));
				WebDriver.Options option = driver.manage();
				option.window().maximize();
				option.timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
				option.deleteAllCookies();

			} catch (Exception e) {
				try {
					driver.quit();
				} catch (Exception e1) {
				}
				try {
					driver.close();
				} catch (Exception e1) {
				}
			}

		}
	}

	/**
	 * Handles multiple window Control passes to child window
	 */
	public static void windowHandle() throws Exception {
		String base = driver.getWindowHandle();
		System.out.println(base);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.manage().window().maximize();
		}
	}

	public void readPropertyFile(String environemnt) {
		propertiesObj = new MyProperties();
		if (environemnt.equalsIgnoreCase("SIT")) {
			filepath = "src/test/resources/PropertiesFile/SIT_environment.properties";
			try {
				in = new FileInputStream(System.getProperty("user.dir") + "/" + filepath);
				propertiesObj.load(in);
				System.out.println(System.getProperty("user.dir") + "/" + filepath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param elementToBeLoaded
	 * @return web element
	 * @throws Exception Checks for visibility of the element
	 */
	public WebElement isElementLoaded(String elementToBeLoaded) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					Long.parseLong(propertiesObj.getProperty("Timeout").toString()));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementToBeLoaded)));
		} catch (StaleElementReferenceException e) {
			CommonFunctions.insertLogMessage("Unable to load element:" + elementToBeLoaded);
			verifyMethod("Unable to load element:" + elementToBeLoaded, false);
			verifyResult();
			Assert.fail("Unable to load element:" + elementToBeLoaded);
		}

		return element;
	}

	/**
	 * Sleeps for specified time Given parameter will multiplies with 1000
	 * milliseconds.
	 * 
	 * @param Seconds in numeric
	 */
	public static void unconditionalWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			verifyMethod("Thread Interrupted", false);
			verifyResult();
			Assert.fail("Thread Interrupted");
			e.printStackTrace();
		}
	}

	/**
	 * Clicks on Locator
	 * 
	 * @param Locator
	 */
	public void click(String locator) {
		waitForElementToBeVisible(locator);

		By byLocator = elementLocator(locator);
		try {
			if (isElementExists(byLocator)) {

				driver.findElement(byLocator).click();
			}
		} catch (Exception e) {
			CommonFunctions
					.insertLogMessage("Unable to find/perform click action on the element using locator :" + locator);
			verifyMethod("Unable to find/perform click action on the element using locator :" + locator, false);
			verifyResult();
			Assert.fail("Unable to find/perform click action on the element using locator :" + locator);

			e.printStackTrace();
		}

	}

	public void click1() {

	}

	/**
	 * Verifies visibility of the locator
	 * 
	 * @param Locator
	 * @return boolean Value
	 */

	public static boolean isDisplayed(String locator) {
		Boolean value = false;
		try {
			By byLocator = elementLocator(locator);

			value = driver.findElement(byLocator).isDisplayed();

		} catch (NoSuchElementException e) {

			CommonFunctions.insertLogMessage("Element is not displayed:" + locator);

			value = false;
		}
		return value;
	}

	/**
	 * 
	 * @param errorMsg
	 * @param status   verifies the condition and on failure ie status=false, gives
	 *                 the error message passed as param
	 */

	public static void verifyMethod(String errorMsg, boolean status) {

		verifyStatus = status;
		if (!status) {
			verifyErrorMessage = verifyErrorMessage + "\n" + errorMsg;
		}
	}

	/**
	 * Verifies result set
	 */
	public static void verifyResult() {

		if ((!verifyStatus) && !(verifyErrorMessage.isEmpty())) {
			logger.error("Error Details Captured at :" + getCurrentTimeStamp() + "\n");
			logger.error(verifyErrorMessage);
			embedScreenshot(HTML_Report, "Error Screenshot");
			System.out.println("Count" + Errorcount);
			errorMessagesCollection.add(verifyErrorMessage);
			Errorcount++;
		} else {
			System.out.println(("Verification Pass"));
			verifyErrorMessage = "";
		}

		Errorcount++;

	}

	/**
	 * This is used to collect all the assert failure and fail the build at last
	 */
	public void verifyMethodResultCapture() {
		System.out.println("Size of Error Collection List::" + errorMessagesCollection.size());
		if (errorMessagesCollection.size() > 0) {
			Assert.fail();
		} else
			System.out.println("ALL THE VERIFICATION HAS PASSED");
	}

	/**
	 * 
	 * @param result
	 * @param name   this method is used for taking failure screenshot
	 */

	/* commented this inorder to use htmlunitdriver */
	public static void embedScreenshot(Scenario result, String name) {
		File screenshot = takeScreenShots(name);
		InputStream screenshotStream;
		try {
			if (result.isFailed()) {
				screenshotStream = new FileInputStream(screenshot);
				result.embed(IOUtils.toByteArray(screenshotStream), "image/jpeg");
			}

		} catch (FileNotFoundException e) {
			CommonFunctions.insertLogMessage("Unable to find file:" + screenshot);
			Assert.fail("Unable to find file:" + screenshot);
			e.printStackTrace();
		} catch (IOException e) {
			verifyMethod("", false);
			verifyResult();
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public static Timestamp getCurrentTimeStamp() {
		java.util.Date date = new java.util.Date();
		return (new Timestamp(date.getTime()));
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public static By elementLocator(String locator) {
		if (locator.startsWith("xpath")) {
			return By.xpath(locator.substring(6));
		}

		else if (locator.startsWith("css"))
			return By.cssSelector(locator.substring(4));

		else if (locator.startsWith("id"))
			return By.id(locator.substring(3));

		else if (locator.startsWith("class"))
			return By.className(locator.substring(6));

		else if (locator.startsWith("name"))
			return By.name(locator.substring(5));

		else if (locator.startsWith("tag"))
			return By.tagName(locator.substring(4));

		else if (locator.startsWith("link"))
			return By.linkText(locator.substring(5));

		else
			CommonFunctions.insertLogMessage("Invalid locator format: " + locator);
		throw new IllegalArgumentException("Invalid locator format: " + locator);
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public static WebElement webElmnt(String locator) {
		if (locator.startsWith("xpath")) {
			return driver.findElement(By.xpath(locator.substring(6)));
		}

		else if (locator.startsWith("css"))
			return driver.findElement(By.cssSelector(locator.substring(4)));

		else if (locator.startsWith("id"))
			return driver.findElement(By.id(locator.substring(3)));

		else if (locator.startsWith("class"))
			return driver.findElement(By.className(locator.substring(6)));

		else if (locator.startsWith("name"))
			return driver.findElement(By.name(locator.substring(5)));

		else if (locator.startsWith("tag"))
			return driver.findElement(By.tagName(locator.substring(4)));

		else if (locator.startsWith("link"))
			return driver.findElement(By.linkText(locator.substring(5)));

		else
			CommonFunctions.insertLogMessage("Unable to locate web element with the locator format: " + locator);
		throw new IllegalArgumentException("Invalid locator format: " + locator);
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public static List<WebElement> webElemnts(String locator) {
		if (locator.startsWith("xpath")) {
			return driver.findElements(By.xpath(locator.substring(6)));
		}

		else if (locator.startsWith("css"))
			return driver.findElements(By.cssSelector(locator.substring(4)));

		else if (locator.startsWith("id"))
			return driver.findElements(By.id(locator.substring(3)));

		else if (locator.startsWith("class"))
			return driver.findElements(By.className(locator.substring(6)));

		else if (locator.startsWith("name"))
			return driver.findElements(By.name(locator.substring(5)));

		else if (locator.startsWith("tag"))
			return driver.findElements(By.tagName(locator.substring(4)));

		else if (locator.startsWith("link"))
			return driver.findElements(By.linkText(locator.substring(5)));

		else
			CommonFunctions.insertLogMessage("Unable to locate web elements with the locator format: " + locator);
		throw new IllegalArgumentException("Invalid locator format: " + locator);
	}

	/**
	 * 
	 * @param locator
	 * @param text
	 * @throws Exception For typing character in UI
	 */
	public static void type(String locator, String text) {
		clear(locator);
		By byLocator = elementLocator(locator);
		try {
			driver.findElement(byLocator).sendKeys(text);
		} catch (Exception e) {
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Unable to type text:" + text);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
		closeAllBrowsers();
	}

	/**
	 * 
	 * @param locator Clears the text box
	 */
	public static void clear(String locator) {

		By byLocator = elementLocator(locator);
		if (isElementExists(byLocator))
			try {
				driver.findElement(byLocator).clear();
			}

			catch (Exception e) {
				verifyMethod("", false);
				verifyResult();
				Assert.fail("Could not clear the element.");
				e.printStackTrace();

			}
	}

	/**
	 * 
	 * @param locator Waits for the element to be visible on screen.
	 */
	public void waitForElementToBeVisible(String locator) {
		CommonFunctions.insertLogMessage("Waiting for the element with xpath" + locator + " to be visible");
		By byLocator = elementLocator(locator);
		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Long.parseLong(propertiesObj.getProperty("Timeout").toString()), TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		} catch (TimeoutException e) {
			verifyMethod("", false);
			verifyResult();
			CommonFunctions.insertLogMessage("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for visibility of element located by " + locator);
			e.getLocalizedMessage();
		}

	}

	public void waitForInvisibilityOfElement(String locator) {
		CommonFunctions.insertLogMessage("Waiting for the element with xpath" + locator + " to be invisible");
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver, Long.parseLong(propertiesObj.getProperty("Timeout").toString()));
		verifyMethod("Unable to find the element using locator :" + locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		try {
			if (webElmnt(locator).isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
			}
		} catch (TimeoutException e) {
			CommonFunctions.insertLogMessage("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for invisibility of element located by " + locator);
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for invisibility of element located by " + locator);
			e.printStackTrace();
		}

	}

	public void waitForElementToBeClickable(String locator) {
		CommonFunctions.insertLogMessage("Waiting for the element with xpath" + locator + " to be clickable");
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver, Long.parseLong(propertiesObj.getProperty("Timeout").toString()));
		verifyMethod("Unable to find the element using locator :" + locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(byLocator));
		} catch (TimeoutException e) {
			CommonFunctions.insertLogMessage("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for the element to be clickable located by " + locator);
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for the element to be clickable located by " + locator);
			e.printStackTrace();
		}

	}

	public void waitForElementAttributeToBeNonEmpty(String locator, String attribute) {
		CommonFunctions.insertLogMessage("Waiting for the element with xpath" + locator + "Attribute to be non-empty");
		By byLocator = elementLocator(locator);

		WebElement element = webElmnt(locator);
		wait = new WebDriverWait(driver, Long.parseLong(propertiesObj.getProperty("Timeout").toString()));
		verifyMethod("Unable to find the element using locator :" + locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		try {
			wait.until(ExpectedConditions.attributeToBeNotEmpty(element, attribute));
		} catch (TimeoutException e) {
			CommonFunctions.insertLogMessage("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for element's attribute to be non-empty located by " + locator);
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Timed out after " + propertiesObj.getProperty("Timeout").toString()
					+ " seconds waiting for element's attribute to be non-empty located by " + locator);
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param locator
	 * @param option  To select option from dropdown
	 */

	public void selectFromDropdown(String locator, String option) {
		CommonFunctions.insertLogMessage(
				"Attempting to select the option: '" + option + "' from dropdown having xpath: " + locator);
		waitForElementToBeVisible(locator);
		By byLocator = elementLocator(locator);
		try {
			Select dropdown = new Select(driver.findElement(byLocator));
			dropdown.selectByVisibleText(option);
		} catch (InvalidSelectorException e) {
			CommonFunctions.insertLogMessage("Option:" + option + " is not visible for selection");
			verifyMethod("Option:" + option + " is not visible for selection", false);
			verifyResult();
			Assert.fail("Option:" + option + " is not visible for selection");
			e.printStackTrace();
		}

	}

	/**
	 * Close all the browser
	 * 
	 */

	public static void closeAllBrowsers() {
		CommonFunctions.insertLogMessage("Closing browser");
		driver.quit();
		CommonFunctions.insertLogMessage("browser closed");
	}

	/**
	 * 
	 * @param by
	 * @return boolean Checks whether element is visible in DOM
	 */
	public static boolean isElementExists(By by) {
		Boolean value = false;
		try {
			driver.findElement(by);
			value = true;
		} catch (ElementNotVisibleException e) {
			value = false;
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Element does not visible" + by);
			e.printStackTrace();
		}
		return value;
	}

	public static boolean isWebElementExists(String locator) {
		Boolean isPresent = true;
		try {
			CommonFunctions.insertLogMessage("Verifying whether web-element exist with xpath: " + locator);
			By byLocator = elementLocator(locator);
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(4, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));

		} catch (Exception e) {
			isPresent = false;
			CommonFunctions.insertLogMessage("web-element doesnot exist with xpath: " + locator);
		}
		return isPresent;
	}

	/**
	 * 
	 * @param locator
	 * @return Return text from a locator
	 */
	public String getText(String locator) {
		String text = null;
		By byLocator = elementLocator(locator);
		if (isElementExists(byLocator)) {
			try {
				text = driver.findElement(byLocator).getText();
			} catch (WebDriverException we) {

				verifyMethod("Element Text is not visible", false);
				verifyResult();
				Assert.fail("Element Text is not present" + locator);
				we.printStackTrace();
			}
		}
		return text;
	}

	/**
	 * 
	 * @param msg
	 * @param status
	 * @throws Exception Captures fail screen shots
	 */

	public static File takeScreenShots(String picture) {
		File temp = null;
		try {
			CommonFunctions.insertLogMessage("Attempting to take screenshot");
			temp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(temp, new File(
					System.getProperty("user.dir") + "/target/screenshots/" + File.separator + picture + ".jpeg"));

		} catch (Exception e) {
			CommonFunctions.insertLogMessage("Getting error while taking screenshot");
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * @return boolean value
	 */
	public boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, 1 /* timeout in seconds */);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (NoAlertPresentException e) {
			verifyMethod("", false);
			verifyResult();
			Assert.fail("No alert is found");
			e.printStackTrace();

		}
		return foundAlert;
	}

	/**
	 * Page scroll down
	 */
	public void Scrolldown() {
		System.out.println("Scrollllll");
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).perform();
	}

	/**
	 * Page scroll up
	 */
	public void scrollUp() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_UP).perform();
	}

	/**
	 * Press enter through keyboard
	 */

	public void enterThrougKey() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).perform();
	}

	/**
	 * Click through javascript
	 * 
	 * @param locator
	 */

	public void executethroughScript(String locator) {
		CommonFunctions.insertLogMessage("clicking through Java script executor to the web-element: " + locator);
		By byLocator = elementLocator(locator);
		WebElement element = driver.findElement(byLocator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		CommonFunctions.insertLogMessage("clicked through Java script executor to the web-element: " + locator);
	}

	public void clickThroughJavaScript(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * @author Srinivas
	 * @param locator
	 */
	public void clickThroughJavaScriptLoc(String locator) {
		By byLocator = elementLocator(locator);
		WebElement element = driver.findElement(byLocator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Getting all the data's of a locator
	 * 
	 * @param locator
	 * @return
	 */
	public List<String> getListOfData(String locator) {
		List<String> data = new ArrayList<String>();
		By byLocator = elementLocator(locator);
		List<WebElement> dataListApp = driver.findElements(byLocator);
		int dataCountApp = dataListApp.size();
		if (dataCountApp > 0) {
			for (int i = 0; i < dataCountApp; i++) {
				data.add(dataListApp.get(i).getText().toString());
			}
		}
		return data;
	}

	/**
	 * Getting list of data of an element in lower case
	 * 
	 * @param locator
	 * @return
	 */
	public List<String> getListOfDataInLowercase(String locator) {
		List<String> data = new ArrayList<String>();
		By byLocator = elementLocator(locator);
		List<WebElement> dataListApp = driver.findElements(byLocator);
		int dataCountApp = dataListApp.size();
		try {

			if (dataCountApp > 0) {
				for (int i = 0; i < dataCountApp; i++) {
					data.add(dataListApp.get(i).getText().toString().toLowerCase());
				}
			}
		} catch (Exception e) {

		}
		return data;
	}

	/**
	 * Verify if radio button is selected
	 * 
	 * @param locator
	 * @param value
	 * @return
	 */
	public Boolean isRadioSelected(String locator, String value) {
		By eleLocator = elementLocator(locator);
		Boolean flag = false;
		if (isElementExists(eleLocator)) {
			Select select = new Select(driver.findElement(eleLocator));
			List<WebElement> allOptions = select.getOptions();
			for (WebElement options : allOptions) {
				if (options.getText() == value) {
					flag = options.isSelected();
				}
			}
		}
		return flag;
	}

	/**
	 * Verifying error message for Blank text entry
	 * 
	 * @param value
	 * @param secondLocator
	 * @param errorLocator
	 * @param errormessage
	 */
	public void validateErrorMessageForBlankTextEntry(String value, String secondLocator, String errorLocator,
			String errormessage) {
		if (value.equals("")) {
			Scrolldown();
			scrollUp();
			if (secondLocator.equals("")) {
				enterThrougKey();

			} else {
				click(secondLocator);
			}

			unconditionalWait(2);
			String inlineErrorMessage = getText(errorLocator);
			System.out.println("Error message expectd: " + inlineErrorMessage);
			System.out.println("Value to be entered:" + value);
			System.out.println("error message to be displayed:" + errormessage);
			verifyMethod("Error message for blank text entry", errormessage.equalsIgnoreCase(inlineErrorMessage));
			verifyResult();
		}
	}

	/**
	 * Verifying error message for Invalid Text
	 * 
	 * @param value
	 * @param secodLocator
	 * @param errorLocator
	 * @param errormessage
	 */
	public void validateErrorMessageForInvalidTextEntry(String value, String secodLocator, String errorLocator,
			String errormessage) {
		if (!value.matches("[a-zA-Z '-]+")) {
			click(secodLocator);
			String inlineErrorMessage = getText(errorLocator);
			System.out.println("Value to be entered:" + value);
			System.out.println("error message to be displayed:" + errormessage);
			verifyMethod("Error message for invalid text entry", errormessage.equalsIgnoreCase(inlineErrorMessage));
			verifyResult();
		}
	}

	/**
	 * verify error message for unselected radio button
	 * 
	 * @param errorLocator
	 * @param errormessage
	 */
	public void validateErrorMessageForRadioButtonNotSelected(String errorLocator, String errormessage) {
		String inlineErrorMessage = getText(errorLocator);
		verifyMethod("Error message for invalid text entry", errormessage.equalsIgnoreCase(inlineErrorMessage));
		verifyResult();
	}

	/**
	 * Returns page title
	 * 
	 * @return
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Returns CSS value of an element
	 * 
	 * @param locator
	 * @param attribute
	 */
	public String getCssValue(String locator, String attribute) {
		System.out.println(locator);
		By byLocator = elementLocator(locator);
		WebElement element = driver.findElement(byLocator);
		String cssValue = element.getCssValue(attribute);
		return cssValue;
	}

	/**
	 * Returns attribute value
	 * 
	 * @param locator
	 * @param attribute
	 */
	public String getAttributeValue(String locator, String attribute) {
		By byLocator = elementLocator(locator);
		WebElement element = driver.findElement(byLocator);
		String attributeValue = element.getAttribute(attribute);
		return attributeValue;
	}

	/**
	 * Waits for the Timeout specified in the Properties file till the page gets
	 * loaded
	 */
	public static void waitForThePageToLoad() {
		try {
			driver.manage().timeouts().pageLoadTimeout(
					Long.parseLong(propertiesObj.getProperty("pageLoadTimeout").toString()), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Requested page failed to load in " + propertiesObj.getProperty("pageLoadTimeout").toString()
					+ " seconds.");
		} catch (Exception e) {
			e.getLocalizedMessage();
			try {
				Thread.sleep(Long.parseLong(propertiesObj.getProperty("pageLoadTimeout").toString()));
			} catch (Exception e1) {
				e1.getLocalizedMessage();
			}
		}
	}

	public static void waitImplicitly() {
		try {
			driver.manage().timeouts().implicitlyWait(
					Long.parseLong(propertiesObj.getProperty("pageLoadTimeout").toString()), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Requested page failed to load in " + propertiesObj.getProperty("pageLoadTimeout").toString()
					+ " seconds.");
		}

	}

	/**
	 * Hover on element through mouse
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void mouseHover(String locator) {
		By byLocator = elementLocator(locator);
		WebElement element = driver.findElement(byLocator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	/**
	 * Switch through window
	 */
	public void SwitchWindow() {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String curWindow : allWindows) {
				driver.switchTo().window(curWindow);
			}
		} catch (NoSuchWindowException exception) {
			verifyMethod("", false);
			verifyResult();
			Assert.fail("Failed to switch to window");
		}

	}

	public void ScrollDown() {
		unconditionalWait(2);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,2000)", "");
	}

	public void scrollToViewElement(String locator) {

		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", webElmnt(locator));

	}

	public void scrollToRight(String locator) {

		Actions actions = new Actions(driver);
		actions.moveToElement(webElmnt(locator));
		actions.perform();
	}

	public static boolean isElementVisible(String locator) {
		Boolean value = true;
		try {
			By byLocator = elementLocator(locator);
			wait = new WebDriverWait(driver, Long.parseLong(propertiesObj.getProperty("MinimumTimeout").toString()));
			wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		} catch (Exception e) {

			value = false;
		}
		return value;
	}

	public static boolean isElementClickable(String locator) {
		Boolean value = true;
		try {
			By byLocator = elementLocator(locator);
			wait = new WebDriverWait(driver, Long.parseLong(propertiesObj.getProperty("MinimumTimeout").toString()));
			wait.until(ExpectedConditions.elementToBeClickable(byLocator));
		} catch (Exception e) {

			value = false;
		}
		return value;
	}

	public void scrollToViewElement(WebElement elmnt) {

		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", elmnt);

	}

	public void scripts_Status(String data_To_Enter, String text_location) throws IOException {
		// Use this to write to notepad when AW required are complete in CRM.
		BufferedWriter bw;

		String methodNameofCallingMethod = new Exception().getStackTrace()[1].getMethodName();
		log.info(methodNameofCallingMethod);
		File file = null;
		file = new File(text_location);
		sb = new StringBuffer(todays_Date_With_Time());
		data_To_Enter = sb.append(" ").append(" :- ").append(data_To_Enter).toString();

		if (!file.exists() && file.isDirectory()) {
			file.createNewFile();
			bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
			bw.write(" ------ List of scripts which were executed ------ ");

		} else {
			bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
			bw.write(data_To_Enter);
		}
		bw.newLine();
		log.info("Done");
		log.info(file.getAbsolutePath());

		bw.flush();
		bw.close();
	}

	public String todays_Date() {
		// Todays date without time
		String todaysDate = dateFormatObject.format(Calendar.getInstance().getTime()).toString();
		return todaysDate;
	}

	public static String todays_Date_With_Time() {
		// Todays date with Time
		String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss S")).toString();
//			log.info(format);
		return format;
	}

	public void readFeatureFile(String featureName) throws IOException {

		log.info("Feature name is : " + featureName);
		sbuilder = new StringBuilder(System.getProperty("user.dir")).append(File.separator)
				.append("src/test/java/resources/FeatureFile/").append(featureName);
		featureName = sbuilder.toString();

		sbuilder = new StringBuilder();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(featureName))) {

			String line;
			while ((line = br.readLine()) != null) {
				sbuilder.append(line).append("\n");
			}

		} catch (IOException e) {
			log.info("Exception occured while writing data to master config feature files");
			log.info("IOException: %s%n", e);
		}
		String filePath = System.getProperty("user.dir") + File.separator + "BDD_Config.feature";

		Files.write(Paths.get(filePath), sbuilder.toString().getBytes());

		log.info("Writing feature to BDD_Config.feature");
		log.info("Feature file data.");
		try (BufferedReader br = Files.newBufferedReader(Paths.get(featureName))) {
			String line;
			while ((line = br.readLine()) != null) {
				log.info(line);
			}

		} catch (IOException e) {
			log.info("Feature exception");
		}
	}

}
