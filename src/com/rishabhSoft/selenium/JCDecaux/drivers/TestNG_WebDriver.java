package com.rishabhSoft.selenium.JCDecaux.drivers;

import static com.rishabhSoft.selenium.JCDecaux.drivers.ReadObjectProperties.objLocatorValue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.OperaProfile;
import com.rishabhSoft.selenium.JCDecaux.commonLibrary.DateFormatUtilities;
import com.rishabhSoft.selenium.JCDecaux.commonLibrary.FileHandlingUtilities;
import com.rishabhSoft.selenium.JCDecaux.commonLibrary.XLsReportUtility;

/**
 * Web Driver FrameWork Configuration file using TestNG Framework.
 * 
 * @author Abhishek Singh
 */

public class TestNG_WebDriver extends WaitAndConditions{

	protected static String parentWindowHandle;
	private static String summary;
	private static String description;
	public static int testCaseNo = 3;
	private static StringBuffer sb = new StringBuffer(4000);
	
	@BeforeSuite(alwaysRun=true)
	public void cleanUP() throws Exception{
		
		/**************** TO Delete the existing ScreenShots directory and create a empty directory. *************/
		FileHandlingUtilities.deleteDirectory(new File("ScreenShot_TestFailed"));
		FileHandlingUtilities.deleteDirectory(new File("ScreenShot_TestPassed"));
		FileHandlingUtilities.deleteDirectory(new File("ScreenShot_VerifiedImage"));
		FileHandlingUtilities.deleteFiles("log");
	}
	
	@BeforeMethod(alwaysRun=true)
	public void startDriver() throws Exception{
		
		@SuppressWarnings("unused")
		SetObjectProperties setProperty = new SetObjectProperties();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String browser = SetObjectProperties.appConfig.getPropertyValue("Browser");
		/**************** Run Test in Mozilla Firefox Browser. *************/
		
		if(browser.equalsIgnoreCase("firefox")){
			capabilities.setBrowserName(browser);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			FirefoxProfile firefoxprofile = new FirefoxProfile();
			firefoxprofile.setEnableNativeEvents(true);
			firefoxprofile.setAssumeUntrustedCertificateIssuer(true);
			webDriver = new FirefoxDriver(capabilities);
			webDriver.manage().window().maximize();
		}
		
		/************************* Run with Google Chrome Browser ************************/
		
		else if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", SetObjectProperties.appConfig.getPropertyValue("ChromeDriver"));
			capabilities.setBrowserName(browser);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			//options.addArguments("--lang=" +   "en-GB");                         // To set the browser language
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			webDriver = new ChromeDriver(capabilities);
		}
		
		/************************* Run Test in Opera Browser. ****************/
				
		else if(browser.equalsIgnoreCase("opera")){
			capabilities.setBrowserName(browser);
			OperaProfile profile = new OperaProfile();  									// fresh, random profile
			profile.preferences().set("User Prefs", "Ignore Unrequested Popups", false);
			capabilities.setCapability("opera.profile", profile);
			//capabilities.setCapability("opera.arguments", "-nowin -nomail -fullscreen");
			/************************* Another way to create Opera Browser profile. ****************/
			capabilities.setCapability("opera.profile", new OperaProfile(SetObjectProperties.appConfig.getPropertyValue("OperaProfile")));
			//capabilities.setCapability("opera.logging.level", Level.CONFIG);
			capabilities.setCapability("opera.logging.file", SetObjectProperties.appConfig.getPropertyValue("OperaDriverLog"));
			capabilities.setCapability("opera.display", 8);
			webDriver = new OperaDriver(capabilities);
		}
		
		/*********************** Run with Internet Explorer Browser. ***************/
		
		else if(browser.equalsIgnoreCase("internetExplorer")){
			System.setProperty("webdriver.ie.driver", SetObjectProperties.appConfig.getPropertyValue("IEDriverServer"));
			capabilities.setBrowserName(browser);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setJavascriptEnabled(true);
			webDriver = new InternetExplorerDriver(capabilities);
			webDriver.manage().window().maximize();
			//webDriver.get("javascript:document.getElementById('overridelink').click();");   // Use for https web Application
		}
		
		/*********************** Run with Safari Browser. ***************/
		
		else if(browser.equalsIgnoreCase("safari")){
			capabilities.setBrowserName(browser);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			webDriver = new SafariDriver(capabilities);
			webDriver.manage().window().maximize();
		}
		webDriver.get(SetObjectProperties.appConfig.getPropertyValue("BaseURL"));
		Thread.sleep(3000);
		webDriver.navigate().to(SetObjectProperties.appConfig.getPropertyValue("BaseURL"));
		webDriver.manage().timeouts().implicitlyWait(Long.parseLong(SetObjectProperties.appConfig.getPropertyValue("implicitlyWait")), TimeUnit.SECONDS);
		generateLogFile();
	}
	
	@AfterMethod(alwaysRun=true)
	public void doAfterTest(ITestResult result) throws Exception
	{
		try
		{
	        String classname = result.getTestClass().getRealClass().getName();
	        String methodName = result.getName();
	        String filename = classname+"."+methodName+"_"+DateFormatUtilities.getCurrentDateTime()+".png";
	        File sourceimageFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
	        String status = null;
		   if(result.isSuccess())
		   {
			   status = "PASSED";
			   // Get the Screen shot while test case Passed.
			   FileUtils.copyFile(sourceimageFile, new File("ScreenShot_TestPassed/"+filename));
			   // Screen Shot will attached with log file.
			   Reporter.log("<font color=\"GREN\">Passed Test</font> : <a href=\"..\\..\\ScreenShot_TestPassed\\"+filename+"\"><img src=\"..\\..\\ScreenShot_TestPassed\\"+filename+"\"width=\"100\" height=\"131\" border=\"0\"></a>");
		   }
		   else{
			   status = "FAILED";
			   // Get the Screen shot while test case Failed.
			   FileUtils.copyFile(sourceimageFile, new File("ScreenShot_TestFailed/"+filename));
			   // Screen Shot will attached with log file.
			   Reporter.log("<font color=\"RED\">Failed Test</font> : <a href=\"..\\..\\ScreenShot_TestFailed\\"+filename+"\"><img src=\"..\\..\\ScreenShot_TestFailed\\"+filename+"\"width=\"100\" height=\"131\" border=\"0\"></a>");
		   }
		   // Update Test Result Status of Test Case in Excel Report file.
		   XLsReportUtility.writeDataInXLs(testCaseNo-2, methodName, summary, description, status, SetObjectProperties.appConfig.getPropertyValue("TesterName"));
		   testCaseNo++;
		   sb.delete(0, sb.length());  			// To delete Test description which stored in buffer after run the test
		}
		catch (Throwable t){
			addVerificationFailure(t);
			System.out.println(t.fillInStackTrace());
			Reporter.log(t.getMessage());
		}
		webDriver.quit();
	}
	
/*	@AfterTest(alwaysRun=true)
	public void stopDriver() throws Exception{
		webDriver.quit();
	}*/
	
	/************************ Override Verification Command. ****************************/

	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertEquals(Object actual, Object expected) {
		Assert.assertEquals(expected, actual);
	}

	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertEquals(String actual, String expected) {
		Assert.assertEquals(expected, actual);
	}
	
	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertEquals(String actual, String[] expected) {
		Assert.assertEquals(expected, actual);
	}

	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertEquals(String[] actual, String[] expected) {
		Assert.assertEquals(expected, actual);
	}
	
	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}
	
	// @Override static method of super class (which assumes TestNG conventions)
	public static void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}
	
	// @Override static method of super class (which assumes TestNG conventions)
    public static void verifyTrue(boolean condition) {
    	boolean flag;
        try {
            assertTrue(condition);
            flag = true;
        } catch (Throwable e) {
            addVerificationFailure(e);
            flag = false;
        }
        getScreenshot(flag);
    }
    
    // @Override static method of super class (which assumes TestNG conventions)
    public static void verifyFalse(boolean condition) {
    	boolean flag;
        try {
            assertFalse(condition);
            flag = true;
        } catch (Throwable e) {
            addVerificationFailure(e);
            flag = false;
        }
        getScreenshot(flag);
    }
    
    public static void verifyEquals(Object actual, Object expected) {
    	boolean flag;
        try {
            assertEquals(actual, expected);
            flag = true;
        } catch (Throwable e) {
            addVerificationFailure(e);
            flag = false;
        }
        getScreenshot(flag);
    }
    
    private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
    @SuppressWarnings("unchecked")
	public static List<Throwable> getVerificationFailures() {
        List<?> verificationFailures = (List<?>) verificationFailuresMap.get(Reporter.getCurrentTestResult());
        return (List<Throwable>) (verificationFailures == null ? new ArrayList<Object>() : verificationFailures);
    }
    
    private static void addVerificationFailure(Throwable e) {
        List<Throwable> verificationFailures = getVerificationFailures();
        verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
        verificationFailures.add(e);
     }
	
	// Generate a log file of Test.
	public void generateLogFile() throws SecurityException, IOException{
		Logger logger = Logger.getLogger("");
		FileHandler fh = new FileHandler("log/AppLogs.log");
		logger.addHandler(fh);
		logger.setLevel(Level.ALL);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
	}
	
	public static void getScreenshot(boolean flag) {
		String className = ITestResult.class.getClass().getName();
		String methodName = ITestResult.class.getName();
		String filename = "Cp_"+className+"."+methodName+"_"+DateFormatUtilities.getCurrentDateTime()+".png";
		File sourceimageFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		if(flag)
			try {
				FileUtils.copyFile(sourceimageFile, new File("ScreenShot_TestPassed/"+filename));
				// Screen Shot will attached with log file.
				Reporter.log("<font color=\"GREN\">Passed Verification</font> : <a href=\"..\\..\\ScreenShot_TestPassed\\"+filename+"\"><img src=\"..\\..\\ScreenShot_TestPassed\\"+filename+"\"width=\"100\" height=\"131\" border=\"0\"></a>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
				FileUtils.copyFile(sourceimageFile, new File("ScreenShot_TestFailed/"+filename));
				// Screen Shot will attached with log file.
				Reporter.log("<font color=\"RED\">Failed Verification</font> : <a href=\"..\\..\\ScreenShot_TestFailed\\"+filename+"\"><img src=\"..\\..\\ScreenShot_TestFailed\\"+filename+"\"width=\"100\" height=\"131\" border=\"0\"></a>");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	// To take a screenshot of a specific element
	public static void getScreenshotForSpecificElement(LocatorType locator, String imgName) throws IOException{
		WebElement Image = webDriver.findElement(locator.execute(objLocatorValue));
		File screen=((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        int width=Image.getSize().getWidth();
        int height=Image.getSize().getHeight();
        BufferedImage img=ImageIO.read(screen);
        BufferedImage dest=img.getSubimage(Image.getLocation().getX(), Image.getLocation().getY(), width, height);
        ImageIO.write(dest, "png", screen);
        File file = new File("ScreenShot_VerifiedImage\\"+imgName+".png");
        FileUtils.copyFile(screen, file);
	}
    
    // Customized Log string in Report.
	public static void testLog(String str) throws IOException{
		Reporter.log("<li style=\"color:#0000FF\"> "+str+" </li>");
		description = sb.append("-").append(str).append(" \n").toString();
	}
	
	// Customized Log string in Report.
	public static void testSummary(String str){
		Reporter.log("<h3 style=\"color:#000080\"> "+str+" </h3>");
		summary = str;
	}
}