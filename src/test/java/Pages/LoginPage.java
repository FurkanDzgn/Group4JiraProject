package Pages;

import Utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "login-form-username")
    public WebElement username;

    @FindBy(id = "login-form-password")
    public WebElement password;

    @FindBy(id = "login-form-submit")
    public WebElement submitButton;


}
