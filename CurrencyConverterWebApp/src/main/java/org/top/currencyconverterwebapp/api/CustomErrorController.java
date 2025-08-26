package org.top.currencyconverterwebapp.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ApiMessages.ErrorMessage> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");

        String errorType = "UnknownError";
        String message = "An unexpected error occurred";

        if (exception != null) {
            errorType = exception.getClass().getSimpleName();
            message = exception.getMessage();
        } else if (statusCode != null) {
            errorType = HttpStatus.valueOf(statusCode).getReasonPhrase();
            message = "HTTP Error " + statusCode;
        }

        ApiMessages.ErrorMessage errorMessage = new ApiMessages.ErrorMessage(errorType, message);
        return ResponseEntity.status(statusCode != null ? statusCode : 500).body(errorMessage);
    }
}