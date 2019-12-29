package Muradil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;



public class T02_SpartanTestWithParameters {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://54.90.84.187";        // http://3.89.115.0
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        // above will generate a BASE REQUEST URL OF http://54.90.84.187:8000/api
    }

//    Given no headers are provided
//    When User send request to /hello
//    Then Reponse status code should be 200
//    And Header Content Type should be "text/plain;charset=UTF-8"
//    And Header should contains Date
//    And Header Content-Length should be 17
//    And Body should be "Hello from Sparta"

    @Test
    public void helloTest() {
// Request
        Response response = get("/hello");

// Response
        assertEquals(200, response.statusCode());
        // below codes are doing same exact thing
        assertEquals("text/plain;charset=UTF-8", response.contentType());
        assertEquals("17", response.getHeader("Content-Length"));
        assertEquals("Hello from Sparta", response.body().asString());
        // checking whether the headers are exists ( hasHeaderWithName is not case sensitive )

        assertTrue(response.getHeaders().hasHeaderWithName("Content-Type"));
        assertTrue(response.getHeaders().hasHeaderWithName("date"));
        assertTrue(response.getHeaders().hasHeaderWithName("Content-Length"));


        System.out.println(response.getHeaders());

        System.out.println("Status Code: "+ response.statusCode());
        System.out.println("Status Code: "+ response.getStatusCode());
        System.out.println("Response Content Type: "+ response.contentType());
        System.out.println("Response Content Type: "+ response.getHeader("content-type"));
        System.out.println("Response Transfer-Encoding: "+ response.getHeader("Content-Length"));
        System.out.println("Response Date: "+ response.getHeader("Date"));
        System.out.println("Response Body/PayLoad: "+ response.body().asString());
        System.out.println("Response Body/Payload: "+ response.body().prettyPrint());
    }

//    Given accept type is Json
//    And Id parameter value is 4
//    When user sends GET request to /api/spartans/{id}
//    Then response status code should be 200
//    And response content-type: application/json;charset=UTF-8
//    And "Paige" should be in response payload
    @Test
    public void GetSpartanById_Positive_Path_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                            and().pathParam("id",4).       //  when().get("/spartans/4");
                            when().get("/spartans/{id}");                      //

        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("Paige"));

        response.prettyPrint();

//-----------------------------------------------------log().all();
//                given().accept(ContentType.JSON).
//                and().pathParam("id",4).
//                when().get("/spartans/{id}").
//                then().log().all();
//------------------------------------------------------

    }

//    Given accept type is Json
//    And Id parameter value is 500
//    When user sends GET request to /api/spartans/{id}
//    Then response status code should be 404
//    And response content-type: application/json;charset=UTF-8
//    And "message" should be in response body/payload
//    And "Spartan Not Found" should be in response body/payload
    @Test
    public void GetSpartanById_Negative_Path_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                            and().pathParam("id",500).
                            when().get("/spartans/{id}");

        assertEquals(404, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("message"));
        assertTrue(response.body().asString().contains("Spartan Not Found"));

        response.prettyPrint();

    }

//    Given accept type is Json
//    And Query parameters values are:
//          gender          |   Female
//          nameContains    |   Me
//    When user sends GET request to /api/spartans/search?gender=Female
//    Then response status code should be 200
//    And response content-type: application/json;charset=UTF-8
//    And "Female" should be in response payload
//    And "Janetta" should be in response payload
    @Test
    public void GetSpartanById_Positive_Querey_Param_Test() {
        Response response = given().accept(ContentType.JSON).
                            and().queryParam("gender", "Female").
                            and().queryParam("nameContains", "Me").
                            when().get("/spartans/search");
//opt2
//        Response response = given().accept(ContentType.JSON).
//                            and().queryParams("gender", "Female",
//                                                "nameContains", "Me").
//                            when().get("/spartans/search");
//opt3
//        Response response = given().accept(ContentType.JSON).
//                             when().get("/spartans/search?gender=Female&nameContains=Me");

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("me"));


    }


}
