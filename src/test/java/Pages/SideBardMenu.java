package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Helper.Config;

public class SideBardMenu {
	private static final String SKIP = "-";
	private static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);
	
	@FindBy(xpath="/html/body/div/div[1]/div/ul/a/li/div/div[2]/span")
	List<WebElement> menus;
	
	@FindBy(xpath="/html/body/div/div[1]/div/ul/a[6]/div/div/div/ul/li/div/div/div/span")
	List<WebElement> subMenus;
	
	@FindBy(xpath="/html/body/div/div[1]/div/ul/a[6]/div/div/div/ul[1]/div/div/div/ul/li/div/div/div/span")
	List<WebElement> subSubMenus;
	
	@FindBy(xpath="/html/body/div/div[1]/div/ul/a[6]/div/div/div/ul[1]/div/div/div/ul/div/div/div/a/li/div/div/div/span")
	List<WebElement> subSubSubMenus;
	
	@FindBy(tagName="h5")
	WebElement verifPage;
	
	public SideBardMenu() {
		PageFactory.initElements(Config.driver, this);
	}
	
	public void clickMenus(String menuName, String subMenuName, String subSubMenuName, String subSubSubMenuName) {
		Config.attent(10);
		try {
			for(WebElement menu : menus) {
				if(menu.getText().trim().equalsIgnoreCase(menuName.trim())) {
					Config.waitAndClick(menu, 10);
					menu.click();
					for(WebElement subMenu : subMenus) {
						if(subMenu.getText().trim().equalsIgnoreCase(subMenuName.trim())) {
							Config.waitAndClick(subMenu, 10);
							subMenu.click();
							for(WebElement subSubMenu : subSubMenus) {
								if(subSubMenu.getText().trim().equalsIgnoreCase(subSubMenuName.trim())) {
									Config.waitAndClick(subSubMenu, 10);
									subSubMenu.click();
									for(WebElement subSubSubMenu : subSubSubMenus) {
										if(subSubSubMenu.getText().trim().equalsIgnoreCase(subSubSubMenuName.trim())) {
											Config.waitAndClick(subSubSubMenu, 10);
											subSubSubMenu.click();
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to navigate through menu: " + menuName + " > " + subMenuName + " > " + subSubMenuName + " > " + subSubSubMenuName, e);
		}
	}
	
	public void clickMenu(String menuName) {
		Config.attent(10);
		if (isSkip(menuName)) return;
		clickByTextScoped(menuName, menus);
	}

	public void clickSubMenu(String subMenuName) {
		Config.attent(10);
		if (isSkip(subMenuName)) return;
		clickByTextScoped(subMenuName, subMenus);
	}

	public void clickSubSubMenu(String subSubMenuName) {
		if (isSkip(subSubMenuName)) return;
		clickByTextScoped(subSubMenuName, subSubMenus);
	}

	public void clickSubSubSubMenu(String subSubSubMenuName) {
		if (isSkip(subSubSubMenuName)) return;
		clickByTextScoped(subSubSubMenuName, subSubSubMenus);
	}
	
	public void verifyHomePage(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(Config.driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.visibilityOf(verifPage));
        String actualMsg = verifPage.getText().trim();
        if (actualMsg.equals(expectedTitle.trim())) {
            System.out.println("Verification successful: Home page contains the expected message.");
        } else {
            System.out.println("Verification failed: Expected message '" + expectedTitle + "' but found '" + actualMsg + "'.");
        }
    }

	
	private boolean isSkip(String s) {
        return s == null || s.trim().equals(SKIP) || s.trim().isEmpty();
    }
	
	
	// Find first element with normalized text in a provided list, with a short wait
    private WebElement findElementByTextInList(String text, List<WebElement> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Menu list is empty or not initialized for text: " + text);
        }
        String normalized = text.trim();
        WebDriverWait wait = new WebDriverWait(Config.driver, DEFAULT_WAIT);
        long end = System.currentTimeMillis() + DEFAULT_WAIT.toMillis();
        while (System.currentTimeMillis() < end) {
            for (WebElement el : list) {
                try {
                    String t = el.getText();
                    if (t != null && t.trim().equalsIgnoreCase(normalized) && el.isDisplayed()) {
                        return el;
                    }
                } catch (Exception ignore) {
                }
            }
            try { Thread.sleep(500); } catch (InterruptedException ie) { /* ignore */ }
        }
        throw new RuntimeException("Could not find menu element with text '" + text + "'");
    }

    // Click with hover and robust fallback
    private void clickWithHover(WebElement el) {
        if (el == null) {
            throw new IllegalArgumentException("Element to click is null");
        }

        Actions actions = new Actions(Config.driver);
        WebDriverWait wait = new WebDriverWait(Config.driver, DEFAULT_WAIT);

        try {
            actions.moveToElement(el).pause(Duration.ofMillis(200)).perform();
            wait.until(ExpectedConditions.elementToBeClickable(el));
            try {
                el.click();
            } catch (Exception e) {
                // fallback to JS click
                jsClick(el);
            }
        } catch (Exception e) {
            // final fallback: try locating by text globally
            try {
                clickByTextGlobally(el.getText());
            } catch (Exception inner) {
                throw new RuntimeException("Failed to click element with text '" + el.getText() + "'", inner);
            }
        }
    }

    // Click element by searching text inside a provided list (scoped)
    private void clickByTextScoped(String text, List<WebElement> list) {
        WebElement el = findElementByTextInList(text, list);
        clickWithHover(el);
    }

    // Fallback search across the DOM (use sparingly)
    private void clickByTextGlobally(String text) {
        String xpath = "//*[normalize-space(text())='" + text.trim() + "']";
        WebDriverWait wait = new WebDriverWait(Config.driver, DEFAULT_WAIT);
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        try {
            el.click();
        } catch (Exception e) {
            jsClick(el);
        }
    }

    private void jsClick(WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) Config.driver;
        js.executeScript("arguments[0].scrollIntoView(true);", el);
        js.executeScript("arguments[0].click();", el);
    }
}
