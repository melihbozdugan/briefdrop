package com.example.briefdrop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CountryResolver {

    public record CountryInfo(String countryCode, String currency) {}

    public CountryInfo resolve(HttpServletRequest request) {
        String cfCountry = request.getHeader("CF-IPCountry");
        String country = (cfCountry != null && !cfCountry.isBlank())
                ? cfCountry.trim().toUpperCase()
                : "XX";

        String currency = "TR".equals(country) ? "TRY" : "USD";

        return new CountryInfo(country, currency);
    }
}
