package com.example.demo.sender;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class SenderService {

    @Autowired
    ContactRepository contactRepository;

    @Value("${zmianowy.config.sender_sendfull_api}")
    private String sendfullApi;

    @Value("${zmianowy.config.sender_test_sendfull_api}")
    private String testSendfullApi;

    @Value("${zmianowy.config.sender_tokenapi}")
    private String tokenApi;

    @Value("${zmianowy.config.sender_login}")
    private String senderLogin;

    @Value("${zmianowy.config.sender_password}")
    private String senderPassword;


    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    public String sendSmsRequestToSender(String content, String numbers, String author) throws JSONException {
        final String endpointUrl = sendfullApi;
        final String basicAuth = "Basic " + Base64.getEncoder().encodeToString((senderLogin+":"+senderPassword).getBytes());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorizationtoken", tokenApi);
        headers.set("Author", author);
        headers.set("Authorization", basicAuth);

        JSONObject requestBody = new JSONObject();
        requestBody.put("author", author);
        requestBody.put("numbers", numbers);
        requestBody.put("content", content);
        System.out.println("wysylam");
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(endpointUrl, entity, String.class);
        return response.getBody();
    }
    public String sendSmsTestRequestToSender(String content, String numbers, String author) throws JSONException {
        final String endpointUrl = testSendfullApi;
        final String basicAuth = "Basic " + Base64.getEncoder().encodeToString((senderLogin+":"+senderPassword).getBytes());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorizationtoken", tokenApi);
        headers.set("Author", author);
        headers.set("Authorization", basicAuth);

        JSONObject requestBody = new JSONObject();
        requestBody.put("author", author);
        requestBody.put("numbers", numbers);
        requestBody.put("content", content);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(endpointUrl, entity, String.class);
        return response.getBody();
    }
}
