package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import constants.TestConstants;

import java.time.Duration;

public class LoginBase {
    WebDriver driver;

    // Locators
    private By usernameField = By.id("username-input");
    private By passwordField = By.id("password-input");
    private By loginButton = By.xpath("//button[.//span[@id='sign-in-label']]");
    private By usernameerrorMessage = By.id("username-error");
    private By passworderrorMessage = By.id("password-error");
    private By invalidCredentialsError = By.id("error-message");
    private By successMessage = By.id("success-message");
    private final WebDriverWait wait;


    public LoginBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public String getInvalidCredentialsErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(invalidCredentialsError));
        return driver.findElement(invalidCredentialsError).getText();
    }

    public String getUsernameErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameerrorMessage));
        return driver.findElement(usernameerrorMessage).getText();
    }

    public String getPasswordErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passworderrorMessage));
        return driver.findElement(passworderrorMessage).getText();
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).getText();
    }

    public void assertSuccessfulLogin() {
        String successMessageText = getSuccessMessage();
        String username = TestConstants.VALID_USERNAME;
        boolean result = successMessageText.contains("Welcome, " + username);
        Assert.assertTrue(result, "you have been authenticated.");
        System.out.println("Success message for valid credentials: " + successMessageText);
    }

    public void assertInvalidCredentialsError() {
        String errorMessage = getInvalidCredentialsErrorMessage();
        Assert.assertEquals(errorMessage, TestConstants.Error_invalidCredentials,
            "Invalid credentials error message does not match expected: " + TestConstants.Error_invalidCredentials);
        System.out.println("Error message for invalid credentials: " + errorMessage);
    }

    public void assertEmptyUsernameError() {
        String errorMessage = getUsernameErrorMessage();
        Assert.assertEquals(errorMessage, TestConstants.ERROR_EMPTY_USERNAME,
            "Error message should match expected message for empty username");
        System.out.println("Error message for empty username: " + errorMessage);
    }

    public void assertEmptyPasswordError() {
        String errorMessage = getPasswordErrorMessage();
        Assert.assertEquals(errorMessage, TestConstants.ERROR_WRONG_PASSWORD,
            "Error message should match expected message for empty password");
        System.out.println("Error message for empty password: " + errorMessage);
    }

    // Combined login action
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Login with valid credentials from TestConstants
    public void loginWithValidCredentials() {
        login(TestConstants.VALID_USERNAME, TestConstants.VALID_PASSWORD);
    }

    // Login with invalid credentials from TestConstants
    public void loginWithInvalidCredentials() {
        login(TestConstants.INVALID_USERNAME, TestConstants.INVALID_PASSWORD);
    }

    // Login with empty username from TestConstants
    public void loginWithEmptyUsername() {
        login(TestConstants.EMPTY_STRING, TestConstants.VALID_PASSWORD);
    }

    // Login with empty password from TestConstants
    public void loginWithEmptyPassword() {
        login(TestConstants.VALID_USERNAME, TestConstants.EMPTY_STRING);
    }
}