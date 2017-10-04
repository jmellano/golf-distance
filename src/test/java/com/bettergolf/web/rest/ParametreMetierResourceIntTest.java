package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.ParametreMetier;
import com.bettergolf.repository.ParametreMetierRepository;
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
 * Test class for the ParametreMetierResource REST controller.
 *
 * @see ParametreMetierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class ParametreMetierResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private ParametreMetierRepository parametreMetierRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametreMetierMockMvc;

    private ParametreMetier parametreMetier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametreMetierResource parametreMetierResource = new ParametreMetierResource(parametreMetierRepository);
        this.restParametreMetierMockMvc = MockMvcBuilders.standaloneSetup(parametreMetierResource)
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
    public static ParametreMetier createEntity(EntityManager em) {
        ParametreMetier parametreMetier = new ParametreMetier()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .valeur(DEFAULT_VALEUR);
        return parametreMetier;
    }

    @Before
    public void initTest() {
        parametreMetier = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametreMetier() throws Exception {
        int databaseSizeBeforeCreate = parametreMetierRepository.findAll().size();

        // Create the ParametreMetier
        restParametreMetierMockMvc.perform(post("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreMetier)))
            .andExpect(status().isCreated());

        // Validate the ParametreMetier in the database
        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeCreate + 1);
        ParametreMetier testParametreMetier = parametreMetierList.get(parametreMetierList.size() - 1);
        assertThat(testParametreMetier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testParametreMetier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testParametreMetier.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createParametreMetierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametreMetierRepository.findAll().size();

        // Create the ParametreMetier with an existing ID
        parametreMetier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametreMetierMockMvc.perform(post("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreMetier)))
            .andExpect(status().isBadRequest());

        // Validate the ParametreMetier in the database
        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametreMetierRepository.findAll().size();
        // set the field null
        parametreMetier.setCode(null);

        // Create the ParametreMetier, which fails.

        restParametreMetierMockMvc.perform(post("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreMetier)))
            .andExpect(status().isBadRequest());

        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametreMetierRepository.findAll().size();
        // set the field null
        parametreMetier.setValeur(null);

        // Create the ParametreMetier, which fails.

        restParametreMetierMockMvc.perform(post("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreMetier)))
            .andExpect(status().isBadRequest());

        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametreMetiers() throws Exception {
        // Initialize the database
        parametreMetierRepository.saveAndFlush(parametreMetier);

        // Get all the parametreMetierList
        restParametreMetierMockMvc.perform(get("/api/parametre-metiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametreMetier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }

    @Test
    @Transactional
    public void getParametreMetier() throws Exception {
        // Initialize the database
        parametreMetierRepository.saveAndFlush(parametreMetier);

        // Get the parametreMetier
        restParametreMetierMockMvc.perform(get("/api/parametre-metiers/{id}", parametreMetier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametreMetier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametreMetier() throws Exception {
        // Get the parametreMetier
        restParametreMetierMockMvc.perform(get("/api/parametre-metiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametreMetier() throws Exception {
        // Initialize the database
        parametreMetierRepository.saveAndFlush(parametreMetier);
        int databaseSizeBeforeUpdate = parametreMetierRepository.findAll().size();

        // Update the parametreMetier
        ParametreMetier updatedParametreMetier = parametreMetierRepository.findOne(parametreMetier.getId());
        updatedParametreMetier
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .valeur(UPDATED_VALEUR);

        restParametreMetierMockMvc.perform(put("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametreMetier)))
            .andExpect(status().isOk());

        // Validate the ParametreMetier in the database
        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeUpdate);
        ParametreMetier testParametreMetier = parametreMetierList.get(parametreMetierList.size() - 1);
        assertThat(testParametreMetier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testParametreMetier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testParametreMetier.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingParametreMetier() throws Exception {
        int databaseSizeBeforeUpdate = parametreMetierRepository.findAll().size();

        // Create the ParametreMetier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametreMetierMockMvc.perform(put("/api/parametre-metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreMetier)))
            .andExpect(status().isCreated());

        // Validate the ParametreMetier in the database
        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametreMetier() throws Exception {
        // Initialize the database
        parametreMetierRepository.saveAndFlush(parametreMetier);
        int databaseSizeBeforeDelete = parametreMetierRepository.findAll().size();

        // Get the parametreMetier
        restParametreMetierMockMvc.perform(delete("/api/parametre-metiers/{id}", parametreMetier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParametreMetier> parametreMetierList = parametreMetierRepository.findAll();
        assertThat(parametreMetierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametreMetier.class);
        ParametreMetier parametreMetier1 = new ParametreMetier();
        parametreMetier1.setId(1L);
        ParametreMetier parametreMetier2 = new ParametreMetier();
        parametreMetier2.setId(parametreMetier1.getId());
        assertThat(parametreMetier1).isEqualTo(parametreMetier2);
        parametreMetier2.setId(2L);
        assertThat(parametreMetier1).isNotEqualTo(parametreMetier2);
        parametreMetier1.setId(null);
        assertThat(parametreMetier1).isNotEqualTo(parametreMetier2);
    }
}
