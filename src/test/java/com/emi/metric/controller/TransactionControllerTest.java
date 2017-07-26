package com.emi.metric.controller;

import com.emi.metric.exceptions.OlderTransactionException;
import com.emi.metric.model.Statistic;
import com.emi.metric.model.Transaction;
import com.emi.metric.service.TransactionService;
import com.emi.metric.util.MetricUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Emi on 25/07/2017.
 * Test cases to validate functionalities of TransactionController
 */
public class TransactionControllerTest {
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Before
    public void setup() {
        initMocks(this);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    public void shallAddOneValidTransaction() {
        Transaction transaction = new Transaction(14, MetricUtil.getInstance().getCurrentEpocTime());

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                return null;
            }
        }).when(transactionService).createTransaction(transaction);

        ResponseEntity<Void> response = transactionController.createTransaction(transaction);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(transactionService, times(1)).createTransaction(transaction);
    }

    @Test(expected = OlderTransactionException.class)
    public void shallAddOneOlderTimeStamp() {
        Transaction transaction = new Transaction(14, MetricUtil.getInstance().getCurrentEpocTime() - 80000);
        transactionController.createTransaction(transaction);
    }

    @Test
    public void shallGetStatistic() {
        Statistic stac = new Statistic(0, 0, 0, 0, 0);
        when(transactionService.getTransactionStatistic()).thenReturn(stac);

        ResponseEntity<Statistic> response = transactionController.getTransactionStatistics();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
