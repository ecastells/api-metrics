package com.emi.metric.service;

import com.emi.metric.model.Transaction;
import com.emi.metric.model.Statistic;

/**
 * Created by Emi on 24/07/2017.
 * Declare two methods for add transaction and get the statistic
 */
public interface TransactionService {
    void createTransaction(Transaction transaction);

    Statistic getTransactionStatistic();
}
