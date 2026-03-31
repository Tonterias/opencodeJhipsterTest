package com.opencode.test.web.rest;

import static com.opencode.test.domain.CactivityAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Cactivity;
import com.opencode.test.domain.Community;
import com.opencode.test.repository.CactivityRepository;
import com.opencode.test.service.CactivityService;
import com.opencode.test.service.dto.CactivityDTO;
import com.opencode.test.service.mapper.CactivityMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CactivityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CactivityResourceIT {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cactivities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CactivityRepository cactivityRepository;

    @Mock
    private CactivityRepository cactivityRepositoryMock;

    @Autowired
    private CactivityMapper cactivityMapper;

    @Mock
    private CactivityService cactivityServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCactivityMockMvc;

    private Cactivity cactivity;

    private Cactivity insertedCactivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cactivity createEntity() {
        return new Cactivity().activityName(DEFAULT_ACTIVITY_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cactivity createUpdatedEntity() {
        return new Cactivity().activityName(UPDATED_ACTIVITY_NAME);
    }

    @BeforeEach
    void initTest() {
        cactivity = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCactivity != null) {
            cactivityRepository.delete(insertedCactivity);
            insertedCactivity = null;
        }
    }

    @Test
    @Transactional
    void createCactivity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);
        var returnedCactivityDTO = om.readValue(
            restCactivityMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cactivityDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CactivityDTO.class
        );

        // Validate the Cactivity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCactivity = cactivityMapper.toEntity(returnedCactivityDTO);
        assertCactivityUpdatableFieldsEquals(returnedCactivity, getPersistedCactivity(returnedCactivity));

        insertedCactivity = returnedCactivity;
    }

    @Test
    @Transactional
    void createCactivityWithExistingId() throws Exception {
        // Create the Cactivity with an existing ID
        cactivity.setId(1L);
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCactivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cactivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActivityNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cactivity.setActivityName(null);

        // Create the Cactivity, which fails.
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        restCactivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cactivityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCactivities() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cactivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCactivitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(cactivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCactivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cactivityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCactivitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cactivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCactivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cactivityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCactivity() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get the cactivity
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL_ID, cactivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cactivity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME));
    }

    @Test
    @Transactional
    void getCactivitiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        Long id = cactivity.getId();

        defaultCactivityFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCactivityFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCactivityFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCactivitiesByActivityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList where activityName equals to
        defaultCactivityFiltering("activityName.equals=" + DEFAULT_ACTIVITY_NAME, "activityName.equals=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    void getAllCactivitiesByActivityNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList where activityName in
        defaultCactivityFiltering(
            "activityName.in=" + DEFAULT_ACTIVITY_NAME + "," + UPDATED_ACTIVITY_NAME,
            "activityName.in=" + UPDATED_ACTIVITY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCactivitiesByActivityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList where activityName is not null
        defaultCactivityFiltering("activityName.specified=true", "activityName.specified=false");
    }

    @Test
    @Transactional
    void getAllCactivitiesByActivityNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList where activityName contains
        defaultCactivityFiltering("activityName.contains=" + DEFAULT_ACTIVITY_NAME, "activityName.contains=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    void getAllCactivitiesByActivityNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        // Get all the cactivityList where activityName does not contain
        defaultCactivityFiltering(
            "activityName.doesNotContain=" + UPDATED_ACTIVITY_NAME,
            "activityName.doesNotContain=" + DEFAULT_ACTIVITY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCactivitiesByCommunityIsEqualToSomething() throws Exception {
        Community community;
        if (TestUtil.findAll(em, Community.class).isEmpty()) {
            cactivityRepository.saveAndFlush(cactivity);
            community = CommunityResourceIT.createEntity(em);
        } else {
            community = TestUtil.findAll(em, Community.class).get(0);
        }
        em.persist(community);
        em.flush();
        cactivity.addCommunity(community);
        cactivityRepository.saveAndFlush(cactivity);
        Long communityId = community.getId();
        // Get all the cactivityList where community equals to communityId
        defaultCactivityShouldBeFound("communityId.equals=" + communityId);

        // Get all the cactivityList where community equals to (communityId + 1)
        defaultCactivityShouldNotBeFound("communityId.equals=" + (communityId + 1));
    }

    private void defaultCactivityFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCactivityShouldBeFound(shouldBeFound);
        defaultCactivityShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCactivityShouldBeFound(String filter) throws Exception {
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cactivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME)));

        // Check, that the count call also returns 1
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCactivityShouldNotBeFound(String filter) throws Exception {
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCactivityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCactivity() throws Exception {
        // Get the cactivity
        restCactivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCactivity() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cactivity
        Cactivity updatedCactivity = cactivityRepository.findById(cactivity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCactivity are not directly saved in db
        em.detach(updatedCactivity);
        updatedCactivity.activityName(UPDATED_ACTIVITY_NAME);
        CactivityDTO cactivityDTO = cactivityMapper.toDto(updatedCactivity);

        restCactivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cactivityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cactivityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCactivityToMatchAllProperties(updatedCactivity);
    }

    @Test
    @Transactional
    void putNonExistingCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cactivityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cactivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cactivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cactivityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCactivityWithPatch() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cactivity using partial update
        Cactivity partialUpdatedCactivity = new Cactivity();
        partialUpdatedCactivity.setId(cactivity.getId());

        restCactivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCactivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCactivity))
            )
            .andExpect(status().isOk());

        // Validate the Cactivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCactivityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCactivity, cactivity),
            getPersistedCactivity(cactivity)
        );
    }

    @Test
    @Transactional
    void fullUpdateCactivityWithPatch() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cactivity using partial update
        Cactivity partialUpdatedCactivity = new Cactivity();
        partialUpdatedCactivity.setId(cactivity.getId());

        partialUpdatedCactivity.activityName(UPDATED_ACTIVITY_NAME);

        restCactivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCactivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCactivity))
            )
            .andExpect(status().isOk());

        // Validate the Cactivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCactivityUpdatableFieldsEquals(partialUpdatedCactivity, getPersistedCactivity(partialUpdatedCactivity));
    }

    @Test
    @Transactional
    void patchNonExistingCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cactivityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cactivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cactivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCactivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cactivity.setId(longCount.incrementAndGet());

        // Create the Cactivity
        CactivityDTO cactivityDTO = cactivityMapper.toDto(cactivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCactivityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cactivityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cactivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCactivity() throws Exception {
        // Initialize the database
        insertedCactivity = cactivityRepository.saveAndFlush(cactivity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cactivity
        restCactivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, cactivity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cactivityRepository.count();
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

    protected Cactivity getPersistedCactivity(Cactivity cactivity) {
        return cactivityRepository.findById(cactivity.getId()).orElseThrow();
    }

    protected void assertPersistedCactivityToMatchAllProperties(Cactivity expectedCactivity) {
        assertCactivityAllPropertiesEquals(expectedCactivity, getPersistedCactivity(expectedCactivity));
    }

    protected void assertPersistedCactivityToMatchUpdatableProperties(Cactivity expectedCactivity) {
        assertCactivityAllUpdatablePropertiesEquals(expectedCactivity, getPersistedCactivity(expectedCactivity));
    }
}
