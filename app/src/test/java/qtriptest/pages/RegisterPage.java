package qtriptest.pages;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtriptest.SeleniumWrapper;

public class RegisterPage {
    WebDriver driver;
    public String lastGeneratedUsername = "";

    @FindBy(id = "floatingInput")
    WebElement emailTextBox;

    @FindBy(id = "floatingPassword")
    WebElement passwordTextBox;

    @FindBy(xpath = "//input[@placeholder='Retype Password to Confirm']")
    WebElement confirmPasswordTextBox;

    @FindBy(xpath = "//button[text()='Register Now']")
    WebElement registerNowButton;

    public RegisterPage(WebDriver driver2) {
        this.driver = driver2;    
        // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
        // PageFactory.initElements(this.driver, this);
    }

    public void registerNewUser(String userName, String password, String confirmPassword, Boolean makeDynamic) {
        if (makeDynamic) {
            userName = userName + UUID.randomUUID().toString();
            this.lastGeneratedUsername = userName;
        }
        SeleniumWrapper.sendKeys(emailTextBox, userName);
        SeleniumWrapper.sendKeys(passwordTextBox, password);
        SeleniumWrapper.sendKeys(confirmPasswordTextBox, confirmPassword);
        try {
            SeleniumWrapper.click(registerNowButton, this.driver);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
