package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;

import Helper.Config;
import Helper.Utils;
import Pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDef {
	@Given("admin is on login page") 
	public void admin_is_on_login_page() throws Exception{
		Config.driver = new ChromeDriver();
	    Config.maximazwindow();
	    
	    Config.driver.get(Utils.getProperty("Login_link"));
	}
	
	@When("admin enter correct username {string} and correct password {string}")
	public void admin_enter_correct_username_and_correct_password(String uname, String pwd) {
	    LoginPage login = new LoginPage();
	    login.connect(uname, pwd);
	    
	}
	
	@Then("admin click on role {string}")
	public void admin_click_on_role(String roleName) {
		LoginPage login = new LoginPage();
	    login.selectRole(roleName);
	}
	
	@Then("admin is directed to home page that containes MSG {string}")
	public void admin_is_directed_to_home_page_that_containes_msg(String msg)throws Exception {
		LoginPage login = new LoginPage();
	    login.verifLogin(msg);
	    Config.driver.quit();
	}
}
