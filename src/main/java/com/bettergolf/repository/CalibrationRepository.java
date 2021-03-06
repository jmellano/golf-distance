package com.bettergolf.repository;

import com.bettergolf.domain.Calibration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data JPA repository for the Calibration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalibrationRepository extends JpaRepository<Calibration, Long> {

    List<Calibration> findAllByPlayerClubId(Long playerClubId);

    Calibration getOneByPlayerClubId(Long id);

    Calibration getOneByPlayerClubIdAndForce(Long id, BigDecimal force);
}
