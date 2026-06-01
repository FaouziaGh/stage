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
	
	// public void connect(String uname, String pwd) {
	// 	Config.waitForVisibility(username, 10);
	//     username.sendKeys(uname);
	//     Config.waitForVisibility(password, 10);
	//     password.sendKeys(pwd);
	//     Config.waitAndClick(loginButton, 20);
	//     Config.jsClick(loginButton);
	// }

	public void connect(String uname, String pwd) {
		// Wait for username field to be visible and clickable
		Config.waitForVisibility(username, 10);
	    username.sendKeys(uname);
	    
	    // Wait for password field to be visible and clickable
	    Config.waitForVisibility(password, 10);
	    password.sendKeys(pwd);
	    
	    // Wait for button to be clickable, then click ONCE (not twice)
	    Config.waitAndClick(loginButton, 20);
	    
	    // Wait for the role page to load after login
	    Config.attent(5);  // Brief pause for page transition
	}
	
	// public void selectRole(String roleName) {
	// 	Config.attent(20);
	// 	try {
	// 		for (WebElement role : loginRole) {
	// 			//Config.waitForVisibility(role, 10);
	// 	        if (role.getText().trim().equalsIgnoreCase(roleName.trim())) {
	// 	        	//System.out.println("Role found: " + role.getText());
	// 	        	Config.waitAndClick(role, 30);
	// 	        	Config.jsClick(loginButton);
	// 	            //role.click();
	// 	        }
	// 	    }
	// 	}catch(Exception e) {}
		
	// }
	public void selectRole(String roleName) {
		// Wait for roles to be visible
		Config.attent(3);
		WebDriverWait wait = new WebDriverWait(Config.driver, Duration.ofSeconds(30));
		
		try {
			for (WebElement role : loginRole) {
				if (role.getText().trim().equalsIgnoreCase(roleName.trim())) {
					// Wait until the role element is clickable
					wait.until(ExpectedConditions.elementToBeClickable(role));
					role.click();
					
					// Wait for page transition after role selection
					Config.attent(3);
					return;  // Exit after finding and clicking the role
				}
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// public void verifLogin(String msg)throws Exception {
	// 	Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 30);
	// 	Config.waitForVisibility(verifLogin, 30);
	// 	String actualText = verifLogin.getText();
	// 	//Assert.assertEquals(msg, actualText);
	// 	Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, actualText.toLowerCase().contains(msg.toLowerCase()));
	// 	}
	public void verifLogin(String msg) throws Exception {
		// Wait for URL to contain the home page link
		Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 30);
		
		// Wait for the verification message to be visible
		Config.waitForVisibility(verifLogin, 30);
		
		String actualText = verifLogin.getText();
		Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, 
			actualText.toLowerCase().contains(msg.toLowerCase()));
	}

}
