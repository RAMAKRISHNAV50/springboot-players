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
    private String status;   // "AVAILABLE" or "SOLD"
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

    public boolean isLinkValid() {
        return accessToken != null && 
               tokenExpiry != null && 
               tokenExpiry.isAfter(LocalDateTime.now());
    }
}