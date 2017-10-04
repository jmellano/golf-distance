package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.Shot;

import com.bettergolf.repository.ShotRepository;
import com.bettergolf.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Shot.
 */
@RestController
@RequestMapping("/api")
public class ShotResource {

    private final Logger log = LoggerFactory.getLogger(ShotResource.class);

    private static final String ENTITY_NAME = "shot";

    private final ShotRepository shotRepository;

    public ShotResource(ShotRepository shotRepository) {
        this.shotRepository = shotRepository;
    }

    /**
     * POST  /shots : Create a new shot.
     *
     * @param shot the shot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shot, or with status 400 (Bad Request) if the shot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shots")
    @Timed
    public ResponseEntity<Shot> createShot(@Valid @RequestBody Shot shot) throws URISyntaxException {
        log.debug("REST request to save Shot : {}", shot);
        if (shot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shot cannot already have an ID")).body(null);
        }
        Shot result = shotRepository.save(shot);
        return ResponseEntity.created(new URI("/api/shots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shots : Updates an existing shot.
     *
     * @param shot the shot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shot,
     * or with status 400 (Bad Request) if the shot is not valid,
     * or with status 500 (Internal Server Error) if the shot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shots")
    @Timed
    public ResponseEntity<Shot> updateShot(@Valid @RequestBody Shot shot) throws URISyntaxException {
        log.debug("REST request to update Shot : {}", shot);
        if (shot.getId() == null) {
            return createShot(shot);
        }
        Shot result = shotRepository.save(shot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shots : get all the shots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shots in body
     */
    @GetMapping("/shots")
    @Timed
    public List<Shot> getAllShots() {
        log.debug("REST request to get all Shots");
        return shotRepository.findAll();
        }

    /**
     * GET  /shots/:id : get the "id" shot.
     *
     * @param id the id of the shot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shot, or with status 404 (Not Found)
     */
    @GetMapping("/shots/{id}")
    @Timed
    public ResponseEntity<Shot> getShot(@PathVariable Long id) {
        log.debug("REST request to get Shot : {}", id);
        Shot shot = shotRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shot));
    }

    /**
     * DELETE  /shots/:id : delete the "id" shot.
     *
     * @param id the id of the shot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shots/{id}")
    @Timed
    public ResponseEntity<Void> deleteShot(@PathVariable Long id) {
        log.debug("REST request to delete Shot : {}", id);
        shotRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
