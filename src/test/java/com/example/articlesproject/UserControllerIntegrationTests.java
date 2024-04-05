package com.example.articlesproject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.json.JsonObject;

@SqlGroup({
        @Sql(scripts = "classpath:sql/load-test-data-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(scripts = "classpath:sql/cleanup-test-data-all-users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
@SpringBootTest
public class UserControllerIntegrationTests {
    @Value("${api.base.url}")
    private String baseUrl;
    private RequestSpecification request;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given().header("Content-Type","application/json");
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/users-auth.json")
    public void authentication_ok(JsonObject jsonObject) {
        Response response = request.body(jsonObject.toString()).post("/users/authenticate");

        String jsonString = response.getBody().asString();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(jsonString);
        Assertions.assertNotNull(JsonPath.from(jsonString).get("token"));
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/users-register.json")
    public void register_ok(JsonObject jsonObject) {
        Response response = request.body(jsonObject.toString()).post("/users/register");

        String jsonString = response.getBody().asString();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(jsonString);
        Assertions.assertNotNull(JsonPath.from(jsonString).get("username"));
    }
}
