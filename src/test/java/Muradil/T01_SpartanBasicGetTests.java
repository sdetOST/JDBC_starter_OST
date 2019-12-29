package Muradil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;


public class T01_SpartanBasicGetTests {

    @BeforeClass
    public static void setUp() {
//        RestAssured.baseURI = "http://3.89.115.0";
        RestAssured.baseURI = "http://54.90.84.187";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }
    @Test
    public void ViewAllSpartansTest() {
        //Response response = RestAssured.get("/spartans/");
        Response response = get("/spartans/");
        System.out.println("Status Code: "+ response.getStatusCode());
//        response.body().prettyPrint();
        System.out.println("Response Content Type: "+ response.contentType());
        System.out.println("Response Content Type: "+ response.getHeader("content-type"));
        System.out.println("Response Transfer-Encoding: "+ response.getHeader("Transfer-Encoding"));
        System.out.println("Response Date: "+ response.getHeader("Date"));

        assertEquals(200, response.statusCode());
        // below codes are doing same exact thing
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("application/json;charset=UTF-8", response.getHeader("content-type"));

        // checking whether the headers are exists
        // hasHeaderWithName is not case sensitive

        assertTrue(response.getHeaders().hasHeaderWithName("date"));
        assertTrue(response.getHeaders().hasHeaderWithName("Transfer-Encoding"));
        assertTrue(response.getHeaders().hasHeaderWithName("Content-Type"));
    }



//    Given no headers are provided
//    When User send request to /Spartans
//    Then Reponse status code should be 200
//    and  header should have content Type / json
//    and  header should contains Date
    @Test
    public void Spartan_All_Test() {
        //Response response = RestAssured.get("/spartans/");
        Response response = get("/spartans/");
        assertEquals(200, response.statusCode());
        // below codes are doing same exact thing
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("application/json;charset=UTF-8", response.getHeader("content-type"));

        // checking whether one header exists
        // hasHeaderWithName is not case sensitive
        boolean hasDateHeader = response.getHeaders().hasHeaderWithName("date");
        assertTrue(hasDateHeader);
    }

//    Given except type is jason
//    When User send get request to /spartans
//    Then Reponse status code should be 200
//    And response body should json
//    And response should contain "name":"Nels"
    @Test
    public void ViewAllSpartansTest2() {
        Response response=RestAssured.
                given().accept(ContentType.JSON).
                when().get("/spartans/");
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("Nels"));
    }

//    When User send get request to /spartans/3
//    Then Reponse status code should be 200
//    And content type should be "application/json;charset=UTF-8"
//    And response should contain "name":"Fidole"
    @Test
    public void getASpartanTest() {
        //  request - query
                given().accept(ContentType.JSON).
                when().get("/spartans/3").
       // response - verification
                then().statusCode(200).
                and().contentType("application/json;charset=UTF-8");
    }

//    When User send get request to /spartans/4
//    Then the status code should be 200
//    And content type should be "application/json;charset=UTF-8"
//    And response body should contain "Paige"
    @Test
    public void getASpartanTest2() {
//  request - query
        Response response = when().get("/spartans/4");
// response - verification
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.contentType().contains("application/jsonn"));
        assertTrue(response.body().asString().contains("Paige"));

    }

}
