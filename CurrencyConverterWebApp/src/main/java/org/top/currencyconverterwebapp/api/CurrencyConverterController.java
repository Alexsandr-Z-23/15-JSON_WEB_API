package org.top.currencyconverterwebapp.api;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.top.currencyconverterwebapp.converter.CurrencyConverter;
import org.top.currencyconverterwebapp.converter.InvalidValueException;
import org.top.currencyconverterwebapp.converter.UnsupportedCurrencyException;
import org.top.currencyconverterwebapp.api.ApiMessages.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("api/currencies")
public class CurrencyConverterController {

    private final CurrencyConverter converter;

    public CurrencyConverterController(CurrencyConverter converter) {
        this.converter = converter;
    }

    @GetMapping
    public List<String> supportedCurrencies() {
        return converter.supportedCurrencies();
    }

    /*----------------------------*/
    /* ВАРИАНТ С ExceptionHandler */
    /*----------------------------*/

    @PostMapping("convert")
    public ConvertResultMessage convert(@RequestBody DataToConvertMessage data) {
        if (data.from() == null || data.from().isEmpty()) {
            throw new EmptyRequestDataException("from");
        }
        if (data.to() == null || data.to().isEmpty()) {
            throw new EmptyRequestDataException("to");
        }
        if (data.value() == null) {
            throw new EmptyRequestDataException("value");
        }
        double result = converter.convert(data.from(), data.to(), data.value());
        return new ConvertResultMessage(result, data);
    }

    @ExceptionHandler(EmptyRequestDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleEmptyRequestDataException(EmptyRequestDataException ex) {
        return new ErrorMessage(ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidValueException(InvalidValueException ex) {
        return new ErrorMessage(ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleUnsupportedCurrencyException(UnsupportedCurrencyException ex) {
        return new ErrorMessage(ex.getClass().getSimpleName(), ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiMessages.ErrorMessage handleGlobalException(Exception ex) {
        return new ApiMessages.ErrorMessage(
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }

    /*--------------------------*/
    /* ВАРИАНТ С ResponseEntity */
    /*--------------------------*/

//    @PostMapping("convert")
//    public ResponseEntity<?> convert(@RequestBody DataToConvertMessage data) {
//        if (data.from() == null || data.from().isEmpty()) {
//            // 400
//            ErrorMessage error = new ErrorMessage("EmptyString", "'from' is null or empty");
//            return ResponseEntity.badRequest().body(error);
//        }
//        if (data.to() == null || data.to().isEmpty()) {
//            // 400
//            ErrorMessage error = new ErrorMessage("EmptyString", "'to' is null or empty");
//            return ResponseEntity.badRequest().body(error);
//        }
//        if (data.value() == null) {
//            // 400
//            ErrorMessage error = new ErrorMessage("EmptyValue", "'value' is null");
//            return ResponseEntity.badRequest().body(error);
//        }
//
//        try {
//            double result = converter.convert(data.from(), data.to(), data.value());
//            // 200
//            ConvertResultMessage resultMessage = new ConvertResultMessage(result, data);
//            return ResponseEntity.ok(resultMessage);
//        } catch (InvalidValueException ex) {
//            // 400
//            ErrorMessage error = new ErrorMessage(ex.getClass().getSimpleName(), ex.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        } catch (UnsupportedCurrencyException ex) {
//            // 404
//            ErrorMessage error = new ErrorMessage(ex.getClass().getSimpleName(), ex.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }
//    }
}
