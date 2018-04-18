package com.homework.service;

import com.homework.entity.Statistics;
import com.homework.entity.Transaction;
import com.homework.utils.TransactionRetentionHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class StatisticsService {
    private final static Map<Integer, Statistics> records = new ConcurrentHashMap<>(60);

    /**
     * Add transaction to the records.
     */
    public void addTransaction(Transaction transaction) {
        if (TransactionRetentionHelper.isTimestampOld(transaction.getTimestamp())) {
            return;
        }

        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();
        summaryStatistics.accept(transaction.getAmount());

        //get a second as a key from transaction
        Integer s = TransactionRetentionHelper.getSecond(transaction.getTimestamp());

        //get existing statistics record by key s
        Statistics statistics = records.get(s);

        //if there is no existing record, create new and exit;
        if (statistics == null) {
            Statistics statistics1 = new Statistics();
            statistics1.setTimestamp(transaction.getTimestamp());
            statistics1.setStatistics(summaryStatistics);
            records.put(s, statistics1);
            return;
        }

        // if record's timestamp is old, replace by new statistics
        if (TransactionRetentionHelper.isTimestampOld(statistics.getTimestamp())) {
            Statistics statistics1 = new Statistics();
            statistics1.setTimestamp(transaction.getTimestamp());
            statistics1.setStatistics(summaryStatistics);
            records.put(s, statistics1);
        } else { // otherwise combine statistics and update timestamp
            statistics.setTimestamp(transaction.getTimestamp());
            statistics.getStatistics().combine(summaryStatistics);
            records.put(s, statistics);
        }
    }

    /**
     * Returns statistics for the last minute.
     **/
    public DoubleSummaryStatistics getStatistics() {
        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();
        records.values().stream().filter(current -> (System.currentTimeMillis() - current.getTimestamp()) <= TransactionRetentionHelper.ONE_MINUTE)
                .forEach(current -> summaryStatistics.combine(current.getStatistics()));

        return summaryStatistics;
    }
}