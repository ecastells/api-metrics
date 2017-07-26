package com.emi.metric.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * Created by Emi on 24/07/2017.
 * Transaction object used for add transactions
 */
public class Transaction {

    private double amount;
    @Min(0)
    private long timestamp;

    public Transaction() {
        super();
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @ApiModelProperty(required = true, example = "17.5", position = 0)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @ApiModelProperty(required = true, example = "1500948775677", position = 1)
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
