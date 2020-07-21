package StepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static Utils.PayloadUtils.*;
import static io.restassured.RestAssured.*;

public class JiraBoardAndSprintSteps {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    private static final String NAME="name";
    private static final String TYPE="type";
    private static final String STARTDATE="startDate";
    private static final String ENDDATE="endDate";
    private static final String BOARDID="originBoardId";
    private static final String ID="id";
    private static Integer boardId;
    public static Map<String,Object> desResponse;
    Response response;

    public static List<Integer> ids=new ArrayList<>();
    public static List<String> issueIds=new ArrayList<>();
    public static List<String> issueKeys = new ArrayList<>();
    public static List<String> sprintIds=new ArrayList<>();


    @When("the user goes to JiraBoard localhost")
    public void the_user_goes_to_JiraBoard_localhost() {
        RestAssured.baseURI="http://localhost:8080";
        RestAssured.basePath="rest/agile/1.0/board";
    }

    @Then("the user user sets prerequisite for the request and response")
    public void the_user_user_sets_prerequisite_for_the_request_and_response() throws IOException, URISyntaxException {
        requestSpecification=new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addHeader("Cookie",getJsessionCookie()).build();
        responseSpecification=new ResponseSpecBuilder().log(LogDetail.BODY)
                .expectStatusCode(201).expectContentType(ContentType.JSON).build();
    }


    @And("the user creates JiraBoard with {string}, {string} and {int}")
    public void the_user_creates_JiraBoard_with_and(String boardName, String boardType, int filterId) throws IOException, URISyntaxException {

        response = given().spec(requestSpecification).body(getJiraBoardPayload(boardName,boardType,filterId))
                .when().post().then().spec(responseSpecification).extract().response();
        desResponse=response.as(Map.class);
        boardId = (Integer)desResponse.get(ID);
        ids.add((Integer)desResponse.get(ID));
    }

    @Then("the user validates the JiraBoard's {string}, {string}")
    public void the_User_validates_the_JiraBoard_s(String boardName, String boardType) {
        response.then().body(NAME,Matchers.is(boardName))
                .body(TYPE,Matchers.equalTo(boardType));
    }


    @When("the user goes to JiraSprint localhost")
    public void the_user_goes_to_JiraSprint_localhost() {
        RestAssured.baseURI="http://localhost:8080";
        RestAssured.basePath="rest/agile/1.0/sprint";
    }

    @And("the user creates Sprint with {string}, {string}, {string}, boardId")
    public void the_user_creates_Sprint_with_boardId(String sprintName, String startDate, String endDate) {

        response = given().spec(requestSpecification).body(getJiraSprintPayload(sprintName,startDate,endDate,boardId))
                .when().post().then().spec(responseSpecification).extract().response();
        desResponse=response.as(Map.class);
        sprintIds.add(desResponse.get(ID).toString());
        ids.add((Integer) desResponse.get(ID));

    }

    @Then("the user validates the Sprint's {string}, {string}, {string}, boardId")
    public void the_user_validates_the_Sprint_s_boardId(String sprintName, String startDate, String endDate) {

        response.then().body(NAME,Matchers.equalTo(sprintName)).body(STARTDATE,Matchers.startsWith(startDate))
                .body(ENDDATE,Matchers.startsWith(endDate)).body(BOARDID,Matchers.equalTo(boardId));
    }

    @When("the user goes to JiraIssue localhost")
    public void the_user_goes_to_JiraIssue_localhost() {
        RestAssured.baseURI = "http://localhost:8080";//http://localhost:8080/rest/api/2/issue
        RestAssured.basePath="rest/api/2/issue";
    }

    @Then("the user creates issue with {string}, {string} and {string}")
    public void the_user_creates_issue_with_and(String summary, String description, String issueType) {
        response = given().spec(requestSpecification).body(getJiraIssuePayload(summary,description,issueType))
                .when().post().then().spec(responseSpecification).extract().response();
        desResponse=response.as(Map.class);
        issueIds.add(desResponse.get(ID).toString());
        issueKeys.add(desResponse.get("key").toString());
    }

    @Then("the user executes Get request")
    public void the_user_executes_Get_request() throws IOException, URISyntaxException {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath="rest/api/2/issue/"+issueIds.get(issueIds.size()-1);
        response = given().header("accept", ContentType.JSON).header("Cookie",getJsessionCookie())
                .when().get().then().statusCode(200).and().contentType(ContentType.JSON).extract().response();
    }

    @Then("the user validates the issue's {string}, {string} and {string}")
    public void the_user_validates_the_issue_s_and(String summary, String description, String issueType) {
        response.then().assertThat().body("fields.summary",Matchers.is(summary));
        response.then().assertThat().body("fields.description",Matchers.is(description));
        response.then().assertThat().body("fields.issuetype.name",Matchers.is(issueType));
    }


    @When("the user moves Issue to Sprint")
    public void the_user_moves_Issue_to_Sprint() throws IOException, URISyntaxException {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath="rest/agile/1.0/sprint/"+sprintIds.get(0)+"/issue";
        for(int i=0;i<issueIds.size();i++){
            if(i<=10) {
                given().header("accept",ContentType.JSON).header("content-type",ContentType.JSON).header("cookie",getJsessionCookie())
                        .body(getJiraMoveIssuePayload(issueKeys.get(0),issueIds.get(i),issueKeys.get(10)))
                        .when().post().then().statusCode(204).contentType(ContentType.JSON);

            }else if(i>11 && i<=20){
                RestAssured.baseURI = "http://localhost:8080";
                RestAssured.basePath="rest/agile/1.0/sprint/"+sprintIds.get(1)+"/issue";
                given().header("accept",ContentType.JSON).header("content-type",ContentType.JSON).header("cookie",getJsessionCookie())
                        .body(getJiraMoveIssuePayload(issueKeys.get(11),issueIds.get(i),issueKeys.get(20)))
                        .when().post().then().statusCode(204).contentType(ContentType.JSON);
            }else {
                RestAssured.baseURI = "http://localhost:8080";
                RestAssured.basePath="rest/agile/1.0/sprint/"+sprintIds.get(2)+"/issue";
                given().header("accept",ContentType.JSON).header("content-type",ContentType.JSON).header("cookie",getJsessionCookie())
                        .body(getJiraMoveIssuePayload(issueKeys.get(21),issueIds.get(i),issueKeys.get(30)))
                        .when().post().then().statusCode(204).contentType(ContentType.JSON);
            }
        }
    }


    @Then("the user validates Issue is moved to Sprint")
    public void the_user_validates_Issue_is_moved_to_Sprint() {

    }


}
