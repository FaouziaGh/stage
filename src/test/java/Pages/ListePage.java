package Pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Helper.Config;

public class ListePage {
	@FindBy(xpath = "//*[contains(@class,'MuiIcon-root') and text()='chevron_right']/parent::button")
	List<WebElement> nextBtns;
	
	@FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
	List<WebElement> rowsBefore;
	
	@FindBy(xpath = "/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
	List<WebElement> rowsAfter;
	
	public ListePage() {
		PageFactory.initElements(Config.driver, this);
	}
	
	public boolean goToNextPage() {
		try {
			if (nextBtns.isEmpty()) {
				System.out.println("No next button found — single page.");
				return false;
				}
			WebElement nextBtn = nextBtns.get(0);
			
			if (rowsBefore.isEmpty()) {
				System.out.println("No rows found — stopping."); 
				return false;
				}
			
			String firstRowBefore = rowsBefore.get(0).getAttribute("textContent").trim();
			System.out.println("First row before click: '" + firstRowBefore + "'");
			
			// ── Click next button
			Config.jsClick(nextBtn);
			Config.attent(20);
			
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
	
	public void verifPerideNotInCurrentPage(String periodeName) {
        Config.attent(10);
        int pageNumber = 1;

        while (true) {
            System.out.println("── Checking page: " + pageNumber);

            // Fresh fetch every page
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
                            + " but should not exist!",
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

    // ────────────────────────────────────────────
    // ── Verify periode DOES exist in any page
    // ────────────────────────────────────────────
    public void verifPerideExistsInAnyPage(String periodeName) {
        Config.attent(10);
        int pageNumber = 1;
        boolean found = false;

        while (true) {
            System.out.println("── Searching on page: " + pageNumber);

            // Fresh fetch every page
            List<WebElement> freshRows = Config.driver.findElements(
                By.xpath("/html/body/div/div[2]/div/div/div/div/div[3]/div/div/table/tbody/tr/td[1]/div")
            );

            System.out.println("Total rows on page " + pageNumber + ": " + freshRows.size());

            for (WebElement cell : freshRows) {
                String actualName = cell.getAttribute("textContent").trim();
                if (!actualName.isEmpty()) {
                    System.out.println("Checking row: " + actualName);
                    if (actualName.equalsIgnoreCase(periodeName.trim())) {
                        System.out.println("✔ Found '" + periodeName
                            + "' on page " + pageNumber + " — exists correctly.");
                        found = true;
                        break;
                    }
                }
            }

            if (found) break;

            System.out.println("'" + periodeName + "' not found on page "
                + pageNumber + " — going to next page...");

            boolean hasNextPage = goToNextPage();
            if (!hasNextPage) {
                Assert.fail("❌ Période '" + periodeName
                    + "' was NOT found in any page — but it should still exist!");
                return;
            }

            pageNumber++;
        }

        Assert.assertTrue(
            "❌ Période '" + periodeName + "' should exist but was not found.",
            found
        );
    }
	
	
}

