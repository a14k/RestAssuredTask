package com.samplebooks;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Iterator;

public class pooling {
    public static String baseURL = "https://simple-books-api.glitch.me";
    public static String token;
    int maxAttempts = 5;
    int attempt = 0;

    @Test(priority = 1)
    public void apiPooling() {
        String requestBody = "{\n" +
                "   \"clientName\": \"Zacky\",\n" +
                "   \"clientEmail\": \"axdsfscdfdciidfcf@dedxample.com\"\n" +
                "}";
        Response res = (Response) RestAssured
                .given()
                .baseUri(baseURL)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/api-clients/")
                .then()
                .statusCode(201).extract().response();
        //System.out.println(res.getBody().asString());
        token = res.jsonPath().getString("accessToken");
    }

    @Test(priority = 2)
    public void getStatus() throws InterruptedException {
        while (attempt < maxAttempts) {
            Response res = RestAssured
                    .given()
                    .baseUri(baseURL)
                    .when()
                    .get("/status")
                    .then()
                    .statusCode(200).extract().response();
            String status = res.jsonPath().getString("status");
            System.out.println(res.getBody().asString());
            System.out.println("Attempt " + (attempt + 1) + ": Status = " + status);
            attempt++;
        }
        Thread.sleep(10);
        if (attempt == maxAttempts) {
            System.out.println("Max attempts reached. Stopping polling.");
        }
    }
}

