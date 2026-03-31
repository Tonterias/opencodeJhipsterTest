package com.opencode.test.web.rest;

import static com.opencode.test.domain.CinterestAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Cinterest;
import com.opencode.test.domain.Community;
import com.opencode.test.repository.CinterestRepository;
import com.opencode.test.service.CinterestService;
import com.opencode.test.service.dto.CinterestDTO;
import com.opencode.test.service.mapper.CinterestMapper;
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
 * Integration tests for the {@link CinterestResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CinterestResourceIT {

    private static final String DEFAULT_INTEREST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cinterests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CinterestRepository cinterestRepository;

    @Mock
    private CinterestRepository cinterestRepositoryMock;

    @Autowired
    private CinterestMapper cinterestMapper;

    @Mock
    private CinterestService cinterestServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCinterestMockMvc;

    private Cinterest cinterest;

    private Cinterest insertedCinterest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinterest createEntity() {
        return new Cinterest().interestName(DEFAULT_INTEREST_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinterest createUpdatedEntity() {
        return new Cinterest().interestName(UPDATED_INTEREST_NAME);
    }

    @BeforeEach
    void initTest() {
        cinterest = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCinterest != null) {
            cinterestRepository.delete(insertedCinterest);
            insertedCinterest = null;
        }
    }

    @Test
    @Transactional
    void createCinterest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);
        var returnedCinterestDTO = om.readValue(
            restCinterestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cinterestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CinterestDTO.class
        );

        // Validate the Cinterest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCinterest = cinterestMapper.toEntity(returnedCinterestDTO);
        assertCinterestUpdatableFieldsEquals(returnedCinterest, getPersistedCinterest(returnedCinterest));

        insertedCinterest = returnedCinterest;
    }

    @Test
    @Transactional
    void createCinterestWithExistingId() throws Exception {
        // Create the Cinterest with an existing ID
        cinterest.setId(1L);
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCinterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cinterestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInterestNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cinterest.setInterestName(null);

        // Create the Cinterest, which fails.
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        restCinterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cinterestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCinterests() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinterest.getId().intValue())))
            .andExpect(jsonPath("$.[*].interestName").value(hasItem(DEFAULT_INTEREST_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCinterestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(cinterestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCinterestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cinterestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCinterestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cinterestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCinterestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cinterestRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCinterest() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get the cinterest
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL_ID, cinterest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cinterest.getId().intValue()))
            .andExpect(jsonPath("$.interestName").value(DEFAULT_INTEREST_NAME));
    }

    @Test
    @Transactional
    void getCinterestsByIdFiltering() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        Long id = cinterest.getId();

        defaultCinterestFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCinterestFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCinterestFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCinterestsByInterestNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList where interestName equals to
        defaultCinterestFiltering("interestName.equals=" + DEFAULT_INTEREST_NAME, "interestName.equals=" + UPDATED_INTEREST_NAME);
    }

    @Test
    @Transactional
    void getAllCinterestsByInterestNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList where interestName in
        defaultCinterestFiltering(
            "interestName.in=" + DEFAULT_INTEREST_NAME + "," + UPDATED_INTEREST_NAME,
            "interestName.in=" + UPDATED_INTEREST_NAME
        );
    }

    @Test
    @Transactional
    void getAllCinterestsByInterestNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList where interestName is not null
        defaultCinterestFiltering("interestName.specified=true", "interestName.specified=false");
    }

    @Test
    @Transactional
    void getAllCinterestsByInterestNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList where interestName contains
        defaultCinterestFiltering("interestName.contains=" + DEFAULT_INTEREST_NAME, "interestName.contains=" + UPDATED_INTEREST_NAME);
    }

    @Test
    @Transactional
    void getAllCinterestsByInterestNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        // Get all the cinterestList where interestName does not contain
        defaultCinterestFiltering(
            "interestName.doesNotContain=" + UPDATED_INTEREST_NAME,
            "interestName.doesNotContain=" + DEFAULT_INTEREST_NAME
        );
    }

    @Test
    @Transactional
    void getAllCinterestsByCommunityIsEqualToSomething() throws Exception {
        Community community;
        if (TestUtil.findAll(em, Community.class).isEmpty()) {
            cinterestRepository.saveAndFlush(cinterest);
            community = CommunityResourceIT.createEntity(em);
        } else {
            community = TestUtil.findAll(em, Community.class).get(0);
        }
        em.persist(community);
        em.flush();
        cinterest.addCommunity(community);
        cinterestRepository.saveAndFlush(cinterest);
        Long communityId = community.getId();
        // Get all the cinterestList where community equals to communityId
        defaultCinterestShouldBeFound("communityId.equals=" + communityId);

        // Get all the cinterestList where community equals to (communityId + 1)
        defaultCinterestShouldNotBeFound("communityId.equals=" + (communityId + 1));
    }

    private void defaultCinterestFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCinterestShouldBeFound(shouldBeFound);
        defaultCinterestShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCinterestShouldBeFound(String filter) throws Exception {
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinterest.getId().intValue())))
            .andExpect(jsonPath("$.[*].interestName").value(hasItem(DEFAULT_INTEREST_NAME)));

        // Check, that the count call also returns 1
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCinterestShouldNotBeFound(String filter) throws Exception {
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCinterestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCinterest() throws Exception {
        // Get the cinterest
        restCinterestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCinterest() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cinterest
        Cinterest updatedCinterest = cinterestRepository.findById(cinterest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCinterest are not directly saved in db
        em.detach(updatedCinterest);
        updatedCinterest.interestName(UPDATED_INTEREST_NAME);
        CinterestDTO cinterestDTO = cinterestMapper.toDto(updatedCinterest);

        restCinterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cinterestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cinterestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCinterestToMatchAllProperties(updatedCinterest);
    }

    @Test
    @Transactional
    void putNonExistingCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cinterestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cinterestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cinterestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cinterestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCinterestWithPatch() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cinterest using partial update
        Cinterest partialUpdatedCinterest = new Cinterest();
        partialUpdatedCinterest.setId(cinterest.getId());

        partialUpdatedCinterest.interestName(UPDATED_INTEREST_NAME);

        restCinterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCinterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCinterest))
            )
            .andExpect(status().isOk());

        // Validate the Cinterest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCinterestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCinterest, cinterest),
            getPersistedCinterest(cinterest)
        );
    }

    @Test
    @Transactional
    void fullUpdateCinterestWithPatch() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cinterest using partial update
        Cinterest partialUpdatedCinterest = new Cinterest();
        partialUpdatedCinterest.setId(cinterest.getId());

        partialUpdatedCinterest.interestName(UPDATED_INTEREST_NAME);

        restCinterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCinterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCinterest))
            )
            .andExpect(status().isOk());

        // Validate the Cinterest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCinterestUpdatableFieldsEquals(partialUpdatedCinterest, getPersistedCinterest(partialUpdatedCinterest));
    }

    @Test
    @Transactional
    void patchNonExistingCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cinterestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cinterestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cinterestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCinterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cinterest.setId(longCount.incrementAndGet());

        // Create the Cinterest
        CinterestDTO cinterestDTO = cinterestMapper.toDto(cinterest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCinterestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cinterestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cinterest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCinterest() throws Exception {
        // Initialize the database
        insertedCinterest = cinterestRepository.saveAndFlush(cinterest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cinterest
        restCinterestMockMvc
            .perform(delete(ENTITY_API_URL_ID, cinterest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cinterestRepository.count();
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

    protected Cinterest getPersistedCinterest(Cinterest cinterest) {
        return cinterestRepository.findById(cinterest.getId()).orElseThrow();
    }

    protected void assertPersistedCinterestToMatchAllProperties(Cinterest expectedCinterest) {
        assertCinterestAllPropertiesEquals(expectedCinterest, getPersistedCinterest(expectedCinterest));
    }

    protected void assertPersistedCinterestToMatchUpdatableProperties(Cinterest expectedCinterest) {
        assertCinterestAllUpdatablePropertiesEquals(expectedCinterest, getPersistedCinterest(expectedCinterest));
    }
}
