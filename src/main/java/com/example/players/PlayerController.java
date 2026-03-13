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

    // NEW: Real-time bidding endpoint
    @PostMapping("/{id}/bid")
    public Player placeBid(@PathVariable Long id, @RequestParam Double amount, @RequestParam String bidderName) {
        return repo.findById(id).map(p -> {
            // Temporarily storing current bid in soldPrice and bidder in boughtBy for the Live Room
            p.setSoldPrice(amount); 
            p.setBoughtBy(bidderName);
            return repo.save(p);
        }).orElse(null);
    }

    // NEW: Finalize endpoint to officially set status to SOLD and trigger token
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

    @PutMapping("/{id}")
    public Player update(@PathVariable Long id, @RequestBody Player p) {
        return repo.findById(id).map(existingPlayer -> {
            existingPlayer.setStatus(p.getStatus());
            existingPlayer.setSoldPrice(p.getSoldPrice());
            existingPlayer.setBoughtBy(p.getBoughtBy());

            if (p.getOdiRuns() != null) existingPlayer.setOdiRuns(p.getOdiRuns());
            if (p.getT20Runs() != null) existingPlayer.setT20Runs(p.getT20Runs());
            if (p.getBattingAverage() != null) existingPlayer.setBattingAverage(p.getBattingAverage());
            if (p.getWickets() != null) existingPlayer.setWickets(p.getWickets());

            // Original Sold logic for updates via standard PUT
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