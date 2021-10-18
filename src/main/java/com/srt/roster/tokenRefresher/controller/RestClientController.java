package com.srt.roster.tokenRefresher.controller;

import com.srt.roster.tokenRefresher.dto.Tenant;
import com.srt.roster.tokenRefresher.service.RosterRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api")
public class RestClientController {
    private RosterRestClientService rosterRestClientService;

    @Autowired
    public RestClientController(RosterRestClientService rosterRestClientService) {
        this.rosterRestClientService = rosterRestClientService;
    }

    @GetMapping
    public Set<Tenant> getTenants() {
        return rosterRestClientService.getTenants();
    }
}
