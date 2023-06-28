package actions.commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BagePage2 {

    public static BasePage getBasePageObject() {
        return new BasePage();
    }

    public void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresent(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver,longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        waitForAlertPresent(driver).accept();
    }

    public void cancelAlert(WebDriver driver) {
        waitForAlertPresent(driver).dismiss();
    }

    public String getTextAlert(WebDriver driver) {
        return waitForAlertPresent(driver).getText();
    }

    public void sendKeyToAlert(WebDriver driver, String TextValue) {
        waitForAlertPresent(driver).sendKeys(TextValue);
    }

    public void switchToWindowById(WebDriver driver, String WindowId) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.equals(WindowId)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String tabTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            driver.switchTo().window(id);
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(tabTitle)) {
                break;
            }
        }
    }

    public void closeAllTabWithoutParent(WebDriver driver, String parentId) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.equals(parentId)) {
                driver.switchTo().window(id);
                driver.close();
            }
            driver.switchTo().window(parentId);

        }
    }
    private By getByXpath(String xpathLocator) {
        return By.xpath(xpathLocator);
    }
    public String getDynamicXpath(String xpathLocator, String... dynamicValues) {
        if(xpathLocator.startsWith("")){
            xpathLocator = String.format(xpathLocator,(Object[]) dynamicValues);
        }
        return xpathLocator;
    }

    public WebElement getWebElement(WebDriver driver, String xpathLocator) {
        return driver.findElement(getByXpath(xpathLocator));
    }

    public WebElement getWebElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return driver.findElement(getByXpath(getDynamicXpath(xpathLocator, dynamicValues)));
    }

    private List<WebElement> getListWebElement(WebDriver driver, String xpathLocator) {
        return driver.findElements(getByXpath(xpathLocator));
    }

    public void clickToElement(WebDriver driver, String xpathLocator) {
        getWebElement(driver, xpathLocator).click();
        sleepInSecond(1);
    }

    public void clickToElement(WebDriver driver, String xpathLocator, String... dynamicValue) {
        getWebElement(driver, xpathLocator, dynamicValue).click();
        sleepInSecond(1);
    }

    public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue) {
        WebElement element = getWebElement(driver, xpathLocator);
        element.clear();
        element.sendKeys(textValue);
    }

    public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue, String... dynamicValue){
        WebElement element = getWebElement(driver, xpathLocator, dynamicValue);
        element.clear();
        element.sendKeys(textValue);
    }

    //*********
    public void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String textItem) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        select.selectByValue(textItem);
    }

    public String getSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        return select.getFirstSelectedOption().getText();

    }

    public boolean isDropdownMultiple(WebDriver driver, String xpathLocator) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        return select.isMultiple();
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem) {
        getWebElement(driver, parentXpath).click();
        sleepInSecond(1);
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        List<WebElement> allItems = expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }
    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem, String... dynamicValues) {
        getWebElement(driver, parentXpath, dynamicValues).click();
        sleepInSecond(1);
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        List<WebElement> allItems = expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }

    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
        return getWebElement(driver,xpathLocator).getAttribute(attributeName);
    }
    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName, String... dynamicValues) {
        return getWebElement(driver,getDynamicXpath(xpathLocator, dynamicValues)).getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver, String xpathLocator) {
        return getWebElement(driver,xpathLocator).getText();
    }

    public String getElementText(WebDriver driver, String xpathLocator, String... dynamicValues ) {
        return getWebElement(driver,getDynamicXpath(xpathLocator, dynamicValues)).getText();
    }

    public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
        return getWebElement(driver,xpathLocator).getCssValue(propertyName);
    }

    public String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    public int getElementSize(WebDriver driver, String xpathLocator) {
        return getListWebElement(driver,xpathLocator).size();

    }
    public int getElementSize(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return getListWebElement(driver,getDynamicXpath(xpathLocator, dynamicValues)).size();

    }

    public void checkToDefaultCheckboxRatio(WebDriver driver, String xpathLocator) {
        WebElement element = getWebElement(driver,xpathLocator);
        if (!element.isSelected()) {
            element.click();
        }

    }

    public void uncheckToDefaultCheckboxRatio(WebDriver driver, String xpathLocator) {
        WebElement element = getWebElement(driver,xpathLocator);
        if (element.isSelected()) {
            element.click();
        }

    }

    public boolean isElementUndisplay(WebDriver driver, String locatorType) {
//        System.out.println("Start time = " + new Date(shortTimeout).toString());
        overrideGlobalTimeout(driver, 2);
        List<WebElement> elements = getListWebElement(driver, locatorType);
        overrideGlobalTimeout(driver, 5);

        if (elements.size() == 0) {
//            System.out.println("Element is not in DOM");
//            System.out.println("End time = " + new Date(shortTimeout).toString());
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
//            System.out.println("Element is in DOM but not visible/displayed");
//            System.out.println("End time = " + new Date(shortTimeout).toString());
            return true;
        } else {
//            System.out.println("Element is in DOM and visible");
            return false;
        }
    }

    private void overrideGlobalTimeout(WebDriver driver, int i) {
        // TODO Auto-generated method stub
    }

    public boolean isElementDisplay(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isDisplayed();
    }

    public boolean isElementEnable(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isSelected();
    }

    public void switchToFrameIFrame(WebDriver driver, String xpathLocator) {
        driver.switchTo().frame(getWebElement(driver, xpathLocator));
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String xpathLocator) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, xpathLocator)).perform();
    }

    public void clickEscapeButton(WebDriver driver) {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    public void scrollToTopPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, 0);");
    }

    public void highlightElement(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, xpathLocator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",element, "style", "border: 2px solid red");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",element,"style",originalStyle);

    }

    public void clickToElementByJs(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
    }

    public void scrollToElement(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollInToView(true);", getWebElement(driver, xpathLocator));

    }

    public void removeAttributeInDOM(WebDriver driver, String xpathLocator, String attributeRemove ) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('"+ attributeRemove + "');", getWebElement(driver, xpathLocator));

    }

    public boolean areJQueryAndJSLoadSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver,longTimeout);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return((Long) jsExecutor.executeScript("return JQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("completed");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    public String getElementValidationMessage(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, xpathLocator));
    }

    public boolean isImageLoad(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"underfined\" && arguments[0].naturalWidth > 0",  getWebElement(driver, xpathLocator));
        if (status) {
            return true;

        } else {
            return false;
        }
    }
    public void waitForElementVisible(WebDriver driver, String xpathLocator) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));

    }
    public void waitForAllElementVisible(WebDriver driver, String xpathLocator) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
    }

    public void waitForElementInvisible(WebDriver driver, String xpathLocator) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForAllElementInvisible(WebDriver driver, String xpathLocator) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, xpathLocator)));
    }

    public void waitForElementClickable(WebDriver driver, String xpathLocator) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
    }
    public void waitForElementClickable(WebDriver driver, String xpathLocator, String... dynamicValues) {
        WebDriverWait expliciWait = new WebDriverWait(driver, longTimeout);
        expliciWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicXpath(xpathLocator, dynamicValues))));
    }
    public void clickBackSpaceButton(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).sendKeys(Keys.BACK_SPACE);
    }
    public void clearText(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).sendKeys(Keys.CONTROL+"A");
        getWebElement(driver, locatorType).sendKeys(Keys.DELETE);
    }
    public void clearText(WebDriver driver, String locatorType, String... dynamicValues) {
        getWebElement(driver, locatorType, dynamicValues).sendKeys(Keys.CONTROL+"A");
        getWebElement(driver, locatorType, dynamicValues).sendKeys(Keys.DELETE);
    }

    public List<WebElement> getListWebElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return driver.findElements(getByXpath(getDynamicXpath(xpathLocator, dynamicValues)));
    }

    public void waitForElementVisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicXpath(xpathLocator, dynamicValues))));
    }

    public boolean isElementDisplay(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)).isDisplayed();
    }

    private long longTimeout = GlobalContains.LONG_TIMEOUT;
    private short shortTimeout = (short) GlobalContains.SHORT_TIMEOUT;
}
