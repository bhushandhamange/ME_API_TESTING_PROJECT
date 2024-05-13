package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

public class testCase_API_04 {

    @Test(groups = "API Tests")
    public static void TestCase04(){
        
        String username = "bhushan.dhamange@gmail.com";
        String password = "Password@123";

        String jsonString = "{\"email\":\""+ username +"\",\"password\":\""+ password +"\",\"confirmpassword\":\""+ password +"\"}";

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";

        // System.out.println(jsonString);

        RequestSpecification httpRegister = RestAssured.given();
        httpRegister.contentType(ContentType.JSON);
        httpRegister.body(jsonString);

        Response registerResponse = httpRegister.request(Method.POST);

        int responseStatusCode = registerResponse.getStatusCode();

        Assert.assertEquals(responseStatusCode, 400, "Response Code matches for Registration API");
        registerResponse.then().body("success", equalTo(false));
        registerResponse.then().body("message", equalTo("Email already exists"));
    }
}

  

