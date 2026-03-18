package com.example.players;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String country;
    private String position;
    private Integer age;
    private String mobile;

    // Performance & Money
    private Double basicRemuneration;
    private String status;   // "AVAILABLE", "SOLD", "UNSOLD"
    private Double soldPrice;
    private String boughtBy;

    // --- NEW PERFORMANCE FIELDS ---
    private Integer odiRuns;
    private Integer t20Runs;
    private Double battingAverage;
    private Integer wickets;

    // --- EXPIRING LINK FIELDS ---
    @Column(unique = true)
    private String accessToken;
    private LocalDateTime tokenExpiry;

    // ── NEW: Auction live bidding fields ─────────────────────────────────────
    // Stores JSON array of all bids placed during the active auction round.
    // Example: [{"franchiseId":"3","franchiseName":"Mumbai Indians","amount":500000,"timestamp":1234567890}]
    // Reset to "[]" when a new auction round starts or player is finalized.
    @Column(columnDefinition = "TEXT")
    private String bidHistory;

    // Stores JSON map of franchiseId → passkey for the active auction session.
    // Example: {"1":"abc123","2":"xyz456"}
    // This is written by Admin when auction starts, read by BidRoom to validate access.
    // Stored in DB (not localStorage) so any browser/device can validate.
    @Column(columnDefinition = "TEXT")
    private String sessionPasskeys;
    // ─────────────────────────────────────────────────────────────────────────

    public boolean isLinkValid() {
        return accessToken != null &&
                tokenExpiry != null &&
                tokenExpiry.isAfter(LocalDateTime.now());
    }
}