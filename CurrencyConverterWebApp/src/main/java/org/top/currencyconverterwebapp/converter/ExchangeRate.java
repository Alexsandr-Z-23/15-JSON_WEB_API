package org.top.currencyconverterwebapp.converter;

// ExchangeRate - курс валюты по отношению к некоторой валюте
// code - код валюты
// rate - коэффициент курса
public record ExchangeRate(String code, double rate) {
}
