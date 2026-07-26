package com.example.briefdrop.controller;

import com.example.briefdrop.entity.WaitlistLead;
import com.example.briefdrop.repository.WaitlistLeadRepository;
import com.example.briefdrop.service.CountryResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class LandingController {

    private final WaitlistLeadRepository waitlistLeadRepository;
    private final CountryResolver countryResolver;

    public LandingController(WaitlistLeadRepository waitlistLeadRepository, CountryResolver countryResolver) {
        this.waitlistLeadRepository = waitlistLeadRepository;
        this.countryResolver = countryResolver;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "currency", required = false) String overrideCurrency) {
        CountryResolver.CountryInfo info = countryResolver.resolve(request);

        String currency = (overrideCurrency != null && !overrideCurrency.isBlank())
                ? overrideCurrency.toUpperCase()
                : info.currency();

        model.addAttribute("currency", currency);
        model.addAttribute("countryCode", info.countryCode());
        model.addAttribute("pricing", buildPricing(currency));

        return "index";
    }

    private Map<String, Object> buildPricing(String currency) {
        Map<String, Object> pricing = new LinkedHashMap<>();

        if ("TRY".equals(currency)) {
            pricing.put("free", Map.of("price", "0", "period", "/ay", "symbol", "₺"));
            pricing.put("pro", Map.of("price", "149", "period", "/ay", "symbol", "₺"));
            pricing.put("ltd", Map.of("price", "899", "period", "tek seferlik", "symbol", "₺"));
        } else {
            pricing.put("free", Map.of("price", "$0", "period", "/mo", "symbol", "$"));
            pricing.put("pro", Map.of("price", "$9", "period", "/mo", "symbol", "$"));
            pricing.put("ltd", Map.of("price", "$49", "period", "one-time", "symbol", "$"));
        }

        return pricing;
    }

    @PostMapping("/api/waitlist")
    @ResponseBody
    public ResponseEntity<?> joinWaitlist(@Valid @RequestBody WaitlistRequest request,
                                          HttpServletRequest httpRequest) {
        if (waitlistLeadRepository.existsByEmail(request.email)) {
            return ResponseEntity.ok(Map.of(
                "status", "already_joined",
                "message", "This email is already on the waitlist!"
            ));
        }

        CountryResolver.CountryInfo info = countryResolver.resolve(httpRequest);

        String currency = (request.currency != null && !request.currency.isBlank())
                ? request.currency
                : info.currency();

        String countryCode = info.countryCode();

        WaitlistLead lead = new WaitlistLead(
            request.email,
            request.plan,
            request.utmSource,
            countryCode,
            currency
        );
        waitlistLeadRepository.save(lead);

        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Great! You're on the priority list. We'll send you a 50% off access link for your selected plan when it's your turn."
        ));
    }

    public record WaitlistRequest(
        @NotBlank @Email String email,
        @NotBlank String plan,
        String utmSource,
        String currency
    ) {}
}
