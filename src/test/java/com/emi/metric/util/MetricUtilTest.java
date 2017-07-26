package com.emi.metric.util;

import com.emi.metric.model.Transaction;
import org.junit.After;
import org.junit.Test;
import java.util.DoubleSummaryStatistics;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Emi on 25/07/2017.
 * Test cases to validate functionalities of MetricUtil
 */
public class MetricUtilTest {

    private static final double DELTA = 1e-15;
    private final MetricUtil streamMetric = MetricUtil.getInstance();

    @After
    public void teardown() {
        streamMetric.clearAllTransactions();
    }

    @Test
    public  void shallAddOneValidTransaction(){

        Transaction transaction = new Transaction(12.5,streamMetric.getCurrentEpocTime());
        streamMetric.addTransaction(transaction);

        DoubleSummaryStatistics stats =  streamMetric.getSummaryStatistics();
        assertNotNull(stats);
        assertEquals(transaction.getAmount(), stats.getSum(), DELTA);
        assertEquals(transaction.getAmount(), stats.getAverage(), DELTA);
        assertEquals(transaction.getAmount(), stats.getMin(), DELTA);
        assertEquals(transaction.getAmount(), stats.getMax(), DELTA);
        assertEquals(1, stats.getCount());
    }

    @Test
    public  void shallAddTwoValidTransactions(){
        Transaction transaction = new Transaction(12.5,streamMetric.getCurrentEpocTime());
        streamMetric.addTransaction(transaction);
        Transaction transaction2 = new Transaction(18.5,streamMetric.getCurrentEpocTime());
        streamMetric.addTransaction(transaction2);

        DoubleSummaryStatistics stats =  streamMetric.getSummaryStatistics();
        assertNotNull(stats);
        double sum = (transaction.getAmount() + transaction2.getAmount());
        assertEquals(sum, stats.getSum(), DELTA);
        assertEquals( sum/2, stats.getAverage(), DELTA);
        assertEquals(Math.min(transaction.getAmount(),transaction2.getAmount()), stats.getMin(), DELTA);
        assertEquals(Math.max(transaction.getAmount(),transaction2.getAmount()), stats.getMax(), DELTA);
        assertEquals(2, stats.getCount());
    }

    @Test
    public  void shallAddTwoValidTransactionsAndNotAddATransactionWhenTimestampIsOlder(){
        Transaction transaction = new Transaction(12.5,streamMetric.getCurrentEpocTime());
        streamMetric.addTransaction(transaction);
        Transaction transaction2 = new Transaction(18.5,streamMetric.getCurrentEpocTime());
        streamMetric.addTransaction(transaction2);
        Transaction transaction3 = new Transaction(32.5,streamMetric.getCurrentEpocTime()-80000);
        streamMetric.addTransaction(transaction3);

        DoubleSummaryStatistics stats =  streamMetric.getSummaryStatistics();
        assertNotNull(stats);
        double sum = (transaction.getAmount() + transaction2.getAmount());
        assertEquals(sum, stats.getSum(), DELTA);
        assertEquals( sum/2, stats.getAverage(), DELTA);
        assertEquals(Math.min(transaction.getAmount(),transaction2.getAmount()), stats.getMin(), DELTA);
        assertEquals(Math.max(transaction.getAmount(),transaction2.getAmount()), stats.getMax(), DELTA);
        assertEquals(2, stats.getCount());
    }


    @Test
    public  void shallNotAddTransactionWhenTimestampIsOlder(){
        Transaction transaction = new Transaction(12.5,streamMetric.getCurrentEpocTime()-80000);
        streamMetric.addTransaction(transaction);

        DoubleSummaryStatistics stats =  streamMetric.getSummaryStatistics();
        assertNotNull(stats);
        assertEquals(0, stats.getSum(), DELTA);
        assertEquals(0, stats.getAverage(), DELTA);
        assertEquals(0, stats.getCount());
    }

    @Test
    public void shallAValidTimestamp(){
        long currentTime = streamMetric.getCurrentEpocTime();
        assertTrue(streamMetric.isValidTimestamp(currentTime));
    }

    @Test
    public void shallNotAValidTimestamp(){
        long currentTime = streamMetric.getCurrentEpocTime() - 800000;
        assertFalse(streamMetric.isValidTimestamp(currentTime));
    }

    @Test
    public void shallSixtySecondsTimestampEarlier(){
        long sixtySecondsEarlier = streamMetric.getSixtySecondsEarlier();
        long currentTime = streamMetric.getCurrentEpocTime();
        assertTrue((currentTime - 60000) >= sixtySecondsEarlier);
    }
}
