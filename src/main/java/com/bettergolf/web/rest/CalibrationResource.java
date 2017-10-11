package com.bettergolf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bettergolf.domain.Calibration;

import com.bettergolf.repository.CalibrationRepository;
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
 * REST controller for managing Calibration.
 */
@RestController
@RequestMapping("/api")
public class CalibrationResource {

    private final Logger log = LoggerFactory.getLogger(CalibrationResource.class);

    private static final String ENTITY_NAME = "calibration";

    private final CalibrationRepository calibrationRepository;

    public CalibrationResource(CalibrationRepository calibrationRepository) {
        this.calibrationRepository = calibrationRepository;
    }

    /**
     * POST  /calibrations : Create a new calibration.
     *
     * @param calibration the calibration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calibration, or with status 400 (Bad Request) if the calibration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calibrations")
    @Timed
    public ResponseEntity<Calibration> createCalibration(@Valid @RequestBody Calibration calibration) throws URISyntaxException {
        log.debug("REST request to save Calibration : {}", calibration);
        if (calibration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new calibration cannot already have an ID")).body(null);
        }
        Calibration result = calibrationRepository.save(calibration);
        return ResponseEntity.created(new URI("/api/calibrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calibrations : Updates an existing calibration.
     *
     * @param calibration the calibration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calibration,
     * or with status 400 (Bad Request) if the calibration is not valid,
     * or with status 500 (Internal Server Error) if the calibration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calibrations")
    @Timed
    public ResponseEntity<Calibration> updateCalibration(@Valid @RequestBody Calibration calibration) throws URISyntaxException {
        log.debug("REST request to update Calibration : {}", calibration);
        if (calibration.getId() == null) {
            return createCalibration(calibration);
        }
        Calibration result = calibrationRepository.save(calibration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calibration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calibrations : get all the calibrations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calibrations in body
     */
    @GetMapping("/calibrations")
    @Timed
    public List<Calibration> getAllCalibrations() {
        log.debug("REST request to get all Calibrations");
        return calibrationRepository.findAll();
        }

    /**
     * GET  /calibrations/:id : get the "id" calibration.
     *
     * @param id the id of the calibration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calibration, or with status 404 (Not Found)
     */
    @GetMapping("/calibrations/{id}")
    @Timed
    public ResponseEntity<Calibration> getCalibration(@PathVariable Long id) {
        log.debug("REST request to get Calibration : {}", id);
        Calibration calibration = calibrationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(calibration));
    }


    @GetMapping("/calibrations/club/{playerClubId}")
    @Timed
    public List<Calibration> getCalibrationForPlayerClub(@PathVariable Long playerClubId) {
        log.debug("REST request to get playerClubId : {}", playerClubId);
        return calibrationRepository.findAllByPlayerClubId(playerClubId);
    }


    /**
     * DELETE  /calibrations/:id : delete the "id" calibration.
     *
     * @param id the id of the calibration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calibrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalibration(@PathVariable Long id) {
        log.debug("REST request to delete Calibration : {}", id);
        calibrationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
