package com.emi.metric.exceptions;

/**
 * Created by Emi on 24/07/2017.
 * Exception throws when the transaction is older than 60 seconds
 */
public class OlderTransactionException extends RuntimeException {

    public OlderTransactionException() {
        super();
    }

    public OlderTransactionException(String message) {
        super(message);
    }
}
