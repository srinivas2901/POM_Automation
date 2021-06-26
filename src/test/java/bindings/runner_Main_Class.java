package bindings;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class runner_Main_Class {

	public static String log_File_Name;
	static {
		log_File_Name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MMM_yyyy HH-mm-ss"));
		System.setProperty("current.date.time", log_File_Name);
		log_DIR_Custom_Listener();
		System.out.println("Custom Listener");
		}
	
	static Logger log = Logger.getLogger(runner_Main_Class.class.getName());
	
	public static ExtentHtmlReporter htmlReporter;
	protected static String ExtentReportGeneration;
	public static ExtentReports extent;
	public static ExtentTest test, parent, child, child2;
	protected static String htmlReportDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MMM_yyyy HH-mm-ss")).toString();
	
	public static void main(String[] args) {
		
		
		
		log.info("Entry point triggered. Start Cucumber trigger");
		
		/*
		 * log.info("Before"); StringBuffer sb = new
		 * StringBuffer(ExtentReportLocation()); htmlReporter = new
		 * ExtentHtmlReporter(sb.append(htmlReportDate).append(".html").toString());
		 * scenario_Step_Def = scenario_Before; ExtentReportGeneration = sb.toString();
		 * log.info("HTML Report location  -  " + ExtentReportGeneration);
		 * htmlReporter.setAppendExisting(true); extent = new ExtentReports();
		 * 
		 * extent.attachReporter(htmlReporter); parent =
		 * extent.createTest(scenario_Step_Def.getName()); parent.log(Status.INFO,
		 * "Scenario Name is " + scenario_Step_Def.getName());
		 */

		
		
		
		Result resultObj = JUnitCore.runClasses(TestRunner.class);
		for (Failure failure : resultObj.getFailures()) {
			log.info(failure.toString());
		}

		log.info("End of Runner main Class");
	}

	
	public static void log_DIR_Custom_Listener() {
		File file = new File(execution_Log());
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Folder is created!");
			} else {
				System.out.println("Failed to create Folder. Hence terminating the execution.");
				System.exit(0);
			}
		} else if (file.exists() == true) {
			System.out.println("Folder already exists.log_DIR_Custome_Listener");
		}
	}

	public static String execution_Log() {
		String executionLog = "Execution_Log";
		return executionLog;
	}
	
	public static String ExtentReportLocation() {
		log.info("Sending Extent report location");
		StringBuffer sb=new StringBuffer(100);
		return sb.append(System.getProperty("user.dir")).append(File.separator).append("Extent_Report_").toString();
	}
	
	
	
	
}
