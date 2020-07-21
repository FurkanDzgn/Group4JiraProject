package Utils;

import Pojo.PojoJira.ResponseBodyAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                "    \"username\":\""+ConfigReader.getProperty("username")+"\",\n" +
                "    \"password\":\""+ConfigReader.getProperty("password")+"\"\n" +
                "}";
    }

    public static String getJiraIssuePayload(String summary,String description,String issueType) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"TES\"\n" +
                "        },\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description + "\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"" + issueType + "\"\n" +
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

        String cookieName=parsedObject.getSession().get("name");
        String cookieValue=parsedObject.getSession().get("value");

        return String.format("%s=%s",cookieName,cookieValue);
    }

    public static String getJiraBoardPayload(String boardName, String boardType, int filterId){
        return "{\n" +
                "    \"name\": \""+boardName+"\",\n" +
                "    \"type\": \""+boardType+"\",\n" +
                "    \"filterId\": "+filterId+"\n" +
                "}";
    }

    public static String getJiraSprintPayload(String sprintName, String startDate, String endDate, int boardId){
        return "{\n" +
                "    \"name\": \""+sprintName+"\",\n" +
                "    \"startDate\": \""+startDate+"\",\n" +
                "    \"endDate\": \""+endDate+"\",\n" +
                "    \"originBoardId\": "+boardId+"\n" +
                "}";
    }


    public static  String getJiraMoveIssuePayload(String key1,String id,String key2){
        return "{\n" +
                "    \"issues\": [\n" +
                "        \""+key1+"\",\n" +
                "        \""+id+"\",\n" +
                "        \""+key2+"\"\n" +
                "    ]\n" +
                "}";
    }

}
