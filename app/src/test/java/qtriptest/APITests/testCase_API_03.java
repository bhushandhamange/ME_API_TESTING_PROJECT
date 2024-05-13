package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

public class testCase_API_03 {

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
    public static void TestCase03(){
        

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

        String token = loginResponse.then().extract().path("data.token");
        String userId = loginResponse.then().extract().path("data.id");

        //Reservation new

        RestAssured.basePath = "/api/v1/reservations/new";

        jsonString = "{\"userId\":\""+ userId +"\",\"name\":\"testuser\",\"date\":\"2024-09-09\",\"person\":\"1\",\"adventure\":\"2447910730\"}";

        RequestSpecification httpReserve = RestAssured.given();
        httpReserve.contentType(ContentType.JSON);
        httpReserve.body(jsonString);
        httpReserve.header("Authorization", "Bearer " + token );

        Response response_Reservation = httpReserve.request(Method.POST);

        int reserve_Status_Code = response_Reservation.getStatusCode();

        // System.out.println("reserve_Status_Code : "+ reserve_Status_Code);

        Assert.assertEquals(reserve_Status_Code, 200, "Response Code matches for Reservation API");

        //Get Reservation Details

        RestAssured.basePath = "/api/v1/reservations";

        RequestSpecification httpGetReservationDetails = RestAssured.given();

        httpGetReservationDetails.contentType(ContentType.JSON);
        httpGetReservationDetails.queryParam("id", userId);
        httpGetReservationDetails.header("Authorization", "Bearer " + token );

        Response response_ReservationDetails = httpGetReservationDetails.request(Method.GET);

        response_ReservationDetails.then().body("size()", equalTo(1));

        response_ReservationDetails.then().body("[0].isCancelled", equalTo(false));

    }

}
