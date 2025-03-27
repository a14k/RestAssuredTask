package com.samplebooks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class XmlBaseClass {
    public static String baseURl = "https://simple-books-api.glitch.me";

    @Test
    public void trelloxml() {
        String req="{\n" +
                "   \"clientName\": \"Zacky\",\n" +
                "   \"clientEmail\": \"axdxsxfscdddfdciidfcf@dedxample.com\"\n" +
                "}";

        Response res = RestAssured.given()
                .baseUri(baseURl)
                .contentType(ContentType.JSON).body(req)
                .when().post("/api-clients/")
                .then().statusCode(201).and().contentType(ContentType.XML)
                .extract().response();

        XmlPath xmlPath = new XmlPath(res.asString());
        String clientName = xmlPath.getString("client.clientName");
        System.out.print(clientName);

    }
}
