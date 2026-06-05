package Pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Helper.Config;

public class FilterPage {
	@FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[2]/div/div[1]/button")
    WebElement filterBtn;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/div[1]/div/div/p")
    WebElement titleFilter;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/div[1]/div/div/div/div[1]/div/input")
    WebElement startDateField;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[2]/div")
    List<WebElement> startDateOnList;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/div[1]/div/div/div/div[2]/div/input")
    WebElement endDateField;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[3]/div")
    List<WebElement> endDateOnList;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/div[2]/button")
    WebElement reinitialisationBtn;

    public FilterPage() {
        PageFactory.initElements(Config.driver, this);
    }

    // ── Date format used in the app
    private static final DateTimeFormatter APP_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ────────────────────────────────────────────
    // ── Click "Afficher Filtres" button
    // ────────────────────────────────────────────
    public void clickFilterButton() {
        Config.clickElement(filterBtn, 10);
        Config.attent(2);
        System.out.println("Clicked: Afficher Filtres");
    }

    // ────────────────────────────────────────────
    // ── Verify filter form title
    // ────────────────────────────────────────────
    public void verifFilterTitle(String expectedTitle) {
        String actualTitle = Config.getTextOf(titleFilter, 10);
        System.out.println("Filter title: " + actualTitle);
        Assert.assertTrue(
            "Expected title to contain: " + expectedTitle + " but was: " + actualTitle,
            actualTitle.contains(expectedTitle)
        );
    }

    // ────────────────────────────────────────────
    // ── Fill start date filter field
    // ────────────────────────────────────────────
    public void fillStartDateFilter(String date) {
        Config.waitForVisibility(startDateField, 10);
        startDateField.click();
        startDateField.sendKeys(date);
        Config.attent(2);
        System.out.println("Start date filter set to: " + date);
    }

    // ────────────────────────────────────────────
    // ── Verify all displayed rows have start date >= entered date
    // ────────────────────────────────────────────
    public void verifStartDateFilter(String enteredDate) {    
    	Config.attent(3);   
    	LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);   
    	System.out.println("Verifying all start dates >= " + enteredDate);    
    	int pageNumber = 1;  
    	ListePage listePage = new ListePage();    
    	while (true) {        
    		System.out.println("── Checking page: " + pageNumber);        
    		// ── Option simple : recréer l'objet pour liste fraîche        
    		List<WebElement> freshDates = new FilterPage().startDateOnList;        
    		System.out.println("Rows found: " + freshDates.size());       
    		for (WebElement cell : freshDates) {           
    			String dateText = cell.getAttribute("textContent").trim();           
    			if (!dateText.isEmpty()) {                
    				LocalDate rowDate = LocalDate.parse(dateText, APP_FORMAT);                
    				System.out.println("Row start date: " + dateText);                
    				Assert.assertTrue("❌ Start date '" + dateText + "' is before filter date '" + enteredDate + "'",!rowDate.isBefore(filterDate));            
    			}        
    		}        
    		System.out.println("✔ Page " + pageNumber + ": all dates verified.");        
    		boolean hasNextPage = listePage.goToNextPage();       
    		if (!hasNextPage) {            
    			System.out.println("✔ Filter verified on all " + pageNumber + " page(s).");            
    			break;        
    		}       
    		pageNumber++;    
    	}

    }
}
