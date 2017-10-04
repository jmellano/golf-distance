package com.bettergolf.repository;

import com.bettergolf.domain.PlayerClub;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlayerClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerClubRepository extends JpaRepository<PlayerClub, Long> {

}
