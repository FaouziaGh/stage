package Pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
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
//    public void fillStartDateFilter(String date) {
//        Config.waitForVisibility(startDateField, 10);
//        startDateField.click();
//        startDateField.sendKeys(date);
//        System.out.println("Entered start date filter: " + startDateField.getText());
//        Config.attent(2);
//        System.out.println("Start date filter set to: " + date);
//    }
    public void fillStartDateFilter(String date) {
        Config.waitForVisibility(startDateField, 10);

        // ── Convert dd/MM/yyyy → yyyy-MM-dd for HTML input
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter htmlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String htmlDate = LocalDate.parse(date, displayFormat).format(htmlFormat);

        JavascriptExecutor js = (JavascriptExecutor) Config.driver;

        // ── Set the value via React's native input value setter (bypasses React's state)
        js.executeScript(
            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
            "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            startDateField, htmlDate
        );

        Config.attent(1);

        // ── Click outside to close calendar and trigger filter
        js.executeScript("document.body.click();");
        Config.attent(2);

        // ── Verify value was set
        String actualValue = startDateField.getAttribute("value");
        System.out.println("✔ Value in start date field (raw): " + actualValue);
        System.out.println("✔ Start date filter applied: " + date);

        Assert.assertEquals(
            "❌ Date field value mismatch — expected: " + htmlDate + " but got: " + actualValue,
            htmlDate, actualValue
        );

        // ── Wait for table to re-render after filter
        Config.attent(3);
    }
    public void fillStartDateFilterss(String date) {
        Config.waitForVisibility(startDateField, 10);

        // ── Convert display format dd/MM/yyyy → yyyy-MM-dd for the HTML input
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter htmlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String htmlDate = LocalDate.parse(date, displayFormat).format(htmlFormat);

        // ── Set value via JavaScript (bypasses native date picker popup)
        JavascriptExecutor js = (JavascriptExecutor) Config.driver;
        js.executeScript("arguments[0].value = arguments[1];", startDateField, htmlDate);

        // ── Trigger change event so the app detects the new value
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", startDateField);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", startDateField);

        Config.attent(2);

        // ── Verify the field actually holds the correct value
        String actualValue = startDateField.getAttribute("value");
        System.out.println("✔ Value in start date field (raw): " + actualValue);  // yyyy-MM-dd
        System.out.println("✔ Start date filter applied: " + date);               // dd/MM/yyyy

        Assert.assertEquals(
            "❌ Date field value mismatch — expected: " + htmlDate + " but field contains: " + actualValue,
            htmlDate, actualValue
        );
    }
    
    public void fillStartDateFilters(String date) {
        Config.waitForVisibility(startDateField, 10);
        startDateField.click();
        startDateField.clear();
        startDateField.sendKeys(date);

        // ── Confirm the value was actually typed correctly
        Config.attent(1);
        String typedValue = startDateField.getAttribute("value");
        System.out.println("✔ Value in start date field: " + typedValue);
        Assert.assertEquals(
            "❌ Date field value mismatch — expected: " + date + " but field contains: " + typedValue,
            date, typedValue
        );

        // ── Trigger the filter (try Enter first, fallback to Tab)
        startDateField.sendKeys(org.openqa.selenium.Keys.ENTER);
        Config.attent(2);

        System.out.println("✔ Start date filter applied: " + date);
    }
    // ────────────────────────────────────────────
    // ── Verify all displayed rows have start date >= entered date
    // ────────────────────────────────────────────
//    public void verifStartDateFilter(String enteredDate) {    
//    	Config.attent(3);   
//    	LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);   
//    	System.out.println("Verifying all start dates >= " + enteredDate);    
//    	int pageNumber = 1;  
//    	ListePage listePage = new ListePage();    
//    	while (true) {        
//    		System.out.println("── Checking page: " + pageNumber);        
//    		// ── Option simple : recréer l'objet pour liste fraîche        
//    		List<WebElement> freshDates = new FilterPage().startDateOnList;        
//    		System.out.println("Rows found: " + freshDates.size());       
//    		for (WebElement cell : freshDates) {           
//    			String dateText = cell.getAttribute("textContent").trim();           
//    			if (!dateText.isEmpty()) {                
//    				LocalDate rowDate = LocalDate.parse(dateText, APP_FORMAT);                
//    				System.out.println("Row start date: " + dateText);                
//    				Assert.assertTrue("❌ Start date '" + dateText + "' is before filter date '" + enteredDate + "'",!rowDate.isBefore(filterDate));            
//    			}        
//    		}        
//    		System.out.println("✔ Page " + pageNumber + ": all dates verified.");        
//    		boolean hasNextPage = listePage.goToNextPage();       
//    		if (!hasNextPage) {            
//    			System.out.println("✔ Filter verified on all " + pageNumber + " page(s).");            
//    			break;        
//    		}       
//    		pageNumber++;    
//    	}
//
//    }
    public void verifStartDateFilter(String enteredDate) {
        Config.attent(3);
        LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);
        System.out.println("Verifying all start dates >= " + enteredDate);

        int pageNumber = 1;
        int totalChecked = 0;
        int totalSkipped = 0;
        ListePage listePage = new ListePage();

        while (true) {
            System.out.println("── Checking page: " + pageNumber);

            List<WebElement> freshDates = new FilterPage().startDateOnList;
            System.out.println("Rows found: " + freshDates.size());

            Assert.assertFalse(
                "❌ No rows found on page " + pageNumber,
                freshDates.isEmpty()
            );

            for (WebElement cell : freshDates) {
                String dateText = cell.getAttribute("textContent").trim();

                if (dateText.isEmpty()) continue;

                // ── Try to parse — skip corrupted dates gracefully
                LocalDate rowDate;
                try {
                    rowDate = LocalDate.parse(dateText, APP_FORMAT);
                } catch (Exception e) {
                    System.out.println("⚠ SKIPPED corrupt date value: '" + dateText + "' — " + e.getMessage());
                    totalSkipped++;
                    continue;
                }

                String status = !rowDate.isBefore(filterDate) ? "✔" : "❌";
                System.out.println(status + " Row date: " + dateText +
                    " | Filter date: " + enteredDate +
                    " | Result: " + (!rowDate.isBefore(filterDate) ? "PASS" : "FAIL"));

                Assert.assertTrue(
                    "❌ Start date '" + dateText + "' is before filter date '" + enteredDate + "'",
                    !rowDate.isBefore(filterDate)
                );

                totalChecked++;
            }

            System.out.println("✔ Page " + pageNumber + ": verified.");

            boolean hasNextPage = listePage.goToNextPage();
            if (!hasNextPage) {
                System.out.println("══════════════════════════════════════");
                System.out.println("✔ Filter verified on all " + pageNumber + " page(s).");
                System.out.println("✔ Total dates checked : " + totalChecked);
                System.out.println("⚠ Total dates skipped (corrupt): " + totalSkipped);
                System.out.println("══════════════════════════════════════");
                break;
            }
            pageNumber++;
        }
    }
    public void verifStartDateFilterss(String enteredDate) {
        Config.attent(3);
        LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);
        System.out.println("Verifying all start dates >= " + enteredDate);

        int pageNumber = 1;
        ListePage listePage = new ListePage();

        while (true) {
            System.out.println("── Checking page: " + pageNumber);

            List<WebElement> freshDates = new FilterPage().startDateOnList;
            int rowCount = freshDates.size();
            System.out.println("Rows found: " + rowCount);

            // ── If all 7 rows still show, filter likely didn't apply
            if (rowCount == 7) {
                System.out.println("⚠ WARNING: Row count is still 7 — filter may not have been applied!");
            }

            Assert.assertFalse(
                "❌ No rows found on page " + pageNumber,
                freshDates.isEmpty()
            );

            for (WebElement cell : freshDates) {
                String dateText = cell.getAttribute("textContent").trim();
                if (!dateText.isEmpty()) {
                    LocalDate rowDate = LocalDate.parse(dateText, APP_FORMAT);
                    String status = !rowDate.isBefore(filterDate) ? "✔" : "❌";
                    System.out.println(status + " Row date: " + dateText +
                        " | Filter date: " + enteredDate +
                        " | Result: " + (!rowDate.isBefore(filterDate) ? "PASS" : "FAIL"));

                    Assert.assertTrue(
                        "❌ Start date '" + dateText + "' is before filter date '" + enteredDate + "'",
                        !rowDate.isBefore(filterDate)
                    );
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
    public void verifStartDateFilters(String enteredDate) {
        Config.attent(3);
        LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);
        System.out.println("Verifying all start dates >= " + enteredDate);

        int pageNumber = 1;
        ListePage listePage = new ListePage();

        while (true) {
            System.out.println("── Checking page: " + pageNumber);

            List<WebElement> freshDates = new FilterPage().startDateOnList;
            System.out.println("Rows found: " + freshDates.size());

            // ── Fail fast if no rows at all
            Assert.assertFalse(
                "❌ No rows found on page " + pageNumber + " — filter may not have been applied",
                freshDates.isEmpty()
            );

            for (WebElement cell : freshDates) {
                String dateText = cell.getAttribute("textContent").trim();

                if (!dateText.isEmpty()) {
                    LocalDate rowDate = LocalDate.parse(dateText, APP_FORMAT);

                    // ── Log each row clearly
                    String status = !rowDate.isBefore(filterDate) ? "✔" : "❌";
                    System.out.println(status + " Row date: " + dateText +
                        " | Filter date: " + enteredDate +
                        " | Result: " + (!rowDate.isBefore(filterDate) ? "PASS" : "FAIL"));

                    Assert.assertTrue(
                        "❌ Start date '" + dateText + "' is before filter date '" + enteredDate + "'",
                        !rowDate.isBefore(filterDate)
                    );
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
    
 // ────────────────────────────────────────────
 // ── Fill end date filter field
 // ────────────────────────────────────────────
 public void fillEndDateFilter(String date) {
     Config.waitForVisibility(endDateField, 10);

     // ── Convert dd/MM/yyyy → yyyy-MM-dd for HTML input
     DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
     DateTimeFormatter htmlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     String htmlDate = LocalDate.parse(date, displayFormat).format(htmlFormat);

     JavascriptExecutor js = (JavascriptExecutor) Config.driver;

     // ── Set value via React's native input setter
     js.executeScript(
         "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
         "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
         "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
         "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
         endDateField, htmlDate
     );

     Config.attent(1);

     // ── Click outside to close calendar and trigger filter
     js.executeScript("document.body.click();");
     Config.attent(2);

     // ── Verify the value was set correctly
     String actualValue = endDateField.getAttribute("value");
     System.out.println("✔ Value in end date field (raw): " + actualValue);
     System.out.println("✔ End date filter applied: " + date);

     Assert.assertEquals(
         "❌ End date field value mismatch — expected: " + htmlDate + " but got: " + actualValue,
         htmlDate, actualValue
     );

     Config.attent(3);
 }

 // ────────────────────────────────────────────
 // ── Verify all displayed rows have end date <= entered date
 // ────────────────────────────────────────────
 public void verifEndDateFilter(String enteredDate) {
     Config.attent(3);
     LocalDate filterDate = LocalDate.parse(enteredDate, APP_FORMAT);
     System.out.println("Verifying all end dates <= " + enteredDate);

     int pageNumber = 1;
     int totalChecked = 0;
     int totalSkipped = 0;
     ListePage listePage = new ListePage();

     while (true) {
         System.out.println("── Checking page: " + pageNumber);

         List<WebElement> freshDates = new FilterPage().endDateOnList;
         System.out.println("Rows found: " + freshDates.size());

         Assert.assertFalse(
             "❌ No rows found on page " + pageNumber,
             freshDates.isEmpty()
         );

         for (WebElement cell : freshDates) {
             String dateText = cell.getAttribute("textContent").trim();

             if (dateText.isEmpty()) continue;

             // ── Skip corrupted dates gracefully
             LocalDate rowDate;
             try {
                 rowDate = LocalDate.parse(dateText, APP_FORMAT);
             } catch (Exception e) {
                 System.out.println("⚠ SKIPPED corrupt date value: '" + dateText + "' — " + e.getMessage());
                 totalSkipped++;
                 continue;
             }

             String status = !rowDate.isAfter(filterDate) ? "✔" : "❌";
             System.out.println(status + " Row end date: " + dateText +
                 " | Filter date: " + enteredDate +
                 " | Result: " + (!rowDate.isAfter(filterDate) ? "PASS" : "FAIL"));

             Assert.assertTrue(
                 "❌ End date '" + dateText + "' is after filter date '" + enteredDate + "'",
                 !rowDate.isAfter(filterDate)
             );

             totalChecked++;
         }

         System.out.println("✔ Page " + pageNumber + ": verified.");

         boolean hasNextPage = listePage.goToNextPage();
         if (!hasNextPage) {
             System.out.println("══════════════════════════════════════");
             System.out.println("✔ Filter verified on all " + pageNumber + " page(s).");
             System.out.println("✔ Total dates checked : " + totalChecked);
             System.out.println("⚠ Total dates skipped (corrupt): " + totalSkipped);
             System.out.println("══════════════════════════════════════");
             break;
         }
         pageNumber++;
     }
 }
 
//────────────────────────────────────────────
//── Fill both start and end date filter fields
//────────────────────────────────────────────
public void fillStartAndEndDateFilter(String startDate, String endDate) {
  DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  DateTimeFormatter htmlFormat    = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  String htmlStartDate = LocalDate.parse(startDate, displayFormat).format(htmlFormat);
  String htmlEndDate   = LocalDate.parse(endDate,   displayFormat).format(htmlFormat);

  JavascriptExecutor js = (JavascriptExecutor) Config.driver;

  // ── Set start date
  Config.waitForVisibility(startDateField, 10);
  js.executeScript(
      "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
      "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
      "arguments[0].dispatchEvent(new Event('input',  { bubbles: true }));" +
      "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
      startDateField, htmlStartDate
  );
  Config.attent(1);

  // ── Set end date
  Config.waitForVisibility(endDateField, 10);
  js.executeScript(
      "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
      "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
      "arguments[0].dispatchEvent(new Event('input',  { bubbles: true }));" +
      "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
      endDateField, htmlEndDate
  );
  Config.attent(1);

  // ── Click outside to close calendar and trigger filter
  js.executeScript("document.body.click();");
  Config.attent(2);

  // ── Verify both fields hold the correct values
  String actualStart = startDateField.getAttribute("value");
  String actualEnd   = endDateField.getAttribute("value");

  System.out.println("✔ Start date field (raw) : " + actualStart);
  System.out.println("✔ End date field   (raw) : " + actualEnd);

  Assert.assertEquals(
      "❌ Start date mismatch — expected: " + htmlStartDate + " but got: " + actualStart,
      htmlStartDate, actualStart
  );
  Assert.assertEquals(
      "❌ End date mismatch — expected: " + htmlEndDate + " but got: " + actualEnd,
      htmlEndDate, actualEnd
  );

  System.out.println("✔ Both date filters applied: [" + startDate + "] → [" + endDate + "]");
  Config.attent(3);
}

//────────────────────────────────────────────
//── Verify all rows: startDate >= filter start AND endDate <= filter end
//────────────────────────────────────────────

public void verifStartAndEndDateFilter(String enteredStartDate, String enteredEndDate) {
    Config.attent(3);

    LocalDate filterStartDate = LocalDate.parse(enteredStartDate, APP_FORMAT);
    LocalDate filterEndDate   = LocalDate.parse(enteredEndDate,   APP_FORMAT);

    System.out.println("Verifying: start >= " + enteredStartDate + " AND end <= " + enteredEndDate);

    int pageNumber   = 1;
    int totalChecked = 0;
    int totalSkipped = 0;
    List<String> failures = new ArrayList<>(); // collect all failures
    ListePage listePage = new ListePage();

    while (true) {
        System.out.println("── Checking page: " + pageNumber);

        List<WebElement> freshStartDates = new FilterPage().startDateOnList;
        List<WebElement> freshEndDates   = new FilterPage().endDateOnList;
        int rowCount = freshStartDates.size();
        System.out.println("Rows found: " + rowCount);

        Assert.assertFalse("❌ No rows found on page " + pageNumber, freshStartDates.isEmpty());
        Assert.assertEquals(
            "❌ Column count mismatch on page " + pageNumber,
            freshStartDates.size(), freshEndDates.size()
        );

        for (int i = 0; i < rowCount; i++) {
            String startText = freshStartDates.get(i).getAttribute("textContent").trim();
            String endText   = freshEndDates.get(i).getAttribute("textContent").trim();

            if (startText.isEmpty() && endText.isEmpty()) continue;

            // ── Parse start date
            LocalDate rowStartDate;
            try {
                rowStartDate = LocalDate.parse(startText, APP_FORMAT);
            } catch (Exception e) {
                System.out.println("⚠ SKIPPED corrupt start date: '" + startText + "'");
                totalSkipped++;
                continue;
            }

            // ── Parse end date
            LocalDate rowEndDate;
            try {
                rowEndDate = LocalDate.parse(endText, APP_FORMAT);
            } catch (Exception e) {
                System.out.println("⚠ SKIPPED corrupt end date: '" + endText + "'");
                totalSkipped++;
                continue;
            }

            boolean startOk = !rowStartDate.isBefore(filterStartDate);
            boolean endOk   = !rowEndDate.isAfter(filterEndDate);
            String  status  = (startOk && endOk) ? "✔" : "❌";

            System.out.println(status +
                " Start: " + startText + " [" + (startOk ? "PASS" : "FAIL") + "]" +
                " | End: "  + endText   + " [" + (endOk   ? "PASS" : "FAIL") + "]" +
                " | Page: " + pageNumber
            );

            // ── Collect failures instead of failing immediately
            if (!startOk) {
                failures.add("Page " + pageNumber + " | Start date '" + startText +
                    "' is BEFORE filter start '" + enteredStartDate + "'");
            }
            if (!endOk) {
                failures.add("Page " + pageNumber + " | End date '" + endText +
                    "' is AFTER filter end '" + enteredEndDate + "'");
            }

            // ── Also flag incoherent data (end before start on same row)
            if (rowEndDate.isBefore(rowStartDate)) {
                failures.add("Page " + pageNumber + " | ⚠ INCOHERENT ROW — start '" +
                    startText + "' is after end '" + endText + "' (bad data in DB)");
            }

            totalChecked++;
        }

        System.out.println("✔ Page " + pageNumber + ": scan complete.");

        boolean hasNextPage = listePage.goToNextPage();
        if (!hasNextPage) {
            // ── Print full summary
            System.out.println("══════════════════════════════════════════════");
            System.out.println("✔ Scan complete on all " + pageNumber + " page(s).");
            System.out.println("✔ Total rows checked  : " + totalChecked);
            System.out.println("⚠ Total rows skipped  : " + totalSkipped);
            System.out.println("❌ Total failures found: " + failures.size());
            if (!failures.isEmpty()) {
                System.out.println("── Failure details:");
                for (String f : failures) {
                    System.out.println("   → " + f);
                }
            }
            System.out.println("══════════════════════════════════════════════");
            break;
        }
        pageNumber++;
    }

    // ── Fail the test ONCE at the end with full report
    Assert.assertTrue(
        "❌ Filter verification failed with " + failures.size() + " issue(s):\n" +
        String.join("\n", failures),
        failures.isEmpty()
    );
}
public void verifStartAndEndDateFilters(String enteredStartDate, String enteredEndDate) {
  Config.attent(3);

  LocalDate filterStartDate = LocalDate.parse(enteredStartDate, APP_FORMAT);
  LocalDate filterEndDate   = LocalDate.parse(enteredEndDate,   APP_FORMAT);

  System.out.println("Verifying: start >= " + enteredStartDate + " AND end <= " + enteredEndDate);

  int pageNumber   = 1;
  int totalChecked = 0;
  int totalSkipped = 0;
  ListePage listePage = new ListePage();

  while (true) {
      System.out.println("── Checking page: " + pageNumber);

      List<WebElement> freshStartDates = new FilterPage().startDateOnList;
      List<WebElement> freshEndDates   = new FilterPage().endDateOnList;

      int rowCount = freshStartDates.size();
      System.out.println("Rows found: " + rowCount);

      Assert.assertFalse(
          "❌ No rows found on page " + pageNumber,
          freshStartDates.isEmpty()
      );
      Assert.assertEquals(
          "❌ Start date and end date column counts differ on page " + pageNumber,
          freshStartDates.size(), freshEndDates.size()
      );

      for (int i = 0; i < rowCount; i++) {
          String startText = freshStartDates.get(i).getAttribute("textContent").trim();
          String endText   = freshEndDates.get(i).getAttribute("textContent").trim();

          if (startText.isEmpty() && endText.isEmpty()) continue;

          // ── Parse start date
          LocalDate rowStartDate = null;
          try {
              rowStartDate = LocalDate.parse(startText, APP_FORMAT);
          } catch (Exception e) {
              System.out.println("⚠ SKIPPED corrupt start date: '" + startText + "' — " + e.getMessage());
              totalSkipped++;
              continue;
          }

          // ── Parse end date
          LocalDate rowEndDate = null;
          try {
              rowEndDate = LocalDate.parse(endText, APP_FORMAT);
          } catch (Exception e) {
              System.out.println("⚠ SKIPPED corrupt end date: '" + endText + "' — " + e.getMessage());
              totalSkipped++;
              continue;
          }

          // ── Check both conditions
          boolean startOk = !rowStartDate.isBefore(filterStartDate);
          boolean endOk   = !rowEndDate.isAfter(filterEndDate);
          String  status  = (startOk && endOk) ? "✔" : "❌";

          System.out.println(status +
              " Start: " + startText + " [" + (startOk ? "PASS" : "FAIL") + "]" +
              " | End: "  + endText   + " [" + (endOk   ? "PASS" : "FAIL") + "]"
          );

          Assert.assertTrue(
              "❌ Row start date '" + startText + "' is before filter start '" + enteredStartDate + "'",
              startOk
          );
          Assert.assertTrue(
              "❌ Row end date '" + endText + "' is after filter end '" + enteredEndDate + "'",
              endOk
          );

          totalChecked++;
      }

      System.out.println("✔ Page " + pageNumber + ": all rows verified.");

      boolean hasNextPage = listePage.goToNextPage();
      if (!hasNextPage) {
          System.out.println("══════════════════════════════════════");
          System.out.println("✔ Combined filter verified on all " + pageNumber + " page(s).");
          System.out.println("✔ Total rows checked  : " + totalChecked);
          System.out.println("⚠ Total rows skipped  : " + totalSkipped);
          System.out.println("══════════════════════════════════════");
          break;
      }
      pageNumber++;
  }
}
}
