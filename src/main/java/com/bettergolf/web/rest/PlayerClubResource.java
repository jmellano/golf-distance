package com.bettergolf.web.rest;

import com.bettergolf.domain.PlayerClub;
import com.bettergolf.repository.PlayerClubDistanceRepository;
import com.bettergolf.repository.PlayerClubRepository;
import com.bettergolf.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managing PlayerClub.
 */
@RestController
@RequestMapping("/api")
public class PlayerClubResource {

    private final Logger log = LoggerFactory.getLogger(PlayerClubResource.class);

    private static final String ENTITY_NAME = "playerClub";

    private final PlayerClubDistanceRepository playerClubDistanceRepository;

    private final PlayerClubRepository playerClubRepository;

    public PlayerClubResource(PlayerClubRepository playerClubRepository, PlayerClubDistanceRepository playerClubDistanceRepository) {
        this.playerClubRepository = playerClubRepository;
        this.playerClubDistanceRepository = playerClubDistanceRepository;
    }

    /**
     * POST  /player-clubs : Create a new playerClub.
     *
     * @param playerClub the playerClub to create
     * @return the ResponseEntity with status 201 (Created) and with body the new playerClub, or with status 400 (Bad Request) if the playerClub has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/player-clubs")
    @Timed
    public ResponseEntity<PlayerClub> createPlayerClub(@Valid @RequestBody PlayerClub playerClub) throws URISyntaxException {
        log.debug("REST request to save PlayerClub : {}", playerClub);
        if (playerClub.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new playerClub cannot already have an ID")).body(null);
        }
        PlayerClub result = playerClubRepository.save(playerClub);
        return ResponseEntity.created(new URI("/api/player-clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /player-clubs : Updates an existing playerClub.
     *
     * @param playerClub the playerClub to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated playerClub,
     * or with status 400 (Bad Request) if the playerClub is not valid,
     * or with status 500 (Internal Server Error) if the playerClub couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/player-clubs")
    @Timed
    public ResponseEntity<PlayerClub> updatePlayerClub(@Valid @RequestBody PlayerClub playerClub) throws URISyntaxException {
        log.debug("REST request to update PlayerClub : {}", playerClub);
        if (playerClub.getId() == null) {
            return createPlayerClub(playerClub);
        }
        PlayerClub result = playerClubRepository.save(playerClub);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, playerClub.getId().toString()))
            .body(result);
    }

    /**
     * GET  /player-clubs : get all the playerClubs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of playerClubs in body
     */
    @GetMapping("/player-clubs")
    @Timed
    public List<PlayerClub> getAllPlayerClubs() {
        log.debug("REST request to get all PlayerClubs");
        return playerClubRepository.findAll();
    }

    /**
     * GET  /player-clubs : get all the playerClubs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of playerClubs in body
     */
    @GetMapping("/player-clubs/player/{id}")
    @Timed
    public List<PlayerClub> getAllPlayerClubsForPlayer(@PathVariable Long id) {
        log.debug("REST request to get all PlayerClubs");
        // TODO: Finir le test avec PlayerClubDistance
        List<PlayerClub> clubs = playerClubRepository.findByPlayerId(id);
        return clubs;
    }

    /**
     * GET  /player-clubs/:id : get the "id" playerClub.
     *
     * @param id the id of the playerClub to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the playerClub, or with status 404 (Not Found)
     */
    @GetMapping("/player-clubs/{id}")
    @Timed
    public ResponseEntity<PlayerClub> getPlayerClub(@PathVariable Long id) {
        log.debug("REST request to get PlayerClub : {}", id);
        PlayerClub playerClub = playerClubRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(playerClub));
    }

    /**
     * DELETE  /player-clubs/:id : delete the "id" playerClub.
     *
     * @param id the id of the playerClub to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/player-clubs/{id}")
    @Timed
    public ResponseEntity<Void> deletePlayerClub(@PathVariable Long id) {
        log.debug("REST request to delete PlayerClub : {}", id);
        playerClubRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
