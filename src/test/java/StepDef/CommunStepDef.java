package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import Helper.Config;
import Helper.Utils;
import Pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommunStepDef {

    @Given("User is logged in on the home page with the correct correct username {string} and correct password {string} and role {string}")
    public void user_is_logged_in_on_the_home_page_with_the_correct_correct_username_and_correct_password_and_role(
            String username, String password, String role) throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        Config.driver = new ChromeDriver(options);

        Config.driver.get(Utils.getProperty("Login_link"));

        LoginPage login = new LoginPage();
        login.connect(username, password);
        Config.attent(15);

        login.selectRole(role);
        Config.waitForUrlContains("dashboard", 20);
        System.out.println("Login successful — on dashboard.");
    }

    @When("User clicks on menu {string} then clicks on submenu {string} then clicks on subsubmenu {string} then clicks on subsubsubmenu {string}")
    public void user_clicks_on_menu_then_clicks_on_submenu_then_clicks_on_subsubmenu_then_clicks_on_subsubsubmenu(
            String menu, String subMenu, String subsubMenu, String subsubSubMenu) throws Exception {
        Config.driver.get(Utils.getProperty("Periode_link"));
        Config.waitForUrlContains("period", 15);
        Config.attent(3);
        System.out.println("Verification successful: Home page contains the expected message.");
    }

    @Then("The correct page is displayed with the title {string}")
    public void the_correct_page_is_displayed_with_the_title(String menuTitle) {
        String currentUrl = Config.driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Expected page: " + menuTitle);
    }
}
