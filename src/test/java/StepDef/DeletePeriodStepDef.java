package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;

import Helper.Config;
import Helper.Utils;
import Pages.DeletePage;
import Pages.PeriodePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeletePeriodStepDef {
	
	@Given("User is on the page")
	public void user_is_on_the_page() throws Exception {
		Config.driver = new ChromeDriver();
		Config.maximazwindow();
		Config.driver.get(Utils.getProperty("HomePage_link"));
		
	}
	
	@When("User searches and clicks on the delete icon of the periode {string}")
    public void user_searches_and_clicks_on_delete_icon(String periodeName) {
        DeletePage periode = new DeletePage();
        periode.findAndClickDeleteIcon(periodeName);
    }

    @Then("The delete confirmation message is displayed {string}")
    public void the_delete_confirmation_message_is_displayed(String expectedMsg) {
    	DeletePage periode = new DeletePage();
        periode.verifDeleteConfirmMsg(expectedMsg);
    }

    @When("User clicks on \"Oui, supprimer!\"")
    public void user_clicks_on_oui_supprimer() {
    	DeletePage periode = new DeletePage();
        periode.clickConfirmDelete();
    }

    @Then("The success popup is displayed {string}")
    public void the_success_popup_is_displayed(String expectedMsg) {
    	DeletePage periode = new DeletePage();
        periode.verifSuccessPopup(expectedMsg);
    }

    @Then("User is redirected to the list of périodes and {string} does not exist in any page")
    public void user_is_redirected_and_periode_deleted(String periodeName) {
    	DeletePage periode = new DeletePage();
        periode.verifAfterDelete(periodeName);
        Config.driver.quit();
    }
    
    @When("User clicks on \"Annuler\"")
    public void user_clicks_on_annuler() {
    	DeletePage periode = new DeletePage();
        periode.clickCancelDelete();
    }

    @Then("User is redirected to the list and {string} still exists in the list of périodes")
    public void user_is_redirected_and_periode_still_exists(String periodeName) {
    	DeletePage periode = new DeletePage();
        periode.verifAfterCancel(periodeName);
        Config.driver.quit();
    }

}
