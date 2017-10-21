package com.bettergolf.repository;

import com.bettergolf.domain.Shot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data JPA repository for the Shot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {

    List<Shot> findAllByPlayerClubId(Long id);

    List<Shot> findAllByPlayerClubIdAndForce(Long id, BigDecimal force);
}
