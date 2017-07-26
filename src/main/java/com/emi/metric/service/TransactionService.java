package com.emi.metric.service;

import com.emi.metric.model.Transaction;
import com.emi.metric.model.Statistic;

/**
 * Created by Emi on 24/07/2017.
 */
public interface TransactionService {
    void createTransaction(Transaction transaction);

    Statistic getTransactionStatistic();
}
