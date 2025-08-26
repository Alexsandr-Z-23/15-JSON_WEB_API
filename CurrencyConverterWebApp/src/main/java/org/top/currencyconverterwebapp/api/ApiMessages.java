package org.top.currencyconverterwebapp.api;

public class ApiMessages {

    // StringMessage - строковое сообщение
    public record StringMessage(String message) {}

    // DataToConvertMessage - данные для конвертации значения между двумя валютами
    public record DataToConvertMessage(String from, String to, Double value) {}

    // ConvertResultMessage = результат конвертации
    public record ConvertResultMessage(Double result, DataToConvertMessage arg) {}

    // ErrorMessage - сообщение об ошибке
    public record ErrorMessage(String type, String message) {}
}
