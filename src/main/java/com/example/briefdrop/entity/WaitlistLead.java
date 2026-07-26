package com.example.briefdrop.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "waitlist_leads")
public class WaitlistLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "selected_plan", nullable = false, length = 50)
    private String selectedPlan;

    @Column(name = "utm_source", length = 100)
    private String utmSource;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(length = 5)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public WaitlistLead() {}

    public WaitlistLead(String email, String selectedPlan, String utmSource, String countryCode, String currency) {
        this.email = email;
        this.selectedPlan = selectedPlan;
        this.utmSource = utmSource;
        this.countryCode = countryCode;
        this.currency = currency;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSelectedPlan() { return selectedPlan; }
    public void setSelectedPlan(String selectedPlan) { this.selectedPlan = selectedPlan; }
    public String getUtmSource() { return utmSource; }
    public void setUtmSource(String utmSource) { this.utmSource = utmSource; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
