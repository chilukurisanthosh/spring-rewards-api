
package com.example.rewards.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthFlowIT {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void tokenThenAccessProtected() {
        String base = "http://localhost:" + port;

        // Request token
        ResponseEntity<Map> tokenResp = rest.postForEntity(base + "/auth/token",
                Map.of("username", "user", "password", "password"), Map.class);
        assertThat(tokenResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = (String) tokenResp.getBody().get("access_token");
        assertThat(token).isNotBlank();

        // Call protected endpoint with Bearer token
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);
        ResponseEntity<String> rewards = rest.exchange(base + "/api/rewards?start=2025-01-01&end=2025-03-31",
                HttpMethod.GET, new HttpEntity<>(h), String.class);
        assertThat(rewards.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
