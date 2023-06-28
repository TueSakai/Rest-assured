package StepDefinitions;


import action.JSONAction.BasePage;
import action.PostRequest.InitUser;
import com.google.gson.JsonObject;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;


import org.w3c.dom.Element;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Steps {
    InitUser initUser = new InitUser();
    @Given("GET demo request 1")
    public void GET_request_1() {
        RestAssured.baseURI = "https://reqres.in/api/user?page=2";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();

    }
    @Test
    @Given("GET demo request 2")
    public void GET_request_2() {
//        String url = "https://reqres.in";
//        String path = "/api/user/2";
//        String response = getJSONResponse(getJSONRequest(url, path),"GET");
//        JsonPath jsonPath = new JsonPath(response);
//        jsonPath.prettyPrint();
        RestAssured.baseURI = "https://reqres.in/api/user";
        RequestSpecification httpRequest = RestAssured.given().param("page","2");
        Response response = httpRequest.request(Method.GET);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();
    }
    @Test
    @When("POST demo request 1")
    public void POST_request_1() {
//        initUser.Init_user();
        initUser.getUser();
    }

    @Test
    @When("POST demo request 2")
    public void POST_request_2() throws Exception{
        RestAssured.baseURI = "https://reqres.in/api/login";

        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/java/StepDefinitions/data.json")));
        RequestSpecification httpRequest = RestAssured.given();

//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("email","eve.holt@reqres.in");
//        requestBody.addProperty("password","cityslicka");

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestBody);

        Response response = httpRequest.request(Method.POST);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        jsonPath.prettyPrint();
    }
    @Test
    @Then("POST demo request 3")
    public void POST_request_3() {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email","Nitin@gmail.com");

        Response response = RestAssured
                .given()
                    .baseUri("https://reqres.in").basePath("/api/login")
                    .contentType(ContentType.JSON)
                .when()
                    .body(requestBody)
                    .post()
                .then()
                    .statusCode(400)
                    .extract().response()
                ;

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        try {
            Assert.assertEquals(jsonPath.get("error"),"Missing password");
            System.out.println("Pass");
        }
        catch (Exception e){
            System.out.println("False");
        }

        jsonPath.prettyPrint();
    }
    @Test
    @Then("Test SOAP API POST")
    public void POST_RESTAssuredSOAPAPI() throws Exception{
        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/java/StepDefinitions/addition-requestbody.xml")));
        System.out.println();


        RequestSpecification request =  RestAssured
                .given().request().header("SOAPAction","authenticate")
                    .baseUri("http://restapi.adequateshop.com")
                    .basePath("/api/Traveler")
                    .contentType("application/xml")
                    .accept(ContentType.XML)
                    .and()
                    .body(requestBody);
        Response response = request.request(Method.POST);
        XmlPath jsXpath = new XmlPath(response.getBody().asString());
        System.out.println("\n=========================================\n");
        jsXpath.prettyPrint();

    }

    @Test
    @Then("Test SOAP API GET")
    public void GET_RESTAssuredSOAPAPI() throws Exception{
        RequestSpecification request = RestAssured
                .given()
                .baseUri("http://restapi.adequateshop.com")
                .basePath("/api/Traveler/11133")
                .header("Content-Type", "application/xml");

        Response response = request.request(Method.GET);
        XmlPath jsXpath = new XmlPath(response.getBody().asString());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(jsXpath.prettyPrint())));
        Element rootElement = document.getDocumentElement();
        System.out.println("\nName is: " + rootElement.getElementsByTagName("name").item(0).getTextContent());
        System.out.println("\nID is: " + rootElement.getElementsByTagName("id").item(0).getTextContent());
        System.out.println("\nEmail is: " + rootElement.getElementsByTagName("email").item(0).getTextContent());
        System.out.println("\nAdderes is: " + rootElement.getElementsByTagName("adderes").item(0).getTextContent());
    }

    public static void main(String[] args) {
    }

}
