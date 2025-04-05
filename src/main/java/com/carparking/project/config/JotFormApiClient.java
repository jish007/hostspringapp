package com.carparking.project.config;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class JotFormApiClient {

    public static void main(String[] args) {
        String apiKey = "cddafdb394ece027f76898d35eca913d";
        String formId = "250874001847053";
        String url = "https://api.jotform.com/form/" + formId + "/submissions?apiKey=" + apiKey;

        Map<String, Object> submission = new HashMap<>();
        submission.put("3", "John Doe"); // Name
        submission.put("4", "john.doe@example.com"); // Email
        submission.put("5", "KL65H432"); // Vehicle Number
        submission.put("6", "9645794547"); // Phone Number

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("submission", submission);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("APIKEY", apiKey); // Move API key to headers

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        System.out.println(response.getBody());
    }
}