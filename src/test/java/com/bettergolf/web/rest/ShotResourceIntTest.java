package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.Shot;
import com.bettergolf.repository.CalibrationRepository;
import com.bettergolf.repository.ShotRepository;
import com.bettergolf.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShotResource REST controller.
 *
 * @see ShotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class ShotResourceIntTest {

    private static final BigDecimal DEFAULT_FORCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FORCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISTANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCE = new BigDecimal(2);

    @Autowired
    private ShotRepository shotRepository;

    @Autowired
    private CalibrationRepository calibrationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShotMockMvc;

    private Shot shot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShotResource shotResource = new ShotResource(shotRepository, calibrationRepository);
        this.restShotMockMvc = MockMvcBuilders.standaloneSetup(shotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shot createEntity(EntityManager em) {
        Shot shot = new Shot()
            .force(DEFAULT_FORCE)
            .distance(DEFAULT_DISTANCE);
        return shot;
    }

    @Before
    public void initTest() {
        shot = createEntity(em);
    }

    @Test
    @Transactional
    public void createShot() throws Exception {
        int databaseSizeBeforeCreate = shotRepository.findAll().size();

        // Create the Shot
        restShotMockMvc.perform(post("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shot)))
            .andExpect(status().isCreated());

        // Validate the Shot in the database
        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeCreate + 1);
        Shot testShot = shotList.get(shotList.size() - 1);
        assertThat(testShot.getForce()).isEqualTo(DEFAULT_FORCE);
        assertThat(testShot.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void createShotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shotRepository.findAll().size();

        // Create the Shot with an existing ID
        shot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShotMockMvc.perform(post("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shot)))
            .andExpect(status().isBadRequest());

        // Validate the Shot in the database
        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkForceIsRequired() throws Exception {
        int databaseSizeBeforeTest = shotRepository.findAll().size();
        // set the field null
        shot.setForce(null);

        // Create the Shot, which fails.

        restShotMockMvc.perform(post("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shot)))
            .andExpect(status().isBadRequest());

        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = shotRepository.findAll().size();
        // set the field null
        shot.setDistance(null);

        // Create the Shot, which fails.

        restShotMockMvc.perform(post("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shot)))
            .andExpect(status().isBadRequest());

        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShots() throws Exception {
        // Initialize the database
        shotRepository.saveAndFlush(shot);

        // Get all the shotList
        restShotMockMvc.perform(get("/api/shots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shot.getId().intValue())))
            .andExpect(jsonPath("$.[*].force").value(hasItem(DEFAULT_FORCE.intValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.intValue())));
    }

    @Test
    @Transactional
    public void getShot() throws Exception {
        // Initialize the database
        shotRepository.saveAndFlush(shot);

        // Get the shot
        restShotMockMvc.perform(get("/api/shots/{id}", shot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shot.getId().intValue()))
            .andExpect(jsonPath("$.force").value(DEFAULT_FORCE.intValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShot() throws Exception {
        // Get the shot
        restShotMockMvc.perform(get("/api/shots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShot() throws Exception {
        // Initialize the database
        shotRepository.saveAndFlush(shot);
        int databaseSizeBeforeUpdate = shotRepository.findAll().size();

        // Update the shot
        Shot updatedShot = shotRepository.findOne(shot.getId());
        updatedShot
            .force(UPDATED_FORCE)
            .distance(UPDATED_DISTANCE);

        restShotMockMvc.perform(put("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShot)))
            .andExpect(status().isOk());

        // Validate the Shot in the database
        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeUpdate);
        Shot testShot = shotList.get(shotList.size() - 1);
        assertThat(testShot.getForce()).isEqualTo(UPDATED_FORCE);
        assertThat(testShot.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingShot() throws Exception {
        int databaseSizeBeforeUpdate = shotRepository.findAll().size();

        // Create the Shot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShotMockMvc.perform(put("/api/shots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shot)))
            .andExpect(status().isCreated());

        // Validate the Shot in the database
        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShot() throws Exception {
        // Initialize the database
        shotRepository.saveAndFlush(shot);
        int databaseSizeBeforeDelete = shotRepository.findAll().size();

        // Get the shot
        restShotMockMvc.perform(delete("/api/shots/{id}", shot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shot> shotList = shotRepository.findAll();
        assertThat(shotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shot.class);
        Shot shot1 = new Shot();
        shot1.setId(1L);
        Shot shot2 = new Shot();
        shot2.setId(shot1.getId());
        assertThat(shot1).isEqualTo(shot2);
        shot2.setId(2L);
        assertThat(shot1).isNotEqualTo(shot2);
        shot1.setId(null);
        assertThat(shot1).isNotEqualTo(shot2);
    }
}
