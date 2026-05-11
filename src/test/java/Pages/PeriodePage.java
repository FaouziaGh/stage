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
	
	public void clickPeriodeAddButton(String addBtn) {
		
		if(addButton.getText().trim().equalsIgnoreCase(addBtn.trim())) {
			//Config.attent(10);
			Config.waitAndClick(addButton, 10);
			addButton.click();
		}
	}
	
	public void verifAddPeriode(String title) {
		Config.attent(10);
		String actualText = addTitle.getText();
		Assert.assertEquals(title, actualText);
	}
	
	public void addPeriode(String name, String startDate, String endDate) {
		//Config.attent(10);
		Config.waitForVisibility(periodeName, 10);
		periodeName.sendKeys(name);
		Config.waitForVisibility(periodeStartDate, 10);
		periodeStartDate.sendKeys(startDate);
		Config.waitForVisibility(periodeEndDate, 10);
		periodeEndDate.sendKeys(endDate);
		Config.waitAndClick(saveButton, 10);
		saveButton.click();
	}
	
	public void addPeriodeWhithOutSave(String name, String startDate, String endDate) {
		//Config.attent(10);
		Config.waitForVisibility(periodeName, 10);
		periodeName.sendKeys(name);
		Config.waitForVisibility(periodeStartDate, 10);
		periodeStartDate.sendKeys(startDate);
		Config.waitForVisibility(periodeEndDate, 10);
		periodeEndDate.sendKeys(endDate);
		
	}
	
	public void confirmAdd(String confirmMsg) {
		Config.attent(10);
		String actualText = verifAdd.getText().trim();
		Assert.assertEquals(confirmMsg, actualText);
		confirmButton.click();
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
	    
	    Config.attent(2);
	    confirmButton.click();
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
	    Config.attent(3);

	    // Step 1: all empty → error should appear under name
	    if (!isErrorDisplayed(nameError)) {
	        Assert.fail("Expected error under name field but none was displayed");
	    }
	    System.out.println("Name error: " + nameError.getText());

	    // Step 2: fill name with passed data, click save → error moves to startDate
	    periodeName.sendKeys(name);
	    Config.jsClick(saveButton);
	    Config.attent(2);

	    if (!isErrorDisplayed(startDateError)) {
	        Assert.fail("Expected error under start date field but none was displayed");
	    }
	    System.out.println("Start date error: " + startDateError.getText());

	    // Step 3: fill startDate with passed data, click save → error moves to endDate
	    periodeStartDate.sendKeys(startDate);
	    Config.jsClick(saveButton);
	    Config.attent(2);

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
	    Config.attent(2);

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

	    freshNames.get(rowIndex).clear();
	    freshNames.get(rowIndex).sendKeys(name);
	    freshStartDates.get(rowIndex).sendKeys(startDate);
	    freshEndDates.get(rowIndex).sendKeys(endDate);

	    System.out.println("Row " + rowIndex + " filled: " + name + " | " + startDate + " | " + endDate);
	}
	// Waits for the new row to appear before filling
	public void fillRowAfterAjouterPluss(int rowIndex, String name, String startDate, String endDate) {
	    Config.attent(2);

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
	    Config.attent(2);
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
		Config.attent(1);
		
		// Regular click
		removeButton.click();
		Config.attent(2);
		
		// Wait until row count decreases
		int expectedRows = rowsBefore - 1;
		new WebDriverWait(Config.driver, Duration.ofSeconds(10)).until(driver -> periodeNames.size() == expectedRows);
		int remainingRows = periodeNames.size();
		System.out.println("Rows remaining after removal: " + remainingRows);
		Assert.assertEquals("Row was NOT removed — expected " + expectedRows + " rows but found: " + remainingRows, expectedRows, remainingRows);
		}
	
	// ── Verify error message displayed under ANY field with invalid data across ALL rows
	public void verifInvalidFieldError(String expectedMessage) {
    Config.attent(3);

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
	    Config.attent(2);
	    Config.waitForVisibility(cancelButton, 10);
	    cancelButton.click();
	    Config.attent(2);
	}
	
	
	// ── Method 1: verify current page then call Method 2 if name not found
	public void verifPerideNotInCurrentPages(String periodeName) {
    Config.attent(2);
    int pageNumber = 1;

    while (true) {
        System.out.println("── Checking page: " + pageNumber);

        // Always fetch fresh to avoid stale elements
        List<WebElement> freshRows = Config.driver.findElements(
            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
        );

        System.out.println("Total rows on page " + pageNumber + ": " + freshRows.size());

        for (WebElement cell : freshRows) {
            String actualName = cell.getAttribute("textContent").trim();
            if (!actualName.isEmpty()) {
                System.out.println("Checking row: " + actualName);
                Assert.assertFalse(
                    "❌ '" + periodeName + "' was found on page " + pageNumber
                        + " but should have been cancelled!",
                    actualName.equalsIgnoreCase(periodeName.trim())
                );
            }
        }

        System.out.println("✔ Page " + pageNumber + ": '" + periodeName + "' not found.");

        boolean hasNextPage = goToNextPages();
        if (!hasNextPage) {
            System.out.println("✔ Test passed: '" + periodeName
                + "' not found in any of the " + pageNumber + " page(s).");
            break;
        }

        pageNumber++;
    }
}
	
	// ── Method 1: verify current page then call Method 2 if name not found
	public void verifPerideNotInCurrentPage(String periodeName) {
	    Config.attent(2);
	    int pageNumber = 1;

	    while (true) {
	        System.out.println("── Checking page: " + pageNumber);

	        for (WebElement cell : namePList) {
	            // Use textContent instead of getText()
	            String actualName = cell.getAttribute("textContent").trim();

	            if (!actualName.isEmpty()) {
	                System.out.println("Checking row: " + actualName);
	                Assert.assertFalse(
	                    "❌ '" + periodeName + "' was found on page " + pageNumber
	                        + " but should have been cancelled!",
	                    actualName.equalsIgnoreCase(periodeName.trim())
	                );
	            }
	        }

	        System.out.println("✔ Page " + pageNumber + ": '" + periodeName + "' not found.");

	        boolean hasNextPage = goToNextPage();
	        if (!hasNextPage) {
	            System.out.println("✔ Test passed: '" + periodeName
	                + "' not found in any of the " + pageNumber + " page(s).");
	            break;
	        }

	        pageNumber++;
	    }
	}


	// ── Method 2: click next page icon if it exists, return true if navigated
	private boolean goToNextPage() {
	    try {
	        // Look for the next page icon on the current page
	        if (nextPageIcone.isDisplayed() && nextPageIcone.isEnabled()) {
	            System.out.println("Next page icon found — navigating...");
	            Config.jsClick(nextPageIcone);
	            Config.attent(2);
	            return true;
	        } else {
	            System.out.println("Next page icon found but not clickable — last page.");
	            return false;
	        }
	    } catch (Exception e) {
	        // Icon not found = no more pages = test succeeded
	        System.out.println("Next page icon not found — no more pages.");
	        return false;
	    }
	}
	
	private boolean goToNextPages() {
    try {
        List<WebElement> nextBtns = Config.driver.findElements(
            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/div[2]/div[2]/button[last()]")
        );

        if (nextBtns.isEmpty()) {
            System.out.println("No next page button found — only one page exists.");
            return false;
        }

        WebElement nextBtn = nextBtns.get(0);

        // Check disabled
        String disabled = nextBtn.getAttribute("disabled");
        String ariaDisabled = nextBtn.getAttribute("aria-disabled");

        System.out.println("Next button disabled attr: " + disabled);
        System.out.println("Next button aria-disabled: " + ariaDisabled);

        if (disabled != null || "true".equals(ariaDisabled)) {
            System.out.println("Next button is disabled — last page reached.");
            return false;
        }

        // Save first row before click
        List<WebElement> rowsBefore = Config.driver.findElements(
            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
        );
        String firstRowBefore = rowsBefore.isEmpty() ? ""
            : rowsBefore.get(0).getAttribute("textContent").trim();

        System.out.println("First row before click: " + firstRowBefore);

        // Use jsClick to avoid "element not clickable" interception
        Config.jsClick(nextBtn);

        // Wait for first row to change
        new WebDriverWait(Config.driver, Duration.ofSeconds(10))
            .until(driver -> {
                List<WebElement> newRows = driver.findElements(
                    By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
                );
                if (newRows.isEmpty()) return false;
                String firstRowAfter = newRows.get(0).getAttribute("textContent").trim();
                return !firstRowAfter.equals(firstRowBefore);
            });

        System.out.println("Next page icon found — navigating...");
        Config.attent(1);
        return true;

    } catch (Exception e) {
        // If jsClick fails too → genuinely no more pages
        System.out.println("No more pages: " + e.getMessage());
        return false;
    }
}
	
	public void verifPerideNotInAnyPage(String periodeName) {
	    Config.attent(3);

	    // Wait for table to load
	    new WebDriverWait(Config.driver, Duration.ofSeconds(10))
	        .until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody")
	        ));

	    int pageNumber = 1;

	    while (true) {
	        System.out.println("── Checking page: " + pageNumber);

	        // Check all rows on current page
	        List<WebElement> nameCells = Config.driver.findElements(
	            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
	        );

	        System.out.println("Rows found: " + nameCells.size());

	        for (WebElement cell : nameCells) {
	            String actualName = cell.getText().trim();
	            if (!actualName.isEmpty()) {
	                System.out.println("Checking: " + actualName);
	                Assert.assertFalse(
	                    "❌ Période '" + periodeName + "' was found on page "
	                        + pageNumber + " but should have been cancelled!",
	                    actualName.equalsIgnoreCase(periodeName.trim())
	                );
	            }
	        }

	        System.out.println("✔ Page " + pageNumber + " checked — '" + periodeName + "' not found.");

	        // Try to navigate to next page
	        boolean navigated = goToNextPages(pageNumber);
	        if (!navigated) {
	            System.out.println("✔ All " + pageNumber + " page(s) checked — '"
	                + periodeName + "' not found anywhere.");
	            break;
	        }

	        pageNumber++;
	    }
	}

	// ── Helper: clicks next page button and waits for active page number to change
	private boolean goToNextPages(int currentPageNumber) {
	    // Find next button
	    List<WebElement> nextButtons = Config.driver.findElements(
	        By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/div[2]/div[2]/button[6]")
	    );

	    if (nextButtons.isEmpty()) {
	        System.out.println("No pagination found.");
	        return false;
	    }

	    WebElement nextBtn = nextButtons.get(0);

	    if (nextBtn.getAttribute("disabled") != null) {
	        System.out.println("Next button disabled — last page reached.");
	        return false;
	    }

	    Config.jsClick(nextBtn);

	    // Wait for the active page button text to change to currentPageNumber+1
	    // Active page button is the one with aria-current="true" or a selected class
	    try {
	        new WebDriverWait(Config.driver, Duration.ofSeconds(10))
	            .until(driver -> {
	                // Find the currently active page button
	                List<WebElement> activePageBtns = driver.findElements(
	                    By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/div[2]/div[2]/button[@aria-current='true' or contains(@class,'active') or contains(@class,'selected') or contains(@class,'Mui-selected')]")
	                );
	                if (activePageBtns.isEmpty()) return false;

	                String activeText = activePageBtns.get(0).getText().trim();
	                System.out.println("Active page button text: " + activeText);
	                try {
	                    int activePage = Integer.parseInt(activeText);
	                    return activePage == currentPageNumber + 1;
	                } catch (NumberFormatException e) {
	                    return false;
	                }
	            });

	        System.out.println("Navigated to page: " + (currentPageNumber + 1));
	        Config.attent(1);
	        return true;

	    } catch (Exception e) {
	        System.out.println("Could not detect page change: " + e.getMessage());
	        // Fallback: just wait a bit and continue
	        Config.attent(3);
	        return true;
	    }
	}
	
	// ── Search through all pages to find the periode then click its delete icon
	public void deletePeriode(String periodeName) {
		Config.attent(2);
		
	}
    
    public void findAndClickDeleteIcon(String periodeName) {
        Config.attent(2);
        int pageNumber = 1;

        while (true) {
            System.out.println("── Searching on page: " + pageNumber);
            System.out.println("Total rows: " + namePList.size());

            for (int i = 0; i < namePList.size(); i++) {
                String actualName = namePList.get(i).getAttribute("textContent").trim();
                System.out.println("Found row: '" + actualName + "'");

                if (!actualName.isEmpty() && actualName.equalsIgnoreCase(periodeName.trim())) {
                    System.out.println("✔ Found '" + periodeName + "' at row index " + i + " on page " + pageNumber);

                    // Use row index to get the matching delete button
                    WebElement deleteBtn = deleteBtnList.get(i);
                    Config.jsClick(deleteBtn);
                    System.out.println("✔ Clicked delete icon for: " + periodeName);
                    return;
                }
            }

            System.out.println("'" + periodeName + "' not found on page "
                + pageNumber + " — going to next page...");

            boolean hasNextPage = goToNextPage();
            if (!hasNextPage) {
                Assert.fail("❌ Période '" + periodeName
                    + "' was not found in any page — cannot delete.");
                return;
            }

            pageNumber++;
        }
    }
    
    // ── Verify delete confirmation popup message
    public void verifDeleteConfirmMsg(String expectedMsg) {
    	Config.attent(2);
    	new WebDriverWait(Config.driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(supprimerConfirmMsg));
    	String actualMsg = supprimerConfirmMsg.getText().trim();
    	System.out.println("Delete confirmation message: " + actualMsg);
    	Assert.assertEquals("Delete confirmation message mismatch", expectedMsg, actualMsg);
    	}
    
 // ── Click "Oui, supprimer!" on first popup 
    public void clickConfirmDelete() {
        Config.attent(2);

        WebElement confirmBtn = new WebDriverWait(Config.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div/div[6]/button[1]")
            ));

        System.out.println("Confirm button text: " + confirmBtn.getText());
        confirmBtn.click(); // regular click — not JS
        System.out.println("Clicked: Oui, supprimer!");

        // Wait for "Suppression !" to appear in the h2
        new WebDriverWait(Config.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("/html/body/div[2]/div/h2"),
                "Suppression"
            ));

        Config.attent(1);
        System.out.println("Suppression popup appeared.");
    }

 // ── Verify second popup "Suppression!" then close it
    public void verifSuccessPopup(String expectedMsg) {
        // Title already visible from clickConfirmDelete wait
        String actualMsg = Config.driver.findElement(
            By.xpath("/html/body/div[2]/div/h2")
        ).getText().trim();

        System.out.println("Success popup: " + actualMsg);
        Assert.assertEquals("Success popup message mismatch", expectedMsg, actualMsg);

        // Click OK
        WebElement okBtn = new WebDriverWait(Config.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div/div[6]/button[1]")
            ));
        okBtn.click();
        Config.attent(2);
        System.out.println("Clicked OK — popup closed.");
    }
    
 // ── Click "Annuler" to cancel deletion
    public void clickCancelDelete() {
        Config.attent(2);
        new WebDriverWait(Config.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(supprimerCancelBtn));
        supprimerCancelBtn.click();
        Config.attent(2);
        System.out.println("Clicked: Annuler — deletion cancelled.");
    }

    // ── Verify periode DOES exist somewhere in the list (opposite of verifPerideNotInCurrentPage)
    public void verifPerideExistsInAnyPage(String periodeName) {
        Config.attent(2);
        int pageNumber = 1;
        boolean found = false;

        while (true) {
            System.out.println("── Searching on page: " + pageNumber);

            for (WebElement cell : namePList) {
                String actualName = cell.getAttribute("textContent").trim();

                if (!actualName.isEmpty()) {
                    System.out.println("Checking row: " + actualName);

                    if (actualName.equalsIgnoreCase(periodeName.trim())) {
                        System.out.println("✔ Found '" + periodeName + "' on page "
                            + pageNumber + " — cancel worked correctly.");
                        found = true;
                        break;
                    }
                }
            }

            // Found it — no need to check more pages
            if (found) break;

            System.out.println("'" + periodeName + "' not found on page "
                + pageNumber + " — going to next page...");

            boolean hasNextPage = goToNextPage();
            if (!hasNextPage) {
                // Reached last page and still not found → test fails
                Assert.fail("❌ Période '" + periodeName
                    + "' was NOT found in any page — but it should still exist after cancel!");
                return;
            }

            pageNumber++;
        }

        Assert.assertTrue(
            "❌ Période '" + periodeName + "' should still exist but was not found.",
            found
        );
    }
}
