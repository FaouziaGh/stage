package Pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	
	public void connect(String uname, String pwd) {
		Config.attent(10);
		//Config.waitForVisibility(username, 10);
		username.sendKeys(uname);
		//Config.waitForVisibility(password, 10);
		password.sendKeys(pwd);
		//Config.waitAndClick(loginButton, 10);
		loginButton.click();
	}
	
	public void selectRole(String roleName) {
		//Config.attent(10);
		try {
			for (WebElement role : loginRole) {
				//Config.waitForVisibility(role, 10);
		        if (role.getText().trim().equalsIgnoreCase(roleName.trim())) {
		        	//System.out.println("Role found: " + role.getText());
		        	Config.waitAndClick(role, 10);
		        	
		            role.click();
		        }
		    }
		}catch(Exception e) {}
		
	}
	
	public void verifLogin(String msg)throws Exception {
		Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 15);
		Config.waitForVisibility(verifLogin, 10);
		String actualText = verifLogin.getText();
		//Assert.assertEquals(msg, actualText);
		Assert.assertTrue("Expected page to contain: " + msg + ", but found: " + actualText, actualText.toLowerCase().contains(msg.toLowerCase()));
		}
	

}
