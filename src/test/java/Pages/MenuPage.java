package Pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Helper.Config;
import Helper.Utils;

public class MenuPage {
	
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
	
	public MenuPage() {
		PageFactory.initElements(Config.driver, this);
	}
	
    
    public void clickMenus(String menu, String subMenu, String subsubMenu, String subsubSubMenu) {
        try {
            // Step 1: click main menu
        	Config.jsClick(Config.waitAndFind(menu));
            Config.attent(10);

            // Step 2: click submenu
            Config.jsClick(Config.waitAndFind(subMenu));
            Config.attent(10);

            // Step 3: click sub-submenu
            Config.jsClick(Config.waitAndFind(subsubMenu));
            Config.attent(10);

            // Step 4: click sub-sub-submenu
            Config.jsClick(Config.waitAndFind(subsubSubMenu));
            Config.attent(10);

        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate through menu: " + menu + " > " + subMenu + " > " + subsubMenu + " > " + subsubSubMenu, e);
        }
    }

	
	public void clickMenu(String menuName, String subMenuName, String subSubMenuName, String subSubSubMenuName) {
		Config.attent(10);
		try {
			for(WebElement menu : menus) {
				if(menu.getText().trim().equalsIgnoreCase(menuName.trim())) {
					//Config.waitAndClick(menu, 10);
					Config.waitForUrlContains(Utils.getProperty("HomePage_link"), 15);
					Config.waitForVisibility(menu, 20);
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
	
	public void verifyHomePage(String expectedTitle) {
        Config.waitForVisibility(verifPage, 15);
       
        String actualMsg = verifPage.getText().trim();
        if (actualMsg.equals(expectedTitle.trim())) {
            System.out.println("Verification successful: Home page contains the expected message.");
        } else {
            System.out.println("Verification failed: Expected message '" + expectedTitle + "' but found '" + actualMsg + "'.");
        }
    }

}
