package com.bettergolf.repository;

import com.bettergolf.domain.Calibration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Calibration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalibrationRepository extends JpaRepository<Calibration, Long> {

}
