package com.example.users;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

public class UaaClient {
    private final String clientId;
    private final String clientSecret;
    private final String uaaUrl;
    private final RestTemplate restTemplate;

    public UaaClient(String clientId, String clientSecret, String uaaUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.uaaUrl = uaaUrl;
        this.restTemplate = new RestTemplate();
    }

    public Optional<User> createUser(String name, String password) {
        String clientToken = getClientToken(clientId, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + clientToken);

        ParameterizedTypeReference<Map<String, ?>> userReturnValue = new ParameterizedTypeReference<Map<String, ?>>() {
        };

        Map<String, Object> userData = createUserData(name, password);

        HttpEntity<Map<String, ?>> request = new HttpEntity<>(userData, headers);
        ResponseEntity<Map<String, ?>> response = restTemplate.exchange(
                uaaUrl + "/uaa/Users",
                HttpMethod.POST,
                request,
                userReturnValue
        );

        return Optional.of(new User(name, (String) response.getBody().get("id")));
    }

    public Optional<Session> logIn(String name, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("grant_type", "password");
        form.add("username", name);
        form.add("password", password);

        ParameterizedTypeReference<Map<String, String>> returnValue = new ParameterizedTypeReference<Map<String, String>>() {
        };

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map<String, String>> clientTokenResponse = restTemplate.exchange(
                uaaUrl + "/uaa/oauth/token",
                HttpMethod.POST,
                request,
                returnValue
        );

        return Optional.of(new Session(clientTokenResponse.getBody().get("access_token")));
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        return "Basic " + new String(Base64.encodeBase64((clientId + ":" + clientSecret).getBytes(Charset.forName("US-ASCII"))));
    }

    private Map<String, Object> createUserData(String userName, String password) {
        Map<String, Object> data = new HashMap<>();

        /*
        {
            "userName":"username",
            "password": "user password",
            "emails": [{
                "value": "foo@example.com"
            }]
        }
         */

        data.put("userName", userName);
        data.put("password", password);
        List<Map<String, String>> emails = new ArrayList<>();
        Map<String, String> email = new HashMap<>();
        emails.add(email);
        email.put("value", "user-" + userName + "@example.com");
        data.put("emails", emails);

        return data;
    }

    private String getClientToken(String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("grant_type", "client_credentials");

        ParameterizedTypeReference<Map<String, String>> clientTokenReturnValue = new ParameterizedTypeReference<Map<String, String>>() {
        };

        HttpEntity<MultiValueMap<String, String>> clientTokenRequest = new HttpEntity<>(form, headers);
        ResponseEntity<Map<String, String>> clientTokenResponse = restTemplate.exchange(
                uaaUrl + "/uaa/oauth/token",
                HttpMethod.POST,
                clientTokenRequest,
                clientTokenReturnValue
        );

        return clientTokenResponse.getBody().get("access_token");
    }
}
