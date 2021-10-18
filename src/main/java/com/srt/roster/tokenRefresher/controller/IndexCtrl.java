package com.srt.roster.tokenRefresher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class IndexCtrl
{
    @ApiIgnore
    @GetMapping("api/")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
