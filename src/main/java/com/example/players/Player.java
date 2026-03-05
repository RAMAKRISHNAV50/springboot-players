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

    // Basic Info from Form
    private String name;
    private String email;
    private String password;
    private String country;
    private String position;
    private Integer age;
    private String mobile;
    
    // Performance & Money
    private Double basicRemuneration; // Base Price
    private String averagePerformance;

    // Auction Status Logic
    private String status;   // Will be "AVAILABLE" or "SOLD"
    private Double soldPrice;
    private String boughtBy; // Name of the Franchise

    // --- NEW FIELDS FOR EXPIRING LINK ---
    
    @Column(unique = true)
    private String accessToken; // The unique ID in the SMS link

    private LocalDateTime tokenExpiry; // When the link dies

    /**
     * Helper method to check if the link is still valid
     * @return true if token is present and not expired
     */
    public boolean isLinkValid() {
        return accessToken != null && 
               tokenExpiry != null && 
               tokenExpiry.isAfter(LocalDateTime.now());
    }
}