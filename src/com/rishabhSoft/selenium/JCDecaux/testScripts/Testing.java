package com.rishabhSoft.selenium.JCDecaux.testScripts;

import java.io.IOException;

import jxl.read.biff.BiffException;

import com.rishabhSoft.selenium.JCDecaux.drivers.TestNG_WebDriver;
import com.rishabhSoft.selenium.JCDecaux.module.AtlasHome;

public class Testing extends TestNG_WebDriver{
	
	//@Test
	//public void SimpleTest() throws InterruptedException, AWTException, BiffException, IOException{
	public static void main(String []arg) throws BiffException, IOException{

		AtlasHome.verifyAvailableDataAfterBooking();
		System.out.println("Updated");
	}
}
