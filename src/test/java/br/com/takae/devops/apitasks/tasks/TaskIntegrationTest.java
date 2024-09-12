package br.com.takae.devops.apitasks.tasks;

import br.com.takae.devops.apitasks.tasks.dto.REQSaveTask;
import io.restassured.RestAssured;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskIntegrationTest {
//    static PostgreSQLContainer postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.4"))
//            .withDatabaseName("api-tasks")
//            .withUsername("tester")
//            .withPassword("tester");


    @Test
    public void WHEN__PostTasksWithoutXAuthUsernameHeader__RETURNS__Unauthorized() {
        REQSaveTask body = new REQSaveTask();
        body.setName("Do the Chores");
        body.setContent("until mom arrives!");
        RestAssured.given()
                .body(body)
                .post("/api/v1/tasks")
                .then()
                .statusCode(401);
    }

    @Test
    public void WHEN__GetTasksWithXAuthUsernameHeader__RETURNS__EmptyPage() {
        RestAssured.given()
                .header("Content-type", "application/json")
                .header("X-Auth-Username", "foobar")
                .when()
                .get("/api/v1/tasks")
                .then()
                .statusCode(200);
    }

    @Test
    public void WHEN__GetTasksWithXAuthUsernameHeader__RETURNS__PageWithTask() {
        RestAssured.given().header("X-Auth-Username", "foobar")
                .when()
                .get("/api/v1/tasks")
                .then()
                .statusCode(200);
    }

    @Test
    public void WHEN__GetTasksWithoutXAuthUsernameHeader__RETURNS__Unauthorized() {
        RestAssured.get("/api/v1/tasks")
                .then()
                .statusCode(401);
    }


}