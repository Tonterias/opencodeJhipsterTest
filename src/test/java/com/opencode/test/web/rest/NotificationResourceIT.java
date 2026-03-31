package com.opencode.test.web.rest;

import static com.opencode.test.domain.NotificationAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Notification;
import com.opencode.test.domain.enumeration.NotificationReason;
import com.opencode.test.repository.NotificationRepository;
import com.opencode.test.service.dto.NotificationDTO;
import com.opencode.test.service.mapper.NotificationMapper;
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
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOTIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOTIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final NotificationReason DEFAULT_NOTIFICATION_REASON = NotificationReason.FOLLOWING;
    private static final NotificationReason UPDATED_NOTIFICATION_REASON = NotificationReason.UNFOLLOWING;

    private static final String DEFAULT_NOTIFICATION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELIVERED = false;
    private static final Boolean UPDATED_IS_DELIVERED = true;

    private static final String ENTITY_API_URL = "/api/notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    private Notification insertedNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .creationDate(DEFAULT_CREATION_DATE)
            .notificationDate(DEFAULT_NOTIFICATION_DATE)
            .notificationReason(DEFAULT_NOTIFICATION_REASON)
            .notificationText(DEFAULT_NOTIFICATION_TEXT)
            .isDelivered(DEFAULT_IS_DELIVERED);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        notification.setAppuser(appuser);
        return notification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification updatedNotification = new Notification()
            .creationDate(UPDATED_CREATION_DATE)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .notificationReason(UPDATED_NOTIFICATION_REASON)
            .notificationText(UPDATED_NOTIFICATION_TEXT)
            .isDelivered(UPDATED_IS_DELIVERED);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        updatedNotification.setAppuser(appuser);
        return updatedNotification;
    }

    @BeforeEach
    void initTest() {
        notification = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedNotification != null) {
            notificationRepository.delete(insertedNotification);
            insertedNotification = null;
        }
    }

    @Test
    @Transactional
    void createNotification() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        var returnedNotificationDTO = om.readValue(
            restNotificationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NotificationDTO.class
        );

        // Validate the Notification in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNotification = notificationMapper.toEntity(returnedNotificationDTO);
        assertNotificationUpdatableFieldsEquals(returnedNotification, getPersistedNotification(returnedNotification));

        insertedNotification = returnedNotification;
    }

    @Test
    @Transactional
    void createNotificationWithExistingId() throws Exception {
        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notification.setCreationDate(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotificationReasonIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notification.setNotificationReason(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotifications() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(DEFAULT_NOTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationReason").value(hasItem(DEFAULT_NOTIFICATION_REASON.toString())))
            .andExpect(jsonPath("$.[*].notificationText").value(hasItem(DEFAULT_NOTIFICATION_TEXT)))
            .andExpect(jsonPath("$.[*].isDelivered").value(hasItem(DEFAULT_IS_DELIVERED)));
    }

    @Test
    @Transactional
    void getNotification() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.notificationDate").value(DEFAULT_NOTIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.notificationReason").value(DEFAULT_NOTIFICATION_REASON.toString()))
            .andExpect(jsonPath("$.notificationText").value(DEFAULT_NOTIFICATION_TEXT))
            .andExpect(jsonPath("$.isDelivered").value(DEFAULT_IS_DELIVERED));
    }

    @Test
    @Transactional
    void getNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        Long id = notification.getId();

        defaultNotificationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNotificationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNotificationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where creationDate equals to
        defaultNotificationFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where creationDate in
        defaultNotificationFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where creationDate is not null
        defaultNotificationFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate equals to
        defaultNotificationFiltering(
            "notificationDate.equals=" + DEFAULT_NOTIFICATION_DATE,
            "notificationDate.equals=" + UPDATED_NOTIFICATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate in
        defaultNotificationFiltering(
            "notificationDate.in=" + DEFAULT_NOTIFICATION_DATE + "," + UPDATED_NOTIFICATION_DATE,
            "notificationDate.in=" + UPDATED_NOTIFICATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is not null
        defaultNotificationFiltering("notificationDate.specified=true", "notificationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationReason equals to
        defaultNotificationFiltering(
            "notificationReason.equals=" + DEFAULT_NOTIFICATION_REASON,
            "notificationReason.equals=" + UPDATED_NOTIFICATION_REASON
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationReasonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationReason in
        defaultNotificationFiltering(
            "notificationReason.in=" + DEFAULT_NOTIFICATION_REASON + "," + UPDATED_NOTIFICATION_REASON,
            "notificationReason.in=" + UPDATED_NOTIFICATION_REASON
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationReason is not null
        defaultNotificationFiltering("notificationReason.specified=true", "notificationReason.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationTextIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationText equals to
        defaultNotificationFiltering(
            "notificationText.equals=" + DEFAULT_NOTIFICATION_TEXT,
            "notificationText.equals=" + UPDATED_NOTIFICATION_TEXT
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationTextIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationText in
        defaultNotificationFiltering(
            "notificationText.in=" + DEFAULT_NOTIFICATION_TEXT + "," + UPDATED_NOTIFICATION_TEXT,
            "notificationText.in=" + UPDATED_NOTIFICATION_TEXT
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationText is not null
        defaultNotificationFiltering("notificationText.specified=true", "notificationText.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationTextContainsSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationText contains
        defaultNotificationFiltering(
            "notificationText.contains=" + DEFAULT_NOTIFICATION_TEXT,
            "notificationText.contains=" + UPDATED_NOTIFICATION_TEXT
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationTextNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationText does not contain
        defaultNotificationFiltering(
            "notificationText.doesNotContain=" + UPDATED_NOTIFICATION_TEXT,
            "notificationText.doesNotContain=" + DEFAULT_NOTIFICATION_TEXT
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByIsDeliveredIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where isDelivered equals to
        defaultNotificationFiltering("isDelivered.equals=" + DEFAULT_IS_DELIVERED, "isDelivered.equals=" + UPDATED_IS_DELIVERED);
    }

    @Test
    @Transactional
    void getAllNotificationsByIsDeliveredIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where isDelivered in
        defaultNotificationFiltering(
            "isDelivered.in=" + DEFAULT_IS_DELIVERED + "," + UPDATED_IS_DELIVERED,
            "isDelivered.in=" + UPDATED_IS_DELIVERED
        );
    }

    @Test
    @Transactional
    void getAllNotificationsByIsDeliveredIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where isDelivered is not null
        defaultNotificationFiltering("isDelivered.specified=true", "isDelivered.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByAppuserIsEqualToSomething() throws Exception {
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            notificationRepository.saveAndFlush(notification);
            appuser = AppuserResourceIT.createEntity(em);
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(appuser);
        em.flush();
        notification.setAppuser(appuser);
        notificationRepository.saveAndFlush(notification);
        Long appuserId = appuser.getId();
        // Get all the notificationList where appuser equals to appuserId
        defaultNotificationShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the notificationList where appuser equals to (appuserId + 1)
        defaultNotificationShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    private void defaultNotificationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNotificationShouldBeFound(shouldBeFound);
        defaultNotificationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationShouldBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(DEFAULT_NOTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notificationReason").value(hasItem(DEFAULT_NOTIFICATION_REASON.toString())))
            .andExpect(jsonPath("$.[*].notificationText").value(hasItem(DEFAULT_NOTIFICATION_TEXT)))
            .andExpect(jsonPath("$.[*].isDelivered").value(hasItem(DEFAULT_IS_DELIVERED)));

        // Check, that the count call also returns 1
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationShouldNotBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotification() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .creationDate(UPDATED_CREATION_DATE)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .notificationReason(UPDATED_NOTIFICATION_REASON)
            .notificationText(UPDATED_NOTIFICATION_TEXT)
            .isDelivered(UPDATED_IS_DELIVERED);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNotificationToMatchAllProperties(updatedNotification);
    }

    @Test
    @Transactional
    void putNonExistingNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification
            .creationDate(UPDATED_CREATION_DATE)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .notificationText(UPDATED_NOTIFICATION_TEXT)
            .isDelivered(UPDATED_IS_DELIVERED);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotificationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNotification, notification),
            getPersistedNotification(notification)
        );
    }

    @Test
    @Transactional
    void fullUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification
            .creationDate(UPDATED_CREATION_DATE)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .notificationReason(UPDATED_NOTIFICATION_REASON)
            .notificationText(UPDATED_NOTIFICATION_TEXT)
            .isDelivered(UPDATED_IS_DELIVERED);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotificationUpdatableFieldsEquals(partialUpdatedNotification, getPersistedNotification(partialUpdatedNotification));
    }

    @Test
    @Transactional
    void patchNonExistingNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notificationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotification() throws Exception {
        // Initialize the database
        insertedNotification = notificationRepository.saveAndFlush(notification);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the notification
        restNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, notification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return notificationRepository.count();
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

    protected Notification getPersistedNotification(Notification notification) {
        return notificationRepository.findById(notification.getId()).orElseThrow();
    }

    protected void assertPersistedNotificationToMatchAllProperties(Notification expectedNotification) {
        assertNotificationAllPropertiesEquals(expectedNotification, getPersistedNotification(expectedNotification));
    }

    protected void assertPersistedNotificationToMatchUpdatableProperties(Notification expectedNotification) {
        assertNotificationAllUpdatablePropertiesEquals(expectedNotification, getPersistedNotification(expectedNotification));
    }
}
