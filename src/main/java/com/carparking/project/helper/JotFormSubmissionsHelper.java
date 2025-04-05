package com.carparking.project.helper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JotFormSubmissionsHelper {
    private static final String API_KEY = "a0dcc7bd565cd7c6b947bca0e68c75a0";
    // private static final String FORM_ID = "250571659232054";


    public String getFormResponse(String formId) {
        String url = "https://api.jotform.com/form/" + formId + "/submissions?apiKey=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return response;
    }


    public String deleteForm(String submissionId) {

        try {
            URL url = new URL("https://api.jotform.com/submission/" + submissionId + "?apiKey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Submission deleted successfully!");
            } else {
                System.out.println("Failed to delete. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Submission deleted successfully!";
    }

    public void printFormFieldIds(String formId) {
        try {
            String jsonResponse = getFormResponse(formId);
            System.out.println("Form Structure:");
            System.out.println(jsonResponse);

            // Parse and print out the field IDs and their corresponding names
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray submissions = jsonObject.getJSONArray("content");

            if (submissions.length() > 0) {
                JSONObject firstSubmission = submissions.getJSONObject(0);
                JSONObject answers = firstSubmission.getJSONObject("answers");

                System.out.println("\nField IDs and their names:");
                for (String key : answers.keySet()) {
                    JSONObject field = answers.getJSONObject(key);
                    String fieldName = field.optString("text", "Unnamed Field");
                    System.out.println("Field ID: " + key + " - Name: " + fieldName);
                }
            } else {
                System.out.println("No submissions found. Please submit a test entry to your form first.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray getSubmissionId(String formId) {

        JSONArray submissions = null;
        try {
            URL url = new URL("https://api.jotform.com/form/" + formId + "/submissions?apiKey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            submissions = jsonResponse.getJSONArray("content");

            System.out.println("Submission IDs:");
            for (int i = 0; i < submissions.length(); i++) {
                String submissionId = submissions.getJSONObject(i).getString("id");
                System.out.println(submissionId);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }
}

