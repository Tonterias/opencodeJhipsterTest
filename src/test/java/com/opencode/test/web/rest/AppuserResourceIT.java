package com.opencode.test.web.rest;

import static com.opencode.test.domain.AppuserAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Activity;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Celeb;
import com.opencode.test.domain.Interest;
import com.opencode.test.domain.User;
import com.opencode.test.repository.AppuserRepository;
import com.opencode.test.repository.UserRepository;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.mapper.AppuserMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AppuserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppuserResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/appusers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppuserMapper appuserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppuserMockMvc;

    private Appuser appuser;

    private Appuser insertedAppuser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appuser createEntity(EntityManager em) {
        Appuser appuser = new Appuser()
            .creationDate(DEFAULT_CREATION_DATE)
            .bio(DEFAULT_BIO)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .linkedin(DEFAULT_LINKEDIN)
            .instagram(DEFAULT_INSTAGRAM)
            .birthdate(DEFAULT_BIRTHDATE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        appuser.setUser(user);
        return appuser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appuser createUpdatedEntity(EntityManager em) {
        Appuser updatedAppuser = new Appuser()
            .creationDate(UPDATED_CREATION_DATE)
            .bio(UPDATED_BIO)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .linkedin(UPDATED_LINKEDIN)
            .instagram(UPDATED_INSTAGRAM)
            .birthdate(UPDATED_BIRTHDATE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedAppuser.setUser(user);
        return updatedAppuser;
    }

    @BeforeEach
    void initTest() {
        appuser = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAppuser != null) {
            appuserRepository.delete(insertedAppuser);
            insertedAppuser = null;
        }
    }

    @Test
    @Transactional
    void createAppuser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);
        var returnedAppuserDTO = om.readValue(
            restAppuserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppuserDTO.class
        );

        // Validate the Appuser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAppuser = appuserMapper.toEntity(returnedAppuserDTO);
        assertAppuserUpdatableFieldsEquals(returnedAppuser, getPersistedAppuser(returnedAppuser));

        insertedAppuser = returnedAppuser;
    }

    @Test
    @Transactional
    void createAppuserWithExistingId() throws Exception {
        // Create the Appuser with an existing ID
        appuser.setId(1L);
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppuserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        appuser.setCreationDate(null);

        // Create the Appuser, which fails.
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        restAppuserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppusers() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())));
    }

    @Test
    @Transactional
    void getAppuser() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get the appuser
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL_ID, appuser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appuser.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()));
    }

    @Test
    @Transactional
    void getAppusersByIdFiltering() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        Long id = appuser.getId();

        defaultAppuserFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAppuserFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAppuserFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppusersByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate equals to
        defaultAppuserFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAppusersByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate in
        defaultAppuserFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllAppusersByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate is not null
        defaultAppuserFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByBioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where bio equals to
        defaultAppuserFiltering("bio.equals=" + DEFAULT_BIO, "bio.equals=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    void getAllAppusersByBioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where bio in
        defaultAppuserFiltering("bio.in=" + DEFAULT_BIO + "," + UPDATED_BIO, "bio.in=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    void getAllAppusersByBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where bio is not null
        defaultAppuserFiltering("bio.specified=true", "bio.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByBioContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where bio contains
        defaultAppuserFiltering("bio.contains=" + DEFAULT_BIO, "bio.contains=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    void getAllAppusersByBioNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where bio does not contain
        defaultAppuserFiltering("bio.doesNotContain=" + UPDATED_BIO, "bio.doesNotContain=" + DEFAULT_BIO);
    }

    @Test
    @Transactional
    void getAllAppusersByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where facebook equals to
        defaultAppuserFiltering("facebook.equals=" + DEFAULT_FACEBOOK, "facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    void getAllAppusersByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where facebook in
        defaultAppuserFiltering("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK, "facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    void getAllAppusersByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where facebook is not null
        defaultAppuserFiltering("facebook.specified=true", "facebook.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByFacebookContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where facebook contains
        defaultAppuserFiltering("facebook.contains=" + DEFAULT_FACEBOOK, "facebook.contains=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    void getAllAppusersByFacebookNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where facebook does not contain
        defaultAppuserFiltering("facebook.doesNotContain=" + UPDATED_FACEBOOK, "facebook.doesNotContain=" + DEFAULT_FACEBOOK);
    }

    @Test
    @Transactional
    void getAllAppusersByTwitterIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where twitter equals to
        defaultAppuserFiltering("twitter.equals=" + DEFAULT_TWITTER, "twitter.equals=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    void getAllAppusersByTwitterIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where twitter in
        defaultAppuserFiltering("twitter.in=" + DEFAULT_TWITTER + "," + UPDATED_TWITTER, "twitter.in=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    void getAllAppusersByTwitterIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where twitter is not null
        defaultAppuserFiltering("twitter.specified=true", "twitter.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByTwitterContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where twitter contains
        defaultAppuserFiltering("twitter.contains=" + DEFAULT_TWITTER, "twitter.contains=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    void getAllAppusersByTwitterNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where twitter does not contain
        defaultAppuserFiltering("twitter.doesNotContain=" + UPDATED_TWITTER, "twitter.doesNotContain=" + DEFAULT_TWITTER);
    }

    @Test
    @Transactional
    void getAllAppusersByLinkedinIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where linkedin equals to
        defaultAppuserFiltering("linkedin.equals=" + DEFAULT_LINKEDIN, "linkedin.equals=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    void getAllAppusersByLinkedinIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where linkedin in
        defaultAppuserFiltering("linkedin.in=" + DEFAULT_LINKEDIN + "," + UPDATED_LINKEDIN, "linkedin.in=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    void getAllAppusersByLinkedinIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where linkedin is not null
        defaultAppuserFiltering("linkedin.specified=true", "linkedin.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByLinkedinContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where linkedin contains
        defaultAppuserFiltering("linkedin.contains=" + DEFAULT_LINKEDIN, "linkedin.contains=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    void getAllAppusersByLinkedinNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where linkedin does not contain
        defaultAppuserFiltering("linkedin.doesNotContain=" + UPDATED_LINKEDIN, "linkedin.doesNotContain=" + DEFAULT_LINKEDIN);
    }

    @Test
    @Transactional
    void getAllAppusersByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where instagram equals to
        defaultAppuserFiltering("instagram.equals=" + DEFAULT_INSTAGRAM, "instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    void getAllAppusersByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where instagram in
        defaultAppuserFiltering("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM, "instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    void getAllAppusersByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where instagram is not null
        defaultAppuserFiltering("instagram.specified=true", "instagram.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByInstagramContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where instagram contains
        defaultAppuserFiltering("instagram.contains=" + DEFAULT_INSTAGRAM, "instagram.contains=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    void getAllAppusersByInstagramNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where instagram does not contain
        defaultAppuserFiltering("instagram.doesNotContain=" + UPDATED_INSTAGRAM, "instagram.doesNotContain=" + DEFAULT_INSTAGRAM);
    }

    @Test
    @Transactional
    void getAllAppusersByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where birthdate equals to
        defaultAppuserFiltering("birthdate.equals=" + DEFAULT_BIRTHDATE, "birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllAppusersByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where birthdate in
        defaultAppuserFiltering("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE, "birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllAppusersByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where birthdate is not null
        defaultAppuserFiltering("birthdate.specified=true", "birthdate.specified=false");
    }

    @Test
    @Transactional
    void getAllAppusersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = appuser.getUser();
        appuserRepository.saveAndFlush(appuser);
        Long userId = user.getId();
        // Get all the appuserList where user equals to userId
        defaultAppuserShouldBeFound("userId.equals=" + userId);

        // Get all the appuserList where user equals to (userId + 1)
        defaultAppuserShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllAppusersByInterestIsEqualToSomething() throws Exception {
        Interest interest;
        if (TestUtil.findAll(em, Interest.class).isEmpty()) {
            appuserRepository.saveAndFlush(appuser);
            interest = InterestResourceIT.createEntity();
        } else {
            interest = TestUtil.findAll(em, Interest.class).get(0);
        }
        em.persist(interest);
        em.flush();
        appuser.addInterest(interest);
        appuserRepository.saveAndFlush(appuser);
        Long interestId = interest.getId();
        // Get all the appuserList where interest equals to interestId
        defaultAppuserShouldBeFound("interestId.equals=" + interestId);

        // Get all the appuserList where interest equals to (interestId + 1)
        defaultAppuserShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }

    @Test
    @Transactional
    void getAllAppusersByActivityIsEqualToSomething() throws Exception {
        Activity activity;
        if (TestUtil.findAll(em, Activity.class).isEmpty()) {
            appuserRepository.saveAndFlush(appuser);
            activity = ActivityResourceIT.createEntity();
        } else {
            activity = TestUtil.findAll(em, Activity.class).get(0);
        }
        em.persist(activity);
        em.flush();
        appuser.addActivity(activity);
        appuserRepository.saveAndFlush(appuser);
        Long activityId = activity.getId();
        // Get all the appuserList where activity equals to activityId
        defaultAppuserShouldBeFound("activityId.equals=" + activityId);

        // Get all the appuserList where activity equals to (activityId + 1)
        defaultAppuserShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }

    @Test
    @Transactional
    void getAllAppusersByCelebIsEqualToSomething() throws Exception {
        Celeb celeb;
        if (TestUtil.findAll(em, Celeb.class).isEmpty()) {
            appuserRepository.saveAndFlush(appuser);
            celeb = CelebResourceIT.createEntity();
        } else {
            celeb = TestUtil.findAll(em, Celeb.class).get(0);
        }
        em.persist(celeb);
        em.flush();
        appuser.addCeleb(celeb);
        appuserRepository.saveAndFlush(appuser);
        Long celebId = celeb.getId();
        // Get all the appuserList where celeb equals to celebId
        defaultAppuserShouldBeFound("celebId.equals=" + celebId);

        // Get all the appuserList where celeb equals to (celebId + 1)
        defaultAppuserShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    private void defaultAppuserFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAppuserShouldBeFound(shouldBeFound);
        defaultAppuserShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppuserShouldBeFound(String filter) throws Exception {
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())));

        // Check, that the count call also returns 1
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppuserShouldNotBeFound(String filter) throws Exception {
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppuserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppuser() throws Exception {
        // Get the appuser
        restAppuserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppuser() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appuser
        Appuser updatedAppuser = appuserRepository.findById(appuser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppuser are not directly saved in db
        em.detach(updatedAppuser);
        updatedAppuser
            .creationDate(UPDATED_CREATION_DATE)
            .bio(UPDATED_BIO)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .linkedin(UPDATED_LINKEDIN)
            .instagram(UPDATED_INSTAGRAM)
            .birthdate(UPDATED_BIRTHDATE);
        AppuserDTO appuserDTO = appuserMapper.toDto(updatedAppuser);

        restAppuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appuserDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO))
            )
            .andExpect(status().isOk());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppuserToMatchAllProperties(updatedAppuser);
    }

    @Test
    @Transactional
    void putNonExistingAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appuserDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appuserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appuserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppuserWithPatch() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appuser using partial update
        Appuser partialUpdatedAppuser = new Appuser();
        partialUpdatedAppuser.setId(appuser.getId());

        partialUpdatedAppuser.bio(UPDATED_BIO).instagram(UPDATED_INSTAGRAM).birthdate(UPDATED_BIRTHDATE);

        restAppuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppuser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppuser))
            )
            .andExpect(status().isOk());

        // Validate the Appuser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppuserUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAppuser, appuser), getPersistedAppuser(appuser));
    }

    @Test
    @Transactional
    void fullUpdateAppuserWithPatch() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appuser using partial update
        Appuser partialUpdatedAppuser = new Appuser();
        partialUpdatedAppuser.setId(appuser.getId());

        partialUpdatedAppuser
            .creationDate(UPDATED_CREATION_DATE)
            .bio(UPDATED_BIO)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .linkedin(UPDATED_LINKEDIN)
            .instagram(UPDATED_INSTAGRAM)
            .birthdate(UPDATED_BIRTHDATE);

        restAppuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppuser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppuser))
            )
            .andExpect(status().isOk());

        // Validate the Appuser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppuserUpdatableFieldsEquals(partialUpdatedAppuser, getPersistedAppuser(partialUpdatedAppuser));
    }

    @Test
    @Transactional
    void patchNonExistingAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appuserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appuserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appuserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appuser.setId(longCount.incrementAndGet());

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppuserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appuserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppuser() throws Exception {
        // Initialize the database
        insertedAppuser = appuserRepository.saveAndFlush(appuser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appuser
        restAppuserMockMvc
            .perform(delete(ENTITY_API_URL_ID, appuser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appuserRepository.count();
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

    protected Appuser getPersistedAppuser(Appuser appuser) {
        return appuserRepository.findById(appuser.getId()).orElseThrow();
    }

    protected void assertPersistedAppuserToMatchAllProperties(Appuser expectedAppuser) {
        assertAppuserAllPropertiesEquals(expectedAppuser, getPersistedAppuser(expectedAppuser));
    }

    protected void assertPersistedAppuserToMatchUpdatableProperties(Appuser expectedAppuser) {
        assertAppuserAllUpdatablePropertiesEquals(expectedAppuser, getPersistedAppuser(expectedAppuser));
    }
}
