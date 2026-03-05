package com.example.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Custom query to find player by the token generated during the sale
    Player findByAccessToken(String accessToken);
}