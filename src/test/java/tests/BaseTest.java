package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import constants.TestConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;
import java.net.URL;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentTest extentTest;
    private static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        // Choose browser based on constant
        if (TestConstants.BROWSER.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (TestConstants.BROWSER.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();

        // Load HTML file from resources folder
        URL resource = BaseTest.class.getClassLoader().getResource("login.html");
        if (resource != null) {
            driver.get(resource.toExternalForm());
        } else {
            throw new RuntimeException("login.html not found in resources folder");
        }

        extentTest = extent.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip(result.getThrowable());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
}