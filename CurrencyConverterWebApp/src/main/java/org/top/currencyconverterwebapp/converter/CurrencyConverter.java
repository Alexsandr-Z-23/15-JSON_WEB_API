package org.top.currencyconverterwebapp.converter;

import java.util.List;

// CurrencyConverter - конвертер валют
public interface CurrencyConverter {
    // supportedCurrencies - метод получения поддерживаемых для конвертации валют
    // вход: -
    // выход: массив с кодами валют
    // исключения: -
    List<String> supportedCurrencies();

    // convert - метод конвертации значения одной валюты в другую
    // вход:
    //  - from - код исходной валюты
    //  - to - код целевой валюты
    //  - value - значение для конвертации
    // выход: сконвертированное значение
    // исключения: UnsupportedCurrencyException, InvalidValueException
    double convert(String from, String to, double value);
}
