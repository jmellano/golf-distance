package com.bettergolf.repository;

import com.bettergolf.domain.PlayerClubDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the PlayerClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerClubDistanceRepository extends JpaRepository<PlayerClubDistance, Long> {

    List<PlayerClubDistance> findByPlayerId(Long id);
}
