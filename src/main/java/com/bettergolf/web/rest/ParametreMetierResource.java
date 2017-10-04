package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.ParametreMetier;

import com.bettergolf.repository.ParametreMetierRepository;
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
 * REST controller for managing ParametreMetier.
 */
@RestController
@RequestMapping("/api")
public class ParametreMetierResource {

    private final Logger log = LoggerFactory.getLogger(ParametreMetierResource.class);

    private static final String ENTITY_NAME = "parametreMetier";

    private final ParametreMetierRepository parametreMetierRepository;

    public ParametreMetierResource(ParametreMetierRepository parametreMetierRepository) {
        this.parametreMetierRepository = parametreMetierRepository;
    }

    /**
     * POST  /parametre-metiers : Create a new parametreMetier.
     *
     * @param parametreMetier the parametreMetier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametreMetier, or with status 400 (Bad Request) if the parametreMetier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametre-metiers")
    @Timed
    public ResponseEntity<ParametreMetier> createParametreMetier(@Valid @RequestBody ParametreMetier parametreMetier) throws URISyntaxException {
        log.debug("REST request to save ParametreMetier : {}", parametreMetier);
        if (parametreMetier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parametreMetier cannot already have an ID")).body(null);
        }
        ParametreMetier result = parametreMetierRepository.save(parametreMetier);
        return ResponseEntity.created(new URI("/api/parametre-metiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametre-metiers : Updates an existing parametreMetier.
     *
     * @param parametreMetier the parametreMetier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametreMetier,
     * or with status 400 (Bad Request) if the parametreMetier is not valid,
     * or with status 500 (Internal Server Error) if the parametreMetier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametre-metiers")
    @Timed
    public ResponseEntity<ParametreMetier> updateParametreMetier(@Valid @RequestBody ParametreMetier parametreMetier) throws URISyntaxException {
        log.debug("REST request to update ParametreMetier : {}", parametreMetier);
        if (parametreMetier.getId() == null) {
            return createParametreMetier(parametreMetier);
        }
        ParametreMetier result = parametreMetierRepository.save(parametreMetier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametreMetier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametre-metiers : get all the parametreMetiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametreMetiers in body
     */
    @GetMapping("/parametre-metiers")
    @Timed
    public ResponseEntity<List<ParametreMetier>> getAllParametreMetiers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ParametreMetiers");
        Page<ParametreMetier> page = parametreMetierRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametre-metiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametre-metiers/:id : get the "id" parametreMetier.
     *
     * @param id the id of the parametreMetier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametreMetier, or with status 404 (Not Found)
     */
    @GetMapping("/parametre-metiers/{id}")
    @Timed
    public ResponseEntity<ParametreMetier> getParametreMetier(@PathVariable Long id) {
        log.debug("REST request to get ParametreMetier : {}", id);
        ParametreMetier parametreMetier = parametreMetierRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parametreMetier));
    }

    /**
     * DELETE  /parametre-metiers/:id : delete the "id" parametreMetier.
     *
     * @param id the id of the parametreMetier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametre-metiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametreMetier(@PathVariable Long id) {
        log.debug("REST request to delete ParametreMetier : {}", id);
        parametreMetierRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
