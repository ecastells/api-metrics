package com.emi.metric.exceptions;

/**
 * Created by Emi on 24/07/2017.
 */
public class OlderTransactionException extends GenericApplicationException {

    public OlderTransactionException() {
        super();
    }

    public OlderTransactionException(String message) {
        super(message);
    }
}
