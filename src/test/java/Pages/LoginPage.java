package Pages;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Helper.Config;
import Helper.Utils;

public class LoginPage {
	@FindBy(id="sign-in-email-input")
	WebElement username;
	
	@FindBy(id="sign-in-password-input")
	WebElement password;
	
	@FindBy(id="sign-in-button")
	WebElement loginButton;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div/div/div[2]/div/div/div/div/div/div/div[3]/div/h6")
	List<WebElement> loginRole;
	
	@FindBy(xpath="/html/body/div/div[1]/div/div[2]/div/div[2]/p")
	WebElement verifLogin;
	
	public LoginPage() {
		PageFactory.initElements(Config.driver, this);	
	}
	
	public void connect(String uname, String pwd) throws Exception {
		WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(15));
		
		// Wait for and fill username
		wait.until(ExpectedConditions.visibilityOf(username));
		username.clear();
		username.sendKeys(uname);
		System.out.println("✓ Entered username: " + uname);
		
		// Wait for and fill password
		wait.until(ExpectedConditions.visibilityOf(password));
		password.clear();
		password.sendKeys(pwd);
		System.out.println("✓ Entered password");
		
		// Wait for button to be clickable and click
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
		System.out.println("✓ Clicked login button");
		
		// Wait for role selection page or dashboard
		Thread.sleep(3000);  // Give the page time to transition
		System.out.println("Current URL after login: " + Config.driver.getCurrentUrl());
	}
	
	public void selectRole(String roleName) throws Exception {
	    WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(20));

	    try {
	        System.out.println("Looking for role: " + roleName);

	        // Wait for role popup to appear by waiting for at least one role element
	        wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("/html/body/div[2]/div[3]/div/div/div/div[2]/div/div/div/div/div/div/div[3]/div/h6")
	        ));

	        // Re-fetch fresh elements — loginRole from @FindBy is stale
	        List<WebElement> freshRoles = Config.driver.findElements(
	            By.xpath("/html/body/div[2]/div[3]/div/div/div/div[2]/div/div/div/div/div/div/div[3]/div/h6")
	        );

	        System.out.println("Total roles found: " + freshRoles.size());

	        boolean roleFound = false;
	        for (WebElement role : freshRoles) {
	            String roleText = role.getText().trim();
	            System.out.println("Available role: '" + roleText + "'");

	            if (roleText.equalsIgnoreCase(roleName.trim())) {
	                System.out.println("✔ Role found: " + roleName);
	                wait.until(ExpectedConditions.elementToBeClickable(role));
	                role.click();
	                System.out.println("✔ Clicked role: " + roleName);
	                roleFound = true;
	                break;
	            }
	        }

	        if (!roleFound) {
	            System.out.println("✗ Role NOT found: " + roleName);
	            System.out.println("Available roles: " + freshRoles.stream()
	                .map(r -> r.getText().trim())
	                .toList());
	        }

	        Thread.sleep(3000);
	        System.out.println("Current URL after role selection: " + Config.driver.getCurrentUrl());

	    } catch (Exception e) {
	        System.out.println("✗ Error selecting role: " + e.getMessage());
	        throw e;
	    }
	}
	
	public void selectRoles(String roleName) throws Exception {
		WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(20));
		
		try {
			System.out.println("Looking for role: " + roleName);
			System.out.println("Total roles found: " + loginRole.size());
			
			// Wait for at least one role to be visible
			if (!loginRole.isEmpty()) {
				wait.until(ExpectedConditions.visibilityOf(loginRole.get(0)));
			}
			
			boolean roleFound = false;
			for (WebElement role : loginRole) {
				String roleText = role.getText().trim();
				System.out.println("Available role: '" + roleText + "'");
				
				if (roleText.equalsIgnoreCase(roleName.trim())) {
					System.out.println("✓ Role found: " + roleName);
					wait.until(ExpectedConditions.elementToBeClickable(role));
					role.click();
					System.out.println("✓ Clicked role: " + roleName);
					roleFound = true;
					break;
				}
			}
			
			if (!roleFound) {
				System.out.println("✗ Role NOT found: " + roleName);
				System.out.println("Available roles: " + loginRole.stream()
					.map(r -> r.getText().trim())
					.toList());
			}
			
			Thread.sleep(3000);  // Wait for navigation after role selection
			System.out.println("Current URL after role selection: " + Config.driver.getCurrentUrl());
			
		} catch(Exception e) {
			System.out.println("✗ Error selecting role: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	public void verifLogin(String msg) throws Exception {
		WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(30));
		
		try {
			System.out.println("Verifying login with message: " + msg);
			System.out.println("Current URL: " + Config.driver.getCurrentUrl());
			
			// Option 1: Wait for URL to contain dashboard
			try {
				wait.until(ExpectedConditions.urlContains("dashboards"));
				System.out.println("✓ Successfully navigated to dashboard");
			} catch(Exception e) {
				System.out.println("⚠ URL did not change to dashboard. Current: " + Config.driver.getCurrentUrl());
				System.out.println("Continuing with verification message check...");
			}
			
			// Option 2: Wait for verification message to appear
			wait.until(ExpectedConditions.visibilityOf(verifLogin));
			String actualText = verifLogin.getText().trim();
			System.out.println("Verification message found: '" + actualText + "'");
			System.out.println("Expected message: '" + msg + "'");
			
			Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, 
				actualText.toLowerCase().contains(msg.toLowerCase()));
			
			System.out.println("✓ Verification passed!");
			
		} catch(Exception e) {
			System.out.println("✗ Verification failed: " + e.getMessage());
			// Take screenshot for debugging
			System.out.println("Page source length: " + Config.driver.getPageSource().length());
			throw e;
		}
	}
}
// package Pages;

// import java.time.Duration;
// import java.util.List;

// import org.junit.Assert;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.FindBy;
// import org.openqa.selenium.support.PageFactory;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// import Helper.Config;
// import Helper.Utils;

// public class LoginPage {
// 	@FindBy(id="sign-in-email-input")
// 	WebElement username;
	
// 	@FindBy(id="sign-in-password-input")
// 	WebElement password;
	
// 	@FindBy(id="sign-in-button")
// 	WebElement loginButton;
	
// 	@FindBy(xpath="/html/body/div[2]/div[3]/div/div/div/div[2]/div/div/div/div/div/div/div[3]/div/h6")
// 	List<WebElement> loginRole;
	
// 	@FindBy(xpath="/html/body/div/div[1]/div/div[2]/div/div[2]/p")
// 	WebElement verifLogin;
	
// 	public LoginPage() {
// 		PageFactory.initElements(Config.driver, this);	
// 	}
	
// 	// public void connect(String uname, String pwd) {
// 	// 	Config.waitForVisibility(username, 10);
// 	//     username.sendKeys(uname);
// 	//     Config.waitForVisibility(password, 10);
// 	//     password.sendKeys(pwd);
// 	//     Config.waitAndClick(loginButton, 20);
// 	//     Config.jsClick(loginButton);
// 	// }

// 	public void connect(String uname, String pwd) {
// 		// Wait for username field to be visible and clickable
// 		Config.waitForVisibility(username, 10);
// 	    username.sendKeys(uname);
	    
// 	    // Wait for password field to be visible and clickable
// 	    Config.waitForVisibility(password, 10);
// 	    password.sendKeys(pwd);
	    
// 	    // Wait for button to be clickable, then click ONCE (not twice)
// 	    Config.waitAndClick(loginButton, 20);
	    
// 	    // Wait for the role page to load after login
// 	    Config.attent(5);  // Brief pause for page transition
// 	}
	
// 	// public void selectRole(String roleName) {
// 	// 	Config.attent(20);
// 	// 	try {
// 	// 		for (WebElement role : loginRole) {
// 	// 			//Config.waitForVisibility(role, 10);
// 	// 	        if (role.getText().trim().equalsIgnoreCase(roleName.trim())) {
// 	// 	        	//System.out.println("Role found: " + role.getText());
// 	// 	        	Config.waitAndClick(role, 30);
// 	// 	        	Config.jsClick(loginButton);
// 	// 	            //role.click();
// 	// 	        }
// 	// 	    }
// 	// 	}catch(Exception e) {}
		
// 	// }
// 	public void selectRole(String roleName) {
// 		// Wait for roles to be visible
// 		Config.attent(3);
// 		WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(30));
		
// 		try {
// 			for (WebElement role : loginRole) {
// 				if (role.getText().trim().equalsIgnoreCase(roleName.trim())) {
// 					// Wait until the role element is clickable
// 					wait.until(ExpectedConditions.elementToBeClickable(role));
// 					role.click();
					
// 					// Wait for page transition after role selection
// 					Config.attent(3);
// 					return;  // Exit after finding and clicking the role
// 				}
// 		    }
// 		} catch(Exception e) {
// 			e.printStackTrace();
// 		}
// 	}
	
// 	// public void verifLogin(String msg)throws Exception {
// 	// 	Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 30);
// 	// 	Config.waitForVisibility(verifLogin, 30);
// 	// 	String actualText = verifLogin.getText();
// 	// 	//Assert.assertEquals(msg, actualText);
// 	// 	Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, actualText.toLowerCase().contains(msg.toLowerCase()));
// 	// 	}
// 	public void verifLogin(String msg) throws Exception {
// 		// Wait for URL to contain the home page link
// 		Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 30);
		
// 		// Wait for the verification message to be visible
// 		Config.waitForVisibility(verifLogin, 30);
		
// 		String actualText = verifLogin.getText();
// 		Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, 
// 			actualText.toLowerCase().contains(msg.toLowerCase()));
// 	}

// }