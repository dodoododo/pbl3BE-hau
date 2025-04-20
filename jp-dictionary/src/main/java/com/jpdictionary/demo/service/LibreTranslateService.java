package com.jpdictionary.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LibreTranslateService {

    // Updated to use your local Docker LibreTranslate
    private final String TRANSLATE_API_URL = "http://localhost:5000/translate";

    public String translateToVietnamese(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("q", text);
        body.put("source", "en");
        body.put("target", "vi");
        body.put("format", "text");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    TRANSLATE_API_URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("translatedText");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi gọi API dịch: " + e.getMessage());
        }

        return text; // fallback: return original if failed
    }
}
