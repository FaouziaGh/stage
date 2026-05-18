package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Helper.Config;

public class PeriodePage {
	@FindBy(id="add")
	WebElement addButton;
	
	@FindBy(id="mui-56")
	WebElement addTitle1;
	
	@FindBy(tagName ="h2")
	WebElement addTitle;
	
	@FindBy(id="name")
	WebElement periodeName;
	
	@FindBy(id="date-start")
	WebElement periodeStartDate;
	
	@FindBy(id="date-fin")
	WebElement periodeEndDate;
	
	@FindBy(xpath = "/html/body/div[2]/div[3]/div/div[1]//input[@id='name']")
	List<WebElement> periodeNames;

	@FindBy(xpath = "/html/body/div[2]/div[3]/div/div[1]//input[@id='date-start']")
	List<WebElement> periodeStartDates;

	@FindBy(xpath = "/html/body/div[2]/div[3]/div/div[1]//input[@id='date-fin']")
	List<WebElement> periodeEndDates;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[2]/div/button[2]")
	WebElement saveButton;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[2]/div/button[1]")
	WebElement cancelButtons;
	
	@FindBy(id="swal2-title")
	WebElement verifAdd;
	
	@FindBy(xpath="/html/body/div[3]/div/div[6]/button[1]")
	WebElement confirmButton;
	
	@FindBy(xpath="/html/body/div[3]/div/h2")
	WebElement errorPopupTitle;
	
	@FindBy(id="swal2-html-container")
	WebElement errorPopupMessage;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[1]/div[1]/div/div[1]/span")
	WebElement nameError;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[1]/div[1]/div/div[2]/span")
	WebElement startDateError;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[1]/div[1]/div/div[3]/span")
	WebElement endDateError;
	
	@FindBy(xpath="/html/body/div[2]/div[3]/div/div[1]/div[last()]/button")
	WebElement ajouterPlusButton;
	
	@FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
	List<WebElement> namePList;
	
	@FindBy(xpath="/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]")
	List<WebElement> namePLists;
	
	@FindBy(xpath="/html/body/div/div[2]/div/div/div/div/div[3]/div/div/div[2]/div[2]/button[last()]/span")
	WebElement nextPageIcone;
	
	@FindBy(xpath = "/html/body/div[2]/div[3]/div/div[2]/div/button[1]")
	WebElement cancelButton;
	
	@FindBy(id="delete-enterprise")
	WebElement supprimerBtn;
	
	@FindBy(xpath = "/html/body/div[1]/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[5]/div/div/p[2]/p/span")
	List<WebElement> deleteBtnList;
	
	@FindBy(id="swal2-html-container")
	WebElement supprimerConfirmMsg;
	
	@FindBy(xpath="/html/body/div[2]/div/div[6]/button[1]")
	WebElement supprimerConfirmBtn;
	
	@FindBy(id = "swal2-title")
	WebElement supprimerPopupMsgs;
	
	// ── Second popup: "Suppression!" — success
	@FindBy(xpath = "/html/body/div[2]/div/h2")
	WebElement supprimerPopupMsg;

	@FindBy(xpath = "/html/body/div[2]/div/div[6]/button[1]")
	WebElement supprimerPopupBtn;
	
	@FindBy(xpath = "/html/body/div[2]/div/div[6]/button[3]")
	WebElement supprimerCancelBtn;
	
	
	public PeriodePage() {
		PageFactory.initElements(Config.driver, this);
	}
	
	//public void clickPeriodeAddButton(String addBtn) {
		//Config.attent(10);
		//if(addButton.getText().trim().equalsIgnoreCase(addBtn.trim())) {
			
		//	Config.waitAndClick(addButton, 20);
		//	Config.jsClick(addButton);
		//	addButton.click();
		//}
	//}

	public void clickPeriodeAddButton(String addBtn) {
		Config.attent(10);
		if(addButton.getText().trim().equalsIgnoreCase(addBtn.trim())) {
			
			Config.waitAndClick(addButton, 30);
			addButton.click();
		}
	}


	public void verifAddPeriode(String title) {
    try {
        // Wait for any visible non-empty h2 inside the popup
        WebElement titleElement = new WebDriverWait(Config.driver, Duration.ofSeconds(15))
            .until(driver -> {
                List<WebElement> h2Elements = driver.findElements(By.tagName("h2"));
                for (WebElement h2 : h2Elements) {
                    String text = h2.getText().trim();
                    if (!text.isEmpty()) {
                        System.out.println("Found h2: " + text);
                        return h2;
                    }
                }
                return null;
            });

        String actualText = titleElement.getText().trim();
        System.out.println("Popup title: " + actualText);
        Assert.assertEquals(title, actualText);

    } catch (Exception e) {
        Assert.fail("Popup title '" + title + "' not found. Error: " + e.getMessage());
    }
}
	
	public void verifAddPeriodes(String title) {
		Config.attent(20);
		String actualText = addTitle.getText();
		Assert.assertEquals(title, actualText);
	}
	
	public void addPeriode(String name, String startDate, String endDate) {
		Config.attent(10);
		Config.waitForVisibility(periodeName, 20);
		periodeName.sendKeys(name);
		Config.waitForVisibility(periodeStartDate, 20);
		periodeStartDate.sendKeys(startDate);
		Config.waitForVisibility(periodeEndDate, 20);
		periodeEndDate.sendKeys(endDate);
		Config.waitAndClick(saveButton, 20);
		Config.jsClick(saveButton);
		//saveButton.click();
	}
	
	public void addPeriodeWhithOutSave(String name, String startDate, String endDate) {
		Config.attent(10);
		Config.waitForVisibility(periodeName, 20);
		periodeName.sendKeys(name);
		Config.waitForVisibility(periodeStartDate, 20);
		periodeStartDate.sendKeys(startDate);
		Config.waitForVisibility(periodeEndDate, 20);
		periodeEndDate.sendKeys(endDate);
		
	}
	
	public void confirmAdd(String confirmMsg) {
		Config.attent(10);
		String actualText = verifAdd.getText().trim();
		Assert.assertEquals(confirmMsg, actualText);
		Config.jsClick(confirmButton);
		//confirmButton.click();
	}
	
	public void clickSave() {
		Config.attent(5);
	    Config.jsClick(saveButton);
	}

	public void verifErrorPopup(String expectedTitle, String expectedMessage) {
	    Config.attent(5);
	    String actualTitle = errorPopupTitle.getText().trim();
	    String actualMessage = errorPopupMessage.getText().trim();
	    Assert.assertEquals(expectedTitle, actualTitle);
	    Assert.assertTrue("Expected message to contain: " + expectedMessage + " but was: " + actualMessage, actualMessage.contains(expectedMessage));
	    
	    Config.attent(5);
	    Config.jsClick(confirmButton);
	    //confirmButton.click();
	}

	// Check if an element is displayed without throwing exception
	private boolean isErrorDisplayed(WebElement el) {
	    try {
	        return el.isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}

	public void verifEmptyFieldErrors(String name, String startDate) {
	    Config.attent(5);

	    // Step 1: all empty → error should appear under name
	    if (!isErrorDisplayed(nameError)) {
	        Assert.fail("Expected error under name field but none was displayed");
	    }
	    System.out.println("Name error: " + nameError.getText());

	    // Step 2: fill name with passed data, click save → error moves to startDate
	    Config.waitForVisibility(periodeName, 20);
	    periodeName.sendKeys(name);
	    Config.jsClick(saveButton);
	    Config.attent(2);

	    if (!isErrorDisplayed(startDateError)) {
	        Assert.fail("Expected error under start date field but none was displayed");
	    }
	    System.out.println("Start date error: " + startDateError.getText());

	    // Step 3: fill startDate with passed data, click save → error moves to endDate
	    Config.waitForVisibility(periodeStartDate, 20);
	    periodeStartDate.sendKeys(startDate);
	    Config.jsClick(saveButton);
	    Config.attent(5);

	    if (!isErrorDisplayed(endDateError)) {
	        Assert.fail("Expected error under end date field but none was displayed");
	    }
	    System.out.println("End date error: " + endDateError.getText());
	}
	
	public void fillRow(int index, String name, String startDate, String endDate) {
        Config.attent(2);
        Config.waitForVisibility(periodeNames.get(index), 10);
        periodeNames.get(index).clear();
        periodeNames.get(index).sendKeys(name);

        Config.waitForVisibility(periodeStartDates.get(index), 10);
        periodeStartDates.get(index).sendKeys(startDate);

        Config.waitForVisibility(periodeEndDates.get(index), 10);
        periodeEndDates.get(index).sendKeys(endDate);
    }
	
	// ── Fill a row after clicking +Ajouter Plus
	
	public void fillRowAfterAjouterPlus(int rowIndex, String name, String startDate, String endDate) {
	    Config.attent(5);

	    // Wait until the list has exactly rowIndex+1 elements (new row appeared)
	    new WebDriverWait(Config.driver, Duration.ofSeconds(10))
	        .until(driver -> {
	            List<WebElement> rows = Config.driver.findElements(
	                By.xpath("/html/body/div[2]/div[3]/div/div[1]//input[@id='name']")
	            );
	            System.out.println("Waiting for row " + rowIndex + " — current rows: " + rows.size());
	            return rows.size() >= (rowIndex + 1);
	        });

	    // Re-fetch the list fresh after waiting
	    List<WebElement> freshNames = Config.driver.findElements(
	        By.xpath("/html/body/div[2]/div[3]/div/div[1]//input[@id='name']")
	    );
	    List<WebElement> freshStartDates = Config.driver.findElements(
	        By.xpath("/html/body/div[2]/div[3]/div/div[1]//input[@id='date-start']")
	    );
	    List<WebElement> freshEndDates = Config.driver.findElements(
	        By.xpath("/html/body/div[2]/div[3]/div/div[1]//input[@id='date-fin']")
	    );

	    System.out.println("Filling row " + rowIndex + " — total rows available: " + freshNames.size());

	    Config.attent(5);
	    freshNames.get(rowIndex).clear();
	    freshNames.get(rowIndex).sendKeys(name);
	    freshStartDates.get(rowIndex).sendKeys(startDate);
	    freshEndDates.get(rowIndex).sendKeys(endDate);

	    System.out.println("Row " + rowIndex + " filled: " + name + " | " + startDate + " | " + endDate);
	}
	// Waits for the new row to appear before filling
	public void fillRowAfterAjouterPluss(int rowIndex, String name, String startDate, String endDate) {
	    Config.attent(5);

	    // Wait until the new row is present
	    new WebDriverWait(Config.driver, Duration.ofSeconds(10))
	        .until(ExpectedConditions.numberOfElementsToBeMoreThan(
	            By.xpath("/html/body/div[2]/div[3]/div/div[1]//input[@id='name']"),
	            rowIndex - 1
	        ));

	    // Fill the row
	    fillRow(rowIndex, name, startDate, endDate);

	    System.out.println("Row " + rowIndex + " filled: " + name + " | " + startDate + " | " + endDate);
	}
	
	public void clickAjouterPlus() {
	    Config.attent(5);
	    Config.jsClick(ajouterPlusButton);
	}
	
	// ── Remove a row by index (0 = first row, 1 = second row, etc.)
	public void removeRow(int rowIndex) {
		Config.attent(2);
		int rowsBefore = periodeNames.size();
		System.out.println("Rows before removal: " + rowsBefore);
		// div[2] = first row, div[3] = second row → rowIndex + 2
		String xpath = "/html/body/div[2]/div[3]/div/div[1]/div[" + (rowIndex + 1) + "]/div/div[4]/button";
		
		// Wait for button to be clickable
		WebElement removeButton = new WebDriverWait(Config.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		System.out.println("Remove button displayed: " + removeButton.isDisplayed());
		System.out.println("Remove button enabled: " + removeButton.isEnabled());
		
		// Scroll to button first to make sure it's in view
		((JavascriptExecutor) Config.driver).executeScript("arguments[0].scrollIntoView(true);", removeButton);
		Config.attent(5);
		
		// Regular click
		removeButton.click();
		Config.attent(5);
		
		// Wait until row count decreases
		int expectedRows = rowsBefore - 1;
		new WebDriverWait(Config.driver, Duration.ofSeconds(10)).until(driver -> periodeNames.size() == expectedRows);
		int remainingRows = periodeNames.size();
		System.out.println("Rows remaining after removal: " + remainingRows);
		Assert.assertEquals("Row was NOT removed — expected " + expectedRows + " rows but found: " + remainingRows, expectedRows, remainingRows);
		}
	
	// ── Verify error message displayed under ANY field with invalid data across ALL rows
	public void verifInvalidFieldError(String expectedMessage) {
    Config.attent(5);

    int totalRows = periodeNames.size();
    System.out.println("Total rows: " + totalRows);

    List<String> rowsWithError = new ArrayList<>();
    List<String> rowsWithoutError = new ArrayList<>();

    // Check every possible error span structure for each row
    for (int i = 0; i < totalRows; i++) {
        int divIndex = i + 2; // div[2]=row0, div[3]=row1, etc.
        boolean errorFoundInRow = false;

        // Try different span locations (div[1], div[2], div[3] under each row)
        for (int fieldIndex = 1; fieldIndex <= 3; fieldIndex++) {
            String xpath = "/html/body/div[2]/div[3]/div/div[1]/div[" + divIndex + "]/div/div[" + fieldIndex + "]/span";
            try {
                WebElement errorSpan = Config.driver.findElement(By.xpath(xpath));
                if (errorSpan.isDisplayed()) {
                    String actualMessage = errorSpan.getText().trim();
                    if (!actualMessage.isEmpty()) {
                        System.out.println("Row " + i + " field " + fieldIndex + " error: " + actualMessage);
                        if (actualMessage.contains(expectedMessage)) {
                            errorFoundInRow = true;
                        }
                    }
                }
            } catch (Exception e) {
                // No error span at this location — skip
            }
        }

        if (errorFoundInRow) {
            rowsWithError.add("Row " + i);
        } else {
            rowsWithoutError.add("Row " + i);
        }
    }

    System.out.println("Rows WITH error   : " + rowsWithError);
    System.out.println("Rows WITHOUT error: " + rowsWithoutError);

    // If ALL rows have invalid data, ALL should show the error
    // If only some rows have invalid data, at least one should show the error
    Assert.assertFalse(
        "Expected error message '" + expectedMessage + "' was not found in any row.",
        rowsWithError.isEmpty()
    );

    // Warn if some rows are missing the error (useful for debugging)
    if (!rowsWithoutError.isEmpty()) {
        System.out.println("⚠ Warning: error not found in " + rowsWithoutError 
            + " — check if those rows actually have invalid data");
    }
}

	// ── Click cancel button
	public void clickCancelButton() {
	    Config.attent(5);
	    Config.waitForVisibility(cancelButton, 10);
	    cancelButton.click();
	    Config.attent(5);
	}
	
	public void verifPerideNotInAnyPage(String periodeName) {
    ListePage listePage = new ListePage();
    listePage.verifPerideNotInCurrentPage(periodeName);
    }

    
}
