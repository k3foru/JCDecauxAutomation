package com.rishabhSoft.selenium.JCDecaux.module;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;

import com.rishabhSoft.selenium.JCDecaux.drivers.CommonActions;
import com.rishabhSoft.selenium.JCDecaux.commonLibrary.XLsReaderUtility;
import com.rishabhSoft.selenium.JCDecaux.commonLibrary.XLsWriterUtility;

import static com.rishabhSoft.selenium.JCDecaux.drivers.SetObjectProperties.*;

public class AtlasHome extends CommonActions{

	static String networkName, unitName, unitID, networkID;
	
	public static void enterBudget(){
		sendKeys(atlasHome.getLocatorType("AtlasHome_budget"), "");
	}
	
	public static void selectBrand() throws InterruptedException{
		sendKeys(atlasHome.getLocatorType("AtlasHome_Brand"), "Variety Bakeries");
		Thread.sleep(1000);
		clickElement(atlasHome.getLocatorType("AtlasHome_Brand_choosenBrand"));
	}
	
	public static void selectCampaignStartDate() throws InterruptedException{
		clickElement(atlasHome.getLocatorType("AtlasHome_campaignStartDateCaln"));
		clickElement(atlasHome.getLocatorType("AtlasHome_campaignStartDateCaln_next"));
		clickElement(atlasHome.getLocatorType("AtlasHome_campaignStartDateCaln_Date"));
	}
	
	public static void selectAudienceCategoryIndex(){
		clickElement(atlasHome.getLocatorType("AtlasHome_AudienceCategoryIndex_0"));
	}
	
	public static void selectPlace(){
		pageScrollDown(439, 798);
		clickElement(atlasHome.getLocatorType("AtlasHome_Place_TvAreaSelector"));
		clickElement(atlasHome.getLocatorType("AtlasHome_Place_TvAreaSelector_Border"));
		clickElement(atlasHome.getLocatorType("AtlasHome_Place_TvAreaSelector_Border_Ok"));
	}
	
	public static void clickToCheckAvailabilityBtn(){
		clickElement(atlasHome.getLocatorType("AtlasHome_CheckAvailability"));
		waitForElement(atlasHome.getLocatorType("AtlasHome_SearchAvailability"));
	}
	
	public static void clickToInvestmentOptionsBtn(){
		clickElement(atlasHome.getLocatorType("AtlasHome_InvestmentOptions"));
		waitForElement(atlasHome.getLocatorType("AtlasHome_digitalAvailabilityPanel"));
	}
	
	public static void expandResultDetailDigitalPanel(){
		pageScrollDown(1376, 782);
		clickElement(atlasHome.getLocatorType("AtlasHome_resultDetailDigitalPanel"));
		waitForElement(atlasHome.getLocatorType("AtlasHome_digitalAvailabilityPanel"));
	}
	
	public static void clickOnBookButton() throws InterruptedException{
		pageScrollDown(1576, 782);
		clickElement(atlasHome.getLocatorType("AtlasHome_BookButton"));
		Thread.sleep(2000);
		clickElement(atlasHome.getLocatorType("AtlasHome_AlertOK"));
		waitForElementPresent(atlasHome.getLocatorType("AtlasHome_AlertOK"));
		clickElement(atlasHome.getLocatorType("AtlasHome_AlertOK"));
		Thread.sleep(10000);
	}
	
/*	public static void getSearchData() throws InterruptedException{
		pageScrollDown(439, 798);
		Thread.sleep(1000);
		clickElement(atlasHome.getLocatorType("Search_DayFirst"));
		clickElement(atlasHome.getLocatorType("Search_DayFirst_Child"));
		clickElement(atlasHome.getLocatorType("Search_DayFirst_Child_SubChild"));
		pageScrollDown(1557, 439);
		clickElement(atlasHome.getLocatorType("Search_DayFirst_Child_SubChild_cat"));
		Thread.sleep(1000);
		String data = CommonActions.getText(atlasHome.getLocatorType("Search_DayFirst_Child_SubChild_Units"));
		System.out.println("Get Data "+data);
	}*/
	
	public static void getSearchData() throws InterruptedException, BiffException, IOException{
		pageScrollDown(439, 798);
		Thread.sleep(1000);
		int hour = 1, rowNum = 1;
		boolean flag = false;
		for(int ov = 1; ov <100; ov++){
			for(int d = 0; d < 14; d++){
				int h = 6;
				flag = isElementPresentOnPage(By.id("ov-"+ov+"-d-"+d));
				if(!flag){
					break;
				}
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
				for(int dp = 2; dp < 6; dp++){
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
					if(hour == ov){
						webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp+"-h-"+h)).click();
						networkName = "Carlisle";
						getNetworkUnitID();
						webDriver.findElement(By.id("ov-1-"+networkID+"-d-0-dp-2-h-"+h)).click();
						Thread.sleep(1000);
						hour++;
					}
					for(int i = 0; i < 4; i++){
						unitName = "CA1 2SB - TESCO CARLISLE";
						getNetworkUnitID();
						String units = webDriver.findElement(By.id("up-"+unitID+"-d-"+d+"-dp-"+dp+"-h-"+h)).getText();
						XLsWriterUtility.writeDataInXLs("DataTable/TestData.xls", "AvailableUnits", rowNum, 0, units);
						h++; rowNum++;
					}
					Thread.sleep(1000);
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
				}
				Thread.sleep(1000);
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
			}
			if(!flag){
				break;
			}
		}
	}
	
	public static void getBookingData() throws InterruptedException, BiffException, IOException{
		pageScrollDown(439, 798);
		Thread.sleep(1000);
		int hour = 1, rowNum = 1;
		boolean flag = false;
		for(int ov = 1; ov <100; ov++){
			for(int d = 0; d < 14; d++){
				int h = 6;
				flag = isElementPresentOnPage(By.id("ov-"+ov+"-d-"+d));
				if(!flag){
					break;
				}
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
				for(int dp = 2; dp < 6; dp++){
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
					if(hour == ov){
						webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp+"-h-"+h)).click();
						networkName = "Carlisle";
						getNetworkUnitID();
						webDriver.findElement(By.id("ov-1-"+networkID+"-d-0-dp-2-h-"+h)).click();
						Thread.sleep(1000);
						hour++;
					}
					for(int i = 0; i < 4; i++){
						unitName = "CA1 2SB - TESCO CARLISLE";
						getNetworkUnitID();
						String units = webDriver.findElement(By.id("up-"+unitID+"-d-"+d+"-dp-"+dp+"-h-"+h)).getText();
						XLsWriterUtility.writeDataInXLs("DataTable/TestData.xls", "AvailableUnits", rowNum, 1, units);
						h++; rowNum++;
					}
					Thread.sleep(1000);
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
				}
				Thread.sleep(1000);
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
			}
			if(!flag){
				break;
			}
		}
	}
	
	public static void getBookedData() throws InterruptedException, BiffException, IOException{
		pageScrollDown(439, 798);
		Thread.sleep(1000);
		int hour = 1, rowNum = 1;
		boolean flag = false;
		for(int ov = 1; ov <100; ov++){
			for(int d = 0; d < 14; d++){
				int h = 6;
				flag = isElementPresentOnPage(By.id("ov-"+ov+"-d-"+d));
				if(!flag){
					break;
				}
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
				for(int dp = 2; dp < 6; dp++){
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
					if(hour == ov){
						webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp+"-h-"+h)).click();
						networkName = "Carlisle";
						getNetworkUnitID();
						webDriver.findElement(By.id("ov-1-"+networkID+"-d-0-dp-2-h-"+h)).click();
						Thread.sleep(1000);
						hour++;
					}
					for(int i = 0; i < 4; i++){
						unitName = "CA1 2SB - TESCO CARLISLE";
						getNetworkUnitID();
						String units = webDriver.findElement(By.id("up-"+unitID+"-d-"+d+"-dp-"+dp+"-h-"+h)).getText();
						XLsWriterUtility.writeDataInXLs("DataTable/TestData.xls", "AvailableUnits", rowNum, 3, units);
						h++; rowNum++;
					}
					Thread.sleep(1000);
					webDriver.findElement(By.id("ov-"+ov+"-d-"+d+"-dp-"+dp)).click();
				}
				Thread.sleep(1000);
				webDriver.findElement(By.id("ov-"+ov+"-d-"+d)).click();
			}
			if(!flag){
				break;
			}
		}
	}
	
	public static void getNetworkUnitID() throws BiffException, IOException{
		XLsReaderUtility.columnDictionary("DataTable/TestData.xls", "Stores");
		for(int rowCnt = 0; rowCnt < XLsReaderUtility.rowCount(); rowCnt++)
		{
			if(XLsReaderUtility.readCell(XLsReaderUtility.getCell("Units"), rowCnt).equalsIgnoreCase(unitName)){
				unitID = XLsReaderUtility.readCell(XLsReaderUtility.getCell("ID"), rowCnt);
			}
			else if (XLsReaderUtility.readCell(XLsReaderUtility.getCell("Network"), rowCnt).equalsIgnoreCase(networkName)) {
				networkID = XLsReaderUtility.readCell(XLsReaderUtility.getCell("ID"), rowCnt);
			}
		}
	}
	
	public static void verifyAvailableDataAfterBooking() throws BiffException, IOException{
		XLsReaderUtility.columnDictionary("DataTable/TestData.xls", "AvailableUnits");
		String expectedAvailableUnits;
		for(int rowCnt = 1; rowCnt < XLsReaderUtility.rowCount(); rowCnt++)
		{
			String availableUnit = XLsReaderUtility.readCell(XLsReaderUtility.getCell("AvailableUnits"), rowCnt);
			String bookedUnit = XLsReaderUtility.readCell(XLsReaderUtility.getCell("BookedUnits"), rowCnt);
			if(availableUnit.equals("-"))
				expectedAvailableUnits = "-";
			else {
				double expectedAvailableData = Double.parseDouble(availableUnit) - Double.parseDouble(bookedUnit);
				expectedAvailableUnits = String.valueOf(expectedAvailableData)+"0";
			}
			XLsWriterUtility.writeDataInXLs("DataTable/TestData.xls", "AvailableUnits", rowCnt, XLsReaderUtility.getCell("ExpectedData"), expectedAvailableUnits);
			if(XLsReaderUtility.readCell(XLsReaderUtility.getCell("ActualData"), rowCnt).equals(expectedAvailableUnits)){
				System.out.println("Verified : Ectual Unit "+XLsReaderUtility.readCell(XLsReaderUtility.getCell("ActualData"), rowCnt)+" is equals to Available units "+availableUnit+" - Booked unit "+bookedUnit);
				testLog("Verified : Ectual Unit "+XLsReaderUtility.readCell(XLsReaderUtility.getCell("ActualData"), rowCnt)+" is equals to Available units "+availableUnit+" - Booked unit "+bookedUnit);
			}
			else{
				System.out.println("Verified : Ectual Unit "+XLsReaderUtility.readCell(XLsReaderUtility.getCell("ActualData"), rowCnt)+" is not equals to Available units "+availableUnit+" - Booked unit "+bookedUnit);
				testLog("Verified : Ectual Unit "+XLsReaderUtility.readCell(XLsReaderUtility.getCell("ActualData"), rowCnt)+" is not equals to Available units "+availableUnit+" - Booked unit "+bookedUnit);
			}
		}
	}
}
