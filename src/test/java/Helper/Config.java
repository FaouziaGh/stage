package Helper;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Config {
    public static WebDriver driver;

    public static void maximazwindows() {
        driver.manage().window().maximize();
    }

    public static void maximazwindow() {
        ChromeOptions options = new ChromeOptions();
        if (System.getProperty("jenkins") != null
                || System.getenv("JENKINS_HOME") != null) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            System.out.println("Running in headless mode (Jenkins)");
        } else {
            options.addArguments("--start-maximized");
            System.out.println("Running in normal mode (local)");
        }
        driver = new ChromeDriver(options);
    }

    public static void attent(int s) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(s));
    }

    public static WebElement waitForVisibility(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitAndClick(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForUrlContains(String url, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
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
