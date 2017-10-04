package com.bettergolf.web.rest;

import com.bettergolf.GolfDistanceApp;

import com.bettergolf.domain.PlayerClub;
import com.bettergolf.repository.PlayerClubRepository;
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
 * Test class for the PlayerClubResource REST controller.
 *
 * @see PlayerClubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolfDistanceApp.class)
public class PlayerClubResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private PlayerClubRepository playerClubRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlayerClubMockMvc;

    private PlayerClub playerClub;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerClubResource playerClubResource = new PlayerClubResource(playerClubRepository);
        this.restPlayerClubMockMvc = MockMvcBuilders.standaloneSetup(playerClubResource)
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
    public static PlayerClub createEntity(EntityManager em) {
        PlayerClub playerClub = new PlayerClub()
            .type(DEFAULT_TYPE)
            .label(DEFAULT_LABEL);
        return playerClub;
    }

    @Before
    public void initTest() {
        playerClub = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerClub() throws Exception {
        int databaseSizeBeforeCreate = playerClubRepository.findAll().size();

        // Create the PlayerClub
        restPlayerClubMockMvc.perform(post("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerClub)))
            .andExpect(status().isCreated());

        // Validate the PlayerClub in the database
        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerClub testPlayerClub = playerClubList.get(playerClubList.size() - 1);
        assertThat(testPlayerClub.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPlayerClub.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createPlayerClubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerClubRepository.findAll().size();

        // Create the PlayerClub with an existing ID
        playerClub.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerClubMockMvc.perform(post("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerClub)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerClub in the database
        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerClubRepository.findAll().size();
        // set the field null
        playerClub.setType(null);

        // Create the PlayerClub, which fails.

        restPlayerClubMockMvc.perform(post("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerClub)))
            .andExpect(status().isBadRequest());

        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerClubRepository.findAll().size();
        // set the field null
        playerClub.setLabel(null);

        // Create the PlayerClub, which fails.

        restPlayerClubMockMvc.perform(post("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerClub)))
            .andExpect(status().isBadRequest());

        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlayerClubs() throws Exception {
        // Initialize the database
        playerClubRepository.saveAndFlush(playerClub);

        // Get all the playerClubList
        restPlayerClubMockMvc.perform(get("/api/player-clubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerClub.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getPlayerClub() throws Exception {
        // Initialize the database
        playerClubRepository.saveAndFlush(playerClub);

        // Get the playerClub
        restPlayerClubMockMvc.perform(get("/api/player-clubs/{id}", playerClub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerClub.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerClub() throws Exception {
        // Get the playerClub
        restPlayerClubMockMvc.perform(get("/api/player-clubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerClub() throws Exception {
        // Initialize the database
        playerClubRepository.saveAndFlush(playerClub);
        int databaseSizeBeforeUpdate = playerClubRepository.findAll().size();

        // Update the playerClub
        PlayerClub updatedPlayerClub = playerClubRepository.findOne(playerClub.getId());
        updatedPlayerClub
            .type(UPDATED_TYPE)
            .label(UPDATED_LABEL);

        restPlayerClubMockMvc.perform(put("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayerClub)))
            .andExpect(status().isOk());

        // Validate the PlayerClub in the database
        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeUpdate);
        PlayerClub testPlayerClub = playerClubList.get(playerClubList.size() - 1);
        assertThat(testPlayerClub.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlayerClub.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerClub() throws Exception {
        int databaseSizeBeforeUpdate = playerClubRepository.findAll().size();

        // Create the PlayerClub

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlayerClubMockMvc.perform(put("/api/player-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerClub)))
            .andExpect(status().isCreated());

        // Validate the PlayerClub in the database
        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlayerClub() throws Exception {
        // Initialize the database
        playerClubRepository.saveAndFlush(playerClub);
        int databaseSizeBeforeDelete = playerClubRepository.findAll().size();

        // Get the playerClub
        restPlayerClubMockMvc.perform(delete("/api/player-clubs/{id}", playerClub.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlayerClub> playerClubList = playerClubRepository.findAll();
        assertThat(playerClubList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerClub.class);
        PlayerClub playerClub1 = new PlayerClub();
        playerClub1.setId(1L);
        PlayerClub playerClub2 = new PlayerClub();
        playerClub2.setId(playerClub1.getId());
        assertThat(playerClub1).isEqualTo(playerClub2);
        playerClub2.setId(2L);
        assertThat(playerClub1).isNotEqualTo(playerClub2);
        playerClub1.setId(null);
        assertThat(playerClub1).isNotEqualTo(playerClub2);
    }
}
