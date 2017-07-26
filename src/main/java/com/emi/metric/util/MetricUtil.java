package com.emi.metric.util;

import com.emi.metric.model.Transaction;
import com.google.common.annotations.VisibleForTesting;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by Emi on 24/07/2017.
 */
public final class MetricUtil {

    private final Collection<Transaction> transactions = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static class MetricLoader {
        private MetricLoader(){}

        private static final MetricUtil INSTANCE = new MetricUtil();
    }

    private MetricUtil() {
        if (MetricLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    @VisibleForTesting
    protected void clearAllTransactions(){
        transactions.clear();
    }

    public static MetricUtil getInstance() {
        return MetricLoader.INSTANCE;
    }

    public void addTransaction(Transaction transaction){
        lock.writeLock().lock();
        try {
            transactions.add(transaction);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeOlderTransaction(){
        lock.writeLock().lock();
        try {
            transactions.removeIf(t -> !MetricUtil.getInstance().isValidTimestamp(t.getTimestamp()));
        } finally {
             lock.writeLock().unlock();
        }
    }


    public DoubleSummaryStatistics getSummaryStatistics(){
        lock.readLock().lock();
        try {
            return transactions.stream().filter(t -> t.getTimestamp() > MetricUtil.getInstance().getSixtySecondsEarlier() && t.getTimestamp() <= MetricUtil.getInstance().getCurrentEpocTime())
                .mapToDouble(Transaction::getAmount).summaryStatistics();
        } finally {
            lock.readLock().unlock();
        }
    }

    public synchronized long getCurrentEpocTime(){
        return Instant.now().toEpochMilli();
    }

    public synchronized boolean isValidTimestamp(final long timeStamp){
        return ((getCurrentEpocTime() - timeStamp) / 1000) > 60 ? false : true;
    }

    public synchronized long getSixtySecondsEarlier(){
        return (getCurrentEpocTime() - 60000);
    }
}
