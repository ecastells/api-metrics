package com.emi.metric.service;

import com.emi.metric.model.Transaction;
import com.emi.metric.model.Statistic;
import com.emi.metric.util.MetricUtil;
import org.springframework.stereotype.Service;
import java.util.DoubleSummaryStatistics;


/**
 * Created by Emi on 24/07/2017.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final MetricUtil streamMetric = MetricUtil.getInstance();

    @Override
    public void createTransaction(Transaction transaction) {
        streamMetric.addTransaction(transaction);
        Runnable task2 = () -> streamMetric.removeOlderTransaction();
        new Thread(task2).start();
    }

    @Override
    public synchronized Statistic getTransactionStatistic() {
        DoubleSummaryStatistics stats = streamMetric.getSummaryStatistics();
        return new Statistic(stats.getSum(), stats.getAverage(),
                (stats.getMax() <= Double.NEGATIVE_INFINITY) ? 0 : stats.getMax(),
                (stats.getMin() >= Double.POSITIVE_INFINITY) ? 0 : stats.getMin(), stats.getCount());
    }


}
