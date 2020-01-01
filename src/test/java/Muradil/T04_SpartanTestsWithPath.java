package Muradil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class T04_SpartanTestsWithPath {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.base_uri");
        RestAssured.port =Integer.parseInt(ConfigurationReader.getProperty("spartan.port"));
        RestAssured.basePath = ConfigurationReader.getProperty("spartan.base_path");

    }

//    Given accept type is Json
//    And Path Parameter id is 10
//    When user sends GET request to /api/spartans/{id}
//    Then response status code should be 200
//    And response content-type: application/json;charset=UTF-8
//    And "Lorenza" should be in response payload
//    And response payload values match the following:
//          id: 10
//          name: Lorenza
//          gender: Female
//          phone: 3312820936
//
    @Test
    public void SpartanGetWithId_Json_Test_using_path() {
        Response response = given().accept(ContentType.JSON).
                and().pathParam("id",10).
                when().get("/spartans/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("Lorenza"));

        int id =  response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        String phone = response.path("phone").toString();

        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals("3312820936", phone);

        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("gender: " + gender);
        System.out.println("phone: " + phone);
        response.prettyPrint();

    }

    @Test
    public void GetAllSpartans_using_path_withList() {
        Response response = given().accept(ContentType.JSON).
                            when().get("/spartans/");

        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());

// extract first id
        int id1 =  response.path("id[0]");              System.out.println("id1 = " + id1);
// extract first name
        String name1 = response.path("name[0]");        System.out.println("name1 = " + name1);
// extract first gender
        String gender1 = response.path("gender[0]");    System.out.println("gender1 = " + gender1);
// extract phone1
        Long phone1 = response.path("phone[0]");        System.out.println("phone1 = " + phone1);
// extract last first-name
        String nameLast = response.path("name[-1]");    System.out.println("nameLast = " + nameLast);


// extract all names & print out
        List<String> names = response.path("name");
        System.out.println("number of names = " + names.size());
        System.out.println("names = " + names);

// extract all phone numbers & print out
        List<Long> phNumbers = response.path("phone");
        System.out.println("number of phoneNumbers = " + phNumbers.size());
        System.out.println("phNumbers = " + phNumbers);

    }


    @Test
    public void GetCountries_Extract_nested_key_values_using_path() {
        RestAssured.baseURI=ConfigurationReader.getProperty("hr.base_uri");
        RestAssured.port=Integer.parseInt(ConfigurationReader.getProperty("hr.port"));
        RestAssured.basePath=ConfigurationReader.getProperty("hr.base_path");

        Response response = given().accept(ContentType.JSON).
                            and().queryParams("q", "{\"region_id\":2}").
                            when().get("/countries");

        String CountryId1=response.path("items.country_id[0]");
        String CountryName1=response.path("items.country_name[0]");

        System.out.println("CountryId1: " + CountryId1);
        System.out.println("CountryName1: " + CountryName1);

// get all country names and print them out
        List<String> countries = response.path("items.country_name");
        System.out.println("countries: " + countries);

// Assert that all region id's are 2
        List<Integer> regionIDs = response.path("items.region_id");
        for (int regionId:regionIDs ) assertEquals(2,regionId );

        System.out.println("regionIDs: " + regionIDs);

        response.prettyPrint();

    }


}
