package qtriptest.pages;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtriptest.SeleniumWrapper;

public class LoginPage {
    WebDriver driver;

    @FindBy(id="floatingInput")
    WebElement emailTextBox;

    @FindBy(id="floatingPassword")
    WebElement passwordTextBox;
    
    @FindBy(xpath = "//button[text()='Login to QTrip']")
    WebElement loginButton;

    public LoginPage(WebDriver driver2)
    {
        this.driver = driver2;    
    //  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
        // PageFactory.initElements(this.driver, this);
    }

    public void performLogin(String username , String password)
    {
        SeleniumWrapper.sendKeys(emailTextBox, username);
        SeleniumWrapper.sendKeys(passwordTextBox, password);
        try {
            SeleniumWrapper.click(loginButton, this.driver);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
