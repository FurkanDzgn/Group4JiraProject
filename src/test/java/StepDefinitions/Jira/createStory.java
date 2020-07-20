package StepDefinitions.Jira;


import Pojo.PojoJira.ResponseBodyAuth;
import Pojo.PojoJira.ResponseBodyJiraCreate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.*;

public class createStory {

    public static String userName;
    public static String value;
    public static String name;

    ResponseBodyJiraCreate responseBodyJira;

    @Given("the user create a cookies")
    public void the_user_create_a_cookies() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath = "rest/auth/1/session";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON).setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON).build();

        File filePet = new File("jira.json");
        Response response = given().spec(requestSpecification)
                .body(filePet)
                .when().post()
                .then().spec(responseSpecification)
                .extract().response();

        ResponseBodyAuth responseBodyJira = response.as(ResponseBodyAuth.class);

        userName = responseBodyJira.getSession().getName();
        value = responseBodyJira.getSession().getValue();

    }

    @When("the user create a story")
    public void the_user_create_a_story() {

        File filePet2 = new File("jiraIssue.json");

        RestAssured.requestSpecification = new RequestSpecBuilder().setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON).addHeader("Cookie",userName+"="+value).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(201)
                .expectContentType(ContentType.JSON).build();

        Response response = given().spec(requestSpecification)
                .body(filePet2)
                .when().post("http://localhost:8080/rest/api/2/issue")
                .then().spec(responseSpecification)
                .extract().response();
        responseBodyJira = response.as(ResponseBodyJiraCreate.class);
        name = responseBodyJira.getKey();


    }

    @Then("the user validate status code")
    public void the_user_validate_status_code() {

        System.out.println(responseBodyJira.getId());

    }

}
