package StepDefinitions.Jira;


import Pages.LoginPage;
import Utils.ConfigReader;
import Utils.Driver;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class LoginFunctionality {

    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage();

    @Given("The user enter username and password")
    public void the_user_enter_username_and_password() {
        driver.get(ConfigReader.getProperty("urlJira"));
        loginPage.username.sendKeys(ConfigReader.getProperty("userName"));
        loginPage.password.sendKeys(ConfigReader.getProperty("password"));
        loginPage.submitButton.click();
    }
}
