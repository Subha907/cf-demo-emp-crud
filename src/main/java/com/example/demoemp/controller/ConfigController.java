package com.example.demoemp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfigController {

    @Value("${app.welcome.message}")
    private String welcomeMessage;

    @Value("${app.max.employees}")
    private int maxEmployees;

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("welcomeMessage", welcomeMessage);
        config.put("maxEmployees", maxEmployees);
        return config;
    }
}
