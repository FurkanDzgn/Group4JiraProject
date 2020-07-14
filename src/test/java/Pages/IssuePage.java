package Pages;

import Utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssuePage {

    public IssuePage()
    {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id="key-val")
    public WebElement keyValue;

    @FindBy(xpath = "//div[@class='user-content-block']//p")
    public WebElement description;

    @FindBy(xpath="//a[@id='header-details-user-fullname']")
    public WebElement owl;

    @FindBy(xpath="//a[@id='log_out']")
    public WebElement logOutButton;
}

