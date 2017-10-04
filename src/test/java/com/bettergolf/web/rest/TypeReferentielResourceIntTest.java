package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.TypeReferentiel;
import com.bettergolf.repository.TypeReferentielRepository;
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
 * Test class for the TypeReferentielResource REST controller.
 *
 * @see TypeReferentielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class TypeReferentielResourceIntTest {

    private static final String DEFAULT_TRF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRF_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeReferentielRepository typeReferentielRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeReferentielMockMvc;

    private TypeReferentiel typeReferentiel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeReferentielResource typeReferentielResource = new TypeReferentielResource(typeReferentielRepository);
        this.restTypeReferentielMockMvc = MockMvcBuilders.standaloneSetup(typeReferentielResource)
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
    public static TypeReferentiel createEntity(EntityManager em) {
        TypeReferentiel typeReferentiel = new TypeReferentiel()
            .trfCode(DEFAULT_TRF_CODE)
            .libelle(DEFAULT_LIBELLE);
        return typeReferentiel;
    }

    @Before
    public void initTest() {
        typeReferentiel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeReferentiel() throws Exception {
        int databaseSizeBeforeCreate = typeReferentielRepository.findAll().size();

        // Create the TypeReferentiel
        restTypeReferentielMockMvc.perform(post("/api/type-referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeReferentiel)))
            .andExpect(status().isCreated());

        // Validate the TypeReferentiel in the database
        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeCreate + 1);
        TypeReferentiel testTypeReferentiel = typeReferentielList.get(typeReferentielList.size() - 1);
        assertThat(testTypeReferentiel.getTrfCode()).isEqualTo(DEFAULT_TRF_CODE);
        assertThat(testTypeReferentiel.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeReferentielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeReferentielRepository.findAll().size();

        // Create the TypeReferentiel with an existing ID
        typeReferentiel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeReferentielMockMvc.perform(post("/api/type-referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeReferentiel)))
            .andExpect(status().isBadRequest());

        // Validate the TypeReferentiel in the database
        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTrfCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeReferentielRepository.findAll().size();
        // set the field null
        typeReferentiel.setTrfCode(null);

        // Create the TypeReferentiel, which fails.

        restTypeReferentielMockMvc.perform(post("/api/type-referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeReferentiel)))
            .andExpect(status().isBadRequest());

        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeReferentiels() throws Exception {
        // Initialize the database
        typeReferentielRepository.saveAndFlush(typeReferentiel);

        // Get all the typeReferentielList
        restTypeReferentielMockMvc.perform(get("/api/type-referentiels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeReferentiel.getId().intValue())))
            .andExpect(jsonPath("$.[*].trfCode").value(hasItem(DEFAULT_TRF_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getTypeReferentiel() throws Exception {
        // Initialize the database
        typeReferentielRepository.saveAndFlush(typeReferentiel);

        // Get the typeReferentiel
        restTypeReferentielMockMvc.perform(get("/api/type-referentiels/{id}", typeReferentiel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeReferentiel.getId().intValue()))
            .andExpect(jsonPath("$.trfCode").value(DEFAULT_TRF_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeReferentiel() throws Exception {
        // Get the typeReferentiel
        restTypeReferentielMockMvc.perform(get("/api/type-referentiels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeReferentiel() throws Exception {
        // Initialize the database
        typeReferentielRepository.saveAndFlush(typeReferentiel);
        int databaseSizeBeforeUpdate = typeReferentielRepository.findAll().size();

        // Update the typeReferentiel
        TypeReferentiel updatedTypeReferentiel = typeReferentielRepository.findOne(typeReferentiel.getId());
        updatedTypeReferentiel
            .trfCode(UPDATED_TRF_CODE)
            .libelle(UPDATED_LIBELLE);

        restTypeReferentielMockMvc.perform(put("/api/type-referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeReferentiel)))
            .andExpect(status().isOk());

        // Validate the TypeReferentiel in the database
        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeUpdate);
        TypeReferentiel testTypeReferentiel = typeReferentielList.get(typeReferentielList.size() - 1);
        assertThat(testTypeReferentiel.getTrfCode()).isEqualTo(UPDATED_TRF_CODE);
        assertThat(testTypeReferentiel.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeReferentiel() throws Exception {
        int databaseSizeBeforeUpdate = typeReferentielRepository.findAll().size();

        // Create the TypeReferentiel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeReferentielMockMvc.perform(put("/api/type-referentiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeReferentiel)))
            .andExpect(status().isCreated());

        // Validate the TypeReferentiel in the database
        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeReferentiel() throws Exception {
        // Initialize the database
        typeReferentielRepository.saveAndFlush(typeReferentiel);
        int databaseSizeBeforeDelete = typeReferentielRepository.findAll().size();

        // Get the typeReferentiel
        restTypeReferentielMockMvc.perform(delete("/api/type-referentiels/{id}", typeReferentiel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeReferentiel> typeReferentielList = typeReferentielRepository.findAll();
        assertThat(typeReferentielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeReferentiel.class);
        TypeReferentiel typeReferentiel1 = new TypeReferentiel();
        typeReferentiel1.setId(1L);
        TypeReferentiel typeReferentiel2 = new TypeReferentiel();
        typeReferentiel2.setId(typeReferentiel1.getId());
        assertThat(typeReferentiel1).isEqualTo(typeReferentiel2);
        typeReferentiel2.setId(2L);
        assertThat(typeReferentiel1).isNotEqualTo(typeReferentiel2);
        typeReferentiel1.setId(null);
        assertThat(typeReferentiel1).isNotEqualTo(typeReferentiel2);
    }
}
