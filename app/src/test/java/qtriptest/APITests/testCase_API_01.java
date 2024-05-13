package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.Random;
import java.util.UUID;
import static org.hamcrest.Matchers.*;


public class testCase_API_01 {

    // Function to generate a random string of characters
    public static String generateRandomString(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        // Generate random string
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            randomString.append(allowedChars.charAt(index));
        }

        return randomString.toString();
    }
    
    @Test(groups = "API Tests")
    public static void TestCase01(){
        

        String username = generateRandomString(10) + "@gmail.com";
        String password = generateRandomString(5) + "@123";

        String jsonString = "{\"email\":\""+ username +"\",\"password\":\""+ password +"\",\"confirmpassword\":\""+ password +"\"}";

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";

        // System.out.println(jsonString);

        RequestSpecification httpRegister = RestAssured.given();
        httpRegister.contentType(ContentType.JSON);
        httpRegister.body(jsonString);

        Response registerResponse = httpRegister.request(Method.POST);

        int responseStatusCode = registerResponse.getStatusCode();

        Assert.assertEquals(responseStatusCode, 201, "Response Code matches for Registration API");

        //For Login API

        jsonString = "{\"email\":\""+ username +"\",\"password\":\""+ password +"\"}";

        RestAssured.basePath = "/api/v1/login";

        // System.out.println(jsonString);

        RequestSpecification httpLogin = RestAssured.given();
        httpLogin.contentType(ContentType.JSON);
        httpLogin.body(jsonString);

        Response loginResponse = httpLogin.request(Method.POST);

        // System.out.println("loginResponse status code : " + loginResponse.statusCode());
        int loginResponseStatusCode = loginResponse.statusCode();

        Assert.assertEquals(loginResponseStatusCode, 201, "Response Code matches for Login API");

        loginResponse.then().body("success", equalTo(true));

    }
   
}
