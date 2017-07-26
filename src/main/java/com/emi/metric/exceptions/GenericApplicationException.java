package com.emi.metric.exceptions;

/**
 * Created by Emi on 24/07/2017.
 */
public abstract class GenericApplicationException extends RuntimeException {

    public GenericApplicationException() {
        super();
    }

    public GenericApplicationException(String message) {
        super(message);
    }
}
