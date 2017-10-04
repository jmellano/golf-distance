package com.bettergolf.repository;

import com.bettergolf.domain.ParametreMetier;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ParametreMetier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametreMetierRepository extends JpaRepository<ParametreMetier, Long> {

}
