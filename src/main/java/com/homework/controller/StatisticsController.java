package com.homework.controller;

import com.homework.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.GET, value = "/statistics")
    public ResponseEntity<?> getFuelRegistrations() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticsService.getStatistics());
    }
}
