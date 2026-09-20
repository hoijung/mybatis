package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StaticsController {

    @Autowired
    private StaticsService userService;

    @GetMapping("/monthly-orders")
    public List<Map<String, Object>> users(
            @org.springframework.web.bind.annotation.RequestParam(name = "startDate", defaultValue = "20250101") String startDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "endDate", defaultValue = "20260919") String endDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "distributeYn", required = false) String distributeYn,
            @org.springframework.web.bind.annotation.RequestParam(name = "existOrderYn", required = false) String existOrderYn) {

        return userService.selectUser(startDate, endDate, distributeYn, existOrderYn);
    }

    @GetMapping("/prod-stats")
    public List<Map<String, Object>> prodStats(
            @org.springframework.web.bind.annotation.RequestParam(name = "startDate", defaultValue = "20250101") String startDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "endDate", defaultValue = "20260919") String endDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "distributeYn", required = false) String distributeYn,
            @org.springframework.web.bind.annotation.RequestParam(name = "existOrderYn", required = false) String existOrderYn,
            @org.springframework.web.bind.annotation.RequestParam(name = "etcThreshold", defaultValue = "4") Integer etcThreshold) {

        return userService.selectProd(startDate, endDate, distributeYn, existOrderYn, etcThreshold);
    }

    @GetMapping("/team-stats")
    public List<Map<String, Object>> teamStats(
            @org.springframework.web.bind.annotation.RequestParam(name = "startDate", defaultValue = "2026-01") String startDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "endDate", defaultValue = "2026-12") String endDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "distributeYn", required = false) String distributeYn,
            @org.springframework.web.bind.annotation.RequestParam(name = "existOrderYn", required = false) String existOrderYn) {

        return userService.selectTeam(startDate, endDate, distributeYn, existOrderYn);
    }

    @GetMapping("/state-stats")
    public List<Map<String, Object>> stateStats(
            @org.springframework.web.bind.annotation.RequestParam(name = "startDate", defaultValue = "202601") String startDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "endDate", defaultValue = "202612") String endDate,
            @org.springframework.web.bind.annotation.RequestParam(name = "stateType", defaultValue = "all") String stateType,
            @org.springframework.web.bind.annotation.RequestParam(name = "distributeYn", required = false) String distributeYn,
            @org.springframework.web.bind.annotation.RequestParam(name = "existOrderYn", required = false) String existOrderYn) {

        return userService.selectState(startDate, endDate, stateType, distributeYn, existOrderYn);
    }
}
