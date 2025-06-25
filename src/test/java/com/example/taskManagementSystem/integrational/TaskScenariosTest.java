//package com.example.taskManagementSystem.integrational;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.testcontainers.containers.ComposeContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.io.File;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ActiveProfiles("test")
//public class TaskScenariosTest {
//
//    private static final String TASK_SERVICE_URL = "http://localhost:8082";
//    private static final String AUTH_SERVICE_URL = "http://localhost:8083";
//    private static final String FILE_SERVICE_URL = "http://localhost:8084";
//    private static final String SPACE_SERVICE_URL = "http://localhost:8085";
//
//    @Container
//    static ComposeContainer compose =
//            new ComposeContainer(new File("compose-all.yaml"))
//                    .withExposedService("auth-service", 8083, Wait.forHealthcheck())
//                    .withExposedService("task-service", 8082, Wait.forHealthcheck())
//                    .withExposedService("kafka", 29092, Wait.forHealthcheck())
//                    .withExposedService("postgres", 5425)
//                    .withBuild(true)
//                    .withLocalCompose(true);
//
//    @BeforeAll
//    static void beforeAll() {
//        compose.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        compose.stop();
//    }
//
//    @Test
//    public void testScenarioWithAuthService() {
//        String authHost = compose.getServiceHost("auth-service", 8083);
//        Integer authPort = compose.getServicePort("auth-service", 8083);
//
//        System.out.println("Auth service is running at: " + authHost + ":" + authPort);
//
//        String token = given()
//                .body(Map.of("email", "a@admin.com", "password", "12345678"))
//                .post(AUTH_SERVICE_URL + "/api/v1/auth/login")
//                .then()
//                .statusCode(200)
//                .extract()
//                .path("accessToken"); // или "accessToken", в зависимости от схемы ответа
//
//        System.out.println("Получен токен: " + token);
//    }
//}
