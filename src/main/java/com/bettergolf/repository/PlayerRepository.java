package com.bettergolf.repository;

import com.bettergolf.domain.Player;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findOneByUserId(Long id);
}
