package com.opencode.test.web.rest;

import static com.opencode.test.domain.CelebAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Celeb;
import com.opencode.test.repository.CelebRepository;
import com.opencode.test.service.CelebService;
import com.opencode.test.service.dto.CelebDTO;
import com.opencode.test.service.mapper.CelebMapper;
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
 * Integration tests for the {@link CelebResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CelebResourceIT {

    private static final String DEFAULT_CELEB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CELEB_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/celebs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CelebRepository celebRepository;

    @Mock
    private CelebRepository celebRepositoryMock;

    @Autowired
    private CelebMapper celebMapper;

    @Mock
    private CelebService celebServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCelebMockMvc;

    private Celeb celeb;

    private Celeb insertedCeleb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Celeb createEntity() {
        return new Celeb().celebName(DEFAULT_CELEB_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Celeb createUpdatedEntity() {
        return new Celeb().celebName(UPDATED_CELEB_NAME);
    }

    @BeforeEach
    void initTest() {
        celeb = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCeleb != null) {
            celebRepository.delete(insertedCeleb);
            insertedCeleb = null;
        }
    }

    @Test
    @Transactional
    void createCeleb() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);
        var returnedCelebDTO = om.readValue(
            restCelebMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CelebDTO.class
        );

        // Validate the Celeb in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCeleb = celebMapper.toEntity(returnedCelebDTO);
        assertCelebUpdatableFieldsEquals(returnedCeleb, getPersistedCeleb(returnedCeleb));

        insertedCeleb = returnedCeleb;
    }

    @Test
    @Transactional
    void createCelebWithExistingId() throws Exception {
        // Create the Celeb with an existing ID
        celeb.setId(1L);
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCelebMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCelebNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        celeb.setCelebName(null);

        // Create the Celeb, which fails.
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        restCelebMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCelebs() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList
        restCelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(celeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].celebName").value(hasItem(DEFAULT_CELEB_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCelebsWithEagerRelationshipsIsEnabled() throws Exception {
        when(celebServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCelebMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(celebServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCelebsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(celebServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCelebMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(celebRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCeleb() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get the celeb
        restCelebMockMvc
            .perform(get(ENTITY_API_URL_ID, celeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(celeb.getId().intValue()))
            .andExpect(jsonPath("$.celebName").value(DEFAULT_CELEB_NAME));
    }

    @Test
    @Transactional
    void getCelebsByIdFiltering() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        Long id = celeb.getId();

        defaultCelebFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCelebFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCelebFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCelebsByCelebNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList where celebName equals to
        defaultCelebFiltering("celebName.equals=" + DEFAULT_CELEB_NAME, "celebName.equals=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCelebsByCelebNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList where celebName in
        defaultCelebFiltering("celebName.in=" + DEFAULT_CELEB_NAME + "," + UPDATED_CELEB_NAME, "celebName.in=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCelebsByCelebNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList where celebName is not null
        defaultCelebFiltering("celebName.specified=true", "celebName.specified=false");
    }

    @Test
    @Transactional
    void getAllCelebsByCelebNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList where celebName contains
        defaultCelebFiltering("celebName.contains=" + DEFAULT_CELEB_NAME, "celebName.contains=" + UPDATED_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCelebsByCelebNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        // Get all the celebList where celebName does not contain
        defaultCelebFiltering("celebName.doesNotContain=" + UPDATED_CELEB_NAME, "celebName.doesNotContain=" + DEFAULT_CELEB_NAME);
    }

    @Test
    @Transactional
    void getAllCelebsByAppuserIsEqualToSomething() throws Exception {
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            celebRepository.saveAndFlush(celeb);
            appuser = AppuserResourceIT.createEntity(em);
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(appuser);
        em.flush();
        celeb.addAppuser(appuser);
        celebRepository.saveAndFlush(celeb);
        Long appuserId = appuser.getId();
        // Get all the celebList where appuser equals to appuserId
        defaultCelebShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the celebList where appuser equals to (appuserId + 1)
        defaultCelebShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    private void defaultCelebFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCelebShouldBeFound(shouldBeFound);
        defaultCelebShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCelebShouldBeFound(String filter) throws Exception {
        restCelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(celeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].celebName").value(hasItem(DEFAULT_CELEB_NAME)));

        // Check, that the count call also returns 1
        restCelebMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCelebShouldNotBeFound(String filter) throws Exception {
        restCelebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCelebMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCeleb() throws Exception {
        // Get the celeb
        restCelebMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCeleb() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the celeb
        Celeb updatedCeleb = celebRepository.findById(celeb.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCeleb are not directly saved in db
        em.detach(updatedCeleb);
        updatedCeleb.celebName(UPDATED_CELEB_NAME);
        CelebDTO celebDTO = celebMapper.toDto(updatedCeleb);

        restCelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, celebDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO))
            )
            .andExpect(status().isOk());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCelebToMatchAllProperties(updatedCeleb);
    }

    @Test
    @Transactional
    void putNonExistingCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, celebDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(celebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(celebDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCelebWithPatch() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the celeb using partial update
        Celeb partialUpdatedCeleb = new Celeb();
        partialUpdatedCeleb.setId(celeb.getId());

        partialUpdatedCeleb.celebName(UPDATED_CELEB_NAME);

        restCelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCeleb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCeleb))
            )
            .andExpect(status().isOk());

        // Validate the Celeb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCelebUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCeleb, celeb), getPersistedCeleb(celeb));
    }

    @Test
    @Transactional
    void fullUpdateCelebWithPatch() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the celeb using partial update
        Celeb partialUpdatedCeleb = new Celeb();
        partialUpdatedCeleb.setId(celeb.getId());

        partialUpdatedCeleb.celebName(UPDATED_CELEB_NAME);

        restCelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCeleb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCeleb))
            )
            .andExpect(status().isOk());

        // Validate the Celeb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCelebUpdatableFieldsEquals(partialUpdatedCeleb, getPersistedCeleb(partialUpdatedCeleb));
    }

    @Test
    @Transactional
    void patchNonExistingCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, celebDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(celebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(celebDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCeleb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        celeb.setId(longCount.incrementAndGet());

        // Create the Celeb
        CelebDTO celebDTO = celebMapper.toDto(celeb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCelebMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(celebDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Celeb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCeleb() throws Exception {
        // Initialize the database
        insertedCeleb = celebRepository.saveAndFlush(celeb);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the celeb
        restCelebMockMvc
            .perform(delete(ENTITY_API_URL_ID, celeb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return celebRepository.count();
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

    protected Celeb getPersistedCeleb(Celeb celeb) {
        return celebRepository.findById(celeb.getId()).orElseThrow();
    }

    protected void assertPersistedCelebToMatchAllProperties(Celeb expectedCeleb) {
        assertCelebAllPropertiesEquals(expectedCeleb, getPersistedCeleb(expectedCeleb));
    }

    protected void assertPersistedCelebToMatchUpdatableProperties(Celeb expectedCeleb) {
        assertCelebAllUpdatablePropertiesEquals(expectedCeleb, getPersistedCeleb(expectedCeleb));
    }
}
