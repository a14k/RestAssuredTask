package com.samplebooks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

public class TrelloRest {
    public static String baseURL="https://api.trello.com/1/";
    @Test
    public void createBoard()
    {
        Response res= RestAssured
                .given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .queryParam("name","Ank")
                .queryParam("Key","05d0e28c06c2d83faca8c20bf3bcf66a")
                .queryParam("token","ATTA468e89a9485916bfd2cb90087536bcc08ff928319eeb051bad9b3acfbd4d9eee0FE85E84")
                .when()
                .post("boards/?name={name}&key=APIKey&token=APIToken").then().statusCode(200).extract().response();
        System.out.print(res.getBody().asString());
    }
}
