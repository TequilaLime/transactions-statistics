package com.homework.controller;

import com.homework.entity.Transaction;
import com.homework.exception.BadRequestException;
import com.homework.service.StatisticsService;
import com.homework.utils.TransactionRetentionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity<?> registerFuelConsumption(@Valid @RequestBody Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getTimestamp() == null) {
            throw new BadRequestException("Please provide amount or timestamp");
        }
        if (TransactionRetentionHelper.isTimestampOld(transaction.getTimestamp())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        statisticsService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
