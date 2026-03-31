package com.opencode.test.web.rest;

import static com.opencode.test.domain.AppphotoAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appphoto;
import com.opencode.test.domain.Appuser;
import com.opencode.test.repository.AppphotoRepository;
import com.opencode.test.service.dto.AppphotoDTO;
import com.opencode.test.service.mapper.AppphotoMapper;
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
 * Integration tests for the {@link AppphotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppphotoResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/appphotos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppphotoRepository appphotoRepository;

    @Autowired
    private AppphotoMapper appphotoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppphotoMockMvc;

    private Appphoto appphoto;

    private Appphoto insertedAppphoto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appphoto createEntity(EntityManager em) {
        Appphoto appphoto = new Appphoto()
            .creationDate(DEFAULT_CREATION_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        appphoto.setAppuser(appuser);
        return appphoto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appphoto createUpdatedEntity(EntityManager em) {
        Appphoto updatedAppphoto = new Appphoto()
            .creationDate(UPDATED_CREATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        updatedAppphoto.setAppuser(appuser);
        return updatedAppphoto;
    }

    @BeforeEach
    void initTest() {
        appphoto = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAppphoto != null) {
            appphotoRepository.delete(insertedAppphoto);
            insertedAppphoto = null;
        }
    }

    @Test
    @Transactional
    void createAppphoto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);
        var returnedAppphotoDTO = om.readValue(
            restAppphotoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appphotoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppphotoDTO.class
        );

        // Validate the Appphoto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAppphoto = appphotoMapper.toEntity(returnedAppphotoDTO);
        assertAppphotoUpdatableFieldsEquals(returnedAppphoto, getPersistedAppphoto(returnedAppphoto));

        insertedAppphoto = returnedAppphoto;
    }

    @Test
    @Transactional
    void createAppphotoWithExistingId() throws Exception {
        // Create the Appphoto with an existing ID
        appphoto.setId(1L);
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppphotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appphotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        appphoto.setCreationDate(null);

        // Create the Appphoto, which fails.
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        restAppphotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appphotoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppphotos() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appphoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getAppphoto() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        // Get the appphoto
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL_ID, appphoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appphoto.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getAppphotosByIdFiltering() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        Long id = appphoto.getId();

        defaultAppphotoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAppphotoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAppphotoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppphotosByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate equals to
        defaultAppphotoFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllAppphotosByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate in
        defaultAppphotoFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllAppphotosByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate is not null
        defaultAppphotoFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAppphotosByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = appphoto.getAppuser();
        appphotoRepository.saveAndFlush(appphoto);
        Long appuserId = appuser.getId();
        // Get all the appphotoList where appuser equals to appuserId
        defaultAppphotoShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the appphotoList where appuser equals to (appuserId + 1)
        defaultAppphotoShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    private void defaultAppphotoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAppphotoShouldBeFound(shouldBeFound);
        defaultAppphotoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppphotoShouldBeFound(String filter) throws Exception {
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appphoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppphotoShouldNotBeFound(String filter) throws Exception {
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppphotoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppphoto() throws Exception {
        // Get the appphoto
        restAppphotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppphoto() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appphoto
        Appphoto updatedAppphoto = appphotoRepository.findById(appphoto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppphoto are not directly saved in db
        em.detach(updatedAppphoto);
        updatedAppphoto.creationDate(UPDATED_CREATION_DATE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(updatedAppphoto);

        restAppphotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appphotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appphotoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppphotoToMatchAllProperties(updatedAppphoto);
    }

    @Test
    @Transactional
    void putNonExistingAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appphotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appphotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appphotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appphotoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppphotoWithPatch() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appphoto using partial update
        Appphoto partialUpdatedAppphoto = new Appphoto();
        partialUpdatedAppphoto.setId(appphoto.getId());

        restAppphotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppphoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppphoto))
            )
            .andExpect(status().isOk());

        // Validate the Appphoto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppphotoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAppphoto, appphoto), getPersistedAppphoto(appphoto));
    }

    @Test
    @Transactional
    void fullUpdateAppphotoWithPatch() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appphoto using partial update
        Appphoto partialUpdatedAppphoto = new Appphoto();
        partialUpdatedAppphoto.setId(appphoto.getId());

        partialUpdatedAppphoto.creationDate(UPDATED_CREATION_DATE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restAppphotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppphoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppphoto))
            )
            .andExpect(status().isOk());

        // Validate the Appphoto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppphotoUpdatableFieldsEquals(partialUpdatedAppphoto, getPersistedAppphoto(partialUpdatedAppphoto));
    }

    @Test
    @Transactional
    void patchNonExistingAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appphotoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appphotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appphotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppphoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appphoto.setId(longCount.incrementAndGet());

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppphotoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appphotoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appphoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppphoto() throws Exception {
        // Initialize the database
        insertedAppphoto = appphotoRepository.saveAndFlush(appphoto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appphoto
        restAppphotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, appphoto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appphotoRepository.count();
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

    protected Appphoto getPersistedAppphoto(Appphoto appphoto) {
        return appphotoRepository.findById(appphoto.getId()).orElseThrow();
    }

    protected void assertPersistedAppphotoToMatchAllProperties(Appphoto expectedAppphoto) {
        assertAppphotoAllPropertiesEquals(expectedAppphoto, getPersistedAppphoto(expectedAppphoto));
    }

    protected void assertPersistedAppphotoToMatchUpdatableProperties(Appphoto expectedAppphoto) {
        assertAppphotoAllUpdatablePropertiesEquals(expectedAppphoto, getPersistedAppphoto(expectedAppphoto));
    }
}
