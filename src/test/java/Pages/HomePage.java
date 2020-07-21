package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='login-form-username']")
    public WebElement username;

    @FindBy(xpath = "//input[@id='login-form-password']")
    public WebElement password;

    @FindBy(id = "login-form-submit")
    public WebElement loginButton;

    @FindBy(xpath = "//div[@data-id='sidebar-navigation-panel']//a")
    public List<WebElement> sidebarButtons;

    @FindBy(xpath = "//div[@class='js-issue js-sortable js-parent-drag ghx-issue-compact ghx-type-10004']")
    public List<WebElement> listOfBugs;

    @FindBy(xpath = "//span[@class='issue-link-key']")
    public List<WebElement> listOfIssues;
}
