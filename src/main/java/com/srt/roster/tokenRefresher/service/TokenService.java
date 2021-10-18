package com.srt.roster.tokenRefresher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srt.roster.tokenRefresher.dto.TokenDTO;
import com.srt.roster.tokenRefresher.dto.TokenRequestDTO;
import com.srt.roster.tokenRefresher.mapper.ObjectToUrlEncodedConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class TokenService {
    @Value("${keycloak.url}")
    private String URL;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.username}")
    private String username;
    @Value("${keycloak.password}")
    private String password;

    private TokenDTO token;

    public String getAccessToken() {
        return this.token.access_token;
    }

    @PostConstruct
    private void fetchToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        TokenRequestDTO tokenRequest = new TokenRequestDTO();
        tokenRequest.client_id = clientId;
        tokenRequest.username = username;
        tokenRequest.password = password;
        tokenRequest.grant_type = "password";

        HttpEntity<TokenRequestDTO> entity = new HttpEntity<>(tokenRequest, headers);
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(new ObjectMapper()));
        ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL, entity, TokenDTO.class);

        this.token = response.getBody();
    }

    @Scheduled(fixedDelayString = "${keycloak.tokenRefreshInterval}", initialDelayString = "${keycloak.tokenRefreshInterval}")
    private void refreshToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        TokenRequestDTO tokenRequest = new TokenRequestDTO();
        tokenRequest.client_id = clientId;
        tokenRequest.grant_type = "refresh_token";
        tokenRequest.refresh_token = this.token.refresh_token;

        HttpEntity<TokenRequestDTO> entity = new HttpEntity<>(tokenRequest, headers);
        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(new ObjectMapper()));
        ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL, entity, TokenDTO.class);

        this.token = response.getBody();
    }
}
