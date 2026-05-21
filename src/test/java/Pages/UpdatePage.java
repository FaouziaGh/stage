package Pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Helper.Config;

public class UpdatePage {

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
    List<WebElement> namePList;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[5]/div/div/div[2]/p/span")
    List<WebElement> updateBtnList;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/h2")
    WebElement updatePopupTitle;

    @FindBy(id = "name")
    WebElement periodeName;

    @FindBy(id = "date-start")
    WebElement periodeStartDate;

    @FindBy(id = "date-fin")
    WebElement periodeEndDate;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div[2]/div/button[2]")
    WebElement saveUpdateBtn;

    @FindBy(id = "swal2-html-container")
    WebElement updateConfirmMsg;
    
    @FindBy(id = "swal2-html-container")
    WebElement updatePopupContent;

    @FindBy(xpath = "/html/body/div[3]/div/div[6]/button[1]")
    WebElement updateConfirmBtn;
    
    @FindBy(id = "swal2-title")
    WebElement updateErreurTitle;
    
    @FindBy(id = "swal2-html-container")
    WebElement updateErreurMsg;
    
    @FindBy(xpath= "/html/body/div[2]/div[3]/div/div[1]/div/div/div/span")
    List<WebElement> errorMsg;
    
    @FindBy(xpath= "/html/body/div[2]/div[3]/div/div[2]/div/button[1]")
    WebElement cancelBtn;

    public UpdatePage() {
        PageFactory.initElements(Config.driver, this);
    }

    // ────────────────────────────────────────────
    // ── Find and click update icon
    // ────────────────────────────────────────────
    public void findAndClickUpdateIcon(String periodeName) {
        ListePage listePage = new ListePage();
        int pageNumber = 1;

        while (true) {
            System.out.println("── Searching on page: " + pageNumber);

            List<WebElement> freshRows = Config.driver.findElements(
                By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
            );

            System.out.println("Total rows: " + freshRows.size());

            for (int i = 0; i < freshRows.size(); i++) {
                String actualName = freshRows.get(i).getAttribute("textContent").trim();
                System.out.println("Found row: '" + actualName + "'");

                if (!actualName.isEmpty() && actualName.equalsIgnoreCase(periodeName.trim())) {
                    System.out.println("✔ Found '" + periodeName + "' at row " + i + " on page " + pageNumber);

                    List<WebElement> freshUpdateBtns = Config.driver.findElements(
                        By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[5]/div/div/div[2]/p/span")
                    );

                    // Scroll into view first, then jsClick to bypass any overlay
                    ((JavascriptExecutor) Config.driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", freshUpdateBtns.get(i)
                    );
                    Config.jsClick(freshUpdateBtns.get(i));
                    System.out.println("✔ Clicked update icon for: " + periodeName);
                    return;
                }
            }

            System.out.println("'" + periodeName + "' not found on page "
                + pageNumber + " — going to next page...");

            boolean hasNextPage = listePage.goToNextPage();
            if (!hasNextPage) {
                Assert.fail("❌ Période '" + periodeName
                    + "' was not found in any page — cannot update.");
                return;
            }

            pageNumber++;
        }
    }

    // ────────────────────────────────────────────
    // ── Verify update popup title
    // ────────────────────────────────────────────
    public void verifUpdatePopup(String expectedTitle) {
        // ── Use Config.getTextOf instead of WebDriverWait
        String actualTitle = Config.getTextOf(updatePopupTitle, 10);
        System.out.println("Update popup title: " + actualTitle);
        Assert.assertEquals("Update popup title mismatch", expectedTitle, actualTitle);
    }

    // ────────────────────────────────────────────
    // ── Fill update form and click save
    // ────────────────────────────────────────────
    public void updatePeriodes(String name, String startDate, String endDate) {
        Config.waitForVisibility(periodeName, 10);
        
        // Clear name field properly using CTRL+A then DELETE
        periodeName.click();
        periodeName.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
        periodeName.sendKeys(org.openqa.selenium.Keys.DELETE);
        periodeName.clear();
        periodeName.sendKeys(name);

        // Clear and fill start date
        periodeStartDate.click();
        periodeStartDate.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
        periodeStartDate.sendKeys(org.openqa.selenium.Keys.DELETE);
        periodeStartDate.clear();
        periodeStartDate.sendKeys(startDate);

        // Clear and fill end date
        periodeEndDate.click();
        periodeEndDate.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
        periodeEndDate.sendKeys(org.openqa.selenium.Keys.DELETE);
        periodeEndDate.clear();
        periodeEndDate.sendKeys(endDate);

        Config.clickElement(saveUpdateBtn, 10);
        System.out.println("Update form submitted: " + name + " | " + startDate + " | " + endDate);
    }
    
    public void updatePeriode(String name, String startDate, String endDate) {
        Config.waitForVisibility(periodeName, 10);

        clearField(periodeName);
        periodeName.sendKeys(name);

        clearField(periodeStartDate);
        periodeStartDate.sendKeys(startDate);

        clearField(periodeEndDate);
        periodeEndDate.sendKeys(endDate);

        Config.clickElement(saveUpdateBtn, 10);
        System.out.println("Update form submitted: " + name + " | " + startDate + " | " + endDate);
    }

    private void clearField(WebElement field) {
        Config.waitForVisibility(field, 10);
        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.BACK_SPACE);
        System.out.println("Field cleared. Current value: '" + field.getAttribute("value") + "'");
    }

    // ────────────────────────────────────────────
    // ── Verify update confirmation message
    // ────────────────────────────────────────────
    public void verifUpdateConfirmMsg(String expectedMsg) {
        // ── Use Config.getTextOf instead of WebDriverWait
        String actualMsg = Config.getTextOf(updateConfirmMsg, 10);
        System.out.println("Update confirmation: " + actualMsg);
        Assert.assertTrue(
            "Expected: " + expectedMsg + " but was: " + actualMsg,
            actualMsg.contains(expectedMsg)
        );

        // ── Use Config.clickElement instead of WebDriverWait
        Config.clickElement(updateConfirmBtn, 10);
        Config.attent(2);
    }
    
    public void verifUpdateErrorPopup(String expectedTitle, String expectedMessage) {
        String actualTitle = Config.getTextOf(updateErreurTitle, 10);
        String actualMessage = Config.getTextOf(updatePopupContent, 10);

        System.out.println("Error popup title: " + actualTitle);
        System.out.println("Error popup message: " + actualMessage);

        Assert.assertEquals("Error title mismatch", expectedTitle, actualTitle);
        Assert.assertTrue(
            "Expected message to contain: " + expectedMessage + " but was: " + actualMessage,
            actualMessage.contains(expectedMessage)
        );

        Config.jsClick(updateConfirmBtn);
        System.out.println("Error popup closed.");
    }
 // ── Verify error message under any field
    public void verifFieldErrorMsg(String expectedMessage) {
        Config.attent(30);

        if (errorMsg.isEmpty()) {
            Assert.fail("Expected error message '" + expectedMessage
                + "' but no error messages were found under any field.");
        }

        boolean found = false;

        for (WebElement error : errorMsg) {
            try {
                if (error.isDisplayed()) {
                    String actualMessage = Config.getTextOf(error, 10);
                    System.out.println("Error found: " + actualMessage);
                    if (actualMessage.contains(expectedMessage)) {
                        found = true;
                        System.out.println("✔ Matched expected error: " + expectedMessage);
                    }
                }
            } catch (Exception e) {
                // Element not visible — skip
            }
        }

        Assert.assertTrue(
            "Expected error '" + expectedMessage + "' was not found under any field.",
            found
        );
    }
    
 // ── Clear all fields then click update (to trigger all empty field errors)
    public void clearAllFieldsAndClickUpdate() {
    	Config.attent(10);
    	
    	// ── Clear name field using CTRL+A + DELETE
    	Config.waitForVisibility(periodeName, 20);
    	periodeName.click();
    	periodeName.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
    	periodeName.sendKeys(org.openqa.selenium.Keys.DELETE);
    	periodeName.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
    	System.out.println("Name field after clear: '" + periodeName.getAttribute("value") + "'");
    	
    	// ── Clear start date field
    	periodeStartDate.click();
    	periodeStartDate.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
    	periodeStartDate.sendKeys(org.openqa.selenium.Keys.DELETE);
    	// For date inputs: send BACKSPACE multiple times
    	for (int i = 0; i < 10; i++) {
    		periodeStartDate.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
    		}
    	System.out.println("Start date after clear: '" + periodeStartDate.getAttribute("value") + "'");
    	
    	// ── Clear end date field
    	periodeEndDate.click();
    	periodeEndDate.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
    	periodeEndDate.sendKeys(org.openqa.selenium.Keys.DELETE);

    	for (int i = 0; i < 10; i++) {
    		periodeEndDate.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
    	}
    	System.out.println("End date after clear: '" + periodeEndDate.getAttribute("value") + "'");
    
    	// ── Click update button
    	Config.clickElement(saveUpdateBtn, 10);
    	Config.attent(20);
    	System.out.println("Clicked update button with empty fields.");

    }

    // ── Verify error appears under each empty field sequentially
    // name = value to fill in name field after first error check
    // startDate = value to fill in start date field after second error check
    public void verifEmptyFieldErrors(String name, String startDate) {
    
    	Config.attent(30);
    	// ── Step 1: all empty → error under name
    	List<WebElement> freshErrors = Config.driver.findElements(By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/div/div/span"));
    	System.out.println("Step 1 - errors found: " + freshErrors.size());
    	Assert.assertFalse("Expected error under name field but none found.", freshErrors.isEmpty());
    	for (WebElement e : freshErrors) {
    		if (e.isDisplayed()) System.out.println("Step 1 error: " + e.getText());
    	}
    	System.out.println("✔ Step 1 passed: error under name field.");

    	// ── Step 2: fill name → click update → error moves to startDate
    	Config.clearAndType(periodeName, name);
    	Config.clickElement(saveUpdateBtn, 20);
    	Config.attent(20);
    	freshErrors = Config.driver.findElements(By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/div/div/span"));
    	System.out.println("Step 2 - errors found: " + freshErrors.size());
    	Assert.assertFalse("Expected error under start date field but none found.", freshErrors.isEmpty());
    	for (WebElement e : freshErrors) {
    		if (e.isDisplayed()) System.out.println("Step 2 error: " + e.getText());
    	}
    	System.out.println("✔ Step 2 passed: error under start date field.");

    	// ── Step 3: fill startDate → click update → error moves to endDate
    	Config.clearAndType(periodeStartDate, startDate);
    	Config.clickElement(saveUpdateBtn, 20);
    	Config.attent(20);
    
    	freshErrors = Config.driver.findElements(By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/div/div/span"));
    	System.out.println("Step 3 - errors found: " + freshErrors.size());    
    	Assert.assertFalse("Expected error under end date field but none found.", freshErrors.isEmpty());    
    	for (WebElement e : freshErrors) {        
    		if (e.isDisplayed()) System.out.println("Step 3 error: " + e.getText());    
    	}    
    	System.out.println("✔ Step 3 passed: error under end date field.");
    }
    
 // ── Fill form then click cancel
    public void updatePeriodeAndCancel(String name, String startDate, String endDate) {
        Config.attent(20);

        // Fill the form
        Config.clearAndType(periodeName, name);
        Config.clearAndType(periodeStartDate, startDate);
        Config.clearAndType(periodeEndDate, endDate);

        System.out.println("Form filled with: " + name + " | " + startDate + " | " + endDate);

        // Click cancel instead of save
        Config.clickElement(cancelBtn, 20);
        Config.attent(20);

        System.out.println("Clicked ANNULER — update cancelled.");
    }
}