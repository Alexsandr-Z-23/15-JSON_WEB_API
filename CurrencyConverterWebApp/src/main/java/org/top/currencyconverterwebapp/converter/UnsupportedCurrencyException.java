package org.top.currencyconverterwebapp.converter;

public class UnsupportedCurrencyException extends RuntimeException {
    public UnsupportedCurrencyException(String currencyCode) {
        super("currency '" + currencyCode + "' is unsupported");
    }
}
