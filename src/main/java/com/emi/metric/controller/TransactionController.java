package com.emi.metric.controller;

import com.emi.metric.exceptions.OlderTransactionException;
import com.emi.metric.model.Transaction;
import com.emi.metric.model.Statistic;
import com.emi.metric.service.TransactionService;
import com.emi.metric.util.MetricUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.HttpURLConnection;

/**
 * Created by Emi on 24/07/2017.
 * Provide a Rest Operation Calculate realtime statistic from the last 60 seconds
 * POST /metric/transactions - Add a new Transaction
 * GET /metric/statistics - Returns the statistic based of the transactions of the last 60 seconds
 */
@RestController
@RequestMapping("/metric")
@Api("Calculate realtime statistic from the last 60 seconds")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Add a new Transaction")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Transaction Added"),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Older Transaction"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal Server Problems")
    })
    @RequestMapping(value="/transactions", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody @ApiParam(value = "Transaction to add",required = true) Transaction transaction) {
        if (transaction == null || !MetricUtil.getInstance().isValidTimestamp(transaction.getTimestamp())){
            throw new OlderTransactionException();
        }
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation(value = "Returns the statistic based of the transactions of the last 60 seconds")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Return Statistic"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server problems")
    })
    @RequestMapping(value="/statistics", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Statistic> getTransactionStatistics() {
        Statistic transactionStatistic = transactionService.getTransactionStatistic();
        return new ResponseEntity<>(transactionStatistic, HttpStatus.OK);
    }
}
