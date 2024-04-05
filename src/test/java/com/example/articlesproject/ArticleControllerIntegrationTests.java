package com.example.articlesproject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.json.JsonObject;

@SqlGroup({
        @Sql(scripts = "classpath:sql/cleanup-test-articles.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
@SpringBootTest
public class ArticleControllerIntegrationTests {
    @Value("${api.base.url}")
    private String baseUrl;
    private RequestSpecification request;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given()
                .header("Authorization", "Bearer " + obtainAccessToken_adminRole())
                .header("Content-Type","application/json");
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/articles.json")
    public void addArticle(JsonObject jsonObject) {
        Response response = request.body(jsonObject.toString()).post("/articles");
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    @Test
    public void getArticleWithDefaultParams() {
        Response response = request.get("/articles");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    @Test
    public void getArticle() {
        Response response = request.get("/articles?size=5&page=1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    @Test
    public void getStatistics_userRole_notOk() {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + obtainAccessToken_userRole())
                .header("Content-Type","application/json");
        Response response = request.get("/articles/statistics");
        Assertions.assertEquals(403, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    @Test
    public void getStatistics_adminRole_ok() {
        Response response = request.get("/articles/statistics");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    private String obtainAccessToken_adminRole() {
        RestAssured.baseURI = baseUrl;

        RequestSpecification request = RestAssured.given();

        String payload = "{\n" +
                "    \"username\": \"Alice\",\n" +
                "    \"password\" : \"12345678\"\n" +
                "}";
        request.header("Content-Type","application/json");
        Response responseFromGenerateToken = request.body(payload).post("/users/authenticate");
        String jsonString = responseFromGenerateToken.getBody().asString();
        return JsonPath.from(jsonString).get("token");
    }

    private String obtainAccessToken_userRole() {
        RestAssured.baseURI = baseUrl;

        RequestSpecification request = RestAssured.given();

        String payload = "{\n" +
                "    \"username\": \"Bob\",\n" +
                "    \"password\" : \"12345678\"\n" +
                "}";
        request.header("Content-Type","application/json");
        Response responseFromGenerateToken = request.body(payload).post("/users/authenticate");
        String jsonString = responseFromGenerateToken.getBody().asString();
        return JsonPath.from(jsonString).get("token");
    }
}
