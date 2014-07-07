package com.rishabhSoft.selenium.JCDecaux.commonLibrary;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLsWriterUtility {
	
	private static HSSFWorkbook workbook = null;
	
    public static void writeDataInXLs(String fileName, String sheetName, int rowNum, int columnNum, String value){

		try{
			FileInputStream fis = new FileInputStream(fileName);
			workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			HSSFRow row;
			if(sheet.getRow(rowNum) != null)
				row = sheet.getRow(rowNum);
			else
				row = sheet.createRow(rowNum);
			row.createCell(columnNum).setCellValue(value);
			FileOutputStream fileOut =  new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
		}
		catch ( Exception ex ) {
			ex.getStackTrace();
		}
    }
}
