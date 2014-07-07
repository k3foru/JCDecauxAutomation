package com.rishabhSoft.selenium.JCDecaux.testScripts;

import org.testng.annotations.Test;
import com.rishabhSoft.selenium.JCDecaux.drivers.TestNG_WebDriver;
import com.rishabhSoft.selenium.JCDecaux.module.AtlasHome;

public class AtlasSearch extends TestNG_WebDriver {
	
	@Test  (description="Search for Unit Availability", groups = { "regression" })
	public void searchAvailability() throws Exception{
		testSummary("Search for Unit Availability");
		AtlasHome.enterBudget();
		AtlasHome.selectCampaignStartDate();
		AtlasHome.selectBrand();
		AtlasHome.selectAudienceCategoryIndex();
		AtlasHome.selectPlace();
		AtlasHome.clickToCheckAvailabilityBtn();
		AtlasHome.getSearchData();
		testLog("Verified : Units are Availabile for given search criteria");
	}
	
	@Test  (description="Atlas Unit Booking", groups = { "regression" })
	public void atlasUnitBooking() throws Exception{
		testSummary("Book units");
		AtlasHome.enterBudget();
		AtlasHome.selectCampaignStartDate();
		AtlasHome.selectBrand();
		AtlasHome.selectAudienceCategoryIndex();
		AtlasHome.selectPlace();
		AtlasHome.clickToInvestmentOptionsBtn();
		Thread.sleep(2000);
		AtlasHome.expandResultDetailDigitalPanel();
		AtlasHome.getBookingData();
		AtlasHome.clickOnBookButton();
		testLog("Verified : Units Booked for given search criteria");
	}
	
	@Test  (description="Search for Unit Availability", groups = { "regression" })
	public void verifyBookedUnit() throws Exception{
		testSummary("Search for Unit Availability");
		AtlasHome.enterBudget();
		AtlasHome.selectCampaignStartDate();
		AtlasHome.selectBrand();
		AtlasHome.selectAudienceCategoryIndex();
		AtlasHome.selectPlace();
		AtlasHome.clickToCheckAvailabilityBtn();
		AtlasHome.getBookedData();
		AtlasHome.verifyAvailableDataAfterBooking();
		testLog("Verified : Available Units after Booking");
	}
}
