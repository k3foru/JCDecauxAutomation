package com.rishabhSoft.selenium.JCDecaux.drivers;

import static com.rishabhSoft.selenium.JCDecaux.drivers.ReadObjectProperties.objLocatorValue;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitAndConditions{
	
	protected static WebDriver webDriver;
	
    /** Default wait time for an element. 40 seconds. */
    public static final int timeOutInSeconds = 40;
    
    /**
     * Wait for the element to be present in the DOM, and displayed on the page.
     * And returns the first WebElement using the given method.
     *
     * @param WebDriver        The driver object to be used
     * @param By        selector to find the element
     * @param int        The time in seconds to wait until returning a failure
     *
     * @return WebElement        the first WebElement using the given method, or null (if the timeout is reached)
     */
    public static WebElement waitForElement(final LocatorType locator) {
    	WebElement element;
        try{
        	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        	WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
        	element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator.execute(objLocatorValue)));
        	//webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
        	return element; //return the element        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Wait for the element to be present in the DOM, regardless of being displayed or not.
     * And returns the first WebElement using the given method.
     *
     * @param WebDriver        The driver object to be used
     * @param By        selector to find the element
     * @param int        The time in seconds to wait until returning a failure
     *
     * @return WebElement        the first WebElement using the given method, or null (if the timeout is reached)
     */
    public static WebElement waitForElementPresent(final LocatorType locator) {
        WebElement element;
        try{
        	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        	WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
        	element = wait.until(ExpectedConditions.presenceOfElementLocated(locator.execute(objLocatorValue)));
            //webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
            return element; //return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /** 
     * Wait for the List<WebElement> to be present in the DOM, regardless of being displayed or not.
     * Returns all elements within the current page DOM.
     *
     * @param WebDriver        The driver object to be used
     * @param By        selector to find the element
     * @param int        The time in seconds to wait until returning a failure
     *
     * @return List<WebElement> all elements within the current page DOM, or null (if the timeout is reached)
     */
    public static List<WebElement> waitForListElementsPresent(final LocatorType locator) {
    	List<WebElement> elements;
	    try{        
	    	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
	        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
	        wait.until((new ExpectedCondition<Boolean>(){
	        	@Override
	        	public Boolean apply(WebDriver webDriver) {
	        		return areElementsPresent(locator);
	        	}
	        }));
	        elements = webDriver.findElements(locator.execute(objLocatorValue));
	        //webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
	        return elements; //return the element        
	    } catch (Exception e) {
	    	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Wait for an element to appear on the refreshed web-page.
     * And returns the first WebElement using the given method.
     *
     * This method is to deal with dynamic pages.
     *
     * Some sites I (Mark) have tested have required a page refresh to add additional elements to the DOM.
     * Generally you (Chon) wouldn't need to do this in a typical AJAX scenario.
     *
     * @param WebDriver        The driver object to use to perform this element search
     * @param locator        selector to find the element
     * @param int        The time in seconds to wait until returning a failure
     *
     * @return WebElement        the first WebElement using the given method, or null(if the timeout is reached)
     *
     * @author Mark Collin
     */
     public static WebElement waitForElementRefresh(final LocatorType locator) {
	    WebElement element;
	    try{        
	    	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
	    	new WebDriverWait(webDriver, timeOutInSeconds) {}.until(new ExpectedCondition<Boolean>() {
	    	 @Override
	    	 public Boolean apply(WebDriver driverObject) {
	    		 driverObject.navigate().refresh(); //refresh the page ****************
	    		 return isElementPresentAndDisplay(locator);
	    	 }
	     });
	     element = webDriver.findElement(locator.execute(objLocatorValue));
	    // webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
	     return element; //return the element
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
     }
     
     /**
      * Wait for the Text to be present in the given element, regardless of being displayed or not.
      *
      * @param WebDriver        The driver object to be used to wait and find the element
      * @param locator        selector of the given element, which should contain the text
      * @param String        The text we are looking
      * @param int        The time in seconds to wait until returning a failure
      *
      * @return boolean
      */
     public static boolean waitForTextPresent(final LocatorType locator, final String text) {
         boolean isPresent = false;
         try{        
        	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        	 new WebDriverWait(webDriver, timeOutInSeconds) {}.until(new ExpectedCondition<Boolean>() {
        		 @Override
        		 public Boolean apply(WebDriver driverObject) {
        			 return isTextPresent(locator, text); //is the Text in the DOM
        		 }
        	 });
        	 isPresent = isTextPresent(locator, text);
        	 //webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
        	 return isPresent;
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         return false;
     }
     
     /**
      * Waits for the Condition of JavaScript.
      *
      *
      * @param WebDriver                The driver object to be used to wait and find the element
      * @param String        The javaScript condition we are waiting. e.g. "return (xmlhttp.readyState >= 2 && xmlhttp.status == 200)"
      * @param int        The time in seconds to wait until returning a failure
      *
      * @return boolean true or false(condition fail, or if the timeout is reached)
      **/
     public static boolean waitForJavaScriptCondition(final String javaScript) {
         boolean jscondition = false;
         try{        
        	 //webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        	 new WebDriverWait(webDriver, timeOutInSeconds) {}.until(new ExpectedCondition<Boolean>() {
        		 @Override
        		 public Boolean apply(WebDriver driverObject) {
        			 return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
        		 }
        	 });
        	 jscondition = (Boolean) ((JavascriptExecutor) webDriver).executeScript(javaScript);
        	 //webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
        	 return jscondition;
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         return false;
     }
     
     /** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition.
     *
     * @param WebDriver - The driver object to be used to wait and find the element
     * @param int - The time in seconds to wait until returning a failure
     *
     * @return boolean true or false(condition fail, or if the timeout is reached)
     * */
    public static boolean waitForJQueryProcessing(){
        boolean jQcondition = false;
        try{        
        	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        	new WebDriverWait(webDriver, timeOutInSeconds) {}.until(new ExpectedCondition<Boolean>() {
        		@Override
        		public Boolean apply(WebDriver driverObject) {
        			return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
        		}
        	});
        	jQcondition = (Boolean) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0");
        	//webDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
        	return jQcondition;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return jQcondition;
    }
    
    /**
     * Coming to implicit wait, If you have set it once then you would have to explicitly set it to zero to nullify it -
     */
    public static void nullifyImplicitWait() {
    	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
    }

    /**
     * Set driver implicitlyWait() time.
     */
    public static void setImplicitWait(int waitTime_InSeconds) {
    	webDriver.manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.SECONDS);
    }
    
    /**
     * Reset ImplicitWait.
     * To reset ImplicitWait time you would have to explicitly
     * set it to zero to nullify it before setting it with a new time value.
     */
    public static void reSetImplicitWait() {
    	webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
    	webDriver.manage().timeouts().implicitlyWait(Long.parseLong(SetObjectProperties.appConfig.getPropertyValue("implicitlyWait")), TimeUnit.SECONDS); //reset implicitlyWait
    }

    /**
     * Reset ImplicitWait.
     * @param int - a new wait time in seconds
     */
    public static void resetImplicitWait(int newWaitTime_InSeconds) {
    	//webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
    	//webDriver.manage().timeouts().implicitlyWait(newWaittime_InSeconds, TimeUnit.SECONDS); //reset implicitlyWait
    }

 /**
     * Checks if the text is present in the element.
*
     * @param driver - The driver object to use to perform this element search
     * @param by - selector to find the element that should contain text
     * @param text - The Text element you are looking for
     * @return true or false
     */
    public static boolean isTextPresent(LocatorType locator, String text)
    {
        try {
        	return webDriver.findElement(locator.execute(objLocatorValue)).getText().contains(text);
        } catch (NullPointerException e) {
        	return false;
        }
    }

    public static boolean isElementPresentOnPage(By by){
    	try {
        	webDriver.findElement(by).isDisplayed();	//if it does not find the element throw NoSuchElementException, which calls "catch(Exception)" and returns false;
        	return true;
        } catch (NoSuchElementException e) {
        	return false;
        }
    }
    
    /**
     * Checks if the elment is in the DOM, regardless of being displayed or not.
     *
     * @param driver - The driver object to use to perform this element search
     * @param by - selector to find the element
     * @return boolean
     */
    public static boolean isElementPresent(LocatorType locator) {
        try {
        	webDriver.findElement(locator.execute(objLocatorValue)).isDisplayed();	//if it does not find the element throw NoSuchElementException, which calls "catch(Exception)" and returns false;
        	return true;
        } catch (NoSuchElementException e) {
        	return false;
        }
    }

    /**
     * Checks if the List<WebElement> are in the DOM, regardless of being displayed or not.
     *
     * @param driver - The driver object to use to perform this element search
     * @param by - selector to find the element
     * @return boolean
     */
    public static boolean areElementsPresent(LocatorType locator) {
        try {
        	webDriver.findElements(locator.execute(objLocatorValue));
                return true;
        } catch (NoSuchElementException e) {
        	return false;
        }
    }

    /**
     * Checks if the element is in the DOM and displayed.
     *
     * @param driver - The driver object to use to perform this element search
     * @param by - selector to find the element
     * @return boolean
     */
    public static boolean isElementPresentAndDisplay(LocatorType locator) {
        try {                        
        	return webDriver.findElement(locator.execute(objLocatorValue)).isDisplayed();
        } catch (NoSuchElementException e) {
        	return false;
        }
    }
    
    /**
     * Checks if the Link is in the DOM and displayed.
     *
     * @param linkText - Link Text to check Link present.
     * @return boolean
     */
    public static boolean isLinkPresent(String linkText) {
        try {                        
        	return webDriver.findElement(By.linkText(linkText)).isDisplayed();
        } catch (NoSuchElementException e) {
        	return false;
        }
    }
}
