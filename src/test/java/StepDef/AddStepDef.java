package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;

import Helper.Config;
import Helper.Utils;
import Pages.PeriodePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddStepDef {
	@Given("User is on the home page")
	public void user_is_on_the_home_page() throws Exception {
		Config.driver = new ChromeDriver();
		Config.maximazwindow();
		Config.driver.get(Utils.getProperty("HomePage_link"));
		
	}
	
	@When("User clicks on button {string}")
	public void user_clicks_on_button(String string) {
	    PeriodePage periode = new PeriodePage();
	    periode.clickPeriodeAddButton(string);
	}
	
	@Then("The correct popup is displayed with the subtitle {string}")
	public void the_correct_popup_is_displayed_with_the_subtitle(String addtitle) {
		PeriodePage periode = new PeriodePage();
	    periode.verifAddPeriode(addtitle);
	}
	
	@And("User fills the form with the following data {string}, {string}, {string} and clicks on save button")
	public void user_fills_the_form_and_clicks_save(String name, String startDate, String endDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.addPeriode(name, startDate, endDate); // addPeriode already clicks save at the end
	}
	
	@Then("User fills the form with the following data {string}, {string}, {string}")
	public void user_fills_the_form_with_the_following_data(String name, String startDate, String endDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.addPeriodeWhithOutSave(name, startDate, endDate);   
	}
	
	@Then("The confirmation message is displayed {string}")
	public void the_confirmation_message_is_displayed(String confirmMsg) {
	    PeriodePage periode = new PeriodePage();
	    periode.confirmAdd(confirmMsg);
	}
	
	@Then("The Annee Scolaire is added successfully")
	public void the_annee_scolaire_is_added_successfully() {
		Config.driver.quit();
	}
	
	@Then("The error popup is displayed {string} and the message {string} is displayed")
	public void the_error_popup_is_displayed_and_message_is_displayed(String expectedTitle, String expectedMessage) {
	    PeriodePage periode = new PeriodePage();
	    periode.verifErrorPopup(expectedTitle, expectedMessage);
	    Config.driver.quit();
	}
	
	@When("User clicks save without filling the form")
	public void user_clicks_save_without_filling_the_form() {
	    PeriodePage periode = new PeriodePage();
	    periode.clickSave();
	}


	@Then("Error messages are displayed under empty fields with data {string}, {string}")
	public void error_messages_are_displayed_under_empty_fields(String name, String startDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.verifEmptyFieldErrors(name, startDate);
	    Config.driver.quit();
	}
	
	// ── Fill first form without saving (used for Ajouter Plus scenario)
	@And("User fills the first form with the following data {string}, {string}, {string}")
	public void user_fills_the_first_form_with_the_following_data(String name, String startDate, String endDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.fillRow(0, name, startDate, endDate);
	}

	// ── Click +Ajouter Plus button only (no fill)
	@When("User clicks on \"+ajouter plus\" button")
	public void user_clicks_on_ajouter_plus_button() {
	    PeriodePage periode = new PeriodePage();
	    periode.clickAjouterPlus();
	}

	// ── Same step with Then keyword
	@Then("User cliks on \"+ajouter plus\" button")
	public void user_cliks_on_ajouter_plus_button() {
	    PeriodePage periode = new PeriodePage();
	    periode.clickAjouterPlus();
	}

	// ── Fill second form
	@And("User fills the second form with the following data {string}, {string}, {string}")
	public void user_fills_the_second_form(String name, String startDate, String endDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.fillRowAfterAjouterPlus(1, name, startDate, endDate);
	}

	// ── Fill third form
	@And("User fills the third form with the following data {string}, {string}, {string}")
	public void user_fills_the_third_form(String name, String startDate, String endDate) {
	    PeriodePage periode = new PeriodePage();
	    periode.fillRowAfterAjouterPlus(2, name, startDate, endDate);
	}

	// ── Click save button (used for Ajouter Plus scenario)
	@And("User clicks on save button")
	public void user_clicks_on_save_button() {
	    PeriodePage periode = new PeriodePage();
	    periode.clickSave();
	}

	// ── Verify all three added successfully
	@Then("The three Periodes are added successfully")
	public void the_three_periodes_are_added_successfully() {
	    Config.driver.quit();
	}
	
	// ── Click remove button of first row
	@Then("User clicks on the remove button of the first row")
	public void user_clicks_on_the_remove_button_of_the_first_row() {
	    PeriodePage periode = new PeriodePage();
	    periode.removeRow(0);
	}

	// ── Verify only second periode was added
	@Then("Only the second Periode is added successfully")
	public void only_the_second_periode_is_added_successfully() {
	    Config.driver.quit();
	}
	
	@Then("The error message is displayed under the field with invalid data {string}")
	public void the_error_message_is_displayed_under_field_with_invalid_data(String expectedMessage) {
	    PeriodePage periode = new PeriodePage();
	    periode.verifInvalidFieldError(expectedMessage);
	    Config.driver.quit();
	}

	@When("User clicks on \"Annuler\" button")
	public void user_clicks_on_annuler_button() {
	    PeriodePage periode = new PeriodePage();
	    periode.clickCancelButton();
	}

	@Then("User is redirected to the list and {string} does not exist in any page")
	public void user_is_redirected_and_periode_not_in_any_page(String periodeName) {
	    PeriodePage periode = new PeriodePage();
	    periode.verifPerideNotInAnyPage(periodeName);
	    Config.driver.quit();
	}

}