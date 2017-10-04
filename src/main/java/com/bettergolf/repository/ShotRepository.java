package com.bettergolf.repository;

import com.bettergolf.domain.Shot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {

}
