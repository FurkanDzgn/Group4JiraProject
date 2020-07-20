package Utils;

import Pojo.PojoJira.ResponseBodyAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class PayloadUtils { // requestBody == Payload

    public static String getPetPayload(int id, String name, String status){
        return "{\n" +
                "  \"id\": "+id+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \""+status+"\"\n" +
                "}";
    }

    public static String putMethod(String name,String job){
        return "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"job\": \""+job+"\"\n" +
                "}";
    }

    public static String generateStringFromResource(String path) throws IOException {

        String petPayload=new String(Files.readAllBytes(Paths.get(path)));
        return petPayload;
    }

    public static String cookieAuthPayload(){
        return "{\n" +
                "    \"username\":\"furkan.duzgun\",\n" +
                "    \"password\":\"VwMGkY2#cD6Xdct\"\n" +
                "}";
    }

    public static String getJiraIssuePayload(String summary,String description,String issuetype){
        return "{\n" +
                "    \"fields\":{\n" +
                "        \"project\":{\n" +
                "            \"key\":\"TEC\"\n" +
                "        },\n" +
                "        \"summary\":\""+summary+"\",\n" +
                "        \"description\":\""+description+"\",\n" +
                "        \"issuetype\":{\n" +
                "            \"name\":\""+issuetype+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public static String getJsessionCookie() throws URISyntaxException, IOException {
        HttpClient httpClient= HttpClientBuilder.create().build();
        URIBuilder uriBuilder=new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/auth/1/session");

        HttpPost httpPost=new HttpPost(uriBuilder.build());
        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("Content-Type","application/json");

        HttpEntity httpEntity=new StringEntity(PayloadUtils.cookieAuthPayload());
        httpPost.setEntity(httpEntity);

        HttpResponse httpResponse=httpClient.execute(httpPost);

        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        ObjectMapper objectMapper=new ObjectMapper();

        ResponseBodyAuth parsedObject =objectMapper.readValue(httpResponse.getEntity().getContent(),
                ResponseBodyAuth.class);

//        System.out.println(parsedObject.getSession().getName());
//        System.out.println(parsedObject.getSession().getValue());

        String cookieName=parsedObject.getSession().getName();
        String cookieValue=parsedObject.getSession().getValue();

        return String.format("%s=%s",cookieName,cookieValue);
    }

    public static String getJiraCookies(){
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

        String userName = responseBodyJira.getSession().getName();
        String value = responseBodyJira.getSession().getValue();

        String result = userName +"="+value;
        //     return String.format("%s=%s",userName,value);
        return result;
    }
}
