package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.Referentiel;

import com.bettergolf.repository.ReferentielRepository;
import com.bettergolf.web.rest.util.HeaderUtil;
import com.bettergolf.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Referentiel.
 */
@RestController
@RequestMapping("/api")
public class ReferentielResource {

    private final Logger log = LoggerFactory.getLogger(ReferentielResource.class);

    private static final String ENTITY_NAME = "referentiel";

    private final ReferentielRepository referentielRepository;

    public ReferentielResource(ReferentielRepository referentielRepository) {
        this.referentielRepository = referentielRepository;
    }

    /**
     * POST  /referentiels : Create a new referentiel.
     *
     * @param referentiel the referentiel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referentiel, or with status 400 (Bad Request) if the referentiel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/referentiels")
    @Timed
    public ResponseEntity<Referentiel> createReferentiel(@Valid @RequestBody Referentiel referentiel) throws URISyntaxException {
        log.debug("REST request to save Referentiel : {}", referentiel);
        if (referentiel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new referentiel cannot already have an ID")).body(null);
        }
        Referentiel result = referentielRepository.save(referentiel);
        return ResponseEntity.created(new URI("/api/referentiels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /referentiels : Updates an existing referentiel.
     *
     * @param referentiel the referentiel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referentiel,
     * or with status 400 (Bad Request) if the referentiel is not valid,
     * or with status 500 (Internal Server Error) if the referentiel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/referentiels")
    @Timed
    public ResponseEntity<Referentiel> updateReferentiel(@Valid @RequestBody Referentiel referentiel) throws URISyntaxException {
        log.debug("REST request to update Referentiel : {}", referentiel);
        if (referentiel.getId() == null) {
            return createReferentiel(referentiel);
        }
        Referentiel result = referentielRepository.save(referentiel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referentiel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /referentiels : get all the referentiels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of referentiels in body
     */
    @GetMapping("/referentiels")
    @Timed
    public ResponseEntity<List<Referentiel>> getAllReferentiels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Referentiels");
        Page<Referentiel> page = referentielRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/referentiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /referentiels/:id : get the "id" referentiel.
     *
     * @param id the id of the referentiel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referentiel, or with status 404 (Not Found)
     */
    @GetMapping("/referentiels/{id}")
    @Timed
    public ResponseEntity<Referentiel> getReferentiel(@PathVariable Long id) {
        log.debug("REST request to get Referentiel : {}", id);
        Referentiel referentiel = referentielRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(referentiel));
    }

    /**
     * DELETE  /referentiels/:id : delete the "id" referentiel.
     *
     * @param id the id of the referentiel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/referentiels/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferentiel(@PathVariable Long id) {
        log.debug("REST request to delete Referentiel : {}", id);
        referentielRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
