package qtriptest.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtriptest.SeleniumWrapper;

public class AdventureDetailsPage {

    WebDriver driver;
    
    @FindBy(xpath = "//input[@name='name']")
    WebElement nameTextBox;

    @FindBy(xpath = "//input[@name='date']")
    WebElement dateTextBox;

    @FindBy(xpath = "//input[@name='person']")
    WebElement personTextBox;

    @FindBy(xpath = "//button[text()='Reserve']")
    WebElement reserveButton;

    public AdventureDetailsPage(WebDriver driver2)
    {
        this.driver = driver2;    
    //  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
        // PageFactory.initElements(this.driver, this);
    }

    
    public void bookAdventure(String name , String Date , int count) throws InterruptedException
    {
        SeleniumWrapper.sendKeys(nameTextBox, name);
        SeleniumWrapper.sendKeys(dateTextBox,Date);
        SeleniumWrapper.sendKeys(personTextBox, String.valueOf(count));
        Thread.sleep(2000);
        SeleniumWrapper.click(reserveButton, this.driver);
    }
    
    public boolean isBookingSuccessful()
    {
        //TODO: Return true if the booking was successful
        return true;
    }
}
