package org.top.currencyconverterwebapp.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.top.currencyconverterwebapp.converter.ExchangeRate;
import org.top.currencyconverterwebapp.converter.RatesProvider;

import java.util.List;
import java.util.Map;

public class ExternalApiRatesProvider implements RatesProvider {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ExternalApiRatesProvider(String apiUrl) {
        this.restTemplate = new RestTemplate();
        this.apiUrl = apiUrl;
    }

    @Override
    public List<ExchangeRate> getRates(String baseCurrency) {
        try {
            String url = apiUrl.replace("{base}", baseCurrency);
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Double> rates = (Map<String, Double>) responseBody.get("rates");

                return rates.entrySet().stream()
                        .map(entry -> new ExchangeRate(entry.getKey(), entry.getValue()))
                        .toList();
            }
        } catch (Exception e) {
            System.err.println("Error fetching rates from external API: " + e.getMessage());
        }

        // Fallback to default rates if API fails
        return List.of(
                new ExchangeRate("RUB", 1.0),
                new ExchangeRate("USD", 0.0128),
                new ExchangeRate("EUR", 0.0110),
                new ExchangeRate("KZT", 6.67)
        );
    }
}