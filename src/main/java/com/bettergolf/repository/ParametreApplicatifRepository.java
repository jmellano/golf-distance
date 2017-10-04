package com.bettergolf.repository;

import com.bettergolf.domain.ParametreApplicatif;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ParametreApplicatif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametreApplicatifRepository extends JpaRepository<ParametreApplicatif, Long> {

}
