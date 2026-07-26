package com.example.briefdrop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CountryResolver {

    public record CountryInfo(String countryCode, String currency) {}

    public CountryInfo resolve(HttpServletRequest request) {
        String country = null;

        String cfCountry = request.getHeader("CF-IPCountry");
        if (cfCountry != null && !cfCountry.isBlank()) {
            country = cfCountry.trim().toUpperCase();
        }

        String forwardedFor = request.getHeader("X-Forwarded-For");
        if ((country == null || country.isEmpty()) && forwardedFor != null && !forwardedFor.isBlank()) {
            country = resolveFromForwardedFor(forwardedFor);
        }

        if (country == null || country.isEmpty()) {
            country = request.getRemoteAddr() != null ? "XX" : "XX";
        }

        String currency = "TR".equals(country) ? "TRY" : "USD";

        return new CountryInfo(country, currency);
    }

    private String resolveFromForwardedFor(String forwardedFor) {
        // Simplified: in production wire to a GeoIP database (MaxMind)
        // For local dev with a Turkish IP range simulation:
        return null;
    }
}
