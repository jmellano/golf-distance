package com.bettergolf.repository;

import com.bettergolf.domain.Referentiel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Referentiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferentielRepository extends JpaRepository<Referentiel, Long> {

}
