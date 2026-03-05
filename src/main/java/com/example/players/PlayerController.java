package com.example.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController 
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:5173") 
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

    // NEW: Endpoint for Player Dashboard to fetch data via the unique link
    @GetMapping("/access/{token}")
    public Player getByToken(@PathVariable String token) {
        Player p = repo.findByAccessToken(token);
        // Returns player only if token exists and hasn't expired (24h)
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
            // Update standard info
            existingPlayer.setStatus(p.getStatus());
            existingPlayer.setSoldPrice(p.getSoldPrice());
            existingPlayer.setBoughtBy(p.getBoughtBy());

            // LOGIC: If the status was just changed to 'SOLD', generate the expiring link
            if ("SOLD".equalsIgnoreCase(p.getStatus())) {
                String token = UUID.randomUUID().toString();
                existingPlayer.setAccessToken(token);
                existingPlayer.setTokenExpiry(LocalDateTime.now().plusDays(1));

                // Simulation of sending a message to the player's mobile
                System.out.println("========================================");
                System.out.println("NOTIFICATION SENT TO: " + existingPlayer.getMobile());
                System.out.println("LINK: http://localhost:5173/player-dashboard?token=" + token);
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