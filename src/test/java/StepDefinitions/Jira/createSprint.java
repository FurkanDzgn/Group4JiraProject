package StepDefinitions.Jira;

import Pojo.PojoJira.ResponseBodyCreateSprint;
import Utils.PayloadUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class createSprint {

    @Given("create a post request")
    public void create_a_post_request() {

        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath = "rest/agile/1.0/sprint";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON).setContentType(ContentType.JSON)
                .addHeader("Cookie", PayloadUtils.getJiraCookies()).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(201).expectContentType(ContentType.JSON).build();

        File filePet = new File("createSprint.json");
        Response response = given().spec(requestSpecification)
                .body(filePet)
                .when().post()
                .then().spec(responseSpecification)
                .extract().response();

        ResponseBodyCreateSprint responseBodyCreateSprint = response.getBody().as(ResponseBodyCreateSprint.class);

        Assert.assertEquals("JiraProject",responseBodyCreateSprint.getName());






    }


}
