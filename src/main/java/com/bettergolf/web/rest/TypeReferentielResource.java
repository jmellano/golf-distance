package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.TypeReferentiel;

import com.bettergolf.repository.TypeReferentielRepository;
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
 * REST controller for managing TypeReferentiel.
 */
@RestController
@RequestMapping("/api")
public class TypeReferentielResource {

    private final Logger log = LoggerFactory.getLogger(TypeReferentielResource.class);

    private static final String ENTITY_NAME = "typeReferentiel";

    private final TypeReferentielRepository typeReferentielRepository;

    public TypeReferentielResource(TypeReferentielRepository typeReferentielRepository) {
        this.typeReferentielRepository = typeReferentielRepository;
    }

    /**
     * POST  /type-referentiels : Create a new typeReferentiel.
     *
     * @param typeReferentiel the typeReferentiel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeReferentiel, or with status 400 (Bad Request) if the typeReferentiel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-referentiels")
    @Timed
    public ResponseEntity<TypeReferentiel> createTypeReferentiel(@Valid @RequestBody TypeReferentiel typeReferentiel) throws URISyntaxException {
        log.debug("REST request to save TypeReferentiel : {}", typeReferentiel);
        if (typeReferentiel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new typeReferentiel cannot already have an ID")).body(null);
        }
        TypeReferentiel result = typeReferentielRepository.save(typeReferentiel);
        return ResponseEntity.created(new URI("/api/type-referentiels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-referentiels : Updates an existing typeReferentiel.
     *
     * @param typeReferentiel the typeReferentiel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeReferentiel,
     * or with status 400 (Bad Request) if the typeReferentiel is not valid,
     * or with status 500 (Internal Server Error) if the typeReferentiel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-referentiels")
    @Timed
    public ResponseEntity<TypeReferentiel> updateTypeReferentiel(@Valid @RequestBody TypeReferentiel typeReferentiel) throws URISyntaxException {
        log.debug("REST request to update TypeReferentiel : {}", typeReferentiel);
        if (typeReferentiel.getId() == null) {
            return createTypeReferentiel(typeReferentiel);
        }
        TypeReferentiel result = typeReferentielRepository.save(typeReferentiel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeReferentiel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-referentiels : get all the typeReferentiels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeReferentiels in body
     */
    @GetMapping("/type-referentiels")
    @Timed
    public ResponseEntity<List<TypeReferentiel>> getAllTypeReferentiels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TypeReferentiels");
        Page<TypeReferentiel> page = typeReferentielRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-referentiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-referentiels/:id : get the "id" typeReferentiel.
     *
     * @param id the id of the typeReferentiel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeReferentiel, or with status 404 (Not Found)
     */
    @GetMapping("/type-referentiels/{id}")
    @Timed
    public ResponseEntity<TypeReferentiel> getTypeReferentiel(@PathVariable Long id) {
        log.debug("REST request to get TypeReferentiel : {}", id);
        TypeReferentiel typeReferentiel = typeReferentielRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeReferentiel));
    }

    /**
     * DELETE  /type-referentiels/:id : delete the "id" typeReferentiel.
     *
     * @param id the id of the typeReferentiel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-referentiels/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeReferentiel(@PathVariable Long id) {
        log.debug("REST request to delete TypeReferentiel : {}", id);
        typeReferentielRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
