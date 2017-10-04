package com.bettergolf.repository;

import com.bettergolf.domain.TypeReferentiel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeReferentiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeReferentielRepository extends JpaRepository<TypeReferentiel, Long> {

}
