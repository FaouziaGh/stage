package Pages;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Helper.Config;

public class DeletePage {
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
	
	@FindBy(xpath = "/html/body/div[2]/div/h2")
	WebElement supprimerPopupMsg;

	@FindBy(xpath = "/html/body/div[2]/div/div[6]/button[1]")
	WebElement supprimerPopupBtn;
	
	@FindBy(xpath = "/html/body/div[2]/div/div[6]/button[3]")
	WebElement supprimerCancelBtn;
	
	@FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
	List<WebElement> namePList;
	
	@FindBy(xpath="/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]")
	List<WebElement> namePLists;
	
	@FindBy(xpath = "//*[contains(@class,'MuiIcon-root') and text()='chevron_right']/parent::button")
	WebElement nextPageIcone;
	
	
	public DeletePage() {
		PageFactory.initElements(Config.driver, this);
	}
	
	public void verifAfterDelete(String periodeName) {
    ListePage listePage = new ListePage();
    listePage.verifPerideNotInCurrentPage(periodeName);
}
	
	public boolean goToNextPage() {
		try {
        // ── Find next button specifically by chevron_right icon text
        List<WebElement> nextBtns = Config.driver.findElements(
            By.xpath("//*[contains(@class,'MuiIcon-root') and text()='chevron_right']/parent::button")
        );

        if (nextBtns.isEmpty()) {
            System.out.println("No next button found — single page.");
            return false;
        }

        WebElement nextBtn = nextBtns.get(0);

        // ── Save first row text before clicking
        List<WebElement> rowsBefore = Config.driver.findElements(
            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
        );

        if (rowsBefore.isEmpty()) {
            System.out.println("No rows found — stopping.");
            return false;
        }

        String firstRowBefore = rowsBefore.get(0).getAttribute("textContent").trim();
        System.out.println("First row before click: '" + firstRowBefore + "'");

        // ── Click next button
        Config.jsClick(nextBtn);
        Config.attent(2);

        // ── Check if first row changed after click
        List<WebElement> rowsAfter = Config.driver.findElements(
            By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
        );

        if (rowsAfter.isEmpty()) {
            System.out.println("No rows after click — stopping.");
            return false;
        }

        String firstRowAfter = rowsAfter.get(0).getAttribute("textContent").trim();
        System.out.println("First row after click: '" + firstRowAfter + "'");

        // ── If first row didn't change → last page reached
        if (firstRowAfter.equals(firstRowBefore)) {
            System.out.println("First row unchanged — last page reached. Stopping.");
            return false;
        }

        System.out.println("Next page icon found — navigating...");
        return true;

    } catch (Exception e) {
        System.out.println("No more pages: " + e.getMessage());
        return false;
    }

	}
	
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
    public void verifAfterCancel(String periodeName) {
    ListePage listePage = new ListePage();
    listePage.verifPerideExistsInAnyPage(periodeName);
}

}
