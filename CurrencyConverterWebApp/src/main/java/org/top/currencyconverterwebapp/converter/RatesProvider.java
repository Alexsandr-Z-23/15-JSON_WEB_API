package org.top.currencyconverterwebapp.converter;

import java.util.List;

// RatesProvider - сервис для получения курсов валют
public interface RatesProvider {
    // GetRates - получить курсы валют
    // вход: код базовой валюты (по отношению к которой будут получены курсы)
    // выход: список курсов валют по отношению к базовой
    List<ExchangeRate> getRates(String baseCurrency);
}
