package com.samplebooks;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TestBaseClass {
    public static String baseURL="https://simple-books-api.glitch.me";
    public static String token;
    public static String orderId;
    @Test(priority = 0)
    public void baseClass(){
        String requestBody="{\n" +
                "   \"clientName\": \"Zacky\",\n" +
                "   \"clientEmail\": \"axdxsxsssdfdeddfdciidfcf@dedxample.com\"\n" +
                "}";
        Response res= (Response) RestAssured
                .given()
                .baseUri(baseURL)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/api-clients/")
                .then()
                .statusCode(201).extract().response();
        //System.out.println(res.getBody().asString());
        token=res.jsonPath().getString("accessToken");

    }
    @Test(priority = 1)
    public void createTest(){
        String requestBody="{\n" +
                "  \"bookId\": 1,\n" +
                "  \"customerName\": \"John\"\n" +
                "}";
        Response res= (Response) RestAssured
                .given()
                .baseUri(baseURL)
                .header("Authorization","Bearer "+token)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/orders")
                .then()
                .statusCode(201).extract().response();
        System.out.print(res.getBody().asString());
        orderId =res.jsonPath().getString("orderId");
    }
    @Test(priority = 2)
    public void getStatus()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .when()
                .get("/status")
                .then()
                .statusCode(200).extract().response();
        System.out.println(res.getBody().asString());
    }
    @Test(priority = 3)
    public void listOfBooks()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .when()
                .get("/books")
                .then()
                .statusCode(200).extract().response();
        System.out.println(res.getBody().asString());
       // bookId=res.jsonPath().getString("id");
       // System.out.print(bookId);
    }
    @Test(priority = 4)
    public void singleBook()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .header("Authorization","Bearer "+token)
                .pathParam("id",orderId)
                .contentType(ContentType.JSON)
                .when()
                .get("/orders/{id}")
                .then()
                .statusCode(200).extract().response();
        System.out.println(res.getBody().asString());
    }
    @Test(priority = 5)
    public void getAllOrder()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .header("Authorization","Bearer "+token)
                .when()
                .get("/orders")
                .then()
                .statusCode(200).extract().response();
        System.out.print(res.getBody().asString());
    }
    @Test(priority = 6)
    public void getAnOrder()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .header("Authorization","Bearer "+token)
                .pathParam("id",orderId)
                .when()
                .get("/orders/{id}")
                .then()
                .statusCode(200).extract().response();
        System.out.print(res.getBody().asString());
    }
    @Test(priority = 7)
    public void updateAnorder()
    {
        Response res= RestAssured
                .given()
                .baseUri(baseURL)
                .pathParam("id",orderId)
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "  \"customerName\": \"Sam\"\n" +
                        "}")
                .when()
                .patch("/orders/{id}")
                .then()
                .statusCode(204).extract().response();
        System.out.print(res.getBody().asString());

    }
    @Test(priority = 8)
    public void deleteOrder()
    {
        Response res = RestAssured
                .given()
                .baseUri(baseURL)
                .header("Authorization","Bearer "+token)
                .pathParam("id",orderId)
                .when()
                .delete("/orders/{id}")
                .then().statusCode(204).extract().response();

    }




}
