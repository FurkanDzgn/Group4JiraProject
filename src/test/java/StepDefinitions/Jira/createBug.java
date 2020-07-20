package StepDefinitions.Jira;


import Pojo.PojoJira.ResponseBodyJiraCreate;
import Utils.PayloadUtils;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.*;

public class createBug {

    @When("the user create a bug")
    public void the_user_create_a_bug() {

        File petFile = new File("bugJira.json");

        RestAssured.requestSpecification = new RequestSpecBuilder().setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON).addHeader("Cookie", PayloadUtils.getJiraCookies()).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(201).expectContentType(ContentType.JSON).build();
        Response response = given().spec(requestSpecification)
                .body(petFile)
                .when().post("http://localhost:8080/rest/api/2/issue")
                .then().assertThat().spec(responseSpecification)
                .extract().response();
        ResponseBodyJiraCreate responseBodyJiraCreate = response.as(ResponseBodyJiraCreate.class);
        System.out.println(responseBodyJiraCreate.getId());


    }

}
