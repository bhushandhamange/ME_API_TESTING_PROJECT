package qtriptest;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;



public class DriverSingleton {
    
    // instance of singleton class
    private static DriverSingleton instanceOfSingletonBrowserClass = null;

    private WebDriver driver;

    // Constructor
    private DriverSingleton() throws MalformedURLException {
        // final DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setBrowserName(BrowserType.CHROME);
		// driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
		options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        
    }

    // TO create instance of class
    public static DriverSingleton getInstanceOfSingletonBrowserClass() throws MalformedURLException {
        if (instanceOfSingletonBrowserClass == null) {
            instanceOfSingletonBrowserClass = new DriverSingleton();
        }
        return instanceOfSingletonBrowserClass;
    }

    // To get driver
    public WebDriver getDriver() {
        return driver;
    }
}
