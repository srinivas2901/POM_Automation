package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ReadWrite {

	static FileInputStream fis;
	static XSSFWorkbook wb = null;
	static XSSFSheet sheet = null;
	static XSSFRow row = null;
	static XSSFCell cell;
	static int cell_Value = -1;
	static int row_Iterator = -1;
	static int column_Iterator = -1;
	static FileOutputStream fos = null;
	public static String methodNameofCallingMethod = null;
	static Logger log = Logger.getLogger(ReadWrite.class.getName());
	public final static String execution_Pass = "Pass";
	public final static String execution_Fail = "Fail";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static String data_Driven_Excel_Location() {
		return "D:\\EclipseWorkspace\\massmutual\\src\\test\\resources\\TestData\\Balance.xlsx";
	}

	public static String data_Driven_Sheet_Name() {
		return "Input";
	}

	public static String todays_Date_With_Time() {
		// Todays date with Time
		String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss S")).toString();
//			log.info(format);
		return format;
	}

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(data_Driven_Excel_Location());
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(data_Driven_Sheet_Name());

		log.info("Number of Rows :" + sheet.getLastRowNum());
		log.info("Number of Columns :" + sheet.getRow(0).getLastCellNum());

		log.info("\n Looping to get all values");
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			log.info("Closing workbook");
			wb.close();
		}
	}

	public String get_Cell_Values_Input(String workbook_Location, String sheet_name, String cell_To_Compare,
			int static_Row) throws Exception {
		log.info("---------------------Start reading excel");
		log.info("Workbook location is :- " + workbook_Location);
		log.info("Worksheet name is :- " + sheet_name);
		log.info("Cell name is :- " + cell_To_Compare);
		log.info("Calling first method is : -  " + new Exception().getStackTrace()[1].getMethodName());
		log.info("Calling first method line is : -  " + new Exception().getStackTrace()[1].getLineNumber());
		log.info("Calling second method is : -  " + new Exception().getStackTrace()[2].getMethodName());
		log.info("Calling second method line is : -  " + new Exception().getStackTrace()[2].getLineNumber());

		FileInputStream fis;
		fis = new FileInputStream(data_Driven_Excel_Location());
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheet_name);
		log.info("Count of Rows: " + sheet.getLastRowNum());
		log.info("Count of Columns: " + sheet.getRow(0).getLastCellNum());
		row = sheet.getRow(0);
		boolean column_Flag = false;
		// Extract the last row num
		int col_number = -1;
		for (int i = 0; i <= (row.getLastCellNum() - 1); i++) {
			log.info(row.getCell(i).getStringCellValue().trim());
			if (row.getCell(i).getStringCellValue().trim().equals(cell_To_Compare)) {
				col_number = i;
				column_Flag = true;
				log.info("Value present at column number - " + col_number);
				break;
			}
		}
		log.info(column_Flag);
		Assert.assertTrue(column_Flag, "Column name cannot be found. Please check column name passed.");

		row = sheet.getRow(static_Row);
		// Get cell value
		cell = row.getCell(col_number);
		String celltype = cell.getCellTypeEnum().toString();
		log.info(celltype);

		switch (celltype) {
		case "NUMERIC":
		case "FORMULA":
			log.info("Converting Numeric value to String");
			DataFormatter df = new DataFormatter();
			cell_To_Compare = df.formatCellValue(cell).toString();
			break;

		default:
			cell_To_Compare = cell.getStringCellValue().toString();
			break;
		}
		log.info("Value found at " + static_Row + " ," + col_number + " is : " + cell_To_Compare.trim());
		try {
			wb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("Implementing TRIM function to remove Leading and Trailing spaces");
		cell_To_Compare = cell_To_Compare.trim().toString();

		log.info("___________________End reading excel");

		// return cell.getNumericCellValue();
		return cell_To_Compare;

	}

	// Checking the presence of column name
	/**
	 * @throws IOException
	 */
	/**
	 * @param row_number
	 * @param sheet_name
	 * @param workbook_location
	 * @throws IOException
	 */
	public static int Check_Column_Name(String workbook_location, String sheet_name, String execution_actual_result,
			int row_number) throws IOException {

		fis = new FileInputStream(workbook_location);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheet_name);
		row = sheet.getRow(0);
		int Column_Count = row.getLastCellNum();

		log.info("sheet_name is :: " + sheet_name);

		String Actual_Column;
		boolean Column_Present = false;
		log.info("Total columns are : " + Column_Count);

		for (int i = 0; i < Column_Count; i++) {

//				log.info("Debug point is here");
			Actual_Column = row.getCell(i).getStringCellValue();
			log.info(i + Actual_Column);

			if (Actual_Column.equalsIgnoreCase(execution_actual_result)) {
				Column_Present = true;
				log.info(i);
				Column_Count = i;
				break;
			}
		}
		log.info(" Is " + execution_actual_result + "column exists : " + Column_Present);

		fis.close();

		fos = new FileOutputStream(workbook_location);

		sheet = wb.getSheet(sheet_name);
		row = sheet.getRow(row_number);

		if (Column_Present == false) {
			log.info("No such column name is exists and Hence adding a new column by the name of "
					+ execution_actual_result);
			Column_Count = row.getLastCellNum();
			sheet.getRow(0).createCell(Column_Count);
			sheet.getRow(0).getCell(Column_Count).setCellValue(execution_actual_result);

			wb.write(fos);
		}

		log.info("New column count is :" + row.getLastCellNum());
		log.info("Logging the column names");
		for (int j = 0; j < row.getLastCellNum(); j++) {
			log.info(j + sheet.getRow(0).getCell(j).getStringCellValue());
		}

		fos.close();
//				wb.close();

		log.info("Printing column count is : " + Column_Count);

		return Column_Count;
	}

	// Use this method to write data to a cell using the columnn name
	@SuppressWarnings("deprecation")
	public static void write_Cell_Data_Specific_Column(String workbook_location, String sheet_name,
			String execution_actual_result_ColumnName, String write_Data, int row_number) throws Exception {
		Assert.assertTrue(write_Data.length() > 0,
				"Data to be  entered in the Actual status is a null. Hence terminated execution.");
		log.info(workbook_location);
		log.info(sheet_name);
		log.info(execution_actual_result_ColumnName);
		log.info(write_Data);
		log.info(row_number);

		log.info("Data to be entered is " + write_Data);

		fis = new FileInputStream(workbook_location);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheet_name);
		log.info("Get 0th Row");
		row = sheet.getRow(0);
		int col_number = -1;
		final int columns_Count = sheet.getRow(0).getLastCellNum();
		log.info("Last column number is :" + columns_Count);

		for (int i = 0; i <= columns_Count - 1; i++) {
			log.info(i + row.getCell(i).getStringCellValue().trim());
			if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(execution_actual_result_ColumnName)) {
				col_number = i;
				break;
			}
		}

		// Calling the 'Check column name' method
		log.info(methodNameofCallingMethod);
		if (!methodNameofCallingMethod.contains("Writing_the_Fee_value")) {
			col_number = Check_Column_Name(workbook_location, sheet_name, execution_actual_result_ColumnName,
					row_number);
		}

		log.info("Row number is :" + row_number);
		log.info("Column number is :" + col_number);

		Assert.assertTrue(row_number > -1 && col_number > -1, "Row number or Column number is less than zero.");

		log.info("Writing data at Row :  " + row_number + " ,  column number: " + col_number);
		// Increment or decrement the row number if the runtime value gets changed.
		XSSFRow row_Write = wb.getSheet(sheet_name).getRow(row_number);

		cell = row_Write.createCell(col_number);
		// Actual Result column must not be a null

		IndexedColors cell_Color = null;
		log.info(write_Data);
		if (write_Data.contains(execution_Pass)) {
			cell_Color = IndexedColors.LIGHT_GREEN;
			// Adding Date stamp to the status
			write_Data = write_Data + todays_Date_With_Time();
		} else if (write_Data.contains(execution_Fail) || write_Data.contains("not verified")) {

			cell_Color = IndexedColors.RED;
			// Adding Date stamp to the status
			write_Data = write_Data + todays_Date_With_Time();
		} else {
			cell_Color = IndexedColors.WHITE;
		}
		log.info(write_Data);

		log.info(cell_Color);
		CellStyle style_obj = wb.createCellStyle();
		style_obj.setFillForegroundColor(cell_Color.getIndex());
		style_obj.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cell.setCellType(CellType.STRING);
		cell.setCellStyle(style_obj);
		cell.setCellValue(write_Data);
		wb.getSheet(sheet_name).autoSizeColumn(col_number);

		fos = new FileOutputStream(workbook_location);
		wb.write(fos);
		fos.close();
		wb.close();
		log.info("Status written succesfully to excel and closed workbook.");

	}

}
