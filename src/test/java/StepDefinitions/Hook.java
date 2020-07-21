package StepDefinitions;


import Utils.BrowserUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Hook {


    @Before
    public void setUp(Scenario scenario){
        System.out.println("This one will run before each scenario");
        System.out.println(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario){
        System.out.println("This one will run after each scenario");
        if(scenario.isFailed()){
            System.out.println("is Failed");
            //BrowserUtils.takeScreenShot();
        }
    }
}
