package org.lwstudio.springtodo.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.security.JwtAuthenticationRequest;
import org.lwstudio.springtodo.security.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void registerAnAccountAndSeeTodosList() {
        JwtAuthenticationRequest jwtRequest = new JwtAuthenticationRequest("ravi", "password");

        restTemplate.postForEntity(
                "/auth/register",
                jwtRequest,
                JwtAuthenticationRequest.class);

        // String jwtToken = restTemplate.postForEntity(
        //         "/auth/login",
        //         jwtRequest,
        //         JwtAuthenticationResponse.class).getBody().getToken();

        // ResponseEntity<User[]> responseUsersEntity =
        //     restTemplate.exchange(
        //         createURLWithPort("/api/users"),
        //         HttpMethod.GET,
        //         new HttpEntity<Object>(generateJwtHeader(jwtToken)),
        //         User[].class);

        // User[] users = responseUsersEntity.getBody();

        // assertEquals(HttpStatus.OK, responseUsersEntity.getStatusCode());
        // assertEquals(1, users.length);
        // assertEquals("ravi", users[0].getUsername());

        // ResponseEntity<Todo[]> responseTodosEntity =
        //     restTemplate.exchange(
        //         createURLWithPort("/api/users/1/todos"),
        //         HttpMethod.GET,
        //         new HttpEntity<Object>(generateJwtHeader(jwtToken)),
        //         Todo[].class);

        // Todo[] todos = responseTodosEntity.getBody();

        // assertEquals(HttpStatus.OK, responseUsersEntity.getStatusCode());
        // assertEquals(0, todos.length);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private MultiValueMap<String, String> generateJwtHeader(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);

        return headers;
    }

    private HttpEntity<Object> newTodoRequestEntity(String description, String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("description", description);

        return new HttpEntity<Object>(body, generateJwtHeader(token));
    }
}