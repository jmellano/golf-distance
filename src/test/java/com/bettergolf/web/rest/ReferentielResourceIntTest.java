package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.Referentiel;
import com.bettergolf.repository.ReferentielRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReferentielResource REST controller.
 *
 * @see ReferentielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class ReferentielResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDRE = 1;
    private static final Integer UPDATED_ORDRE = 2;

    @Autowired
    private ReferentielRepository referentielRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReferentielMockMvc;

    private Referentiel referentiel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferentielResource referentielResource = new ReferentielResource(referentielRepository);
        this.restReferentielMockMvc = MockMvcBuilders.standaloneSetup(referentielResource)
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
    public static Referentiel createEntity(EntityManager em) {
        Referentiel referentiel = new Referentiel()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .ordre(DEFAULT_ORDRE);
        return referentiel;
    }

    @Before
    public void initTest() {
        referentiel = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferentiel() throws Exception {
        int databaseSizeBeforeCreate = referentielRepository.findAll().size();

        // Create the Referentiel
        restReferentielMockMvc.perform(post("/api/referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentiel)))
            .andExpect(status().isCreated());

        // Validate the Referentiel in the database
        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeCreate + 1);
        Referentiel testReferentiel = referentielList.get(referentielList.size() - 1);
        assertThat(testReferentiel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testReferentiel.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testReferentiel.getOrdre()).isEqualTo(DEFAULT_ORDRE);
    }

    @Test
    @Transactional
    public void createReferentielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referentielRepository.findAll().size();

        // Create the Referentiel with an existing ID
        referentiel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferentielMockMvc.perform(post("/api/referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentiel)))
            .andExpect(status().isBadRequest());

        // Validate the Referentiel in the database
        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = referentielRepository.findAll().size();
        // set the field null
        referentiel.setCode(null);

        // Create the Referentiel, which fails.

        restReferentielMockMvc.perform(post("/api/referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentiel)))
            .andExpect(status().isBadRequest());

        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferentiels() throws Exception {
        // Initialize the database
        referentielRepository.saveAndFlush(referentiel);

        // Get all the referentielList
        restReferentielMockMvc.perform(get("/api/referentiels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referentiel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)));
    }

    @Test
    @Transactional
    public void getReferentiel() throws Exception {
        // Initialize the database
        referentielRepository.saveAndFlush(referentiel);

        // Get the referentiel
        restReferentielMockMvc.perform(get("/api/referentiels/{id}", referentiel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referentiel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE));
    }

    @Test
    @Transactional
    public void getNonExistingReferentiel() throws Exception {
        // Get the referentiel
        restReferentielMockMvc.perform(get("/api/referentiels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferentiel() throws Exception {
        // Initialize the database
        referentielRepository.saveAndFlush(referentiel);
        int databaseSizeBeforeUpdate = referentielRepository.findAll().size();

        // Update the referentiel
        Referentiel updatedReferentiel = referentielRepository.findOne(referentiel.getId());
        updatedReferentiel
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .ordre(UPDATED_ORDRE);

        restReferentielMockMvc.perform(put("/api/referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReferentiel)))
            .andExpect(status().isOk());

        // Validate the Referentiel in the database
        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeUpdate);
        Referentiel testReferentiel = referentielList.get(referentielList.size() - 1);
        assertThat(testReferentiel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testReferentiel.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testReferentiel.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void updateNonExistingReferentiel() throws Exception {
        int databaseSizeBeforeUpdate = referentielRepository.findAll().size();

        // Create the Referentiel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReferentielMockMvc.perform(put("/api/referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referentiel)))
            .andExpect(status().isCreated());

        // Validate the Referentiel in the database
        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReferentiel() throws Exception {
        // Initialize the database
        referentielRepository.saveAndFlush(referentiel);
        int databaseSizeBeforeDelete = referentielRepository.findAll().size();

        // Get the referentiel
        restReferentielMockMvc.perform(delete("/api/referentiels/{id}", referentiel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Referentiel> referentielList = referentielRepository.findAll();
        assertThat(referentielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Referentiel.class);
        Referentiel referentiel1 = new Referentiel();
        referentiel1.setId(1L);
        Referentiel referentiel2 = new Referentiel();
        referentiel2.setId(referentiel1.getId());
        assertThat(referentiel1).isEqualTo(referentiel2);
        referentiel2.setId(2L);
        assertThat(referentiel1).isNotEqualTo(referentiel2);
        referentiel1.setId(null);
        assertThat(referentiel1).isNotEqualTo(referentiel2);
    }
}
