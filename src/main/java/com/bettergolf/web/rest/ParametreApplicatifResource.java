package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.ParametreApplicatif;

import com.bettergolf.repository.ParametreApplicatifRepository;
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
 * REST controller for managing ParametreApplicatif.
 */
@RestController
@RequestMapping("/api")
public class ParametreApplicatifResource {

    private final Logger log = LoggerFactory.getLogger(ParametreApplicatifResource.class);

    private static final String ENTITY_NAME = "parametreApplicatif";

    private final ParametreApplicatifRepository parametreApplicatifRepository;

    public ParametreApplicatifResource(ParametreApplicatifRepository parametreApplicatifRepository) {
        this.parametreApplicatifRepository = parametreApplicatifRepository;
    }

    /**
     * POST  /parametre-applicatifs : Create a new parametreApplicatif.
     *
     * @param parametreApplicatif the parametreApplicatif to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametreApplicatif, or with status 400 (Bad Request) if the parametreApplicatif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametre-applicatifs")
    @Timed
    public ResponseEntity<ParametreApplicatif> createParametreApplicatif(@Valid @RequestBody ParametreApplicatif parametreApplicatif) throws URISyntaxException {
        log.debug("REST request to save ParametreApplicatif : {}", parametreApplicatif);
        if (parametreApplicatif.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parametreApplicatif cannot already have an ID")).body(null);
        }
        ParametreApplicatif result = parametreApplicatifRepository.save(parametreApplicatif);
        return ResponseEntity.created(new URI("/api/parametre-applicatifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametre-applicatifs : Updates an existing parametreApplicatif.
     *
     * @param parametreApplicatif the parametreApplicatif to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametreApplicatif,
     * or with status 400 (Bad Request) if the parametreApplicatif is not valid,
     * or with status 500 (Internal Server Error) if the parametreApplicatif couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametre-applicatifs")
    @Timed
    public ResponseEntity<ParametreApplicatif> updateParametreApplicatif(@Valid @RequestBody ParametreApplicatif parametreApplicatif) throws URISyntaxException {
        log.debug("REST request to update ParametreApplicatif : {}", parametreApplicatif);
        if (parametreApplicatif.getId() == null) {
            return createParametreApplicatif(parametreApplicatif);
        }
        ParametreApplicatif result = parametreApplicatifRepository.save(parametreApplicatif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametreApplicatif.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametre-applicatifs : get all the parametreApplicatifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametreApplicatifs in body
     */
    @GetMapping("/parametre-applicatifs")
    @Timed
    public ResponseEntity<List<ParametreApplicatif>> getAllParametreApplicatifs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ParametreApplicatifs");
        Page<ParametreApplicatif> page = parametreApplicatifRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametre-applicatifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametre-applicatifs/:id : get the "id" parametreApplicatif.
     *
     * @param id the id of the parametreApplicatif to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametreApplicatif, or with status 404 (Not Found)
     */
    @GetMapping("/parametre-applicatifs/{id}")
    @Timed
    public ResponseEntity<ParametreApplicatif> getParametreApplicatif(@PathVariable Long id) {
        log.debug("REST request to get ParametreApplicatif : {}", id);
        ParametreApplicatif parametreApplicatif = parametreApplicatifRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parametreApplicatif));
    }

    /**
     * DELETE  /parametre-applicatifs/:id : delete the "id" parametreApplicatif.
     *
     * @param id the id of the parametreApplicatif to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametre-applicatifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametreApplicatif(@PathVariable Long id) {
        log.debug("REST request to delete ParametreApplicatif : {}", id);
        parametreApplicatifRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
