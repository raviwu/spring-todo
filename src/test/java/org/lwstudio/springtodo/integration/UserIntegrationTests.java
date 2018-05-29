package org.lwstudio.springtodo.integration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwstudio.springtodo.model.dto.ErrorDTO;
import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.repository.TodoRepository;
import org.lwstudio.springtodo.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @LocalServerPort
    private int port;

    @Before
    public void deleteAllUsers() {
        for (User user : userRepository.selectAllUsers()) {
            userRepository.deleteUserById(user.getId());
        }

        assertEquals(0, userRepository.selectAllUsers().size());
    }

    @Test
    public void registerAnAccountAndCrudTodo() {
        String username = "ravi";
        String password = "password";

        User ravi = registerAccount(username, password);

        assertEquals(1, userRepository.selectAllUsers().size());

        String jwtToken = loginUser(username, password);

        ResponseEntity<User[]> responseUsersEntity =
            restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(jwtToken)),
                User[].class);

        User[] users = responseUsersEntity.getBody();

        assertEquals(HttpStatus.OK, responseUsersEntity.getStatusCode());
        assertEquals(1, users.length);
        assertEquals("ravi", users[0].getUsername());

        ResponseEntity<Todo[]> responseTodosEntity =
            restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId() + "/todos"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(jwtToken)),
                Todo[].class);

        Todo[] emptyTodos = responseTodosEntity.getBody();

        assertEquals(HttpStatus.OK, responseTodosEntity.getStatusCode());
        assertEquals(0, emptyTodos.length);

        // Create new Todo
        String todoDescription = "This is my todo!";

        ResponseEntity<Todo> newTodoEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId() + "/todos"),
                HttpMethod.POST,
                newTodoRequestEntity(todoDescription, jwtToken),
                Todo.class);

        assertEquals(HttpStatus.CREATED, newTodoEntity.getStatusCode());
        assertEquals(1, todoRepository.selectTodosByUserId(ravi.getId()).size());
        assertEquals(todoDescription, newTodoEntity.getBody().getDescription());

        ResponseEntity<Todo[]> responseNewTodosEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId() + "/todos"), HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(jwtToken)), Todo[].class);

        Todo[] todos = responseNewTodosEntity.getBody();

        assertEquals(HttpStatus.OK, responseNewTodosEntity.getStatusCode());
        assertEquals(1, todos.length);

        Todo todoInDatabase = todoRepository.selectTodoById(todos[0].getId());
        assertEquals(todoDescription, todoInDatabase.getDescription());

        // Delete User will Delete Todo
        ResponseEntity<User> deletedUserEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId()), HttpMethod.DELETE,
                new HttpEntity<Object>(generateJwtHeader(jwtToken)), User.class);

        assertEquals(HttpStatus.NO_CONTENT, deletedUserEntity.getStatusCode());
        assertEquals(0, userRepository.selectAllUsers().size());
        assertEquals(0, todoRepository.selectAllTodos().size());
    }

    @Test
    public void authorizationChecksOnUserResources() {
        String raviUsername = "ravi";
        String johnUsername = "john";
        String password = "password";

        User ravi = registerAccount(raviUsername, password);

        ResponseEntity<ErrorDTO> forbiddenToAccessEntity =
            restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader("invalid Token")),
                ErrorDTO.class);

        assertEquals(HttpStatus.FORBIDDEN, forbiddenToAccessEntity.getStatusCode());

        String raviJwtToken = loginUser(raviUsername, password);

        ResponseEntity<User[]> responseUsersEntity = restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(raviJwtToken)),
                User[].class);

        assertEquals(HttpStatus.OK, responseUsersEntity.getStatusCode());
        assertEquals(1, responseUsersEntity.getBody().length);

        ResponseEntity<User> getRaviUserEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId()),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(raviJwtToken)),
                User.class);

        assertEquals(HttpStatus.OK, getRaviUserEntity.getStatusCode());
        assertEquals(ravi.getUsername(), getRaviUserEntity.getBody().getUsername());

        ResponseEntity<Todo[]> getRaviTodosEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + ravi.getId() + "/todos"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(raviJwtToken)),
                Todo[].class);

        assertEquals(HttpStatus.OK, getRaviTodosEntity.getStatusCode());

        User john = registerAccount(johnUsername, password);

        assertEquals(2, userRepository.selectAllUsers().size());

        ResponseEntity<ErrorDTO> failedToGetJohnUserEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + john.getId()),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(raviJwtToken)),
                ErrorDTO.class);

        assertEquals(HttpStatus.FORBIDDEN, failedToGetJohnUserEntity.getStatusCode());

        ResponseEntity<ErrorDTO> failedToGetJohnTodosEntity = restTemplate.exchange(
                createURLWithPort("/api/users/" + john.getId() + "/todos"),
                HttpMethod.GET,
                new HttpEntity<Object>(generateJwtHeader(raviJwtToken)),
                ErrorDTO.class);

        assertEquals(HttpStatus.FORBIDDEN, failedToGetJohnTodosEntity.getStatusCode());
    }

    private User registerAccount(String username, String password) {
        JwtAuthenticationRequest jwtRequest = new JwtAuthenticationRequest(username, password);

        restTemplate.postForEntity("/auth/register", jwtRequest, JwtAuthenticationRequest.class).getBody().getUsername();

        return userRepository.selectUserByUsername(username);
    }

    private String loginUser(String username, String password) {
        JwtAuthenticationRequest jwtRequest = new JwtAuthenticationRequest(username, password);

        return restTemplate.postForEntity("/auth/login", jwtRequest, JwtAuthenticationResponse.class).getBody().getToken();
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
        String requestBody = "{\"description\":\"" + description + "\"}";

        return new HttpEntity<Object>(requestBody, generateJwtHeader(token));
    }
}