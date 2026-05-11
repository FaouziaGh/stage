package Helper;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Config {
	public static WebDriver driver;
	public static void maximazwindow() {
		driver.manage().window().maximize();
	}
	
	public static void attent(int s) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(s));
	}
	
	public static WebElement waitForVisibility(WebElement element, int seconds) {

	    WebDriverWait wait = new WebDriverWait(driver,
	            Duration.ofSeconds(seconds));

	    return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitAndClick(WebElement element, int seconds) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

	    wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitForUrlContains(String url, int seconds) {

	    WebDriverWait wait = new WebDriverWait(driver,
	            Duration.ofSeconds(seconds));

	    wait.until(ExpectedConditions.urlContains(url));
	}
	
	 public static void jsClick(WebElement el) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
	    }

	    public static WebElement waitAndFind(String text) {
	        String xpath;
	        if (text.contains("'")) {
	            xpath = "//*[contains(text(),\"" + text + "\")]";
	        } else {
	            xpath = "//*[normalize-space(text())='" + text + "']";
	        }
	        return new WebDriverWait(driver, Duration.ofSeconds(10))
	            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	    }
	
}
