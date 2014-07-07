package com.rishabhSoft.selenium.JCDecaux.drivers;

import java.io.File;

public class SetObjectProperties {
	
	// create reference of ReadProperties class.
	public static ReadObjectProperties appConfig;
	public static ReadObjectProperties atlasHome;
	public static ReadObjectProperties common;

	public SetObjectProperties() throws Exception {
		
		// create instance of ReadProperties class.
		appConfig = new ReadObjectProperties();
		atlasHome = new ReadObjectProperties();
		common = new ReadObjectProperties();
		
		// Read AppConfig properties file
		appConfig.setFile(new File("Config/AppConfig.properties"));
		appConfig.readFile();

		// Read Home page properties file
		atlasHome.setFile(new File("ObjectRepository/OR_AtlasHome.properties"));
		atlasHome.readFile();

		// Read Common properties file
		common.setFile(new File("ObjectRepository/OR_Common.properties"));
		common.readFile();
	}
}
