package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.ParametreApplicatif;
import com.bettergolf.repository.ParametreApplicatifRepository;
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
 * Test class for the ParametreApplicatifResource REST controller.
 *
 * @see ParametreApplicatifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class ParametreApplicatifResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private ParametreApplicatifRepository parametreApplicatifRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametreApplicatifMockMvc;

    private ParametreApplicatif parametreApplicatif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametreApplicatifResource parametreApplicatifResource = new ParametreApplicatifResource(parametreApplicatifRepository);
        this.restParametreApplicatifMockMvc = MockMvcBuilders.standaloneSetup(parametreApplicatifResource)
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
    public static ParametreApplicatif createEntity(EntityManager em) {
        ParametreApplicatif parametreApplicatif = new ParametreApplicatif()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .valeur(DEFAULT_VALEUR);
        return parametreApplicatif;
    }

    @Before
    public void initTest() {
        parametreApplicatif = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametreApplicatif() throws Exception {
        int databaseSizeBeforeCreate = parametreApplicatifRepository.findAll().size();

        // Create the ParametreApplicatif
        restParametreApplicatifMockMvc.perform(post("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreApplicatif)))
            .andExpect(status().isCreated());

        // Validate the ParametreApplicatif in the database
        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeCreate + 1);
        ParametreApplicatif testParametreApplicatif = parametreApplicatifList.get(parametreApplicatifList.size() - 1);
        assertThat(testParametreApplicatif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testParametreApplicatif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testParametreApplicatif.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createParametreApplicatifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametreApplicatifRepository.findAll().size();

        // Create the ParametreApplicatif with an existing ID
        parametreApplicatif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametreApplicatifMockMvc.perform(post("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreApplicatif)))
            .andExpect(status().isBadRequest());

        // Validate the ParametreApplicatif in the database
        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametreApplicatifRepository.findAll().size();
        // set the field null
        parametreApplicatif.setCode(null);

        // Create the ParametreApplicatif, which fails.

        restParametreApplicatifMockMvc.perform(post("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreApplicatif)))
            .andExpect(status().isBadRequest());

        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametreApplicatifRepository.findAll().size();
        // set the field null
        parametreApplicatif.setValeur(null);

        // Create the ParametreApplicatif, which fails.

        restParametreApplicatifMockMvc.perform(post("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreApplicatif)))
            .andExpect(status().isBadRequest());

        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametreApplicatifs() throws Exception {
        // Initialize the database
        parametreApplicatifRepository.saveAndFlush(parametreApplicatif);

        // Get all the parametreApplicatifList
        restParametreApplicatifMockMvc.perform(get("/api/parametre-applicatifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametreApplicatif.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }

    @Test
    @Transactional
    public void getParametreApplicatif() throws Exception {
        // Initialize the database
        parametreApplicatifRepository.saveAndFlush(parametreApplicatif);

        // Get the parametreApplicatif
        restParametreApplicatifMockMvc.perform(get("/api/parametre-applicatifs/{id}", parametreApplicatif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametreApplicatif.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametreApplicatif() throws Exception {
        // Get the parametreApplicatif
        restParametreApplicatifMockMvc.perform(get("/api/parametre-applicatifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametreApplicatif() throws Exception {
        // Initialize the database
        parametreApplicatifRepository.saveAndFlush(parametreApplicatif);
        int databaseSizeBeforeUpdate = parametreApplicatifRepository.findAll().size();

        // Update the parametreApplicatif
        ParametreApplicatif updatedParametreApplicatif = parametreApplicatifRepository.findOne(parametreApplicatif.getId());
        updatedParametreApplicatif
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .valeur(UPDATED_VALEUR);

        restParametreApplicatifMockMvc.perform(put("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametreApplicatif)))
            .andExpect(status().isOk());

        // Validate the ParametreApplicatif in the database
        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeUpdate);
        ParametreApplicatif testParametreApplicatif = parametreApplicatifList.get(parametreApplicatifList.size() - 1);
        assertThat(testParametreApplicatif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testParametreApplicatif.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testParametreApplicatif.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingParametreApplicatif() throws Exception {
        int databaseSizeBeforeUpdate = parametreApplicatifRepository.findAll().size();

        // Create the ParametreApplicatif

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametreApplicatifMockMvc.perform(put("/api/parametre-applicatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametreApplicatif)))
            .andExpect(status().isCreated());

        // Validate the ParametreApplicatif in the database
        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametreApplicatif() throws Exception {
        // Initialize the database
        parametreApplicatifRepository.saveAndFlush(parametreApplicatif);
        int databaseSizeBeforeDelete = parametreApplicatifRepository.findAll().size();

        // Get the parametreApplicatif
        restParametreApplicatifMockMvc.perform(delete("/api/parametre-applicatifs/{id}", parametreApplicatif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParametreApplicatif> parametreApplicatifList = parametreApplicatifRepository.findAll();
        assertThat(parametreApplicatifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametreApplicatif.class);
        ParametreApplicatif parametreApplicatif1 = new ParametreApplicatif();
        parametreApplicatif1.setId(1L);
        ParametreApplicatif parametreApplicatif2 = new ParametreApplicatif();
        parametreApplicatif2.setId(parametreApplicatif1.getId());
        assertThat(parametreApplicatif1).isEqualTo(parametreApplicatif2);
        parametreApplicatif2.setId(2L);
        assertThat(parametreApplicatif1).isNotEqualTo(parametreApplicatif2);
        parametreApplicatif1.setId(null);
        assertThat(parametreApplicatif1).isNotEqualTo(parametreApplicatif2);
    }
}
