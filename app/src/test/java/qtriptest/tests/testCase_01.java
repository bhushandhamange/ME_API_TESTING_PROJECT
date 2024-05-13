package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

public class testCase_01 {
    static WebDriver driver;

    @BeforeSuite(groups = {"Login Flow"})
    public static void setupDriver()  throws MalformedURLException {
        DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
		driver = sbc1.getDriver();
    }

    @Test(description = "Verify user registration - login - logout", dataProvider = "data-provider" , dataProviderClass = DP.class, groups = {"Login Flow"})
    public static void TestCase01(String UserName, String Password) throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.gotoHomePage();
        Thread.sleep(2000);
        home.clickRegister();
        RegisterPage register = new RegisterPage(driver);
        register.registerNewUser(UserName, Password, Password, true);
        Thread.sleep(3000);//TODO:UNWANTED ALERT REMOVE
        //driver.switchTo().alert().dismiss();//TODO:UNWANTED ALERT REMOVE
        String username = register.lastGeneratedUsername;
        LoginPage Login = new LoginPage(driver);
        Login.performLogin(username, Password);
        Thread.sleep(3000);//TODO:UNWANTED ALERT REMOVE
        //driver.switchTo().alert().dismiss();//TODO:UNWANTED ALERT REMOVE
        Assert.assertTrue(home.isUserLoggedIn());
        home.logOutUser();
        Thread.sleep(3000);
        Assert.assertFalse(home.isUserLoggedIn());
        home.gotoHomePage();
    }

    @AfterSuite(groups = {"Login Flow"})
    public static void quitDriver() throws InterruptedException
    {
        driver.quit();
    }

}
