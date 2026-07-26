DROP TABLE IF EXISTS waitlist_entries;

CREATE TABLE waitlist_leads (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    selected_plan VARCHAR(50) NOT NULL,
    utm_source VARCHAR(100),
    country_code VARCHAR(5),
    currency VARCHAR(5),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_waitlist_leads_email UNIQUE (email)
);

CREATE INDEX IF NOT EXISTS idx_waitlist_leads_created_at ON waitlist_leads(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_waitlist_leads_country ON waitlist_leads(country_code);
CREATE INDEX IF NOT EXISTS idx_waitlist_leads_plan ON waitlist_leads(selected_plan);
