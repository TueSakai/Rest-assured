package action.JSONAction;

import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public abstract class Sound {
    public abstract RequestSpecification getJSONRequest(String url);

    public abstract String getJSONResponse(RequestSpecification httpRequest, Method method);

    public abstract Map<String, Object> defaultJSONrequestHeader();
}
