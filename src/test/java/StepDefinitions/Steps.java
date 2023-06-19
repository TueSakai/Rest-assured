package StepDefinitions;


import groovy.xml.XmlParser;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.internal.util.IOUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Element;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;
import javax.xml.*;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static io.restassured.config.XmlConfig.xmlConfig;

public class Steps {
    @Given("GET demo request 1")
    public void GET_request_1() {
        RestAssured.baseURI = "https://reqres.in/api/user?page=2";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();

    }
    @Given("GET demo request 2")
    public void GET_request_2() {
        RestAssured.baseURI = "https://reqres.in/api/user/2";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();
    }
    @When("POST demo request 1")
    public void POST_request_1() {
        RestAssured.baseURI = "https://reqres.in/api/users";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("name","Nittin");
        requestBody.put("job","Test enginner");

        httpRequest.body(requestBody.toJSONString());

        Response response = httpRequest.request(Method.POST);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();
    }
    @When("POST demo request 2")
    public void POST_request_2() {
        RestAssured.baseURI = "https://reqres.in/api/login";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("email","eve.holt@reqres.in");
        requestBody.put("password","cityslicka");

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestBody.toJSONString());

        Response response = httpRequest.request(Method.POST);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();
    }
    @Then("POST demo request 3")
    public void POST_request_3() {
        RestAssured.baseURI = "https://reqres.in/api/login";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("email","Nitin@gmail.com");

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestBody.toJSONString());

        Response response = httpRequest.request(Method.POST);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.prettyPrint();
    }
    @Test
    @Then("Test SOAP API POST")
    public void POST_RESTAssuredSOAPAPI() throws Exception{
        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/java/StepDefinitions/addition-requestbody.xml")));
        System.out.println();
        Response response =  RestAssured
                .given().request().header("SOAPAction","authenticate")
                .baseUri("http://restapi.adequateshop.com")
                .basePath("/api/Traveler")
                .contentType("application/xml")
                .accept(ContentType.XML)
                .and()
                .body(requestBody)
                .when().post();
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
}
