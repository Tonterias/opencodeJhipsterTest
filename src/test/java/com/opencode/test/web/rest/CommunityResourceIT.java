package com.opencode.test.web.rest;

import static com.opencode.test.domain.CommunityAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Cactivity;
import com.opencode.test.domain.Cceleb;
import com.opencode.test.domain.Cinterest;
import com.opencode.test.domain.Community;
import com.opencode.test.repository.CommunityRepository;
import com.opencode.test.service.dto.CommunityDTO;
import com.opencode.test.service.mapper.CommunityMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommunityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommunityResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMUNITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITY_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/communities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunityMockMvc;

    private Community community;

    private Community insertedCommunity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Community createEntity(EntityManager em) {
        Community community = new Community()
            .creationDate(DEFAULT_CREATION_DATE)
            .communityName(DEFAULT_COMMUNITY_NAME)
            .communityDescription(DEFAULT_COMMUNITY_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        community.setAppuser(appuser);
        return community;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Community createUpdatedEntity(EntityManager em) {
        Community updatedCommunity = new Community()
            .creationDate(UPDATED_CREATION_DATE)
            .communityName(UPDATED_COMMUNITY_NAME)
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        updatedCommunity.setAppuser(appuser);
        return updatedCommunity;
    }

    @BeforeEach
    void initTest() {
        community = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCommunity != null) {
            communityRepository.delete(insertedCommunity);
            insertedCommunity = null;
        }
    }

    @Test
    @Transactional
    void createCommunity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);
        var returnedCommunityDTO = om.readValue(
            restCommunityMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommunityDTO.class
        );

        // Validate the Community in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCommunity = communityMapper.toEntity(returnedCommunityDTO);
        assertCommunityUpdatableFieldsEquals(returnedCommunity, getPersistedCommunity(returnedCommunity));

        insertedCommunity = returnedCommunity;
    }

    @Test
    @Transactional
    void createCommunityWithExistingId() throws Exception {
        // Create the Community with an existing ID
        community.setId(1L);
        CommunityDTO communityDTO = communityMapper.toDto(community);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        community.setCreationDate(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommunityNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        community.setCommunityName(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommunityDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        community.setCommunityDescription(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCommunities() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityName").value(hasItem(DEFAULT_COMMUNITY_NAME)))
            .andExpect(jsonPath("$.[*].communityDescription").value(hasItem(DEFAULT_COMMUNITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    void getCommunity() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get the community
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL_ID, community.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(community.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.communityName").value(DEFAULT_COMMUNITY_NAME))
            .andExpect(jsonPath("$.communityDescription").value(DEFAULT_COMMUNITY_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    void getCommunitiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        Long id = community.getId();

        defaultCommunityFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCommunityFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCommunityFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCommunitiesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate equals to
        defaultCommunityFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCommunitiesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate in
        defaultCommunityFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate is not null
        defaultCommunityFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName equals to
        defaultCommunityFiltering("communityName.equals=" + DEFAULT_COMMUNITY_NAME, "communityName.equals=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName in
        defaultCommunityFiltering(
            "communityName.in=" + DEFAULT_COMMUNITY_NAME + "," + UPDATED_COMMUNITY_NAME,
            "communityName.in=" + UPDATED_COMMUNITY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName is not null
        defaultCommunityFiltering("communityName.specified=true", "communityName.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName contains
        defaultCommunityFiltering("communityName.contains=" + DEFAULT_COMMUNITY_NAME, "communityName.contains=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName does not contain
        defaultCommunityFiltering(
            "communityName.doesNotContain=" + UPDATED_COMMUNITY_NAME,
            "communityName.doesNotContain=" + DEFAULT_COMMUNITY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription equals to
        defaultCommunityFiltering(
            "communityDescription.equals=" + DEFAULT_COMMUNITY_DESCRIPTION,
            "communityDescription.equals=" + UPDATED_COMMUNITY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription in
        defaultCommunityFiltering(
            "communityDescription.in=" + DEFAULT_COMMUNITY_DESCRIPTION + "," + UPDATED_COMMUNITY_DESCRIPTION,
            "communityDescription.in=" + UPDATED_COMMUNITY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription is not null
        defaultCommunityFiltering("communityDescription.specified=true", "communityDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription contains
        defaultCommunityFiltering(
            "communityDescription.contains=" + DEFAULT_COMMUNITY_DESCRIPTION,
            "communityDescription.contains=" + UPDATED_COMMUNITY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByCommunityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription does not contain
        defaultCommunityFiltering(
            "communityDescription.doesNotContain=" + UPDATED_COMMUNITY_DESCRIPTION,
            "communityDescription.doesNotContain=" + DEFAULT_COMMUNITY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommunitiesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive equals to
        defaultCommunityFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCommunitiesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive in
        defaultCommunityFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCommunitiesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive is not null
        defaultCommunityFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunitiesByAppuserIsEqualToSomething() throws Exception {
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            communityRepository.saveAndFlush(community);
            appuser = AppuserResourceIT.createEntity(em);
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(appuser);
        em.flush();
        community.setAppuser(appuser);
        communityRepository.saveAndFlush(community);
        Long appuserId = appuser.getId();
        // Get all the communityList where appuser equals to appuserId
        defaultCommunityShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the communityList where appuser equals to (appuserId + 1)
        defaultCommunityShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    @Test
    @Transactional
    void getAllCommunitiesByCinterestIsEqualToSomething() throws Exception {
        Cinterest cinterest;
        if (TestUtil.findAll(em, Cinterest.class).isEmpty()) {
            communityRepository.saveAndFlush(community);
            cinterest = CinterestResourceIT.createEntity();
        } else {
            cinterest = TestUtil.findAll(em, Cinterest.class).get(0);
        }
        em.persist(cinterest);
        em.flush();
        community.addCinterest(cinterest);
        communityRepository.saveAndFlush(community);
        Long cinterestId = cinterest.getId();
        // Get all the communityList where cinterest equals to cinterestId
        defaultCommunityShouldBeFound("cinterestId.equals=" + cinterestId);

        // Get all the communityList where cinterest equals to (cinterestId + 1)
        defaultCommunityShouldNotBeFound("cinterestId.equals=" + (cinterestId + 1));
    }

    @Test
    @Transactional
    void getAllCommunitiesByCactivityIsEqualToSomething() throws Exception {
        Cactivity cactivity;
        if (TestUtil.findAll(em, Cactivity.class).isEmpty()) {
            communityRepository.saveAndFlush(community);
            cactivity = CactivityResourceIT.createEntity();
        } else {
            cactivity = TestUtil.findAll(em, Cactivity.class).get(0);
        }
        em.persist(cactivity);
        em.flush();
        community.addCactivity(cactivity);
        communityRepository.saveAndFlush(community);
        Long cactivityId = cactivity.getId();
        // Get all the communityList where cactivity equals to cactivityId
        defaultCommunityShouldBeFound("cactivityId.equals=" + cactivityId);

        // Get all the communityList where cactivity equals to (cactivityId + 1)
        defaultCommunityShouldNotBeFound("cactivityId.equals=" + (cactivityId + 1));
    }

    @Test
    @Transactional
    void getAllCommunitiesByCcelebIsEqualToSomething() throws Exception {
        Cceleb cceleb;
        if (TestUtil.findAll(em, Cceleb.class).isEmpty()) {
            communityRepository.saveAndFlush(community);
            cceleb = CcelebResourceIT.createEntity();
        } else {
            cceleb = TestUtil.findAll(em, Cceleb.class).get(0);
        }
        em.persist(cceleb);
        em.flush();
        community.addCceleb(cceleb);
        communityRepository.saveAndFlush(community);
        Long ccelebId = cceleb.getId();
        // Get all the communityList where cceleb equals to ccelebId
        defaultCommunityShouldBeFound("ccelebId.equals=" + ccelebId);

        // Get all the communityList where cceleb equals to (ccelebId + 1)
        defaultCommunityShouldNotBeFound("ccelebId.equals=" + (ccelebId + 1));
    }

    private void defaultCommunityFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCommunityShouldBeFound(shouldBeFound);
        defaultCommunityShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommunityShouldBeFound(String filter) throws Exception {
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityName").value(hasItem(DEFAULT_COMMUNITY_NAME)))
            .andExpect(jsonPath("$.[*].communityDescription").value(hasItem(DEFAULT_COMMUNITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));

        // Check, that the count call also returns 1
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommunityShouldNotBeFound(String filter) throws Exception {
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommunityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCommunity() throws Exception {
        // Get the community
        restCommunityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommunity() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the community
        Community updatedCommunity = communityRepository.findById(community.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCommunity are not directly saved in db
        em.detach(updatedCommunity);
        updatedCommunity
            .creationDate(UPDATED_CREATION_DATE)
            .communityName(UPDATED_COMMUNITY_NAME)
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        CommunityDTO communityDTO = communityMapper.toDto(updatedCommunity);

        restCommunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(communityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommunityToMatchAllProperties(updatedCommunity);
    }

    @Test
    @Transactional
    void putNonExistingCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(communityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(communityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommunityWithPatch() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the community using partial update
        Community partialUpdatedCommunity = new Community();
        partialUpdatedCommunity.setId(community.getId());

        partialUpdatedCommunity
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);

        restCommunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommunity))
            )
            .andExpect(status().isOk());

        // Validate the Community in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommunityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommunity, community),
            getPersistedCommunity(community)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommunityWithPatch() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the community using partial update
        Community partialUpdatedCommunity = new Community();
        partialUpdatedCommunity.setId(community.getId());

        partialUpdatedCommunity
            .creationDate(UPDATED_CREATION_DATE)
            .communityName(UPDATED_COMMUNITY_NAME)
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);

        restCommunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommunity))
            )
            .andExpect(status().isOk());

        // Validate the Community in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommunityUpdatableFieldsEquals(partialUpdatedCommunity, getPersistedCommunity(partialUpdatedCommunity));
    }

    @Test
    @Transactional
    void patchNonExistingCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(communityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(communityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommunity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        community.setId(longCount.incrementAndGet());

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(communityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Community in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommunity() throws Exception {
        // Initialize the database
        insertedCommunity = communityRepository.saveAndFlush(community);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the community
        restCommunityMockMvc
            .perform(delete(ENTITY_API_URL_ID, community.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return communityRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Community getPersistedCommunity(Community community) {
        return communityRepository.findById(community.getId()).orElseThrow();
    }

    protected void assertPersistedCommunityToMatchAllProperties(Community expectedCommunity) {
        assertCommunityAllPropertiesEquals(expectedCommunity, getPersistedCommunity(expectedCommunity));
    }

    protected void assertPersistedCommunityToMatchUpdatableProperties(Community expectedCommunity) {
        assertCommunityAllUpdatablePropertiesEquals(expectedCommunity, getPersistedCommunity(expectedCommunity));
    }
}
