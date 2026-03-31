package com.opencode.test.web.rest;

import static com.opencode.test.domain.CcelebAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Cceleb;
import com.opencode.test.domain.Community;
import com.opencode.test.repository.CcelebRepository;
import com.opencode.test.service.CcelebService;
import com.opencode.test.service.dto.CcelebDTO;
import com.opencode.test.service.mapper.CcelebMapper;
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
 * Integration tests for the {@link CcelebResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CcelebResourceIT {

    private static final String DEFAULT_CELEB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CELEB_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ccelebs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CcelebRepository ccelebRepository;

    @Mock
    private CcelebRepository ccelebRepositoryMock;

    @Autowired
    private CcelebMapper ccelebMapper;

    @Mock
    private CcelebService ccelebServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCcelebMockMvc;

    private Cceleb cceleb;

    private Cceleb insertedCceleb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cceleb createEntity() {
        return new Cceleb().celebName(DEFAULT_CELEB_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cceleb createUpdatedEntity() {
        return new Cceleb().celebName(UPDATED_CELEB_NAME);
    }

    @BeforeEach
    void initTest() {
        cceleb = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCceleb != null) {
            ccelebRepository.delete(insertedCceleb);
            insertedCceleb = null;
        }
    }

    @Test
    @Transactional
    void createCceleb() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);
        var returnedCcelebDTO = om.readValue(
            restCcelebMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CcelebDTO.class
        );

        // Validate the Cceleb in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCceleb = ccelebMapper.toEntity(returnedCcelebDTO);
        assertCcelebUpdatableFieldsEquals(returnedCceleb, getPersistedCceleb(returnedCceleb));

        insertedCceleb = returnedCceleb;
    }

    @Test
    @Transactional
    void createCcelebWithExistingId() throws Exception {
        // Create the Cceleb with an existing ID
        cceleb.setId(1L);
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCcelebMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCelebNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cceleb.setCelebName(null);

        // Create the Cceleb, which fails.
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        restCcelebMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCcelebs() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cceleb.getId().intValue())))
            .andExpect(jsonPath("$.[*].celebName").value(hasItem(DEFAULT_CELEB_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCcelebsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ccelebServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCcelebMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ccelebServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCcelebsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ccelebServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCcelebMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ccelebRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCceleb() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get the cceleb
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL_ID, cceleb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cceleb.getId().intValue()))
            .andExpect(jsonPath("$.celebName").value(DEFAULT_CELEB_NAME));
    }

    @Test
    @Transactional
    void getCcelebsByIdFiltering() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        Long id = cceleb.getId();

        defaultCcelebFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCcelebFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCcelebFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCcelebsByCelebNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList where celebName equals to
        defaultCcelebFiltering("celebName.equals=" + DEFAULT_CELEB_NAME, "celebName.equals=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCcelebsByCelebNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList where celebName in
        defaultCcelebFiltering("celebName.in=" + DEFAULT_CELEB_NAME + "," + UPDATED_CELEB_NAME, "celebName.in=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCcelebsByCelebNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList where celebName is not null
        defaultCcelebFiltering("celebName.specified=true", "celebName.specified=false");
    }

    @Test
    @Transactional
    void getAllCcelebsByCelebNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList where celebName contains
        defaultCcelebFiltering("celebName.contains=" + DEFAULT_CELEB_NAME, "celebName.contains=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCcelebsByCelebNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        // Get all the ccelebList where celebName does not contain
        defaultCcelebFiltering("celebName.doesNotContain=" + UPDATED_CELEB_NAME, "celebName.doesNotContain=" + DEFAULT_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCcelebsByCommunityIsEqualToSomething() throws Exception {
        Community community;
        if (TestUtil.findAll(em, Community.class).isEmpty()) {
            ccelebRepository.saveAndFlush(cceleb);
            community = CommunityResourceIT.createEntity(em);
        } else {
            community = TestUtil.findAll(em, Community.class).get(0);
        }
        em.persist(community);
        em.flush();
        cceleb.addCommunity(community);
        ccelebRepository.saveAndFlush(cceleb);
        Long communityId = community.getId();
        // Get all the ccelebList where community equals to communityId
        defaultCcelebShouldBeFound("communityId.equals=" + communityId);

        // Get all the ccelebList where community equals to (communityId + 1)
        defaultCcelebShouldNotBeFound("communityId.equals=" + (communityId + 1));
    }

    private void defaultCcelebFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCcelebShouldBeFound(shouldBeFound);
        defaultCcelebShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCcelebShouldBeFound(String filter) throws Exception {
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cceleb.getId().intValue())))
            .andExpect(jsonPath("$.[*].celebName").value(hasItem(DEFAULT_CELEB_NAME)));

        // Check, that the count call also returns 1
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCcelebShouldNotBeFound(String filter) throws Exception {
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCcelebMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCceleb() throws Exception {
        // Get the cceleb
        restCcelebMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCceleb() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cceleb
        Cceleb updatedCceleb = ccelebRepository.findById(cceleb.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCceleb are not directly saved in db
        em.detach(updatedCceleb);
        updatedCceleb.celebName(UPDATED_CELEB_NAME);
        CcelebDTO ccelebDTO = ccelebMapper.toDto(updatedCceleb);

        restCcelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ccelebDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCcelebToMatchAllProperties(updatedCceleb);
    }

    @Test
    @Transactional
    void putNonExistingCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ccelebDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ccelebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ccelebDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCcelebWithPatch() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cceleb using partial update
        Cceleb partialUpdatedCceleb = new Cceleb();
        partialUpdatedCceleb.setId(cceleb.getId());

        restCcelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCceleb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCceleb))
            )
            .andExpect(status().isOk());

        // Validate the Cceleb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCcelebUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCceleb, cceleb), getPersistedCceleb(cceleb));
    }

    @Test
    @Transactional
    void fullUpdateCcelebWithPatch() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cceleb using partial update
        Cceleb partialUpdatedCceleb = new Cceleb();
        partialUpdatedCceleb.setId(cceleb.getId());

        partialUpdatedCceleb.celebName(UPDATED_CELEB_NAME);

        restCcelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCceleb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCceleb))
            )
            .andExpect(status().isOk());

        // Validate the Cceleb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCcelebUpdatableFieldsEquals(partialUpdatedCceleb, getPersistedCceleb(partialUpdatedCceleb));
    }

    @Test
    @Transactional
    void patchNonExistingCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ccelebDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ccelebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ccelebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCceleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cceleb.setId(longCount.incrementAndGet());

        // Create the Cceleb
        CcelebDTO ccelebDTO = ccelebMapper.toDto(cceleb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCcelebMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ccelebDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cceleb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCceleb() throws Exception {
        // Initialize the database
        insertedCceleb = ccelebRepository.saveAndFlush(cceleb);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cceleb
        restCcelebMockMvc
            .perform(delete(ENTITY_API_URL_ID, cceleb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ccelebRepository.count();
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

    protected Cceleb getPersistedCceleb(Cceleb cceleb) {
        return ccelebRepository.findById(cceleb.getId()).orElseThrow();
    }

    protected void assertPersistedCcelebToMatchAllProperties(Cceleb expectedCceleb) {
        assertCcelebAllPropertiesEquals(expectedCceleb, getPersistedCceleb(expectedCceleb));
    }

    protected void assertPersistedCcelebToMatchUpdatableProperties(Cceleb expectedCceleb) {
        assertCcelebAllUpdatablePropertiesEquals(expectedCceleb, getPersistedCceleb(expectedCceleb));
    }
}
