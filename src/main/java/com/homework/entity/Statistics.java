package com.homework.entity;

import java.util.DoubleSummaryStatistics;

public class Statistics {

    private Long timestamp;

    private DoubleSummaryStatistics statistics;

    public Statistics() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public DoubleSummaryStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(DoubleSummaryStatistics statistics) {
        this.statistics = statistics;
    }
}
