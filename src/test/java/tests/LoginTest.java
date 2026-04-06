package tests;
import org.testng.annotations.Test;
import pages.LoginBase;

public class LoginTest extends BaseTest {

    @Test(description = "Test successful login with valid credentials", priority = 1)
    public void testSuccessfulLogin() {
        LoginBase loginPage = new LoginBase(driver);
        loginPage.loginWithValidCredentials();
        loginPage.assertSuccessfulLogin();
    }

    @Test(description = "Test login with invalid credentials and verify error message", priority = 2)
    public void testInvalidCredentials() {
        LoginBase loginPage = new LoginBase(driver);
        loginPage.loginWithInvalidCredentials();
        loginPage.assertInvalidCredentialsError();
    }

    @Test(description = "Test login with empty fields and verify error messages", priority = 3)
    public void testEmptyFields() {
        LoginBase loginPage = new LoginBase(driver);
        // Test empty username
        loginPage.loginWithEmptyUsername();
        loginPage.assertEmptyUsernameError();
        // Reload page for next test
        driver.navigate().refresh();
        // Test empty password
        loginPage.loginWithEmptyPassword();
        loginPage.assertEmptyPasswordError();
    }
}