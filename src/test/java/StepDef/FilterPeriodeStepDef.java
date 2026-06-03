package StepDef;

import org.openqa.selenium.chrome.ChromeDriver;

import Helper.Config;
import Helper.Utils;
import Pages.FilterPeriode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FilterPeriodeStepDef {
	@Given("User is on periode list")
	public void user_is_on_periode_list() throws Exception {
		Config.driver = new ChromeDriver();
		Config.maximazwindow();
		Config.driver.get(Utils.getProperty("Periode_link"));
		
	}
	
	@When("User clicks on \"Afficher Filtres\" button")
    public void user_clicks_on_afficher_filtres_button() {
        FilterPeriode filter = new FilterPeriode();
        filter.clickFilterButton();
    }

    @And("The filter form appears with title {string}")
    public void the_filter_form_appears_with_title(String expectedTitle) {
        FilterPeriode filter = new FilterPeriode();
        filter.verifFilterTitle(expectedTitle);
    }

    @Then("User fills the start date field with the required date {string}")
    public void user_fills_start_date_field(String date) {
        FilterPeriode filter = new FilterPeriode();
        filter.fillStartDateFilter(date);
    }

    @Then("All the periodes with start date greater than or equal to {string} are displayed")
    public void all_periodes_with_start_date_greater_than(String date) {
        FilterPeriode filter = new FilterPeriode();
        filter.verifStartDateFilter(date);
        Config.driver.quit();
    }
}
