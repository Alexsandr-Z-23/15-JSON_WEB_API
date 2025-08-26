package org.top.currencyconverterwebapp.simple;

import org.top.currencyconverterwebapp.converter.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SimpleCurrencyConverter - простой конвертер валют
public class SimpleCurrencyConverter implements CurrencyConverter {

    private final Map<String, Double> rates;

    public SimpleCurrencyConverter(RatesProvider provider) {
        List<ExchangeRate> exchangeRates = provider.getRates("RUB");
        rates = new HashMap<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            rates.put(exchangeRate.code(), exchangeRate.rate());
        }
    }

    @Override
    public List<String> supportedCurrencies() {
        return rates.keySet().stream().toList();
    }

    @Override
    public double convert(String from, String to, double value) {
        if (value < 0) {
            throw new InvalidValueException(value);
        }
        if (!rates.containsKey(from)) {
            throw new UnsupportedCurrencyException(from);
        }
        if (!rates.containsKey(to)) {
            throw new UnsupportedCurrencyException(to);
        }
        return value * rates.get(from) / rates.get(to);
    }
}
