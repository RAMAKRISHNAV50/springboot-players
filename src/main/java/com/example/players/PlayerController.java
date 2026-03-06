package com.example.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController 
@RequestMapping("/players")
// FIX: Using originPatterns instead of origins to avoid the "allowCredentials" 500 error
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

    @PutMapping("/{id}")
    public Player update(@PathVariable Long id, @RequestBody Player p) {
        return repo.findById(id).map(existingPlayer -> {
            // Update Status and Auction Data
            existingPlayer.setStatus(p.getStatus());
            existingPlayer.setSoldPrice(p.getSoldPrice());
            existingPlayer.setBoughtBy(p.getBoughtBy());

            // IMPORTANT: Update New Career Stats if they are provided
            if (p.getOdiRuns() != null) existingPlayer.setOdiRuns(p.getOdiRuns());
            if (p.getT20Runs() != null) existingPlayer.setT20Runs(p.getT20Runs());
            if (p.getBattingAverage() != null) existingPlayer.setBattingAverage(p.getBattingAverage());
            if (p.getWickets() != null) existingPlayer.setWickets(p.getWickets());

            if ("SOLD".equalsIgnoreCase(p.getStatus())) {
                String token = UUID.randomUUID().toString();
                existingPlayer.setAccessToken(token);
                existingPlayer.setTokenExpiry(LocalDateTime.now().plusDays(1));

                // When you deploy, change this to your Vercel/Render URL
                String frontendUrl = "http://localhost:5173"; 
                
                System.out.println("========================================");
                System.out.println("NOTIFICATION SENT TO: " + existingPlayer.getMobile());
                System.out.println("LINK: " + frontendUrl + "/dashboard-player?token=" + token);
                System.out.println("STATUS: Link expires in 24 hours.");
                System.out.println("========================================");
            }

            return repo.save(existingPlayer);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}