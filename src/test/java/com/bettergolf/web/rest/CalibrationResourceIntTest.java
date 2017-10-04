package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.Calibration;
import com.bettergolf.repository.CalibrationRepository;
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
 * Test class for the CalibrationResource REST controller.
 *
 * @see CalibrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class CalibrationResourceIntTest {

    private static final BigDecimal DEFAULT_FORCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FORCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AVERAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVERAGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_STANDARD_DEVIATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANDARD_DEVIATION = new BigDecimal(2);

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

    private MockMvc restCalibrationMockMvc;

    private Calibration calibration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalibrationResource calibrationResource = new CalibrationResource(calibrationRepository);
        this.restCalibrationMockMvc = MockMvcBuilders.standaloneSetup(calibrationResource)
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
    public static Calibration createEntity(EntityManager em) {
        Calibration calibration = new Calibration()
            .force(DEFAULT_FORCE)
            .average(DEFAULT_AVERAGE)
            .standardDeviation(DEFAULT_STANDARD_DEVIATION);
        return calibration;
    }

    @Before
    public void initTest() {
        calibration = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalibration() throws Exception {
        int databaseSizeBeforeCreate = calibrationRepository.findAll().size();

        // Create the Calibration
        restCalibrationMockMvc.perform(post("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isCreated());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeCreate + 1);
        Calibration testCalibration = calibrationList.get(calibrationList.size() - 1);
        assertThat(testCalibration.getForce()).isEqualTo(DEFAULT_FORCE);
        assertThat(testCalibration.getAverage()).isEqualTo(DEFAULT_AVERAGE);
        assertThat(testCalibration.getStandardDeviation()).isEqualTo(DEFAULT_STANDARD_DEVIATION);
    }

    @Test
    @Transactional
    public void createCalibrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calibrationRepository.findAll().size();

        // Create the Calibration with an existing ID
        calibration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalibrationMockMvc.perform(post("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isBadRequest());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkForceIsRequired() throws Exception {
        int databaseSizeBeforeTest = calibrationRepository.findAll().size();
        // set the field null
        calibration.setForce(null);

        // Create the Calibration, which fails.

        restCalibrationMockMvc.perform(post("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isBadRequest());

        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalibrations() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        // Get all the calibrationList
        restCalibrationMockMvc.perform(get("/api/calibrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calibration.getId().intValue())))
            .andExpect(jsonPath("$.[*].force").value(hasItem(DEFAULT_FORCE.intValue())))
            .andExpect(jsonPath("$.[*].average").value(hasItem(DEFAULT_AVERAGE.intValue())))
            .andExpect(jsonPath("$.[*].standardDeviation").value(hasItem(DEFAULT_STANDARD_DEVIATION.intValue())));
    }

    @Test
    @Transactional
    public void getCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);

        // Get the calibration
        restCalibrationMockMvc.perform(get("/api/calibrations/{id}", calibration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calibration.getId().intValue()))
            .andExpect(jsonPath("$.force").value(DEFAULT_FORCE.intValue()))
            .andExpect(jsonPath("$.average").value(DEFAULT_AVERAGE.intValue()))
            .andExpect(jsonPath("$.standardDeviation").value(DEFAULT_STANDARD_DEVIATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCalibration() throws Exception {
        // Get the calibration
        restCalibrationMockMvc.perform(get("/api/calibrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);
        int databaseSizeBeforeUpdate = calibrationRepository.findAll().size();

        // Update the calibration
        Calibration updatedCalibration = calibrationRepository.findOne(calibration.getId());
        updatedCalibration
            .force(UPDATED_FORCE)
            .average(UPDATED_AVERAGE)
            .standardDeviation(UPDATED_STANDARD_DEVIATION);

        restCalibrationMockMvc.perform(put("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalibration)))
            .andExpect(status().isOk());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeUpdate);
        Calibration testCalibration = calibrationList.get(calibrationList.size() - 1);
        assertThat(testCalibration.getForce()).isEqualTo(UPDATED_FORCE);
        assertThat(testCalibration.getAverage()).isEqualTo(UPDATED_AVERAGE);
        assertThat(testCalibration.getStandardDeviation()).isEqualTo(UPDATED_STANDARD_DEVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCalibration() throws Exception {
        int databaseSizeBeforeUpdate = calibrationRepository.findAll().size();

        // Create the Calibration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalibrationMockMvc.perform(put("/api/calibrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calibration)))
            .andExpect(status().isCreated());

        // Validate the Calibration in the database
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCalibration() throws Exception {
        // Initialize the database
        calibrationRepository.saveAndFlush(calibration);
        int databaseSizeBeforeDelete = calibrationRepository.findAll().size();

        // Get the calibration
        restCalibrationMockMvc.perform(delete("/api/calibrations/{id}", calibration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calibration> calibrationList = calibrationRepository.findAll();
        assertThat(calibrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calibration.class);
        Calibration calibration1 = new Calibration();
        calibration1.setId(1L);
        Calibration calibration2 = new Calibration();
        calibration2.setId(calibration1.getId());
        assertThat(calibration1).isEqualTo(calibration2);
        calibration2.setId(2L);
        assertThat(calibration1).isNotEqualTo(calibration2);
        calibration1.setId(null);
        assertThat(calibration1).isNotEqualTo(calibration2);
    }
}
