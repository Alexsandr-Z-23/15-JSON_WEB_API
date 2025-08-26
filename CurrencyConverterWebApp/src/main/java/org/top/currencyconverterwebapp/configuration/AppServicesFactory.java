package org.top.currencyconverterwebapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.top.currencyconverterwebapp.converter.CurrencyConverter;
import org.top.currencyconverterwebapp.converter.RatesProvider;
import org.top.currencyconverterwebapp.external.ExternalApiRatesProvider;
import org.top.currencyconverterwebapp.simple.SimpleCurrencyConverter;
import org.top.currencyconverterwebapp.simple.SimpleRatesProvider;

@Configuration
@ConfigurationProperties(prefix = "currency.converter")
public class AppServicesFactory {

    private String providerType = "simple";
    private String externalApiUrl = "https://api.exchangerate-api.com/v4/latest/{base}";

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getExternalApiUrl() {
        return externalApiUrl;
    }

    public void setExternalApiUrl(String externalApiUrl) {
        this.externalApiUrl = externalApiUrl;
    }

    @Bean
    public RatesProvider ratesProvider() {
        return switch (providerType.toLowerCase()) {
            case "external" -> new ExternalApiRatesProvider(externalApiUrl);
            case "simple" -> new SimpleRatesProvider();
            default -> throw new IllegalArgumentException("Unknown provider type: " + providerType);
        };
    }

    @Bean
    public CurrencyConverter currencyConverter(RatesProvider ratesProvider) {
        return new SimpleCurrencyConverter(ratesProvider);
    }
}