package org.top.currencyconverterwebapp.converter;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(double value) {
        super("value '" + value + "' is invalid");
    }
}
