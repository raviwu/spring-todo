package org.lwstudio.springtodo.security;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -1145943518965154778L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    // Introducing the dummy constructor to resolve integration test error
    public JwtAuthenticationResponse() {
        this.token = "dummyToken";
    }

    public String getToken() {
        return token;
    }

}
