# BriefDrop

Effortless client content collection for web designers, developers, and small agencies. Send a single magic link — get logos, copy, images, and credentials back automatically. No more WhatsApp chaos.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3.4, Java 17 |
| Frontend | Thymeleaf + Tailwind CSS (CDN) |
| Database | PostgreSQL (Flyway migrations) |
| Auth | Magic Link (passwordless) |
| Storage | AWS S3 / MinIO |
| Analytics | Google Analytics 4, Microsoft Clarity |

## Quick Start

### Prerequisites

- Java 17+
- PostgreSQL 15+ (local or remote)
- Gradle (wrapper included)

### Setup

```bash
# 1. Clone the repo
git clone https://github.com/YOUR_USER/briefdrop.git
cd briefdrop

# 2. Create the database
psql -U postgres -c "CREATE DATABASE briefdrop;"

# 3. Set the database password
# Option A: Environment variable
export BRIEFDROP_DB_PASSWORD=your_password_here

# Option B: Create application-local.properties (gitignored)
cp src/main/resources/application-local.properties.example \
   src/main/resources/application-local.properties
# Then edit the file with your real password and run:
./gradlew bootRun --args='--spring.profiles.active=local'

# 4. Run the app
./gradlew bootRun
```

App starts at `http://localhost:8080`

### Language Support

| URL | Language |
|-----|----------|
| `/` | English (default) |
| `/?lang=tr` | Turkish |

### Regional Pricing (PPP)

- **TR users** see TRY prices (149 TL/mo, 899 TL one-time)
- **Global users** see USD prices ($9/mo, $49 one-time)  
- Manual currency toggle in navbar: `$` / `₺`
- Detection via `CF-IPCountry` header (Cloudflare)

## Project Structure

```
src/main/
├── java/com/example/briefdrop/
│   ├── BriefDropApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── WebConfig.java
│   ├── controller/
│   │   └── LandingController.java
│   ├── entity/
│   │   └── WaitlistLead.java
│   ├── repository/
│   │   └── WaitlistLeadRepository.java
│   └── service/
│       └── CountryResolver.java
└── resources/
    ├── application.properties
    ├── application-local.properties.example
    ├── db/migration/
    │   ├── V1__create_waitlist_entries.sql
    │   └── V2__create_waitlist_leads.sql
    ├── messages.properties        (EN)
    ├── messages_en.properties     (EN)
    ├── messages_tr.properties     (TR)
    └── templates/
        └── index.html
```

## Environment Variables

| Variable | Required | Description |
|----------|----------|-------------|
| `BRIEFDROP_DB_PASSWORD` | Yes | PostgreSQL password |
| `GA4_MEASUREMENT_ID` | No | Google Analytics 4 ID |
| `CLARITY_PROJECT_ID` | No | Microsoft Clarity ID |

## Deployment

Build the JAR:

```bash
./gradlew bootJar
java -jar build/libs/BriefDrop-*.jar
```

## License

MIT
