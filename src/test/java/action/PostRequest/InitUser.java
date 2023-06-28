package action.PostRequest;

import action.JSONAction.*;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class InitUser {

//    public void Init_user() {
//        String path = "/api/users";
//        String url = "https://reqres.in" + path;
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("name","Nittin");
//        body.put("job","Test enginner");
//
//        String response = getJSONResponse(getJSONRequest(url).headers(defaultJSONrequestHeader()).body(body), Method.POST);
//        JsonPath jsonPath = new JsonPath(response);
//        jsonPath.prettyPrint();
//    }

    public void getUser(){
        String path = "/api/users";
        String url = "https://reqres.in" + path;

        Voice voice = new Voice();

        Map<String, Object> body = new HashMap<>();
        body.put("name","Nittin");
        body.put("job","Test enginner");

        String response = voice.getJSONResponse(voice.getJSONRequest(url).headers(voice.defaultJSONrequestHeader()).body(body), Method.POST);
        JsonPath jsonPath = new JsonPath(response);

        jsonPath.prettyPrint();

    }
}
