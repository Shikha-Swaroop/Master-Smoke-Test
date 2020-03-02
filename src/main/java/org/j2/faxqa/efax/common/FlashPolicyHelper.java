package org.j2.faxqa.efax.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class FlashPolicyHelper {

    private final ChromeDriver driver;

    public FlashPolicyHelper(ChromeDriver driver) {
        this.driver = driver;
    }

    public FlashPolicyHelper addSite(String site) {
        this.driver.get("chrome://settings/content/siteDetails?site=" + site);
        WebElement root1 = driver.findElement(By.tagName("settings-ui"));
        WebElement shadowRoot1 = expandRootElement(root1);
        WebElement root2 = shadowRoot1.findElement(getByIdentifier("id=container"));
        WebElement main = root2.findElement(getByIdentifier("id=main"));
        WebElement shadowRoot3 = expandRootElement(main);
        WebElement shadowRoot4 = shadowRoot3.findElement(getByIdentifier("class=showing-subpage"));
        WebElement shadowRoot5 = expandRootElement(shadowRoot4);
        WebElement shadowRoot6 = shadowRoot5.findElement(getByIdentifier("id=advancedPage"));
        WebElement shadowRoot7 = shadowRoot6.findElement(By.tagName("settings-privacy-page"));
        WebElement shadowRoot8 = expandRootElement(shadowRoot7);
        WebElement shadowRoot9 = shadowRoot8.findElement(getByIdentifier("id=pages"));
        WebElement shadowRoot10 = shadowRoot9.findElement(By.tagName("settings-subpage"));
        WebElement shadowRoot11 = shadowRoot10.findElement(By.tagName("site-details"));
        WebElement shadowRoot12 = expandRootElement(shadowRoot11);
        WebElement shadowRoot13 = shadowRoot12.findElement(By.id("plugins"));
        WebElement shadowRoot14 = expandRootElement(shadowRoot13);
        new Select(shadowRoot14.findElement(By.id("permission"))).selectByValue("allow");
        return this;
    }

    private By getByIdentifier(String identifier) {
        String[] identifiers = identifier.split("=");

        return identifiers[0].equals("id") ? By.id(identifiers[1]) :
                By.className(identifiers[1]);
    }

    private WebElement expandRootElement(WebElement element) {
        return (WebElement) driver.executeScript("return arguments[0].shadowRoot",element);
    }
}