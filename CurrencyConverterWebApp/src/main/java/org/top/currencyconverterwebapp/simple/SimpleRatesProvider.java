package org.top.currencyconverterwebapp.simple;

import org.top.currencyconverterwebapp.converter.ExchangeRate;
import org.top.currencyconverterwebapp.converter.RatesProvider;

import java.util.List;

// SimpleRatesProvider - простая реализация провайдера валют с "захардкоженными" коэффициентами по отношению к рублю
public class SimpleRatesProvider implements RatesProvider {
    @Override
    public List<ExchangeRate> getRates(String baseCurrency) {
        return List.of(
            new ExchangeRate("RUB", 1),
            new ExchangeRate("USD", 78.1),
            new ExchangeRate("EUR", 91.26),
            new ExchangeRate("KZT", 0.15)
        );
    }
}
