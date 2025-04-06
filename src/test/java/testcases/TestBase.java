package testcases;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.time.Duration;

import static drivers.DriverFactory.getNewInstance;
import static drivers.DriverHolder.getDriver;
import static drivers.DriverHolder.setDriver;

@Listeners(ChainTestListener.class)
public class TestBase
{
    static WebDriver driver;

    // Java class include before and after methods
    @BeforeTest
    public void setupWebDriver()
    {
        setDriver(getNewInstance(""));
        driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://awesomeqa.com/ui/");
    }


    @AfterTest
    public void tearDown()
    {
        driver.quit();
    }

}
