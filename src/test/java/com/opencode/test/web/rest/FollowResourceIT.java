package com.opencode.test.web.rest;

import static com.opencode.test.domain.FollowAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Community;
import com.opencode.test.domain.Follow;
import com.opencode.test.repository.FollowRepository;
import com.opencode.test.service.dto.FollowDTO;
import com.opencode.test.service.mapper.FollowMapper;
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
 * Integration tests for the {@link FollowResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FollowResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/follows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFollowMockMvc;

    private Follow follow;

    private Follow insertedFollow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follow createEntity() {
        return new Follow().creationDate(DEFAULT_CREATION_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follow createUpdatedEntity() {
        return new Follow().creationDate(UPDATED_CREATION_DATE);
    }

    @BeforeEach
    void initTest() {
        follow = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFollow != null) {
            followRepository.delete(insertedFollow);
            insertedFollow = null;
        }
    }

    @Test
    @Transactional
    void createFollow() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);
        var returnedFollowDTO = om.readValue(
            restFollowMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(followDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FollowDTO.class
        );

        // Validate the Follow in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFollow = followMapper.toEntity(returnedFollowDTO);
        assertFollowUpdatableFieldsEquals(returnedFollow, getPersistedFollow(returnedFollow));

        insertedFollow = returnedFollow;
    }

    @Test
    @Transactional
    void createFollowWithExistingId() throws Exception {
        // Create the Follow with an existing ID
        follow.setId(1L);
        FollowDTO followDTO = followMapper.toDto(follow);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFollows() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        // Get all the followList
        restFollowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    void getFollow() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        // Get the follow
        restFollowMockMvc
            .perform(get(ENTITY_API_URL_ID, follow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(follow.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    void getFollowsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        Long id = follow.getId();

        defaultFollowFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFollowFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFollowFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFollowsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate equals to
        defaultFollowFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllFollowsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate in
        defaultFollowFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllFollowsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        // Get all the followList where creationDate is not null
        defaultFollowFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFollowsByFollowedIsEqualToSomething() throws Exception {
        Appuser followed;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            followRepository.saveAndFlush(follow);
            followed = AppuserResourceIT.createEntity(em);
        } else {
            followed = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(followed);
        em.flush();
        follow.setFollowed(followed);
        followRepository.saveAndFlush(follow);
        Long followedId = followed.getId();
        // Get all the followList where followed equals to followedId
        defaultFollowShouldBeFound("followedId.equals=" + followedId);

        // Get all the followList where followed equals to (followedId + 1)
        defaultFollowShouldNotBeFound("followedId.equals=" + (followedId + 1));
    }

    @Test
    @Transactional
    void getAllFollowsByFollowingIsEqualToSomething() throws Exception {
        Appuser following;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            followRepository.saveAndFlush(follow);
            following = AppuserResourceIT.createEntity(em);
        } else {
            following = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(following);
        em.flush();
        follow.setFollowing(following);
        followRepository.saveAndFlush(follow);
        Long followingId = following.getId();
        // Get all the followList where following equals to followingId
        defaultFollowShouldBeFound("followingId.equals=" + followingId);

        // Get all the followList where following equals to (followingId + 1)
        defaultFollowShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }

    @Test
    @Transactional
    void getAllFollowsByCfollowedIsEqualToSomething() throws Exception {
        Community cfollowed;
        if (TestUtil.findAll(em, Community.class).isEmpty()) {
            followRepository.saveAndFlush(follow);
            cfollowed = CommunityResourceIT.createEntity(em);
        } else {
            cfollowed = TestUtil.findAll(em, Community.class).get(0);
        }
        em.persist(cfollowed);
        em.flush();
        follow.setCfollowed(cfollowed);
        followRepository.saveAndFlush(follow);
        Long cfollowedId = cfollowed.getId();
        // Get all the followList where cfollowed equals to cfollowedId
        defaultFollowShouldBeFound("cfollowedId.equals=" + cfollowedId);

        // Get all the followList where cfollowed equals to (cfollowedId + 1)
        defaultFollowShouldNotBeFound("cfollowedId.equals=" + (cfollowedId + 1));
    }

    @Test
    @Transactional
    void getAllFollowsByCfollowingIsEqualToSomething() throws Exception {
        Community cfollowing;
        if (TestUtil.findAll(em, Community.class).isEmpty()) {
            followRepository.saveAndFlush(follow);
            cfollowing = CommunityResourceIT.createEntity(em);
        } else {
            cfollowing = TestUtil.findAll(em, Community.class).get(0);
        }
        em.persist(cfollowing);
        em.flush();
        follow.setCfollowing(cfollowing);
        followRepository.saveAndFlush(follow);
        Long cfollowingId = cfollowing.getId();
        // Get all the followList where cfollowing equals to cfollowingId
        defaultFollowShouldBeFound("cfollowingId.equals=" + cfollowingId);

        // Get all the followList where cfollowing equals to (cfollowingId + 1)
        defaultFollowShouldNotBeFound("cfollowingId.equals=" + (cfollowingId + 1));
    }

    private void defaultFollowFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFollowShouldBeFound(shouldBeFound);
        defaultFollowShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFollowShouldBeFound(String filter) throws Exception {
        restFollowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));

        // Check, that the count call also returns 1
        restFollowMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFollowShouldNotBeFound(String filter) throws Exception {
        restFollowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFollowMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFollow() throws Exception {
        // Get the follow
        restFollowMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFollow() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the follow
        Follow updatedFollow = followRepository.findById(follow.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFollow are not directly saved in db
        em.detach(updatedFollow);
        updatedFollow.creationDate(UPDATED_CREATION_DATE);
        FollowDTO followDTO = followMapper.toDto(updatedFollow);

        restFollowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, followDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(followDTO))
            )
            .andExpect(status().isOk());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFollowToMatchAllProperties(updatedFollow);
    }

    @Test
    @Transactional
    void putNonExistingFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, followDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(followDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(followDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(followDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFollowWithPatch() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the follow using partial update
        Follow partialUpdatedFollow = new Follow();
        partialUpdatedFollow.setId(follow.getId());

        restFollowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFollow.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFollow))
            )
            .andExpect(status().isOk());

        // Validate the Follow in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFollowUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFollow, follow), getPersistedFollow(follow));
    }

    @Test
    @Transactional
    void fullUpdateFollowWithPatch() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the follow using partial update
        Follow partialUpdatedFollow = new Follow();
        partialUpdatedFollow.setId(follow.getId());

        partialUpdatedFollow.creationDate(UPDATED_CREATION_DATE);

        restFollowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFollow.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFollow))
            )
            .andExpect(status().isOk());

        // Validate the Follow in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFollowUpdatableFieldsEquals(partialUpdatedFollow, getPersistedFollow(partialUpdatedFollow));
    }

    @Test
    @Transactional
    void patchNonExistingFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, followDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(followDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(followDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFollow() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        follow.setId(longCount.incrementAndGet());

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(followDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Follow in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFollow() throws Exception {
        // Initialize the database
        insertedFollow = followRepository.saveAndFlush(follow);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the follow
        restFollowMockMvc
            .perform(delete(ENTITY_API_URL_ID, follow.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return followRepository.count();
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

    protected Follow getPersistedFollow(Follow follow) {
        return followRepository.findById(follow.getId()).orElseThrow();
    }

    protected void assertPersistedFollowToMatchAllProperties(Follow expectedFollow) {
        assertFollowAllPropertiesEquals(expectedFollow, getPersistedFollow(expectedFollow));
    }

    protected void assertPersistedFollowToMatchUpdatableProperties(Follow expectedFollow) {
        assertFollowAllUpdatablePropertiesEquals(expectedFollow, getPersistedFollow(expectedFollow));
    }
}
