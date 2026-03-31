package com.opencode.test.web.rest;

import static com.opencode.test.domain.TopicAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Post;
import com.opencode.test.domain.Topic;
import com.opencode.test.repository.TopicRepository;
import com.opencode.test.service.TopicService;
import com.opencode.test.service.dto.TopicDTO;
import com.opencode.test.service.mapper.TopicMapper;
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
 * Integration tests for the {@link TopicResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TopicResourceIT {

    private static final String DEFAULT_TOPIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/topics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TopicRepository topicRepository;

    @Mock
    private TopicRepository topicRepositoryMock;

    @Autowired
    private TopicMapper topicMapper;

    @Mock
    private TopicService topicServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicMockMvc;

    private Topic topic;

    private Topic insertedTopic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topic createEntity() {
        return new Topic().topicName(DEFAULT_TOPIC_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topic createUpdatedEntity() {
        return new Topic().topicName(UPDATED_TOPIC_NAME);
    }

    @BeforeEach
    void initTest() {
        topic = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTopic != null) {
            topicRepository.delete(insertedTopic);
            insertedTopic = null;
        }
    }

    @Test
    @Transactional
    void createTopic() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);
        var returnedTopicDTO = om.readValue(
            restTopicMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TopicDTO.class
        );

        // Validate the Topic in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTopic = topicMapper.toEntity(returnedTopicDTO);
        assertTopicUpdatableFieldsEquals(returnedTopic, getPersistedTopic(returnedTopic));

        insertedTopic = returnedTopic;
    }

    @Test
    @Transactional
    void createTopicWithExistingId() throws Exception {
        // Create the Topic with an existing ID
        topic.setId(1L);
        TopicDTO topicDTO = topicMapper.toDto(topic);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTopicNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        topic.setTopicName(null);

        // Create the Topic, which fails.
        TopicDTO topicDTO = topicMapper.toDto(topic);

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTopics() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topic.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicName").value(hasItem(DEFAULT_TOPIC_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTopicsWithEagerRelationshipsIsEnabled() throws Exception {
        when(topicServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTopicMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(topicServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTopicsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(topicServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTopicMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(topicRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTopic() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get the topic
        restTopicMockMvc
            .perform(get(ENTITY_API_URL_ID, topic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topic.getId().intValue()))
            .andExpect(jsonPath("$.topicName").value(DEFAULT_TOPIC_NAME));
    }

    @Test
    @Transactional
    void getTopicsByIdFiltering() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        Long id = topic.getId();

        defaultTopicFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTopicFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTopicFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTopicsByTopicNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList where topicName equals to
        defaultTopicFiltering("topicName.equals=" + DEFAULT_TOPIC_NAME, "topicName.equals=" + UPDATED_TOPIC_NAME);
    }

    @Test
    @Transactional
    void getAllTopicsByTopicNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList where topicName in
        defaultTopicFiltering("topicName.in=" + DEFAULT_TOPIC_NAME + "," + UPDATED_TOPIC_NAME, "topicName.in=" + UPDATED_TOPIC_NAME);
    }

    @Test
    @Transactional
    void getAllTopicsByTopicNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList where topicName is not null
        defaultTopicFiltering("topicName.specified=true", "topicName.specified=false");
    }

    @Test
    @Transactional
    void getAllTopicsByTopicNameContainsSomething() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList where topicName contains
        defaultTopicFiltering("topicName.contains=" + DEFAULT_TOPIC_NAME, "topicName.contains=" + UPDATED_TOPIC_NAME);
    }

    @Test
    @Transactional
    void getAllTopicsByTopicNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        // Get all the topicList where topicName does not contain
        defaultTopicFiltering("topicName.doesNotContain=" + UPDATED_TOPIC_NAME, "topicName.doesNotContain=" + DEFAULT_TOPIC_NAME);
    }

    @Test
    @Transactional
    void getAllTopicsByPostIsEqualToSomething() throws Exception {
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            topicRepository.saveAndFlush(topic);
            post = PostResourceIT.createEntity(em);
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        em.persist(post);
        em.flush();
        topic.addPost(post);
        topicRepository.saveAndFlush(topic);
        Long postId = post.getId();
        // Get all the topicList where post equals to postId
        defaultTopicShouldBeFound("postId.equals=" + postId);

        // Get all the topicList where post equals to (postId + 1)
        defaultTopicShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    private void defaultTopicFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTopicShouldBeFound(shouldBeFound);
        defaultTopicShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTopicShouldBeFound(String filter) throws Exception {
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topic.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicName").value(hasItem(DEFAULT_TOPIC_NAME)));

        // Check, that the count call also returns 1
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTopicShouldNotBeFound(String filter) throws Exception {
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTopic() throws Exception {
        // Get the topic
        restTopicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTopic() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the topic
        Topic updatedTopic = topicRepository.findById(topic.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTopic are not directly saved in db
        em.detach(updatedTopic);
        updatedTopic.topicName(UPDATED_TOPIC_NAME);
        TopicDTO topicDTO = topicMapper.toDto(updatedTopic);

        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, topicDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTopicToMatchAllProperties(updatedTopic);
    }

    @Test
    @Transactional
    void putNonExistingTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, topicDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(topicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(topicDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTopicWithPatch() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the topic using partial update
        Topic partialUpdatedTopic = new Topic();
        partialUpdatedTopic.setId(topic.getId());

        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTopic))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTopicUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTopic, topic), getPersistedTopic(topic));
    }

    @Test
    @Transactional
    void fullUpdateTopicWithPatch() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the topic using partial update
        Topic partialUpdatedTopic = new Topic();
        partialUpdatedTopic.setId(topic.getId());

        partialUpdatedTopic.topicName(UPDATED_TOPIC_NAME);

        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTopic))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTopicUpdatableFieldsEquals(partialUpdatedTopic, getPersistedTopic(partialUpdatedTopic));
    }

    @Test
    @Transactional
    void patchNonExistingTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, topicDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(topicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(topicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTopic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        topic.setId(longCount.incrementAndGet());

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(topicDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTopic() throws Exception {
        // Initialize the database
        insertedTopic = topicRepository.saveAndFlush(topic);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the topic
        restTopicMockMvc
            .perform(delete(ENTITY_API_URL_ID, topic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return topicRepository.count();
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

    protected Topic getPersistedTopic(Topic topic) {
        return topicRepository.findById(topic.getId()).orElseThrow();
    }

    protected void assertPersistedTopicToMatchAllProperties(Topic expectedTopic) {
        assertTopicAllPropertiesEquals(expectedTopic, getPersistedTopic(expectedTopic));
    }

    protected void assertPersistedTopicToMatchUpdatableProperties(Topic expectedTopic) {
        assertTopicAllUpdatablePropertiesEquals(expectedTopic, getPersistedTopic(expectedTopic));
    }
}
