package Pages;

import Utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BacklogPage {

    public BacklogPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//div[@class='ghx-backlog-container ghx-open ghx-everything-else ui-droppable']" +
            "//div[@class='ghx-issues js-issue-list ghx-has-issues']" +
            "//div[contains(@class,'js-issue js-sortable js-parent-drag ghx-issue-compact ghx-type')]" +
            "//div//div//span[@class='ghx-inner']")
    public List<WebElement> stories;

    @FindBy(xpath = "//a[text()='View Workflow']")
    public WebElement viewWorkflow;

}

