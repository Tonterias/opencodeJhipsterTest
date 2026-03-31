package com.opencode.test.web.rest;

import static com.opencode.test.domain.InterestAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Interest;
import com.opencode.test.repository.InterestRepository;
import com.opencode.test.service.InterestService;
import com.opencode.test.service.dto.InterestDTO;
import com.opencode.test.service.mapper.InterestMapper;
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
 * Integration tests for the {@link InterestResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InterestResourceIT {

    private static final String DEFAULT_INTEREST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/interests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InterestRepository interestRepository;

    @Mock
    private InterestRepository interestRepositoryMock;

    @Autowired
    private InterestMapper interestMapper;

    @Mock
    private InterestService interestServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestMockMvc;

    private Interest interest;

    private Interest insertedInterest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createEntity() {
        return new Interest().interestName(DEFAULT_INTEREST_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createUpdatedEntity() {
        return new Interest().interestName(UPDATED_INTEREST_NAME);
    }

    @BeforeEach
    void initTest() {
        interest = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInterest != null) {
            interestRepository.delete(insertedInterest);
            insertedInterest = null;
        }
    }

    @Test
    @Transactional
    void createInterest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);
        var returnedInterestDTO = om.readValue(
            restInterestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InterestDTO.class
        );

        // Validate the Interest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInterest = interestMapper.toEntity(returnedInterestDTO);
        assertInterestUpdatableFieldsEquals(returnedInterest, getPersistedInterest(returnedInterest));

        insertedInterest = returnedInterest;
    }

    @Test
    @Transactional
    void createInterestWithExistingId() throws Exception {
        // Create the Interest with an existing ID
        interest.setId(1L);
        InterestDTO interestDTO = interestMapper.toDto(interest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInterestNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        interest.setInterestName(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInterests() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].interestName").value(hasItem(DEFAULT_INTEREST_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInterestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(interestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInterestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(interestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInterestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(interestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInterestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(interestRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get the interest
        restInterestMockMvc
            .perform(get(ENTITY_API_URL_ID, interest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interest.getId().intValue()))
            .andExpect(jsonPath("$.interestName").value(DEFAULT_INTEREST_NAME));
    }

    @Test
    @Transactional
    void getInterestsByIdFiltering() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        Long id = interest.getId();

        defaultInterestFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInterestFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInterestFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInterestsByInterestNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList where interestName equals to
        defaultInterestFiltering("interestName.equals=" + DEFAULT_INTEREST_NAME, "interestName.equals=" + UPDATED_INTEREST_NAME);
    }

    @Test
    @Transactional
    void getAllInterestsByInterestNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList where interestName in
        defaultInterestFiltering(
            "interestName.in=" + DEFAULT_INTEREST_NAME + "," + UPDATED_INTEREST_NAME,
            "interestName.in=" + UPDATED_INTEREST_NAME
        );
    }

    @Test
    @Transactional
    void getAllInterestsByInterestNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList where interestName is not null
        defaultInterestFiltering("interestName.specified=true", "interestName.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestsByInterestNameContainsSomething() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList where interestName contains
        defaultInterestFiltering("interestName.contains=" + DEFAULT_INTEREST_NAME, "interestName.contains=" + UPDATED_INTEREST_NAME);
    }

    @Test
    @Transactional
    void getAllInterestsByInterestNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList where interestName does not contain
        defaultInterestFiltering(
            "interestName.doesNotContain=" + UPDATED_INTEREST_NAME,
            "interestName.doesNotContain=" + DEFAULT_INTEREST_NAME
        );
    }

    @Test
    @Transactional
    void getAllInterestsByAppuserIsEqualToSomething() throws Exception {
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            interestRepository.saveAndFlush(interest);
            appuser = AppuserResourceIT.createEntity(em);
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(appuser);
        em.flush();
        interest.addAppuser(appuser);
        interestRepository.saveAndFlush(interest);
        Long appuserId = appuser.getId();
        // Get all the interestList where appuser equals to appuserId
        defaultInterestShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the interestList where appuser equals to (appuserId + 1)
        defaultInterestShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    private void defaultInterestFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInterestShouldBeFound(shouldBeFound);
        defaultInterestShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInterestShouldBeFound(String filter) throws Exception {
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].interestName").value(hasItem(DEFAULT_INTEREST_NAME)));

        // Check, that the count call also returns 1
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInterestShouldNotBeFound(String filter) throws Exception {
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInterest() throws Exception {
        // Get the interest
        restInterestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest
        Interest updatedInterest = interestRepository.findById(interest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInterest are not directly saved in db
        em.detach(updatedInterest);
        updatedInterest.interestName(UPDATED_INTEREST_NAME);
        InterestDTO interestDTO = interestMapper.toDto(updatedInterest);

        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInterestToMatchAllProperties(updatedInterest);
    }

    @Test
    @Transactional
    void putNonExistingInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterestUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedInterest, interest), getPersistedInterest(interest));
    }

    @Test
    @Transactional
    void fullUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        partialUpdatedInterest.interestName(UPDATED_INTEREST_NAME);

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterestUpdatableFieldsEquals(partialUpdatedInterest, getPersistedInterest(partialUpdatedInterest));
    }

    @Test
    @Transactional
    void patchNonExistingInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the interest
        restInterestMockMvc
            .perform(delete(ENTITY_API_URL_ID, interest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return interestRepository.count();
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

    protected Interest getPersistedInterest(Interest interest) {
        return interestRepository.findById(interest.getId()).orElseThrow();
    }

    protected void assertPersistedInterestToMatchAllProperties(Interest expectedInterest) {
        assertInterestAllPropertiesEquals(expectedInterest, getPersistedInterest(expectedInterest));
    }

    protected void assertPersistedInterestToMatchUpdatableProperties(Interest expectedInterest) {
        assertInterestAllUpdatablePropertiesEquals(expectedInterest, getPersistedInterest(expectedInterest));
    }
}
