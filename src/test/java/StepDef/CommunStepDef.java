package StepDef;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Helper.Config;
import Helper.Utils;
import Pages.LoginPage;
import Pages.MenuPage;
import java.util.List;
import org.openqa.selenium.WebElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommunStepDef {

    @Given("User is logged in on the home page with the correct correct username {string} and correct password {string} and role {string}")
public void user_is_logged_in_on_the_home_page_with_the_correct_correct_username_and_correct_password_and_role(
        String username, String password, String role) throws Exception {
    Config.driver = new ChromeDriver();
    Config.maximazwindow();
    Config.driver.get(Utils.getProperty("HomePage_link"));

    LoginPage login = new LoginPage();
    login.connect(username, password);
    Config.attent(3);
login.selectRole(role);

// Wait until redirected away from login page
new WebDriverWait(Config.driver, Duration.ofSeconds(30))
    .until(ExpectedConditions.not(
        ExpectedConditions.titleContains("se connecter")
    ));

// Wait until menu exists
new WebDriverWait(Config.driver, Duration.ofSeconds(30))
    .until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//*[contains(text(),'Plans')]")
    ));

System.out.println("Login successful - role selected: " + role);
System.out.println("Current URL: " + Config.driver.getCurrentUrl());
System.out.println("Current title: " + Config.driver.getTitle());
}

    @When("User clicks on menu {string} then clicks on submenu {string} then clicks on subsubmenu {string} then clicks on subsubsubmenu {string}")
    public void user_clicks_on_menu_then_clicks_on_submenu_then_clicks_on_subsubmenu_then_clicks_on_subsubsubmenu(
            String menu, String subMenu, String subsubMenu, String subsubSubMenu) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickMenus(menu, subMenu, subsubMenu, subsubSubMenu);
    }

    @Then("The correct page is displayed with the title {string}")
    public void the_correct_page_is_displayed_with_the_title(String menuTitle) {
        MenuPage menuPage = new MenuPage();
        menuPage.verifyHomePage(menuTitle);
    }
}
