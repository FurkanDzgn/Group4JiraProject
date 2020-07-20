package StepDefinitions.Jira;


import Pages.BacklogPage;
import Pages.IssuePage;
import Pages.LoginPage;
import Utils.ConfigReader;
import Utils.Driver;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class VerifyUi {

    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage();
    BacklogPage backlogPage = new BacklogPage();
    IssuePage issuePage = new IssuePage();

    @Then("the user validate last created story")
    public void the_user_validate_last_created_story() throws InterruptedException {

        driver.get(ConfigReader.getProperty("urlJira"));
        loginPage.username.sendKeys(ConfigReader.getProperty("userName"));
        loginPage.password.sendKeys(ConfigReader.getProperty("password"));
        loginPage.submitButton.click();
        Thread.sleep(1000);

        int length=backlogPage.stories.size()-1;

        Actions actions=new Actions(driver);
        actions.moveToElement(backlogPage.stories.get(length)).click().perform();
        Assert.assertEquals(issuePage.nameOfCreatedStory.getText(),createStory.name);

    }
}
