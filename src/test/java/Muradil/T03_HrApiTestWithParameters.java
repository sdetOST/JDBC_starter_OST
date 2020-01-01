package Muradil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class T03_HrApiTestWithParameters {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("hr.base_uri");
        RestAssured.port =Integer.parseInt(ConfigurationReader.getProperty("hr.port"));
        RestAssured.basePath = ConfigurationReader.getProperty("hr.base_path");

    }

//    Given accept type is Json
//    And parameters limit=1
//    When user sends GET request to "/regions"
//    Then response status code should be 200
//    And response content-type: application/json
//    And Payload should contain "Europe"
    @Test
    public void GetRegion_Positive_Query_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                            and().queryParam("limit", "1").
                            when().get("/regions");     // http://54.90.84.187:1000/ords/hr/regions?limit=1

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }
//    Given accept type is Json
//    And parameter: q= {"region_id": 1}
//    When user sends GET request to "/regions"
//    Then response status code should be 200
//    And response content-type: application/json
//    And Payload should contain "Europe"
    @Test
    public void GetRegionById_Positive_Query_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                and().queryParam("q", "{\"region_id\": 1}").
                when().get("/regions");     // http://54.90.84.187:1000/ords/hr/regions?limit=1

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }

//    Given accept type is Json
//    And parameter: q= {"region_name": "Americas"}
//    When user sends GET request to "/regions"
//    Then response status code should be 200
//    And response content-type: application/json
//    And Payload should contain "Americas"
    @Test
    public void GetRegionByName_Positive_Query_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                and().queryParam("q", "{\"region_name\": \"Americas\"}").
                when().get("/regions");     // http://54.90.84.187:1000/ords/hr/regions?limit=1

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Americas"));

    }

}
