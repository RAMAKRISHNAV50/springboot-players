package com.example.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
@CrossOrigin(originPatterns = "*")
public class PlayerController {

    @Autowired
    private PlayerRepository repo;

    @PostMapping
    public Player create(@RequestBody Player p) {
        return repo.save(p);
    }

    @GetMapping
    public List<Player> getAll() {
        return repo.findAll();
    }

    @GetMapping("/access/{token}")
    public Player getByToken(@PathVariable String token) {
        Player p = repo.findByAccessToken(token);
        if (p != null && p.isLinkValid()) {
            return p;
        }
        return null;
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    // Real-time bidding endpoint (legacy — kept for compatibility)
    @PostMapping("/{id}/bid")
    public Player placeBid(@PathVariable Long id, @RequestParam Double amount, @RequestParam String bidderName) {
        return repo.findById(id).map(p -> {
            p.setSoldPrice(amount);
            p.setBoughtBy(bidderName);
            return repo.save(p);
        }).orElse(null);
    }

    // Finalize endpoint — officially sets status to SOLD and generates access token
    @PostMapping("/{id}/finalize")
    public Player finalizeSale(@PathVariable Long id) {
        return repo.findById(id).map(p -> {
            p.setStatus("SOLD");

            // Generate Access Token for Player Dashboard Login
            if (p.getAccessToken() == null) {
                String token = UUID.randomUUID().toString();
                p.setAccessToken(token);
                p.setTokenExpiry(LocalDateTime.now().plusDays(1));
            }

            System.out.println("AUCTION FINALIZED: " + p.getName() + " sold to " + p.getBoughtBy());
            return repo.save(p);
        }).orElse(null);
    }

    // ── MAIN UPDATE ENDPOINT ──────────────────────────────────────────────────
    // Used by AdminDashboard and BidRoom for all state changes.
    // KEY FIX: now persists bidHistory and sessionPasskeys so that:
    //   - BidRoom on any device can read bids and validate passkeys from the DB
    //   - AdminDashboard polls bidHistory every 2s to show live bids
    @PutMapping("/{id}")
    public Player update(@PathVariable Long id, @RequestBody Player p) {
        return repo.findById(id).map(existingPlayer -> {

            // ── Core auction fields ───────────────────────────────────────────
            existingPlayer.setStatus(p.getStatus());
            existingPlayer.setSoldPrice(p.getSoldPrice());
            existingPlayer.setBoughtBy(p.getBoughtBy());

            // ── NEW: Bid history (JSON string) — written by BidRoom each bid ─
            // Only update if the incoming value is non-null so we never
            // accidentally wipe bids with an incomplete PUT body.
            if (p.getBidHistory() != null) {
                existingPlayer.setBidHistory(p.getBidHistory());
            }

            // ── NEW: Session passkeys (JSON string) — written by Admin on start
            // Only update if non-null for the same safety reason above.
            if (p.getSessionPasskeys() != null) {
                existingPlayer.setSessionPasskeys(p.getSessionPasskeys());
            }

            // ── Performance stats ─────────────────────────────────────────────
            if (p.getOdiRuns()        != null) existingPlayer.setOdiRuns(p.getOdiRuns());
            if (p.getT20Runs()        != null) existingPlayer.setT20Runs(p.getT20Runs());
            if (p.getBattingAverage() != null) existingPlayer.setBattingAverage(p.getBattingAverage());
            if (p.getWickets()        != null) existingPlayer.setWickets(p.getWickets());

            // ── Access token for sold players ─────────────────────────────────
            if ("SOLD".equalsIgnoreCase(p.getStatus()) && existingPlayer.getAccessToken() == null) {
                String token = UUID.randomUUID().toString();
                existingPlayer.setAccessToken(token);
                existingPlayer.setTokenExpiry(LocalDateTime.now().plusDays(1));
            }

            return repo.save(existingPlayer);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}