package action.JSONAction;

import com.google.gson.JsonObject;
import org.json.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class BasePage{
    public BasePage() {
    }
    public String getJSONResponse(RequestSpecification httpRequest, Method method){
        Response response = null;
        switch (method){
            case PUT:
                response = httpRequest.request(Method.PUT);
                break;
            case POST:
                response = httpRequest.request(Method.POST);
                break;
            case GET:
                response = httpRequest.request(Method.GET);
                break;
            case DELETE:
                response = httpRequest.request(Method.DELETE);
                break;
            case PATCH:
                response = httpRequest.request(Method.PATCH);
                break;
            case HEAD:
                response = httpRequest.request(Method.HEAD);
                break;
            case OPTIONS:
                response = httpRequest.request(Method.OPTIONS);
                break;
            case TRACE:
                response = httpRequest.request(Method.TRACE);
                break;
        }
        Assert.assertNotNull(response);
        return response.getBody().asString();
    }

    public Map<String, Object> defaultJSONrequestHeader(){
        Map<String,Object> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("Accpet","application/json");
        return header;
    }
    public RequestSpecification getJSONRequest(String url){
        return RestAssured
                .given()
                .request()
                .baseUri(url)
                ;
    }
}

