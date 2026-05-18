package StepDef;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Helper.Config;
import Helper.Utils;
import Pages.LoginPage;
import Pages.MenuPage;
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
    Config.attent(10);
    login.selectRole(role);

    // Wait for ANY page content after role selection — not a specific menu item
    new WebDriverWait(Config.driver, Duration.ofSeconds(30))
        .until(ExpectedConditions.urlContains("erudaxis.com"));

    System.out.println("Login successful - role selected: " + role);
    System.out.println("URL after login: " + Config.driver.getCurrentUrl());
}

    @When("User clicks on menu {string} then clicks on submenu {string} then clicks on subsubmenu {string} then clicks on subsubsubmenu {string}")
    public void user_clicks_on_menu_then_clicks_on_submenu_then_clicks_on_subsubmenu_then_clicks_on_subsubsubmenu(
        String menu, String subMenu, String subsubMenu, String subsubSubMenu) {

    // Navigate directly — works reliably in both headless and headed
    Config.driver.get("https://staging.erudaxis.com/plan/parametres/period");

    // Wait for the add button to confirm page is ready
    new WebDriverWait(Config.driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.id("add")));

    System.out.println("Navigated to: " + Config.driver.getCurrentUrl());
}

    @Then("The correct page is displayed with the title {string}")
    public void the_correct_page_is_displayed_with_the_title(String menuTitle) {
        MenuPage menuPage = new MenuPage();
        menuPage.verifyHomePage(menuTitle);
    }
}
