package com.srt.roster.tokenRefresher.service;

import com.srt.roster.tokenRefresher.dto.Skill;
import com.srt.roster.tokenRefresher.dto.Tenant;
import com.srt.roster.tokenRefresher.dto.TokenRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class RosterRestClientService {
    @Value("${roster.url}")
    private String URL;

    private TokenService tokenService;

    @Autowired
    public RosterRestClientService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Set<Tenant> getTenants() {
        Set<Tenant> tenants = new HashSet<>(Arrays.asList(fetchTenants()));
        tenants.forEach(tenant -> tenant.skills = new HashSet<>(Arrays.asList(getSkills(tenant.id))));
        return tenants;
    }

    private Tenant[] fetchTenants() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.tokenService.getAccessToken());

        HttpEntity<TokenRequestDTO> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL, HttpMethod.GET, entity, Tenant[].class).getBody();
    }

    private Skill[] getSkills(Integer tenantId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.tokenService.getAccessToken());

        HttpEntity<TokenRequestDTO> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL + tenantId + "/skill", HttpMethod.GET, entity, Skill[].class).getBody();
    }
}
