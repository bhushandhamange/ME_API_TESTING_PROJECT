package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;


public class testCase_API_02 {

    @Test(groups = "API Tests")
    public static void TestCase02(){

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/cities";

        RequestSpecification http = RestAssured.given();
        http.contentType(ContentType.JSON);
        http.queryParam("q", "beng");

        Response response = http.request(Method.GET);

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, 200, "Status Code is as expected");

        response.then().body("size()", equalTo(1));

        response.then().body("[0].description", equalTo("100+ Places"));

        response.then().assertThat().body(JsonSchemaValidator.
                matchesJsonSchema(new File("C:\\Gradle\\GradleWorkspace\\bhushan-dhamange-ME_API_TESTING_PROJECT\\app\\src\\test\\resources\\schema.json")));
                
    }

}
