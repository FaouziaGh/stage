package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;

import Helper.Config;
import Helper.Utils;
import Pages.PeriodePage;
import Pages.UpdatePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdatePeriodeStepDef {
	
	@Given("User is on periode page")
	public void user_is_on_periode_page() throws Exception {
		Config.driver = new ChromeDriver();
		Config.maximazwindow();
		Config.driver.get(Utils.getProperty("Periode_link"));
		
	}
	@When("User searches and clicks on the update icone of the periode {string}")
	public void user_searches_and_clicks_on_the_update_icone_of_the_periode(String periodeName) {
		UpdatePage update = new UpdatePage();
        update.findAndClickUpdateIcon(periodeName);
	}

    @And("The update popup that contains the title {string} is displayed")
    public void the_update_popup_is_displayed(String expectedTitle) {
        UpdatePage update = new UpdatePage();
        update.verifUpdatePopup(expectedTitle);
    }

    @Then("User update the form with the following data {string}, {string}, {string} and clicks on update button")
    public void user_update_the_form(String name, String startDate, String endDate) {
        UpdatePage update = new UpdatePage();
        update.updatePeriode(name, startDate, endDate);
    }

    @Then("The update confirmation message is displayed {string}")
    public void the_update_confirmation_message_is_displayed(String expectedMsg) {
        UpdatePage update = new UpdatePage();
        update.verifUpdateConfirmMsg(expectedMsg);
    }

    @Then("The Periode is updated successfully")
    public void the_periode_is_updated_successfully() {
        Config.driver.quit();
    }
    
    @And("The update error popup is displayed {string} and the message {string} is displayed")
	public void the_update_error_popup_is_displayed_and_message_is_displayed(String expectedTitle, String expectedMessage) {
	    UpdatePage periode = new UpdatePage();
	    periode.verifUpdateErrorPopup(expectedTitle, expectedMessage);
	    Config.driver.quit();
	}
	
}