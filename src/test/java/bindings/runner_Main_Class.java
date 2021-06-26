package bindings;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class runner_Main_Class {

	public static String log_File_Name;
	static {
		log_File_Name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MMM_yyyy HH-mm-ss"));
		System.setProperty("current.date.time", log_File_Name);
		log_DIR_Custom_Listener();
		System.out.println("Custom Listener");
		}
	
	static Logger log = Logger.getLogger(runner_Main_Class.class.getName());
	
	public static void main(String[] args) {
		
		
		
		log.info("Entry point triggered. Start Cucumber trigger");
		
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
	
	
	
	
	
}
