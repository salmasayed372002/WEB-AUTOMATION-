package Pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

import static util.Utility.selectRandomElement;

public class P01_HomePage {
    WebDriver customDriver;

    public P01_HomePage(WebDriver driver) {
        customDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//a)[@title='My Account']")
    WebElement accountElement;

    @FindBy(xpath = "(//a)[text()='Register']")
    WebElement registerElement;

    @FindBy(xpath = "(//a)[text()='Login'][1]")
    WebElement loginElement;

    @FindBy(xpath = "(//a)[text()='Logout'][1]")
    WebElement logout;

    @FindBy(xpath = "(//div)[@class='row'][3]/div")
    List<WebElement> listOfProductsFromHome;

    @FindBy(xpath = "(//img)[@class='img-responsive']")
    WebElement websiteLogo;

    @FindBy(xpath = "(//h1)[1]")
    WebElement secondItemDetails;

    @FindBy(xpath = "(//div)[@id='input-option218']/div")
    List<WebElement> listOfRadios;

    @FindBy(xpath = "(//div)[@id='input-option223']/div")
    List<WebElement> listOfCheckboxes;

    @FindBy(name = "option[208]")
    WebElement text;

    @FindBy(name = "option[217]")
    WebElement selectOption;

    @FindBy(name = "option[209]")
    WebElement textArea;

    @FindBy(id = "button-upload222")
    WebElement uploadButton;

    @FindBy(xpath = "(//button)[@class='btn btn-default'][3]")
    WebElement calendarButton;

    @FindBy(xpath = "(//table)[@class=\"table-condensed\"]//td")
    List<WebElement> allDates;

    @FindBy(id = "button-cart")
    WebElement addItemToCartButton;

    @FindBy(name = "search")
    WebElement searchTextField;

    @FindBy(xpath = "(//button)[@type='button'][4]")
    WebElement searchButton;


    @FindBy(xpath = "(//div)[@id='content']/div[3]/div")
    List<WebElement> searchResultList;

    @FindBy(xpath = "(//span)[text()='Currency']")
    WebElement currency;

    @FindBy(xpath = "(//ul)[@class='dropdown-menu']/li")
    List<WebElement> currencyList;

    @FindBy(xpath = "(//button)[text()='€ Euro']")
    WebElement poundSterlingElement;

    @FindBy(xpath = "(//strong)")
    WebElement poundSterlingText;

    @FindBy(xpath = "(//ul)[@class='nav navbar-nav']/li")
    List<WebElement> categoryList;

    private String categoryName = "";

    @FindBy(xpath = "(//div)[@id='content']/h2")
    WebElement categoryTitleName;


    public void enterRegisterPage() {
        accountElement.click();
        registerElement.click();
    }

    public void enterLoginPage() {
        accountElement.click();
        loginElement.click();
    }

    public void logout() {
        accountElement.click();
        logout.click();
    }


    public void search(String searchText)
    {
        searchTextField.sendKeys(searchText);
        searchButton.click();
    }

    public Boolean searchResult(String searchText) {
        return searchResultList.get(0).findElement(By.xpath("//div[1]/div/div[2]/div[1]/h4")).getText().contains(searchText);
    }


    public void openCurrencyDropMenu(Boolean getRandom) {
        currency.click();
        if (getRandom) selectRandomElement(currencyList).click();
        else poundSterlingElement.click();
    }


    public Boolean getEuroText() {
        return poundSterlingText.getText().contains("€");
    }


    public void openCategoryDropMenu() {
        selectRandomCategory(categoryList);
    }

    public Boolean isSelectedCategoryTrue() {
        WebDriverWait wait = new WebDriverWait(customDriver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.urlContains("https://awesomeqa.com/ui/index.php?route=product/category"));
            String categoryTitle = categoryTitleName.getText();
            return categoryName.contains(categoryTitle);
        } catch (TimeoutException e) {
            System.err.println("URL did not change as expected within the timeout.");
        }
        return false;
    }

    public void selectFromFirstThreeCategories()
    {
        List<WebElement> firstThreeItems = categoryList.subList(0, 3);
        selectRandomCategory(firstThreeItems);
        System.out.println("The size for the three category list " + firstThreeItems.size());
    }

    private void selectRandomCategory(List<WebElement> categoryItemList)
    {
        WebElement selectRandomFromFirstThreeItems = selectRandomElement(categoryItemList);
        Actions actions = new Actions(customDriver);
        actions.moveToElement(selectRandomFromFirstThreeItems).perform();
        categoryName = selectRandomFromFirstThreeItems.getText();
        selectRandomFromFirstThreeItems.click();
        try {
            List<WebElement> list = selectRandomFromFirstThreeItems.findElements(By.xpath("./div/div/ul/li"));
            System.out.println("step 2");
            if (list.isEmpty()) {
                System.out.println("There are no categories");
            } else {
                WebElement categoryItemName = selectRandomElement(list);
                categoryName = categoryItemName.getText();
                categoryItemName.click();
                System.out.println("There is some categories");
            }
        } catch (StaleElementReferenceException e) {
            // Handle the exception and retry
            System.out.println("Stale element exception. Retrying...");
        }
    }

    @FindBy(xpath = "(//div)[@class='row'][3]/div/div/div[3]/button[1]")
    List<WebElement> listOfAddToCartButton;

    public void addItemTOCartDirectly(){
        websiteLogo.click();
        listOfAddToCartButton.get(0).click();
        listOfAddToCartButton.get(1).click();
    }

    public void addItemTOCartFromItemDetails(){
        websiteLogo.click();
        listOfProductsFromHome.get(2).findElement(By.xpath(".//button[1]")).click();
        try {
            addItemDetailsToCart();
        } catch (AWTException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addItemDetailsToCart() throws AWTException, InterruptedException {
        File uploadFile = new File(System.getProperty("user.dir") + "/sources/123.jpg");

        if (secondItemDetails.getText().contains("Canon EOS 5D")) {
            customDriver.navigate().back();
        } else if (secondItemDetails.getText().contains("Apple Cinema 30")) {
            WebElement firstRadio = selectRandomElement(listOfRadios);
            WebElement element = firstRadio.findElement(By.xpath(".//label/input"));
            System.out.println(element.getText() + "First Radio");
            element.click();
            WebElement secondElement = selectRandomElement(listOfCheckboxes).findElement(By.xpath(".//label/input"));
            System.out.println(secondElement.getText());
            secondElement.click();
            text.clear();
            text.sendKeys("Hello world");
            Select select = new Select(selectOption);
            select.selectByIndex(2);
            textArea.sendKeys("Hello world text area");
            uploadButton.click();
            Robot robot = new Robot();
            StringSelection selection = new StringSelection(uploadFile.getAbsolutePath());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2000);
            Alert alert = customDriver.switchTo().alert();
            alert.accept();
            calendarButton.click();
            for (WebElement date : allDates) {
                String selectedDate = date.getText();
                // once date is 28 then click and break
                if (selectedDate.equalsIgnoreCase("28")) {
                    element.click();
                    break;
                }

            }
            addItemToCartButton.click();
            System.out.println("last step");
            customDriver.navigate().back();
        }

    }

    @FindBy(id = "wishlist-total")
    WebElement wishListPage;

    By first = By.xpath("(//div)[@class='row'][3]/div[1]/div/div[3]/button[2]");
    By second = By.xpath("(//div)[@class='row'][3]/div[2]/div/div[3]/button[2]");

    public void addItemToWishListFromHome()
    {
        websiteLogo.click();
        customDriver.findElement(first).click();
        customDriver.findElement(second).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wishListPage.click();
    }


    @FindBy(xpath = "(//table)[@class='table table-bordered table-hover']/tbody/tr")
    List<WebElement> listOfWishList;

    public Boolean isWishListHasTwoItems()
    {
        return listOfWishList.size() == 2;
    }

    @FindBy(xpath = "(//a)[text()='product comparison']")
    WebElement comparisonProduct;

    By firstCompare = By.xpath("(//div)[@class='row'][3]/div[1]/div/div[3]/button[3]");
    By secondCompare = By.xpath("(//div)[@class='row'][3]/div[3]/div/div[3]/button[3]");

    public void addItemToCompareListFromHome() throws InterruptedException {
        websiteLogo.click();
        customDriver.findElement(firstCompare).click();
        customDriver.findElement(secondCompare).click();
        Thread.sleep(1000);
        comparisonProduct.click();
    }

    @FindBy(xpath = "(//table)[@class='table table-bordered']/tbody[1]/tr[2]/td/img")
    List<WebElement> listOfComparisonProducts;

    public Boolean checkThatItemsAreAddedToCompareList(int itemCount)
    {
        return listOfComparisonProducts.size() == itemCount;
    }

    @FindBy(xpath = "(//i)[@class='fa fa-shopping-cart'][1]")
    WebElement cartIcon;

    public void navigateToCart() {
        cartIcon.click();
    }


}
