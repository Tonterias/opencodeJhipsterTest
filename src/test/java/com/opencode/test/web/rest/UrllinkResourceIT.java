package com.opencode.test.web.rest;

import static com.opencode.test.domain.UrllinkAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Urllink;
import com.opencode.test.repository.UrllinkRepository;
import com.opencode.test.service.dto.UrllinkDTO;
import com.opencode.test.service.mapper.UrllinkMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link UrllinkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UrllinkResourceIT {

    private static final String DEFAULT_LINK_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/urllinks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UrllinkRepository urllinkRepository;

    @Autowired
    private UrllinkMapper urllinkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUrllinkMockMvc;

    private Urllink urllink;

    private Urllink insertedUrllink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Urllink createEntity() {
        return new Urllink().linkText(DEFAULT_LINK_TEXT).linkURL(DEFAULT_LINK_URL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Urllink createUpdatedEntity() {
        return new Urllink().linkText(UPDATED_LINK_TEXT).linkURL(UPDATED_LINK_URL);
    }

    @BeforeEach
    void initTest() {
        urllink = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUrllink != null) {
            urllinkRepository.delete(insertedUrllink);
            insertedUrllink = null;
        }
    }

    @Test
    @Transactional
    void createUrllink() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);
        var returnedUrllinkDTO = om.readValue(
            restUrllinkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UrllinkDTO.class
        );

        // Validate the Urllink in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUrllink = urllinkMapper.toEntity(returnedUrllinkDTO);
        assertUrllinkUpdatableFieldsEquals(returnedUrllink, getPersistedUrllink(returnedUrllink));

        insertedUrllink = returnedUrllink;
    }

    @Test
    @Transactional
    void createUrllinkWithExistingId() throws Exception {
        // Create the Urllink with an existing ID
        urllink.setId(1L);
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrllinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLinkTextIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        urllink.setLinkText(null);

        // Create the Urllink, which fails.
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        restUrllinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkURLIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        urllink.setLinkURL(null);

        // Create the Urllink, which fails.
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        restUrllinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUrllinks() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urllink.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)));
    }

    @Test
    @Transactional
    void getUrllink() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get the urllink
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL_ID, urllink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(urllink.getId().intValue()))
            .andExpect(jsonPath("$.linkText").value(DEFAULT_LINK_TEXT))
            .andExpect(jsonPath("$.linkURL").value(DEFAULT_LINK_URL));
    }

    @Test
    @Transactional
    void getUrllinksByIdFiltering() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        Long id = urllink.getId();

        defaultUrllinkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUrllinkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUrllinkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkTextIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText equals to
        defaultUrllinkFiltering("linkText.equals=" + DEFAULT_LINK_TEXT, "linkText.equals=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkTextIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText in
        defaultUrllinkFiltering("linkText.in=" + DEFAULT_LINK_TEXT + "," + UPDATED_LINK_TEXT, "linkText.in=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText is not null
        defaultUrllinkFiltering("linkText.specified=true", "linkText.specified=false");
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkTextContainsSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText contains
        defaultUrllinkFiltering("linkText.contains=" + DEFAULT_LINK_TEXT, "linkText.contains=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkTextNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText does not contain
        defaultUrllinkFiltering("linkText.doesNotContain=" + UPDATED_LINK_TEXT, "linkText.doesNotContain=" + DEFAULT_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkURLIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL equals to
        defaultUrllinkFiltering("linkURL.equals=" + DEFAULT_LINK_URL, "linkURL.equals=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkURLIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL in
        defaultUrllinkFiltering("linkURL.in=" + DEFAULT_LINK_URL + "," + UPDATED_LINK_URL, "linkURL.in=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL is not null
        defaultUrllinkFiltering("linkURL.specified=true", "linkURL.specified=false");
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkURLContainsSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL contains
        defaultUrllinkFiltering("linkURL.contains=" + DEFAULT_LINK_URL, "linkURL.contains=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllUrllinksByLinkURLNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL does not contain
        defaultUrllinkFiltering("linkURL.doesNotContain=" + UPDATED_LINK_URL, "linkURL.doesNotContain=" + DEFAULT_LINK_URL);
    }

    private void defaultUrllinkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUrllinkShouldBeFound(shouldBeFound);
        defaultUrllinkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUrllinkShouldBeFound(String filter) throws Exception {
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urllink.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)));

        // Check, that the count call also returns 1
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUrllinkShouldNotBeFound(String filter) throws Exception {
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUrllinkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUrllink() throws Exception {
        // Get the urllink
        restUrllinkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUrllink() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the urllink
        Urllink updatedUrllink = urllinkRepository.findById(urllink.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUrllink are not directly saved in db
        em.detach(updatedUrllink);
        updatedUrllink.linkText(UPDATED_LINK_TEXT).linkURL(UPDATED_LINK_URL);
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(updatedUrllink);

        restUrllinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, urllinkDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO))
            )
            .andExpect(status().isOk());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUrllinkToMatchAllProperties(updatedUrllink);
    }

    @Test
    @Transactional
    void putNonExistingUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, urllinkDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(urllinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(urllinkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUrllinkWithPatch() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the urllink using partial update
        Urllink partialUpdatedUrllink = new Urllink();
        partialUpdatedUrllink.setId(urllink.getId());

        partialUpdatedUrllink.linkURL(UPDATED_LINK_URL);

        restUrllinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUrllink.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUrllink))
            )
            .andExpect(status().isOk());

        // Validate the Urllink in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrllinkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUrllink, urllink), getPersistedUrllink(urllink));
    }

    @Test
    @Transactional
    void fullUpdateUrllinkWithPatch() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the urllink using partial update
        Urllink partialUpdatedUrllink = new Urllink();
        partialUpdatedUrllink.setId(urllink.getId());

        partialUpdatedUrllink.linkText(UPDATED_LINK_TEXT).linkURL(UPDATED_LINK_URL);

        restUrllinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUrllink.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUrllink))
            )
            .andExpect(status().isOk());

        // Validate the Urllink in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrllinkUpdatableFieldsEquals(partialUpdatedUrllink, getPersistedUrllink(partialUpdatedUrllink));
    }

    @Test
    @Transactional
    void patchNonExistingUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, urllinkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(urllinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(urllinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUrllink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        urllink.setId(longCount.incrementAndGet());

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrllinkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(urllinkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Urllink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUrllink() throws Exception {
        // Initialize the database
        insertedUrllink = urllinkRepository.saveAndFlush(urllink);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the urllink
        restUrllinkMockMvc
            .perform(delete(ENTITY_API_URL_ID, urllink.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return urllinkRepository.count();
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

    protected Urllink getPersistedUrllink(Urllink urllink) {
        return urllinkRepository.findById(urllink.getId()).orElseThrow();
    }

    protected void assertPersistedUrllinkToMatchAllProperties(Urllink expectedUrllink) {
        assertUrllinkAllPropertiesEquals(expectedUrllink, getPersistedUrllink(expectedUrllink));
    }

    protected void assertPersistedUrllinkToMatchUpdatableProperties(Urllink expectedUrllink) {
        assertUrllinkAllUpdatablePropertiesEquals(expectedUrllink, getPersistedUrllink(expectedUrllink));
    }
}
