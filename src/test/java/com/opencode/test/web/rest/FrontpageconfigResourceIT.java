package com.opencode.test.web.rest;

import static com.opencode.test.domain.FrontpageconfigAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Frontpageconfig;
import com.opencode.test.repository.FrontpageconfigRepository;
import com.opencode.test.service.dto.FrontpageconfigDTO;
import com.opencode.test.service.mapper.FrontpageconfigMapper;
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
 * Integration tests for the {@link FrontpageconfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FrontpageconfigResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TOP_NEWS_1 = 1L;
    private static final Long UPDATED_TOP_NEWS_1 = 2L;
    private static final Long SMALLER_TOP_NEWS_1 = 1L - 1L;

    private static final Long DEFAULT_TOP_NEWS_2 = 1L;
    private static final Long UPDATED_TOP_NEWS_2 = 2L;
    private static final Long SMALLER_TOP_NEWS_2 = 1L - 1L;

    private static final Long DEFAULT_TOP_NEWS_3 = 1L;
    private static final Long UPDATED_TOP_NEWS_3 = 2L;
    private static final Long SMALLER_TOP_NEWS_3 = 1L - 1L;

    private static final Long DEFAULT_TOP_NEWS_4 = 1L;
    private static final Long UPDATED_TOP_NEWS_4 = 2L;
    private static final Long SMALLER_TOP_NEWS_4 = 1L - 1L;

    private static final Long DEFAULT_TOP_NEWS_5 = 1L;
    private static final Long UPDATED_TOP_NEWS_5 = 2L;
    private static final Long SMALLER_TOP_NEWS_5 = 1L - 1L;

    private static final Long DEFAULT_LATEST_NEWS_1 = 1L;
    private static final Long UPDATED_LATEST_NEWS_1 = 2L;
    private static final Long SMALLER_LATEST_NEWS_1 = 1L - 1L;

    private static final Long DEFAULT_LATEST_NEWS_2 = 1L;
    private static final Long UPDATED_LATEST_NEWS_2 = 2L;
    private static final Long SMALLER_LATEST_NEWS_2 = 1L - 1L;

    private static final Long DEFAULT_LATEST_NEWS_3 = 1L;
    private static final Long UPDATED_LATEST_NEWS_3 = 2L;
    private static final Long SMALLER_LATEST_NEWS_3 = 1L - 1L;

    private static final Long DEFAULT_LATEST_NEWS_4 = 1L;
    private static final Long UPDATED_LATEST_NEWS_4 = 2L;
    private static final Long SMALLER_LATEST_NEWS_4 = 1L - 1L;

    private static final Long DEFAULT_LATEST_NEWS_5 = 1L;
    private static final Long UPDATED_LATEST_NEWS_5 = 2L;
    private static final Long SMALLER_LATEST_NEWS_5 = 1L - 1L;

    private static final Long DEFAULT_BREAKING_NEWS_1 = 1L;
    private static final Long UPDATED_BREAKING_NEWS_1 = 2L;
    private static final Long SMALLER_BREAKING_NEWS_1 = 1L - 1L;

    private static final Long DEFAULT_RECENT_POSTS_1 = 1L;
    private static final Long UPDATED_RECENT_POSTS_1 = 2L;
    private static final Long SMALLER_RECENT_POSTS_1 = 1L - 1L;

    private static final Long DEFAULT_RECENT_POSTS_2 = 1L;
    private static final Long UPDATED_RECENT_POSTS_2 = 2L;
    private static final Long SMALLER_RECENT_POSTS_2 = 1L - 1L;

    private static final Long DEFAULT_RECENT_POSTS_3 = 1L;
    private static final Long UPDATED_RECENT_POSTS_3 = 2L;
    private static final Long SMALLER_RECENT_POSTS_3 = 1L - 1L;

    private static final Long DEFAULT_RECENT_POSTS_4 = 1L;
    private static final Long UPDATED_RECENT_POSTS_4 = 2L;
    private static final Long SMALLER_RECENT_POSTS_4 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_1 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_1 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_1 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_2 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_2 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_2 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_3 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_3 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_3 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_4 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_4 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_4 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_5 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_5 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_5 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_6 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_6 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_6 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_7 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_7 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_7 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_8 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_8 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_8 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_9 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_9 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_9 = 1L - 1L;

    private static final Long DEFAULT_FEATURED_ARTICLES_10 = 1L;
    private static final Long UPDATED_FEATURED_ARTICLES_10 = 2L;
    private static final Long SMALLER_FEATURED_ARTICLES_10 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_1 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_1 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_1 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_2 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_2 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_2 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_3 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_3 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_3 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_4 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_4 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_4 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_5 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_5 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_5 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_6 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_6 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_6 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_7 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_7 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_7 = 1L - 1L;

    private static final Long DEFAULT_POPULAR_NEWS_8 = 1L;
    private static final Long UPDATED_POPULAR_NEWS_8 = 2L;
    private static final Long SMALLER_POPULAR_NEWS_8 = 1L - 1L;

    private static final Long DEFAULT_WEEKLY_NEWS_1 = 1L;
    private static final Long UPDATED_WEEKLY_NEWS_1 = 2L;
    private static final Long SMALLER_WEEKLY_NEWS_1 = 1L - 1L;

    private static final Long DEFAULT_WEEKLY_NEWS_2 = 1L;
    private static final Long UPDATED_WEEKLY_NEWS_2 = 2L;
    private static final Long SMALLER_WEEKLY_NEWS_2 = 1L - 1L;

    private static final Long DEFAULT_WEEKLY_NEWS_3 = 1L;
    private static final Long UPDATED_WEEKLY_NEWS_3 = 2L;
    private static final Long SMALLER_WEEKLY_NEWS_3 = 1L - 1L;

    private static final Long DEFAULT_WEEKLY_NEWS_4 = 1L;
    private static final Long UPDATED_WEEKLY_NEWS_4 = 2L;
    private static final Long SMALLER_WEEKLY_NEWS_4 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_1 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_1 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_1 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_2 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_2 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_2 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_3 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_3 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_3 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_4 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_4 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_4 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_5 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_5 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_5 = 1L - 1L;

    private static final Long DEFAULT_NEWS_FEEDS_6 = 1L;
    private static final Long UPDATED_NEWS_FEEDS_6 = 2L;
    private static final Long SMALLER_NEWS_FEEDS_6 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_1 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_1 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_1 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_2 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_2 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_2 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_3 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_3 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_3 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_4 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_4 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_4 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_5 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_5 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_5 = 1L - 1L;

    private static final Long DEFAULT_USEFUL_LINKS_6 = 1L;
    private static final Long UPDATED_USEFUL_LINKS_6 = 2L;
    private static final Long SMALLER_USEFUL_LINKS_6 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_1 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_1 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_1 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_2 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_2 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_2 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_3 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_3 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_3 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_4 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_4 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_4 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_5 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_5 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_5 = 1L - 1L;

    private static final Long DEFAULT_RECENT_VIDEOS_6 = 1L;
    private static final Long UPDATED_RECENT_VIDEOS_6 = 2L;
    private static final Long SMALLER_RECENT_VIDEOS_6 = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/frontpageconfigs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FrontpageconfigRepository frontpageconfigRepository;

    @Autowired
    private FrontpageconfigMapper frontpageconfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrontpageconfigMockMvc;

    private Frontpageconfig frontpageconfig;

    private Frontpageconfig insertedFrontpageconfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frontpageconfig createEntity() {
        return new Frontpageconfig()
            .creationDate(DEFAULT_CREATION_DATE)
            .topNews1(DEFAULT_TOP_NEWS_1)
            .topNews2(DEFAULT_TOP_NEWS_2)
            .topNews3(DEFAULT_TOP_NEWS_3)
            .topNews4(DEFAULT_TOP_NEWS_4)
            .topNews5(DEFAULT_TOP_NEWS_5)
            .latestNews1(DEFAULT_LATEST_NEWS_1)
            .latestNews2(DEFAULT_LATEST_NEWS_2)
            .latestNews3(DEFAULT_LATEST_NEWS_3)
            .latestNews4(DEFAULT_LATEST_NEWS_4)
            .latestNews5(DEFAULT_LATEST_NEWS_5)
            .breakingNews1(DEFAULT_BREAKING_NEWS_1)
            .recentPosts1(DEFAULT_RECENT_POSTS_1)
            .recentPosts2(DEFAULT_RECENT_POSTS_2)
            .recentPosts3(DEFAULT_RECENT_POSTS_3)
            .recentPosts4(DEFAULT_RECENT_POSTS_4)
            .featuredArticles1(DEFAULT_FEATURED_ARTICLES_1)
            .featuredArticles2(DEFAULT_FEATURED_ARTICLES_2)
            .featuredArticles3(DEFAULT_FEATURED_ARTICLES_3)
            .featuredArticles4(DEFAULT_FEATURED_ARTICLES_4)
            .featuredArticles5(DEFAULT_FEATURED_ARTICLES_5)
            .featuredArticles6(DEFAULT_FEATURED_ARTICLES_6)
            .featuredArticles7(DEFAULT_FEATURED_ARTICLES_7)
            .featuredArticles8(DEFAULT_FEATURED_ARTICLES_8)
            .featuredArticles9(DEFAULT_FEATURED_ARTICLES_9)
            .featuredArticles10(DEFAULT_FEATURED_ARTICLES_10)
            .popularNews1(DEFAULT_POPULAR_NEWS_1)
            .popularNews2(DEFAULT_POPULAR_NEWS_2)
            .popularNews3(DEFAULT_POPULAR_NEWS_3)
            .popularNews4(DEFAULT_POPULAR_NEWS_4)
            .popularNews5(DEFAULT_POPULAR_NEWS_5)
            .popularNews6(DEFAULT_POPULAR_NEWS_6)
            .popularNews7(DEFAULT_POPULAR_NEWS_7)
            .popularNews8(DEFAULT_POPULAR_NEWS_8)
            .weeklyNews1(DEFAULT_WEEKLY_NEWS_1)
            .weeklyNews2(DEFAULT_WEEKLY_NEWS_2)
            .weeklyNews3(DEFAULT_WEEKLY_NEWS_3)
            .weeklyNews4(DEFAULT_WEEKLY_NEWS_4)
            .newsFeeds1(DEFAULT_NEWS_FEEDS_1)
            .newsFeeds2(DEFAULT_NEWS_FEEDS_2)
            .newsFeeds3(DEFAULT_NEWS_FEEDS_3)
            .newsFeeds4(DEFAULT_NEWS_FEEDS_4)
            .newsFeeds5(DEFAULT_NEWS_FEEDS_5)
            .newsFeeds6(DEFAULT_NEWS_FEEDS_6)
            .usefulLinks1(DEFAULT_USEFUL_LINKS_1)
            .usefulLinks2(DEFAULT_USEFUL_LINKS_2)
            .usefulLinks3(DEFAULT_USEFUL_LINKS_3)
            .usefulLinks4(DEFAULT_USEFUL_LINKS_4)
            .usefulLinks5(DEFAULT_USEFUL_LINKS_5)
            .usefulLinks6(DEFAULT_USEFUL_LINKS_6)
            .recentVideos1(DEFAULT_RECENT_VIDEOS_1)
            .recentVideos2(DEFAULT_RECENT_VIDEOS_2)
            .recentVideos3(DEFAULT_RECENT_VIDEOS_3)
            .recentVideos4(DEFAULT_RECENT_VIDEOS_4)
            .recentVideos5(DEFAULT_RECENT_VIDEOS_5)
            .recentVideos6(DEFAULT_RECENT_VIDEOS_6);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frontpageconfig createUpdatedEntity() {
        return new Frontpageconfig()
            .creationDate(UPDATED_CREATION_DATE)
            .topNews1(UPDATED_TOP_NEWS_1)
            .topNews2(UPDATED_TOP_NEWS_2)
            .topNews3(UPDATED_TOP_NEWS_3)
            .topNews4(UPDATED_TOP_NEWS_4)
            .topNews5(UPDATED_TOP_NEWS_5)
            .latestNews1(UPDATED_LATEST_NEWS_1)
            .latestNews2(UPDATED_LATEST_NEWS_2)
            .latestNews3(UPDATED_LATEST_NEWS_3)
            .latestNews4(UPDATED_LATEST_NEWS_4)
            .latestNews5(UPDATED_LATEST_NEWS_5)
            .breakingNews1(UPDATED_BREAKING_NEWS_1)
            .recentPosts1(UPDATED_RECENT_POSTS_1)
            .recentPosts2(UPDATED_RECENT_POSTS_2)
            .recentPosts3(UPDATED_RECENT_POSTS_3)
            .recentPosts4(UPDATED_RECENT_POSTS_4)
            .featuredArticles1(UPDATED_FEATURED_ARTICLES_1)
            .featuredArticles2(UPDATED_FEATURED_ARTICLES_2)
            .featuredArticles3(UPDATED_FEATURED_ARTICLES_3)
            .featuredArticles4(UPDATED_FEATURED_ARTICLES_4)
            .featuredArticles5(UPDATED_FEATURED_ARTICLES_5)
            .featuredArticles6(UPDATED_FEATURED_ARTICLES_6)
            .featuredArticles7(UPDATED_FEATURED_ARTICLES_7)
            .featuredArticles8(UPDATED_FEATURED_ARTICLES_8)
            .featuredArticles9(UPDATED_FEATURED_ARTICLES_9)
            .featuredArticles10(UPDATED_FEATURED_ARTICLES_10)
            .popularNews1(UPDATED_POPULAR_NEWS_1)
            .popularNews2(UPDATED_POPULAR_NEWS_2)
            .popularNews3(UPDATED_POPULAR_NEWS_3)
            .popularNews4(UPDATED_POPULAR_NEWS_4)
            .popularNews5(UPDATED_POPULAR_NEWS_5)
            .popularNews6(UPDATED_POPULAR_NEWS_6)
            .popularNews7(UPDATED_POPULAR_NEWS_7)
            .popularNews8(UPDATED_POPULAR_NEWS_8)
            .weeklyNews1(UPDATED_WEEKLY_NEWS_1)
            .weeklyNews2(UPDATED_WEEKLY_NEWS_2)
            .weeklyNews3(UPDATED_WEEKLY_NEWS_3)
            .weeklyNews4(UPDATED_WEEKLY_NEWS_4)
            .newsFeeds1(UPDATED_NEWS_FEEDS_1)
            .newsFeeds2(UPDATED_NEWS_FEEDS_2)
            .newsFeeds3(UPDATED_NEWS_FEEDS_3)
            .newsFeeds4(UPDATED_NEWS_FEEDS_4)
            .newsFeeds5(UPDATED_NEWS_FEEDS_5)
            .newsFeeds6(UPDATED_NEWS_FEEDS_6)
            .usefulLinks1(UPDATED_USEFUL_LINKS_1)
            .usefulLinks2(UPDATED_USEFUL_LINKS_2)
            .usefulLinks3(UPDATED_USEFUL_LINKS_3)
            .usefulLinks4(UPDATED_USEFUL_LINKS_4)
            .usefulLinks5(UPDATED_USEFUL_LINKS_5)
            .usefulLinks6(UPDATED_USEFUL_LINKS_6)
            .recentVideos1(UPDATED_RECENT_VIDEOS_1)
            .recentVideos2(UPDATED_RECENT_VIDEOS_2)
            .recentVideos3(UPDATED_RECENT_VIDEOS_3)
            .recentVideos4(UPDATED_RECENT_VIDEOS_4)
            .recentVideos5(UPDATED_RECENT_VIDEOS_5)
            .recentVideos6(UPDATED_RECENT_VIDEOS_6);
    }

    @BeforeEach
    void initTest() {
        frontpageconfig = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFrontpageconfig != null) {
            frontpageconfigRepository.delete(insertedFrontpageconfig);
            insertedFrontpageconfig = null;
        }
    }

    @Test
    @Transactional
    void createFrontpageconfig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);
        var returnedFrontpageconfigDTO = om.readValue(
            restFrontpageconfigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frontpageconfigDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FrontpageconfigDTO.class
        );

        // Validate the Frontpageconfig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFrontpageconfig = frontpageconfigMapper.toEntity(returnedFrontpageconfigDTO);
        assertFrontpageconfigUpdatableFieldsEquals(returnedFrontpageconfig, getPersistedFrontpageconfig(returnedFrontpageconfig));

        insertedFrontpageconfig = returnedFrontpageconfig;
    }

    @Test
    @Transactional
    void createFrontpageconfigWithExistingId() throws Exception {
        // Create the Frontpageconfig with an existing ID
        frontpageconfig.setId(1L);
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrontpageconfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frontpageconfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        frontpageconfig.setCreationDate(null);

        // Create the Frontpageconfig, which fails.
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        restFrontpageconfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frontpageconfigDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigs() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frontpageconfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].topNews1").value(hasItem(DEFAULT_TOP_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].topNews2").value(hasItem(DEFAULT_TOP_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].topNews3").value(hasItem(DEFAULT_TOP_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].topNews4").value(hasItem(DEFAULT_TOP_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].topNews5").value(hasItem(DEFAULT_TOP_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].latestNews1").value(hasItem(DEFAULT_LATEST_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].latestNews2").value(hasItem(DEFAULT_LATEST_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].latestNews3").value(hasItem(DEFAULT_LATEST_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].latestNews4").value(hasItem(DEFAULT_LATEST_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].latestNews5").value(hasItem(DEFAULT_LATEST_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].breakingNews1").value(hasItem(DEFAULT_BREAKING_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts1").value(hasItem(DEFAULT_RECENT_POSTS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts2").value(hasItem(DEFAULT_RECENT_POSTS_2.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts3").value(hasItem(DEFAULT_RECENT_POSTS_3.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts4").value(hasItem(DEFAULT_RECENT_POSTS_4.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles1").value(hasItem(DEFAULT_FEATURED_ARTICLES_1.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles2").value(hasItem(DEFAULT_FEATURED_ARTICLES_2.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles3").value(hasItem(DEFAULT_FEATURED_ARTICLES_3.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles4").value(hasItem(DEFAULT_FEATURED_ARTICLES_4.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles5").value(hasItem(DEFAULT_FEATURED_ARTICLES_5.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles6").value(hasItem(DEFAULT_FEATURED_ARTICLES_6.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles7").value(hasItem(DEFAULT_FEATURED_ARTICLES_7.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles8").value(hasItem(DEFAULT_FEATURED_ARTICLES_8.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles9").value(hasItem(DEFAULT_FEATURED_ARTICLES_9.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles10").value(hasItem(DEFAULT_FEATURED_ARTICLES_10.intValue())))
            .andExpect(jsonPath("$.[*].popularNews1").value(hasItem(DEFAULT_POPULAR_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].popularNews2").value(hasItem(DEFAULT_POPULAR_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].popularNews3").value(hasItem(DEFAULT_POPULAR_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].popularNews4").value(hasItem(DEFAULT_POPULAR_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].popularNews5").value(hasItem(DEFAULT_POPULAR_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].popularNews6").value(hasItem(DEFAULT_POPULAR_NEWS_6.intValue())))
            .andExpect(jsonPath("$.[*].popularNews7").value(hasItem(DEFAULT_POPULAR_NEWS_7.intValue())))
            .andExpect(jsonPath("$.[*].popularNews8").value(hasItem(DEFAULT_POPULAR_NEWS_8.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews1").value(hasItem(DEFAULT_WEEKLY_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews2").value(hasItem(DEFAULT_WEEKLY_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews3").value(hasItem(DEFAULT_WEEKLY_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews4").value(hasItem(DEFAULT_WEEKLY_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds1").value(hasItem(DEFAULT_NEWS_FEEDS_1.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds2").value(hasItem(DEFAULT_NEWS_FEEDS_2.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds3").value(hasItem(DEFAULT_NEWS_FEEDS_3.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds4").value(hasItem(DEFAULT_NEWS_FEEDS_4.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds5").value(hasItem(DEFAULT_NEWS_FEEDS_5.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds6").value(hasItem(DEFAULT_NEWS_FEEDS_6.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks1").value(hasItem(DEFAULT_USEFUL_LINKS_1.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks2").value(hasItem(DEFAULT_USEFUL_LINKS_2.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks3").value(hasItem(DEFAULT_USEFUL_LINKS_3.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks4").value(hasItem(DEFAULT_USEFUL_LINKS_4.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks5").value(hasItem(DEFAULT_USEFUL_LINKS_5.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks6").value(hasItem(DEFAULT_USEFUL_LINKS_6.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos1").value(hasItem(DEFAULT_RECENT_VIDEOS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos2").value(hasItem(DEFAULT_RECENT_VIDEOS_2.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos3").value(hasItem(DEFAULT_RECENT_VIDEOS_3.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos4").value(hasItem(DEFAULT_RECENT_VIDEOS_4.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos5").value(hasItem(DEFAULT_RECENT_VIDEOS_5.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos6").value(hasItem(DEFAULT_RECENT_VIDEOS_6.intValue())));
    }

    @Test
    @Transactional
    void getFrontpageconfig() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get the frontpageconfig
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL_ID, frontpageconfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frontpageconfig.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.topNews1").value(DEFAULT_TOP_NEWS_1.intValue()))
            .andExpect(jsonPath("$.topNews2").value(DEFAULT_TOP_NEWS_2.intValue()))
            .andExpect(jsonPath("$.topNews3").value(DEFAULT_TOP_NEWS_3.intValue()))
            .andExpect(jsonPath("$.topNews4").value(DEFAULT_TOP_NEWS_4.intValue()))
            .andExpect(jsonPath("$.topNews5").value(DEFAULT_TOP_NEWS_5.intValue()))
            .andExpect(jsonPath("$.latestNews1").value(DEFAULT_LATEST_NEWS_1.intValue()))
            .andExpect(jsonPath("$.latestNews2").value(DEFAULT_LATEST_NEWS_2.intValue()))
            .andExpect(jsonPath("$.latestNews3").value(DEFAULT_LATEST_NEWS_3.intValue()))
            .andExpect(jsonPath("$.latestNews4").value(DEFAULT_LATEST_NEWS_4.intValue()))
            .andExpect(jsonPath("$.latestNews5").value(DEFAULT_LATEST_NEWS_5.intValue()))
            .andExpect(jsonPath("$.breakingNews1").value(DEFAULT_BREAKING_NEWS_1.intValue()))
            .andExpect(jsonPath("$.recentPosts1").value(DEFAULT_RECENT_POSTS_1.intValue()))
            .andExpect(jsonPath("$.recentPosts2").value(DEFAULT_RECENT_POSTS_2.intValue()))
            .andExpect(jsonPath("$.recentPosts3").value(DEFAULT_RECENT_POSTS_3.intValue()))
            .andExpect(jsonPath("$.recentPosts4").value(DEFAULT_RECENT_POSTS_4.intValue()))
            .andExpect(jsonPath("$.featuredArticles1").value(DEFAULT_FEATURED_ARTICLES_1.intValue()))
            .andExpect(jsonPath("$.featuredArticles2").value(DEFAULT_FEATURED_ARTICLES_2.intValue()))
            .andExpect(jsonPath("$.featuredArticles3").value(DEFAULT_FEATURED_ARTICLES_3.intValue()))
            .andExpect(jsonPath("$.featuredArticles4").value(DEFAULT_FEATURED_ARTICLES_4.intValue()))
            .andExpect(jsonPath("$.featuredArticles5").value(DEFAULT_FEATURED_ARTICLES_5.intValue()))
            .andExpect(jsonPath("$.featuredArticles6").value(DEFAULT_FEATURED_ARTICLES_6.intValue()))
            .andExpect(jsonPath("$.featuredArticles7").value(DEFAULT_FEATURED_ARTICLES_7.intValue()))
            .andExpect(jsonPath("$.featuredArticles8").value(DEFAULT_FEATURED_ARTICLES_8.intValue()))
            .andExpect(jsonPath("$.featuredArticles9").value(DEFAULT_FEATURED_ARTICLES_9.intValue()))
            .andExpect(jsonPath("$.featuredArticles10").value(DEFAULT_FEATURED_ARTICLES_10.intValue()))
            .andExpect(jsonPath("$.popularNews1").value(DEFAULT_POPULAR_NEWS_1.intValue()))
            .andExpect(jsonPath("$.popularNews2").value(DEFAULT_POPULAR_NEWS_2.intValue()))
            .andExpect(jsonPath("$.popularNews3").value(DEFAULT_POPULAR_NEWS_3.intValue()))
            .andExpect(jsonPath("$.popularNews4").value(DEFAULT_POPULAR_NEWS_4.intValue()))
            .andExpect(jsonPath("$.popularNews5").value(DEFAULT_POPULAR_NEWS_5.intValue()))
            .andExpect(jsonPath("$.popularNews6").value(DEFAULT_POPULAR_NEWS_6.intValue()))
            .andExpect(jsonPath("$.popularNews7").value(DEFAULT_POPULAR_NEWS_7.intValue()))
            .andExpect(jsonPath("$.popularNews8").value(DEFAULT_POPULAR_NEWS_8.intValue()))
            .andExpect(jsonPath("$.weeklyNews1").value(DEFAULT_WEEKLY_NEWS_1.intValue()))
            .andExpect(jsonPath("$.weeklyNews2").value(DEFAULT_WEEKLY_NEWS_2.intValue()))
            .andExpect(jsonPath("$.weeklyNews3").value(DEFAULT_WEEKLY_NEWS_3.intValue()))
            .andExpect(jsonPath("$.weeklyNews4").value(DEFAULT_WEEKLY_NEWS_4.intValue()))
            .andExpect(jsonPath("$.newsFeeds1").value(DEFAULT_NEWS_FEEDS_1.intValue()))
            .andExpect(jsonPath("$.newsFeeds2").value(DEFAULT_NEWS_FEEDS_2.intValue()))
            .andExpect(jsonPath("$.newsFeeds3").value(DEFAULT_NEWS_FEEDS_3.intValue()))
            .andExpect(jsonPath("$.newsFeeds4").value(DEFAULT_NEWS_FEEDS_4.intValue()))
            .andExpect(jsonPath("$.newsFeeds5").value(DEFAULT_NEWS_FEEDS_5.intValue()))
            .andExpect(jsonPath("$.newsFeeds6").value(DEFAULT_NEWS_FEEDS_6.intValue()))
            .andExpect(jsonPath("$.usefulLinks1").value(DEFAULT_USEFUL_LINKS_1.intValue()))
            .andExpect(jsonPath("$.usefulLinks2").value(DEFAULT_USEFUL_LINKS_2.intValue()))
            .andExpect(jsonPath("$.usefulLinks3").value(DEFAULT_USEFUL_LINKS_3.intValue()))
            .andExpect(jsonPath("$.usefulLinks4").value(DEFAULT_USEFUL_LINKS_4.intValue()))
            .andExpect(jsonPath("$.usefulLinks5").value(DEFAULT_USEFUL_LINKS_5.intValue()))
            .andExpect(jsonPath("$.usefulLinks6").value(DEFAULT_USEFUL_LINKS_6.intValue()))
            .andExpect(jsonPath("$.recentVideos1").value(DEFAULT_RECENT_VIDEOS_1.intValue()))
            .andExpect(jsonPath("$.recentVideos2").value(DEFAULT_RECENT_VIDEOS_2.intValue()))
            .andExpect(jsonPath("$.recentVideos3").value(DEFAULT_RECENT_VIDEOS_3.intValue()))
            .andExpect(jsonPath("$.recentVideos4").value(DEFAULT_RECENT_VIDEOS_4.intValue()))
            .andExpect(jsonPath("$.recentVideos5").value(DEFAULT_RECENT_VIDEOS_5.intValue()))
            .andExpect(jsonPath("$.recentVideos6").value(DEFAULT_RECENT_VIDEOS_6.intValue()));
    }

    @Test
    @Transactional
    void getFrontpageconfigsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        Long id = frontpageconfig.getId();

        defaultFrontpageconfigFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFrontpageconfigFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFrontpageconfigFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where creationDate equals to
        defaultFrontpageconfigFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where creationDate in
        defaultFrontpageconfigFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where creationDate is not null
        defaultFrontpageconfigFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 equals to
        defaultFrontpageconfigFiltering("topNews1.equals=" + DEFAULT_TOP_NEWS_1, "topNews1.equals=" + UPDATED_TOP_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 in
        defaultFrontpageconfigFiltering(
            "topNews1.in=" + DEFAULT_TOP_NEWS_1 + "," + UPDATED_TOP_NEWS_1,
            "topNews1.in=" + UPDATED_TOP_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 is not null
        defaultFrontpageconfigFiltering("topNews1.specified=true", "topNews1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "topNews1.greaterThanOrEqual=" + DEFAULT_TOP_NEWS_1,
            "topNews1.greaterThanOrEqual=" + UPDATED_TOP_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 is less than or equal to
        defaultFrontpageconfigFiltering("topNews1.lessThanOrEqual=" + DEFAULT_TOP_NEWS_1, "topNews1.lessThanOrEqual=" + SMALLER_TOP_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 is less than
        defaultFrontpageconfigFiltering("topNews1.lessThan=" + UPDATED_TOP_NEWS_1, "topNews1.lessThan=" + DEFAULT_TOP_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews1 is greater than
        defaultFrontpageconfigFiltering("topNews1.greaterThan=" + SMALLER_TOP_NEWS_1, "topNews1.greaterThan=" + DEFAULT_TOP_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 equals to
        defaultFrontpageconfigFiltering("topNews2.equals=" + DEFAULT_TOP_NEWS_2, "topNews2.equals=" + UPDATED_TOP_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 in
        defaultFrontpageconfigFiltering(
            "topNews2.in=" + DEFAULT_TOP_NEWS_2 + "," + UPDATED_TOP_NEWS_2,
            "topNews2.in=" + UPDATED_TOP_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 is not null
        defaultFrontpageconfigFiltering("topNews2.specified=true", "topNews2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "topNews2.greaterThanOrEqual=" + DEFAULT_TOP_NEWS_2,
            "topNews2.greaterThanOrEqual=" + UPDATED_TOP_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 is less than or equal to
        defaultFrontpageconfigFiltering("topNews2.lessThanOrEqual=" + DEFAULT_TOP_NEWS_2, "topNews2.lessThanOrEqual=" + SMALLER_TOP_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 is less than
        defaultFrontpageconfigFiltering("topNews2.lessThan=" + UPDATED_TOP_NEWS_2, "topNews2.lessThan=" + DEFAULT_TOP_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews2 is greater than
        defaultFrontpageconfigFiltering("topNews2.greaterThan=" + SMALLER_TOP_NEWS_2, "topNews2.greaterThan=" + DEFAULT_TOP_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 equals to
        defaultFrontpageconfigFiltering("topNews3.equals=" + DEFAULT_TOP_NEWS_3, "topNews3.equals=" + UPDATED_TOP_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 in
        defaultFrontpageconfigFiltering(
            "topNews3.in=" + DEFAULT_TOP_NEWS_3 + "," + UPDATED_TOP_NEWS_3,
            "topNews3.in=" + UPDATED_TOP_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 is not null
        defaultFrontpageconfigFiltering("topNews3.specified=true", "topNews3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "topNews3.greaterThanOrEqual=" + DEFAULT_TOP_NEWS_3,
            "topNews3.greaterThanOrEqual=" + UPDATED_TOP_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 is less than or equal to
        defaultFrontpageconfigFiltering("topNews3.lessThanOrEqual=" + DEFAULT_TOP_NEWS_3, "topNews3.lessThanOrEqual=" + SMALLER_TOP_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 is less than
        defaultFrontpageconfigFiltering("topNews3.lessThan=" + UPDATED_TOP_NEWS_3, "topNews3.lessThan=" + DEFAULT_TOP_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews3 is greater than
        defaultFrontpageconfigFiltering("topNews3.greaterThan=" + SMALLER_TOP_NEWS_3, "topNews3.greaterThan=" + DEFAULT_TOP_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 equals to
        defaultFrontpageconfigFiltering("topNews4.equals=" + DEFAULT_TOP_NEWS_4, "topNews4.equals=" + UPDATED_TOP_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 in
        defaultFrontpageconfigFiltering(
            "topNews4.in=" + DEFAULT_TOP_NEWS_4 + "," + UPDATED_TOP_NEWS_4,
            "topNews4.in=" + UPDATED_TOP_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 is not null
        defaultFrontpageconfigFiltering("topNews4.specified=true", "topNews4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "topNews4.greaterThanOrEqual=" + DEFAULT_TOP_NEWS_4,
            "topNews4.greaterThanOrEqual=" + UPDATED_TOP_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 is less than or equal to
        defaultFrontpageconfigFiltering("topNews4.lessThanOrEqual=" + DEFAULT_TOP_NEWS_4, "topNews4.lessThanOrEqual=" + SMALLER_TOP_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 is less than
        defaultFrontpageconfigFiltering("topNews4.lessThan=" + UPDATED_TOP_NEWS_4, "topNews4.lessThan=" + DEFAULT_TOP_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews4 is greater than
        defaultFrontpageconfigFiltering("topNews4.greaterThan=" + SMALLER_TOP_NEWS_4, "topNews4.greaterThan=" + DEFAULT_TOP_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 equals to
        defaultFrontpageconfigFiltering("topNews5.equals=" + DEFAULT_TOP_NEWS_5, "topNews5.equals=" + UPDATED_TOP_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 in
        defaultFrontpageconfigFiltering(
            "topNews5.in=" + DEFAULT_TOP_NEWS_5 + "," + UPDATED_TOP_NEWS_5,
            "topNews5.in=" + UPDATED_TOP_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 is not null
        defaultFrontpageconfigFiltering("topNews5.specified=true", "topNews5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "topNews5.greaterThanOrEqual=" + DEFAULT_TOP_NEWS_5,
            "topNews5.greaterThanOrEqual=" + UPDATED_TOP_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 is less than or equal to
        defaultFrontpageconfigFiltering("topNews5.lessThanOrEqual=" + DEFAULT_TOP_NEWS_5, "topNews5.lessThanOrEqual=" + SMALLER_TOP_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 is less than
        defaultFrontpageconfigFiltering("topNews5.lessThan=" + UPDATED_TOP_NEWS_5, "topNews5.lessThan=" + DEFAULT_TOP_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByTopNews5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where topNews5 is greater than
        defaultFrontpageconfigFiltering("topNews5.greaterThan=" + SMALLER_TOP_NEWS_5, "topNews5.greaterThan=" + DEFAULT_TOP_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 equals to
        defaultFrontpageconfigFiltering("latestNews1.equals=" + DEFAULT_LATEST_NEWS_1, "latestNews1.equals=" + UPDATED_LATEST_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 in
        defaultFrontpageconfigFiltering(
            "latestNews1.in=" + DEFAULT_LATEST_NEWS_1 + "," + UPDATED_LATEST_NEWS_1,
            "latestNews1.in=" + UPDATED_LATEST_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 is not null
        defaultFrontpageconfigFiltering("latestNews1.specified=true", "latestNews1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews1.greaterThanOrEqual=" + DEFAULT_LATEST_NEWS_1,
            "latestNews1.greaterThanOrEqual=" + UPDATED_LATEST_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews1.lessThanOrEqual=" + DEFAULT_LATEST_NEWS_1,
            "latestNews1.lessThanOrEqual=" + SMALLER_LATEST_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 is less than
        defaultFrontpageconfigFiltering("latestNews1.lessThan=" + UPDATED_LATEST_NEWS_1, "latestNews1.lessThan=" + DEFAULT_LATEST_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews1 is greater than
        defaultFrontpageconfigFiltering(
            "latestNews1.greaterThan=" + SMALLER_LATEST_NEWS_1,
            "latestNews1.greaterThan=" + DEFAULT_LATEST_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 equals to
        defaultFrontpageconfigFiltering("latestNews2.equals=" + DEFAULT_LATEST_NEWS_2, "latestNews2.equals=" + UPDATED_LATEST_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 in
        defaultFrontpageconfigFiltering(
            "latestNews2.in=" + DEFAULT_LATEST_NEWS_2 + "," + UPDATED_LATEST_NEWS_2,
            "latestNews2.in=" + UPDATED_LATEST_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 is not null
        defaultFrontpageconfigFiltering("latestNews2.specified=true", "latestNews2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews2.greaterThanOrEqual=" + DEFAULT_LATEST_NEWS_2,
            "latestNews2.greaterThanOrEqual=" + UPDATED_LATEST_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews2.lessThanOrEqual=" + DEFAULT_LATEST_NEWS_2,
            "latestNews2.lessThanOrEqual=" + SMALLER_LATEST_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 is less than
        defaultFrontpageconfigFiltering("latestNews2.lessThan=" + UPDATED_LATEST_NEWS_2, "latestNews2.lessThan=" + DEFAULT_LATEST_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews2 is greater than
        defaultFrontpageconfigFiltering(
            "latestNews2.greaterThan=" + SMALLER_LATEST_NEWS_2,
            "latestNews2.greaterThan=" + DEFAULT_LATEST_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 equals to
        defaultFrontpageconfigFiltering("latestNews3.equals=" + DEFAULT_LATEST_NEWS_3, "latestNews3.equals=" + UPDATED_LATEST_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 in
        defaultFrontpageconfigFiltering(
            "latestNews3.in=" + DEFAULT_LATEST_NEWS_3 + "," + UPDATED_LATEST_NEWS_3,
            "latestNews3.in=" + UPDATED_LATEST_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 is not null
        defaultFrontpageconfigFiltering("latestNews3.specified=true", "latestNews3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews3.greaterThanOrEqual=" + DEFAULT_LATEST_NEWS_3,
            "latestNews3.greaterThanOrEqual=" + UPDATED_LATEST_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews3.lessThanOrEqual=" + DEFAULT_LATEST_NEWS_3,
            "latestNews3.lessThanOrEqual=" + SMALLER_LATEST_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 is less than
        defaultFrontpageconfigFiltering("latestNews3.lessThan=" + UPDATED_LATEST_NEWS_3, "latestNews3.lessThan=" + DEFAULT_LATEST_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews3 is greater than
        defaultFrontpageconfigFiltering(
            "latestNews3.greaterThan=" + SMALLER_LATEST_NEWS_3,
            "latestNews3.greaterThan=" + DEFAULT_LATEST_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 equals to
        defaultFrontpageconfigFiltering("latestNews4.equals=" + DEFAULT_LATEST_NEWS_4, "latestNews4.equals=" + UPDATED_LATEST_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 in
        defaultFrontpageconfigFiltering(
            "latestNews4.in=" + DEFAULT_LATEST_NEWS_4 + "," + UPDATED_LATEST_NEWS_4,
            "latestNews4.in=" + UPDATED_LATEST_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 is not null
        defaultFrontpageconfigFiltering("latestNews4.specified=true", "latestNews4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews4.greaterThanOrEqual=" + DEFAULT_LATEST_NEWS_4,
            "latestNews4.greaterThanOrEqual=" + UPDATED_LATEST_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews4.lessThanOrEqual=" + DEFAULT_LATEST_NEWS_4,
            "latestNews4.lessThanOrEqual=" + SMALLER_LATEST_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 is less than
        defaultFrontpageconfigFiltering("latestNews4.lessThan=" + UPDATED_LATEST_NEWS_4, "latestNews4.lessThan=" + DEFAULT_LATEST_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews4 is greater than
        defaultFrontpageconfigFiltering(
            "latestNews4.greaterThan=" + SMALLER_LATEST_NEWS_4,
            "latestNews4.greaterThan=" + DEFAULT_LATEST_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 equals to
        defaultFrontpageconfigFiltering("latestNews5.equals=" + DEFAULT_LATEST_NEWS_5, "latestNews5.equals=" + UPDATED_LATEST_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 in
        defaultFrontpageconfigFiltering(
            "latestNews5.in=" + DEFAULT_LATEST_NEWS_5 + "," + UPDATED_LATEST_NEWS_5,
            "latestNews5.in=" + UPDATED_LATEST_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 is not null
        defaultFrontpageconfigFiltering("latestNews5.specified=true", "latestNews5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews5.greaterThanOrEqual=" + DEFAULT_LATEST_NEWS_5,
            "latestNews5.greaterThanOrEqual=" + UPDATED_LATEST_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "latestNews5.lessThanOrEqual=" + DEFAULT_LATEST_NEWS_5,
            "latestNews5.lessThanOrEqual=" + SMALLER_LATEST_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 is less than
        defaultFrontpageconfigFiltering("latestNews5.lessThan=" + UPDATED_LATEST_NEWS_5, "latestNews5.lessThan=" + DEFAULT_LATEST_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByLatestNews5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where latestNews5 is greater than
        defaultFrontpageconfigFiltering(
            "latestNews5.greaterThan=" + SMALLER_LATEST_NEWS_5,
            "latestNews5.greaterThan=" + DEFAULT_LATEST_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 equals to
        defaultFrontpageconfigFiltering(
            "breakingNews1.equals=" + DEFAULT_BREAKING_NEWS_1,
            "breakingNews1.equals=" + UPDATED_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 in
        defaultFrontpageconfigFiltering(
            "breakingNews1.in=" + DEFAULT_BREAKING_NEWS_1 + "," + UPDATED_BREAKING_NEWS_1,
            "breakingNews1.in=" + UPDATED_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 is not null
        defaultFrontpageconfigFiltering("breakingNews1.specified=true", "breakingNews1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "breakingNews1.greaterThanOrEqual=" + DEFAULT_BREAKING_NEWS_1,
            "breakingNews1.greaterThanOrEqual=" + UPDATED_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "breakingNews1.lessThanOrEqual=" + DEFAULT_BREAKING_NEWS_1,
            "breakingNews1.lessThanOrEqual=" + SMALLER_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 is less than
        defaultFrontpageconfigFiltering(
            "breakingNews1.lessThan=" + UPDATED_BREAKING_NEWS_1,
            "breakingNews1.lessThan=" + DEFAULT_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByBreakingNews1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where breakingNews1 is greater than
        defaultFrontpageconfigFiltering(
            "breakingNews1.greaterThan=" + SMALLER_BREAKING_NEWS_1,
            "breakingNews1.greaterThan=" + DEFAULT_BREAKING_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 equals to
        defaultFrontpageconfigFiltering("recentPosts1.equals=" + DEFAULT_RECENT_POSTS_1, "recentPosts1.equals=" + UPDATED_RECENT_POSTS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 in
        defaultFrontpageconfigFiltering(
            "recentPosts1.in=" + DEFAULT_RECENT_POSTS_1 + "," + UPDATED_RECENT_POSTS_1,
            "recentPosts1.in=" + UPDATED_RECENT_POSTS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 is not null
        defaultFrontpageconfigFiltering("recentPosts1.specified=true", "recentPosts1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts1.greaterThanOrEqual=" + DEFAULT_RECENT_POSTS_1,
            "recentPosts1.greaterThanOrEqual=" + UPDATED_RECENT_POSTS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts1.lessThanOrEqual=" + DEFAULT_RECENT_POSTS_1,
            "recentPosts1.lessThanOrEqual=" + SMALLER_RECENT_POSTS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 is less than
        defaultFrontpageconfigFiltering(
            "recentPosts1.lessThan=" + UPDATED_RECENT_POSTS_1,
            "recentPosts1.lessThan=" + DEFAULT_RECENT_POSTS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts1 is greater than
        defaultFrontpageconfigFiltering(
            "recentPosts1.greaterThan=" + SMALLER_RECENT_POSTS_1,
            "recentPosts1.greaterThan=" + DEFAULT_RECENT_POSTS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 equals to
        defaultFrontpageconfigFiltering("recentPosts2.equals=" + DEFAULT_RECENT_POSTS_2, "recentPosts2.equals=" + UPDATED_RECENT_POSTS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 in
        defaultFrontpageconfigFiltering(
            "recentPosts2.in=" + DEFAULT_RECENT_POSTS_2 + "," + UPDATED_RECENT_POSTS_2,
            "recentPosts2.in=" + UPDATED_RECENT_POSTS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 is not null
        defaultFrontpageconfigFiltering("recentPosts2.specified=true", "recentPosts2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts2.greaterThanOrEqual=" + DEFAULT_RECENT_POSTS_2,
            "recentPosts2.greaterThanOrEqual=" + UPDATED_RECENT_POSTS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts2.lessThanOrEqual=" + DEFAULT_RECENT_POSTS_2,
            "recentPosts2.lessThanOrEqual=" + SMALLER_RECENT_POSTS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 is less than
        defaultFrontpageconfigFiltering(
            "recentPosts2.lessThan=" + UPDATED_RECENT_POSTS_2,
            "recentPosts2.lessThan=" + DEFAULT_RECENT_POSTS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts2 is greater than
        defaultFrontpageconfigFiltering(
            "recentPosts2.greaterThan=" + SMALLER_RECENT_POSTS_2,
            "recentPosts2.greaterThan=" + DEFAULT_RECENT_POSTS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 equals to
        defaultFrontpageconfigFiltering("recentPosts3.equals=" + DEFAULT_RECENT_POSTS_3, "recentPosts3.equals=" + UPDATED_RECENT_POSTS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 in
        defaultFrontpageconfigFiltering(
            "recentPosts3.in=" + DEFAULT_RECENT_POSTS_3 + "," + UPDATED_RECENT_POSTS_3,
            "recentPosts3.in=" + UPDATED_RECENT_POSTS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 is not null
        defaultFrontpageconfigFiltering("recentPosts3.specified=true", "recentPosts3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts3.greaterThanOrEqual=" + DEFAULT_RECENT_POSTS_3,
            "recentPosts3.greaterThanOrEqual=" + UPDATED_RECENT_POSTS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts3.lessThanOrEqual=" + DEFAULT_RECENT_POSTS_3,
            "recentPosts3.lessThanOrEqual=" + SMALLER_RECENT_POSTS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 is less than
        defaultFrontpageconfigFiltering(
            "recentPosts3.lessThan=" + UPDATED_RECENT_POSTS_3,
            "recentPosts3.lessThan=" + DEFAULT_RECENT_POSTS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts3 is greater than
        defaultFrontpageconfigFiltering(
            "recentPosts3.greaterThan=" + SMALLER_RECENT_POSTS_3,
            "recentPosts3.greaterThan=" + DEFAULT_RECENT_POSTS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 equals to
        defaultFrontpageconfigFiltering("recentPosts4.equals=" + DEFAULT_RECENT_POSTS_4, "recentPosts4.equals=" + UPDATED_RECENT_POSTS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 in
        defaultFrontpageconfigFiltering(
            "recentPosts4.in=" + DEFAULT_RECENT_POSTS_4 + "," + UPDATED_RECENT_POSTS_4,
            "recentPosts4.in=" + UPDATED_RECENT_POSTS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 is not null
        defaultFrontpageconfigFiltering("recentPosts4.specified=true", "recentPosts4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts4.greaterThanOrEqual=" + DEFAULT_RECENT_POSTS_4,
            "recentPosts4.greaterThanOrEqual=" + UPDATED_RECENT_POSTS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentPosts4.lessThanOrEqual=" + DEFAULT_RECENT_POSTS_4,
            "recentPosts4.lessThanOrEqual=" + SMALLER_RECENT_POSTS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 is less than
        defaultFrontpageconfigFiltering(
            "recentPosts4.lessThan=" + UPDATED_RECENT_POSTS_4,
            "recentPosts4.lessThan=" + DEFAULT_RECENT_POSTS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentPosts4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentPosts4 is greater than
        defaultFrontpageconfigFiltering(
            "recentPosts4.greaterThan=" + SMALLER_RECENT_POSTS_4,
            "recentPosts4.greaterThan=" + DEFAULT_RECENT_POSTS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles1.equals=" + DEFAULT_FEATURED_ARTICLES_1,
            "featuredArticles1.equals=" + UPDATED_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 in
        defaultFrontpageconfigFiltering(
            "featuredArticles1.in=" + DEFAULT_FEATURED_ARTICLES_1 + "," + UPDATED_FEATURED_ARTICLES_1,
            "featuredArticles1.in=" + UPDATED_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 is not null
        defaultFrontpageconfigFiltering("featuredArticles1.specified=true", "featuredArticles1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles1.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_1,
            "featuredArticles1.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles1.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_1,
            "featuredArticles1.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles1.lessThan=" + UPDATED_FEATURED_ARTICLES_1,
            "featuredArticles1.lessThan=" + DEFAULT_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles1 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles1.greaterThan=" + SMALLER_FEATURED_ARTICLES_1,
            "featuredArticles1.greaterThan=" + DEFAULT_FEATURED_ARTICLES_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles2.equals=" + DEFAULT_FEATURED_ARTICLES_2,
            "featuredArticles2.equals=" + UPDATED_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 in
        defaultFrontpageconfigFiltering(
            "featuredArticles2.in=" + DEFAULT_FEATURED_ARTICLES_2 + "," + UPDATED_FEATURED_ARTICLES_2,
            "featuredArticles2.in=" + UPDATED_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 is not null
        defaultFrontpageconfigFiltering("featuredArticles2.specified=true", "featuredArticles2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles2.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_2,
            "featuredArticles2.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles2.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_2,
            "featuredArticles2.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles2.lessThan=" + UPDATED_FEATURED_ARTICLES_2,
            "featuredArticles2.lessThan=" + DEFAULT_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles2 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles2.greaterThan=" + SMALLER_FEATURED_ARTICLES_2,
            "featuredArticles2.greaterThan=" + DEFAULT_FEATURED_ARTICLES_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles3.equals=" + DEFAULT_FEATURED_ARTICLES_3,
            "featuredArticles3.equals=" + UPDATED_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 in
        defaultFrontpageconfigFiltering(
            "featuredArticles3.in=" + DEFAULT_FEATURED_ARTICLES_3 + "," + UPDATED_FEATURED_ARTICLES_3,
            "featuredArticles3.in=" + UPDATED_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 is not null
        defaultFrontpageconfigFiltering("featuredArticles3.specified=true", "featuredArticles3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles3.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_3,
            "featuredArticles3.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles3.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_3,
            "featuredArticles3.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles3.lessThan=" + UPDATED_FEATURED_ARTICLES_3,
            "featuredArticles3.lessThan=" + DEFAULT_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles3 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles3.greaterThan=" + SMALLER_FEATURED_ARTICLES_3,
            "featuredArticles3.greaterThan=" + DEFAULT_FEATURED_ARTICLES_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles4.equals=" + DEFAULT_FEATURED_ARTICLES_4,
            "featuredArticles4.equals=" + UPDATED_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 in
        defaultFrontpageconfigFiltering(
            "featuredArticles4.in=" + DEFAULT_FEATURED_ARTICLES_4 + "," + UPDATED_FEATURED_ARTICLES_4,
            "featuredArticles4.in=" + UPDATED_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 is not null
        defaultFrontpageconfigFiltering("featuredArticles4.specified=true", "featuredArticles4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles4.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_4,
            "featuredArticles4.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles4.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_4,
            "featuredArticles4.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles4.lessThan=" + UPDATED_FEATURED_ARTICLES_4,
            "featuredArticles4.lessThan=" + DEFAULT_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles4 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles4.greaterThan=" + SMALLER_FEATURED_ARTICLES_4,
            "featuredArticles4.greaterThan=" + DEFAULT_FEATURED_ARTICLES_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles5.equals=" + DEFAULT_FEATURED_ARTICLES_5,
            "featuredArticles5.equals=" + UPDATED_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 in
        defaultFrontpageconfigFiltering(
            "featuredArticles5.in=" + DEFAULT_FEATURED_ARTICLES_5 + "," + UPDATED_FEATURED_ARTICLES_5,
            "featuredArticles5.in=" + UPDATED_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 is not null
        defaultFrontpageconfigFiltering("featuredArticles5.specified=true", "featuredArticles5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles5.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_5,
            "featuredArticles5.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles5.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_5,
            "featuredArticles5.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles5.lessThan=" + UPDATED_FEATURED_ARTICLES_5,
            "featuredArticles5.lessThan=" + DEFAULT_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles5 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles5.greaterThan=" + SMALLER_FEATURED_ARTICLES_5,
            "featuredArticles5.greaterThan=" + DEFAULT_FEATURED_ARTICLES_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles6.equals=" + DEFAULT_FEATURED_ARTICLES_6,
            "featuredArticles6.equals=" + UPDATED_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 in
        defaultFrontpageconfigFiltering(
            "featuredArticles6.in=" + DEFAULT_FEATURED_ARTICLES_6 + "," + UPDATED_FEATURED_ARTICLES_6,
            "featuredArticles6.in=" + UPDATED_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 is not null
        defaultFrontpageconfigFiltering("featuredArticles6.specified=true", "featuredArticles6.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles6.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_6,
            "featuredArticles6.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles6.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_6,
            "featuredArticles6.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles6.lessThan=" + UPDATED_FEATURED_ARTICLES_6,
            "featuredArticles6.lessThan=" + DEFAULT_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles6 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles6.greaterThan=" + SMALLER_FEATURED_ARTICLES_6,
            "featuredArticles6.greaterThan=" + DEFAULT_FEATURED_ARTICLES_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles7.equals=" + DEFAULT_FEATURED_ARTICLES_7,
            "featuredArticles7.equals=" + UPDATED_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 in
        defaultFrontpageconfigFiltering(
            "featuredArticles7.in=" + DEFAULT_FEATURED_ARTICLES_7 + "," + UPDATED_FEATURED_ARTICLES_7,
            "featuredArticles7.in=" + UPDATED_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 is not null
        defaultFrontpageconfigFiltering("featuredArticles7.specified=true", "featuredArticles7.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles7.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_7,
            "featuredArticles7.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles7.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_7,
            "featuredArticles7.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles7.lessThan=" + UPDATED_FEATURED_ARTICLES_7,
            "featuredArticles7.lessThan=" + DEFAULT_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles7IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles7 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles7.greaterThan=" + SMALLER_FEATURED_ARTICLES_7,
            "featuredArticles7.greaterThan=" + DEFAULT_FEATURED_ARTICLES_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles8.equals=" + DEFAULT_FEATURED_ARTICLES_8,
            "featuredArticles8.equals=" + UPDATED_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 in
        defaultFrontpageconfigFiltering(
            "featuredArticles8.in=" + DEFAULT_FEATURED_ARTICLES_8 + "," + UPDATED_FEATURED_ARTICLES_8,
            "featuredArticles8.in=" + UPDATED_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 is not null
        defaultFrontpageconfigFiltering("featuredArticles8.specified=true", "featuredArticles8.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles8.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_8,
            "featuredArticles8.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles8.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_8,
            "featuredArticles8.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles8.lessThan=" + UPDATED_FEATURED_ARTICLES_8,
            "featuredArticles8.lessThan=" + DEFAULT_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles8IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles8 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles8.greaterThan=" + SMALLER_FEATURED_ARTICLES_8,
            "featuredArticles8.greaterThan=" + DEFAULT_FEATURED_ARTICLES_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles9.equals=" + DEFAULT_FEATURED_ARTICLES_9,
            "featuredArticles9.equals=" + UPDATED_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 in
        defaultFrontpageconfigFiltering(
            "featuredArticles9.in=" + DEFAULT_FEATURED_ARTICLES_9 + "," + UPDATED_FEATURED_ARTICLES_9,
            "featuredArticles9.in=" + UPDATED_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 is not null
        defaultFrontpageconfigFiltering("featuredArticles9.specified=true", "featuredArticles9.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles9.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_9,
            "featuredArticles9.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles9.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_9,
            "featuredArticles9.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles9.lessThan=" + UPDATED_FEATURED_ARTICLES_9,
            "featuredArticles9.lessThan=" + DEFAULT_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles9IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles9 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles9.greaterThan=" + SMALLER_FEATURED_ARTICLES_9,
            "featuredArticles9.greaterThan=" + DEFAULT_FEATURED_ARTICLES_9
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 equals to
        defaultFrontpageconfigFiltering(
            "featuredArticles10.equals=" + DEFAULT_FEATURED_ARTICLES_10,
            "featuredArticles10.equals=" + UPDATED_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 in
        defaultFrontpageconfigFiltering(
            "featuredArticles10.in=" + DEFAULT_FEATURED_ARTICLES_10 + "," + UPDATED_FEATURED_ARTICLES_10,
            "featuredArticles10.in=" + UPDATED_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 is not null
        defaultFrontpageconfigFiltering("featuredArticles10.specified=true", "featuredArticles10.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles10.greaterThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_10,
            "featuredArticles10.greaterThanOrEqual=" + UPDATED_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 is less than or equal to
        defaultFrontpageconfigFiltering(
            "featuredArticles10.lessThanOrEqual=" + DEFAULT_FEATURED_ARTICLES_10,
            "featuredArticles10.lessThanOrEqual=" + SMALLER_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 is less than
        defaultFrontpageconfigFiltering(
            "featuredArticles10.lessThan=" + UPDATED_FEATURED_ARTICLES_10,
            "featuredArticles10.lessThan=" + DEFAULT_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByFeaturedArticles10IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where featuredArticles10 is greater than
        defaultFrontpageconfigFiltering(
            "featuredArticles10.greaterThan=" + SMALLER_FEATURED_ARTICLES_10,
            "featuredArticles10.greaterThan=" + DEFAULT_FEATURED_ARTICLES_10
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 equals to
        defaultFrontpageconfigFiltering("popularNews1.equals=" + DEFAULT_POPULAR_NEWS_1, "popularNews1.equals=" + UPDATED_POPULAR_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 in
        defaultFrontpageconfigFiltering(
            "popularNews1.in=" + DEFAULT_POPULAR_NEWS_1 + "," + UPDATED_POPULAR_NEWS_1,
            "popularNews1.in=" + UPDATED_POPULAR_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 is not null
        defaultFrontpageconfigFiltering("popularNews1.specified=true", "popularNews1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews1.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_1,
            "popularNews1.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews1.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_1,
            "popularNews1.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 is less than
        defaultFrontpageconfigFiltering(
            "popularNews1.lessThan=" + UPDATED_POPULAR_NEWS_1,
            "popularNews1.lessThan=" + DEFAULT_POPULAR_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews1 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews1.greaterThan=" + SMALLER_POPULAR_NEWS_1,
            "popularNews1.greaterThan=" + DEFAULT_POPULAR_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 equals to
        defaultFrontpageconfigFiltering("popularNews2.equals=" + DEFAULT_POPULAR_NEWS_2, "popularNews2.equals=" + UPDATED_POPULAR_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 in
        defaultFrontpageconfigFiltering(
            "popularNews2.in=" + DEFAULT_POPULAR_NEWS_2 + "," + UPDATED_POPULAR_NEWS_2,
            "popularNews2.in=" + UPDATED_POPULAR_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 is not null
        defaultFrontpageconfigFiltering("popularNews2.specified=true", "popularNews2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews2.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_2,
            "popularNews2.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews2.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_2,
            "popularNews2.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 is less than
        defaultFrontpageconfigFiltering(
            "popularNews2.lessThan=" + UPDATED_POPULAR_NEWS_2,
            "popularNews2.lessThan=" + DEFAULT_POPULAR_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews2 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews2.greaterThan=" + SMALLER_POPULAR_NEWS_2,
            "popularNews2.greaterThan=" + DEFAULT_POPULAR_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 equals to
        defaultFrontpageconfigFiltering("popularNews3.equals=" + DEFAULT_POPULAR_NEWS_3, "popularNews3.equals=" + UPDATED_POPULAR_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 in
        defaultFrontpageconfigFiltering(
            "popularNews3.in=" + DEFAULT_POPULAR_NEWS_3 + "," + UPDATED_POPULAR_NEWS_3,
            "popularNews3.in=" + UPDATED_POPULAR_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 is not null
        defaultFrontpageconfigFiltering("popularNews3.specified=true", "popularNews3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews3.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_3,
            "popularNews3.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews3.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_3,
            "popularNews3.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 is less than
        defaultFrontpageconfigFiltering(
            "popularNews3.lessThan=" + UPDATED_POPULAR_NEWS_3,
            "popularNews3.lessThan=" + DEFAULT_POPULAR_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews3 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews3.greaterThan=" + SMALLER_POPULAR_NEWS_3,
            "popularNews3.greaterThan=" + DEFAULT_POPULAR_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 equals to
        defaultFrontpageconfigFiltering("popularNews4.equals=" + DEFAULT_POPULAR_NEWS_4, "popularNews4.equals=" + UPDATED_POPULAR_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 in
        defaultFrontpageconfigFiltering(
            "popularNews4.in=" + DEFAULT_POPULAR_NEWS_4 + "," + UPDATED_POPULAR_NEWS_4,
            "popularNews4.in=" + UPDATED_POPULAR_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 is not null
        defaultFrontpageconfigFiltering("popularNews4.specified=true", "popularNews4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews4.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_4,
            "popularNews4.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews4.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_4,
            "popularNews4.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 is less than
        defaultFrontpageconfigFiltering(
            "popularNews4.lessThan=" + UPDATED_POPULAR_NEWS_4,
            "popularNews4.lessThan=" + DEFAULT_POPULAR_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews4 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews4.greaterThan=" + SMALLER_POPULAR_NEWS_4,
            "popularNews4.greaterThan=" + DEFAULT_POPULAR_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 equals to
        defaultFrontpageconfigFiltering("popularNews5.equals=" + DEFAULT_POPULAR_NEWS_5, "popularNews5.equals=" + UPDATED_POPULAR_NEWS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 in
        defaultFrontpageconfigFiltering(
            "popularNews5.in=" + DEFAULT_POPULAR_NEWS_5 + "," + UPDATED_POPULAR_NEWS_5,
            "popularNews5.in=" + UPDATED_POPULAR_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 is not null
        defaultFrontpageconfigFiltering("popularNews5.specified=true", "popularNews5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews5.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_5,
            "popularNews5.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews5.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_5,
            "popularNews5.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 is less than
        defaultFrontpageconfigFiltering(
            "popularNews5.lessThan=" + UPDATED_POPULAR_NEWS_5,
            "popularNews5.lessThan=" + DEFAULT_POPULAR_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews5 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews5.greaterThan=" + SMALLER_POPULAR_NEWS_5,
            "popularNews5.greaterThan=" + DEFAULT_POPULAR_NEWS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 equals to
        defaultFrontpageconfigFiltering("popularNews6.equals=" + DEFAULT_POPULAR_NEWS_6, "popularNews6.equals=" + UPDATED_POPULAR_NEWS_6);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 in
        defaultFrontpageconfigFiltering(
            "popularNews6.in=" + DEFAULT_POPULAR_NEWS_6 + "," + UPDATED_POPULAR_NEWS_6,
            "popularNews6.in=" + UPDATED_POPULAR_NEWS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 is not null
        defaultFrontpageconfigFiltering("popularNews6.specified=true", "popularNews6.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews6.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_6,
            "popularNews6.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews6.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_6,
            "popularNews6.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 is less than
        defaultFrontpageconfigFiltering(
            "popularNews6.lessThan=" + UPDATED_POPULAR_NEWS_6,
            "popularNews6.lessThan=" + DEFAULT_POPULAR_NEWS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews6 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews6.greaterThan=" + SMALLER_POPULAR_NEWS_6,
            "popularNews6.greaterThan=" + DEFAULT_POPULAR_NEWS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 equals to
        defaultFrontpageconfigFiltering("popularNews7.equals=" + DEFAULT_POPULAR_NEWS_7, "popularNews7.equals=" + UPDATED_POPULAR_NEWS_7);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 in
        defaultFrontpageconfigFiltering(
            "popularNews7.in=" + DEFAULT_POPULAR_NEWS_7 + "," + UPDATED_POPULAR_NEWS_7,
            "popularNews7.in=" + UPDATED_POPULAR_NEWS_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 is not null
        defaultFrontpageconfigFiltering("popularNews7.specified=true", "popularNews7.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews7.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_7,
            "popularNews7.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews7.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_7,
            "popularNews7.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 is less than
        defaultFrontpageconfigFiltering(
            "popularNews7.lessThan=" + UPDATED_POPULAR_NEWS_7,
            "popularNews7.lessThan=" + DEFAULT_POPULAR_NEWS_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews7IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews7 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews7.greaterThan=" + SMALLER_POPULAR_NEWS_7,
            "popularNews7.greaterThan=" + DEFAULT_POPULAR_NEWS_7
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 equals to
        defaultFrontpageconfigFiltering("popularNews8.equals=" + DEFAULT_POPULAR_NEWS_8, "popularNews8.equals=" + UPDATED_POPULAR_NEWS_8);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 in
        defaultFrontpageconfigFiltering(
            "popularNews8.in=" + DEFAULT_POPULAR_NEWS_8 + "," + UPDATED_POPULAR_NEWS_8,
            "popularNews8.in=" + UPDATED_POPULAR_NEWS_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 is not null
        defaultFrontpageconfigFiltering("popularNews8.specified=true", "popularNews8.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews8.greaterThanOrEqual=" + DEFAULT_POPULAR_NEWS_8,
            "popularNews8.greaterThanOrEqual=" + UPDATED_POPULAR_NEWS_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 is less than or equal to
        defaultFrontpageconfigFiltering(
            "popularNews8.lessThanOrEqual=" + DEFAULT_POPULAR_NEWS_8,
            "popularNews8.lessThanOrEqual=" + SMALLER_POPULAR_NEWS_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 is less than
        defaultFrontpageconfigFiltering(
            "popularNews8.lessThan=" + UPDATED_POPULAR_NEWS_8,
            "popularNews8.lessThan=" + DEFAULT_POPULAR_NEWS_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByPopularNews8IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where popularNews8 is greater than
        defaultFrontpageconfigFiltering(
            "popularNews8.greaterThan=" + SMALLER_POPULAR_NEWS_8,
            "popularNews8.greaterThan=" + DEFAULT_POPULAR_NEWS_8
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 equals to
        defaultFrontpageconfigFiltering("weeklyNews1.equals=" + DEFAULT_WEEKLY_NEWS_1, "weeklyNews1.equals=" + UPDATED_WEEKLY_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 in
        defaultFrontpageconfigFiltering(
            "weeklyNews1.in=" + DEFAULT_WEEKLY_NEWS_1 + "," + UPDATED_WEEKLY_NEWS_1,
            "weeklyNews1.in=" + UPDATED_WEEKLY_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 is not null
        defaultFrontpageconfigFiltering("weeklyNews1.specified=true", "weeklyNews1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews1.greaterThanOrEqual=" + DEFAULT_WEEKLY_NEWS_1,
            "weeklyNews1.greaterThanOrEqual=" + UPDATED_WEEKLY_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews1.lessThanOrEqual=" + DEFAULT_WEEKLY_NEWS_1,
            "weeklyNews1.lessThanOrEqual=" + SMALLER_WEEKLY_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 is less than
        defaultFrontpageconfigFiltering("weeklyNews1.lessThan=" + UPDATED_WEEKLY_NEWS_1, "weeklyNews1.lessThan=" + DEFAULT_WEEKLY_NEWS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews1 is greater than
        defaultFrontpageconfigFiltering(
            "weeklyNews1.greaterThan=" + SMALLER_WEEKLY_NEWS_1,
            "weeklyNews1.greaterThan=" + DEFAULT_WEEKLY_NEWS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 equals to
        defaultFrontpageconfigFiltering("weeklyNews2.equals=" + DEFAULT_WEEKLY_NEWS_2, "weeklyNews2.equals=" + UPDATED_WEEKLY_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 in
        defaultFrontpageconfigFiltering(
            "weeklyNews2.in=" + DEFAULT_WEEKLY_NEWS_2 + "," + UPDATED_WEEKLY_NEWS_2,
            "weeklyNews2.in=" + UPDATED_WEEKLY_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 is not null
        defaultFrontpageconfigFiltering("weeklyNews2.specified=true", "weeklyNews2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews2.greaterThanOrEqual=" + DEFAULT_WEEKLY_NEWS_2,
            "weeklyNews2.greaterThanOrEqual=" + UPDATED_WEEKLY_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews2.lessThanOrEqual=" + DEFAULT_WEEKLY_NEWS_2,
            "weeklyNews2.lessThanOrEqual=" + SMALLER_WEEKLY_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 is less than
        defaultFrontpageconfigFiltering("weeklyNews2.lessThan=" + UPDATED_WEEKLY_NEWS_2, "weeklyNews2.lessThan=" + DEFAULT_WEEKLY_NEWS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews2 is greater than
        defaultFrontpageconfigFiltering(
            "weeklyNews2.greaterThan=" + SMALLER_WEEKLY_NEWS_2,
            "weeklyNews2.greaterThan=" + DEFAULT_WEEKLY_NEWS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 equals to
        defaultFrontpageconfigFiltering("weeklyNews3.equals=" + DEFAULT_WEEKLY_NEWS_3, "weeklyNews3.equals=" + UPDATED_WEEKLY_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 in
        defaultFrontpageconfigFiltering(
            "weeklyNews3.in=" + DEFAULT_WEEKLY_NEWS_3 + "," + UPDATED_WEEKLY_NEWS_3,
            "weeklyNews3.in=" + UPDATED_WEEKLY_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 is not null
        defaultFrontpageconfigFiltering("weeklyNews3.specified=true", "weeklyNews3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews3.greaterThanOrEqual=" + DEFAULT_WEEKLY_NEWS_3,
            "weeklyNews3.greaterThanOrEqual=" + UPDATED_WEEKLY_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews3.lessThanOrEqual=" + DEFAULT_WEEKLY_NEWS_3,
            "weeklyNews3.lessThanOrEqual=" + SMALLER_WEEKLY_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 is less than
        defaultFrontpageconfigFiltering("weeklyNews3.lessThan=" + UPDATED_WEEKLY_NEWS_3, "weeklyNews3.lessThan=" + DEFAULT_WEEKLY_NEWS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews3 is greater than
        defaultFrontpageconfigFiltering(
            "weeklyNews3.greaterThan=" + SMALLER_WEEKLY_NEWS_3,
            "weeklyNews3.greaterThan=" + DEFAULT_WEEKLY_NEWS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 equals to
        defaultFrontpageconfigFiltering("weeklyNews4.equals=" + DEFAULT_WEEKLY_NEWS_4, "weeklyNews4.equals=" + UPDATED_WEEKLY_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 in
        defaultFrontpageconfigFiltering(
            "weeklyNews4.in=" + DEFAULT_WEEKLY_NEWS_4 + "," + UPDATED_WEEKLY_NEWS_4,
            "weeklyNews4.in=" + UPDATED_WEEKLY_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 is not null
        defaultFrontpageconfigFiltering("weeklyNews4.specified=true", "weeklyNews4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews4.greaterThanOrEqual=" + DEFAULT_WEEKLY_NEWS_4,
            "weeklyNews4.greaterThanOrEqual=" + UPDATED_WEEKLY_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "weeklyNews4.lessThanOrEqual=" + DEFAULT_WEEKLY_NEWS_4,
            "weeklyNews4.lessThanOrEqual=" + SMALLER_WEEKLY_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 is less than
        defaultFrontpageconfigFiltering("weeklyNews4.lessThan=" + UPDATED_WEEKLY_NEWS_4, "weeklyNews4.lessThan=" + DEFAULT_WEEKLY_NEWS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByWeeklyNews4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where weeklyNews4 is greater than
        defaultFrontpageconfigFiltering(
            "weeklyNews4.greaterThan=" + SMALLER_WEEKLY_NEWS_4,
            "weeklyNews4.greaterThan=" + DEFAULT_WEEKLY_NEWS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 equals to
        defaultFrontpageconfigFiltering("newsFeeds1.equals=" + DEFAULT_NEWS_FEEDS_1, "newsFeeds1.equals=" + UPDATED_NEWS_FEEDS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 in
        defaultFrontpageconfigFiltering(
            "newsFeeds1.in=" + DEFAULT_NEWS_FEEDS_1 + "," + UPDATED_NEWS_FEEDS_1,
            "newsFeeds1.in=" + UPDATED_NEWS_FEEDS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 is not null
        defaultFrontpageconfigFiltering("newsFeeds1.specified=true", "newsFeeds1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds1.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_1,
            "newsFeeds1.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds1.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_1,
            "newsFeeds1.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 is less than
        defaultFrontpageconfigFiltering("newsFeeds1.lessThan=" + UPDATED_NEWS_FEEDS_1, "newsFeeds1.lessThan=" + DEFAULT_NEWS_FEEDS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds1 is greater than
        defaultFrontpageconfigFiltering("newsFeeds1.greaterThan=" + SMALLER_NEWS_FEEDS_1, "newsFeeds1.greaterThan=" + DEFAULT_NEWS_FEEDS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 equals to
        defaultFrontpageconfigFiltering("newsFeeds2.equals=" + DEFAULT_NEWS_FEEDS_2, "newsFeeds2.equals=" + UPDATED_NEWS_FEEDS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 in
        defaultFrontpageconfigFiltering(
            "newsFeeds2.in=" + DEFAULT_NEWS_FEEDS_2 + "," + UPDATED_NEWS_FEEDS_2,
            "newsFeeds2.in=" + UPDATED_NEWS_FEEDS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 is not null
        defaultFrontpageconfigFiltering("newsFeeds2.specified=true", "newsFeeds2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds2.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_2,
            "newsFeeds2.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds2.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_2,
            "newsFeeds2.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 is less than
        defaultFrontpageconfigFiltering("newsFeeds2.lessThan=" + UPDATED_NEWS_FEEDS_2, "newsFeeds2.lessThan=" + DEFAULT_NEWS_FEEDS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds2 is greater than
        defaultFrontpageconfigFiltering("newsFeeds2.greaterThan=" + SMALLER_NEWS_FEEDS_2, "newsFeeds2.greaterThan=" + DEFAULT_NEWS_FEEDS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 equals to
        defaultFrontpageconfigFiltering("newsFeeds3.equals=" + DEFAULT_NEWS_FEEDS_3, "newsFeeds3.equals=" + UPDATED_NEWS_FEEDS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 in
        defaultFrontpageconfigFiltering(
            "newsFeeds3.in=" + DEFAULT_NEWS_FEEDS_3 + "," + UPDATED_NEWS_FEEDS_3,
            "newsFeeds3.in=" + UPDATED_NEWS_FEEDS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 is not null
        defaultFrontpageconfigFiltering("newsFeeds3.specified=true", "newsFeeds3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds3.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_3,
            "newsFeeds3.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds3.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_3,
            "newsFeeds3.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 is less than
        defaultFrontpageconfigFiltering("newsFeeds3.lessThan=" + UPDATED_NEWS_FEEDS_3, "newsFeeds3.lessThan=" + DEFAULT_NEWS_FEEDS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds3 is greater than
        defaultFrontpageconfigFiltering("newsFeeds3.greaterThan=" + SMALLER_NEWS_FEEDS_3, "newsFeeds3.greaterThan=" + DEFAULT_NEWS_FEEDS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 equals to
        defaultFrontpageconfigFiltering("newsFeeds4.equals=" + DEFAULT_NEWS_FEEDS_4, "newsFeeds4.equals=" + UPDATED_NEWS_FEEDS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 in
        defaultFrontpageconfigFiltering(
            "newsFeeds4.in=" + DEFAULT_NEWS_FEEDS_4 + "," + UPDATED_NEWS_FEEDS_4,
            "newsFeeds4.in=" + UPDATED_NEWS_FEEDS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 is not null
        defaultFrontpageconfigFiltering("newsFeeds4.specified=true", "newsFeeds4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds4.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_4,
            "newsFeeds4.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds4.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_4,
            "newsFeeds4.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 is less than
        defaultFrontpageconfigFiltering("newsFeeds4.lessThan=" + UPDATED_NEWS_FEEDS_4, "newsFeeds4.lessThan=" + DEFAULT_NEWS_FEEDS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds4 is greater than
        defaultFrontpageconfigFiltering("newsFeeds4.greaterThan=" + SMALLER_NEWS_FEEDS_4, "newsFeeds4.greaterThan=" + DEFAULT_NEWS_FEEDS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 equals to
        defaultFrontpageconfigFiltering("newsFeeds5.equals=" + DEFAULT_NEWS_FEEDS_5, "newsFeeds5.equals=" + UPDATED_NEWS_FEEDS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 in
        defaultFrontpageconfigFiltering(
            "newsFeeds5.in=" + DEFAULT_NEWS_FEEDS_5 + "," + UPDATED_NEWS_FEEDS_5,
            "newsFeeds5.in=" + UPDATED_NEWS_FEEDS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 is not null
        defaultFrontpageconfigFiltering("newsFeeds5.specified=true", "newsFeeds5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds5.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_5,
            "newsFeeds5.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds5.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_5,
            "newsFeeds5.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 is less than
        defaultFrontpageconfigFiltering("newsFeeds5.lessThan=" + UPDATED_NEWS_FEEDS_5, "newsFeeds5.lessThan=" + DEFAULT_NEWS_FEEDS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds5 is greater than
        defaultFrontpageconfigFiltering("newsFeeds5.greaterThan=" + SMALLER_NEWS_FEEDS_5, "newsFeeds5.greaterThan=" + DEFAULT_NEWS_FEEDS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 equals to
        defaultFrontpageconfigFiltering("newsFeeds6.equals=" + DEFAULT_NEWS_FEEDS_6, "newsFeeds6.equals=" + UPDATED_NEWS_FEEDS_6);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 in
        defaultFrontpageconfigFiltering(
            "newsFeeds6.in=" + DEFAULT_NEWS_FEEDS_6 + "," + UPDATED_NEWS_FEEDS_6,
            "newsFeeds6.in=" + UPDATED_NEWS_FEEDS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 is not null
        defaultFrontpageconfigFiltering("newsFeeds6.specified=true", "newsFeeds6.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds6.greaterThanOrEqual=" + DEFAULT_NEWS_FEEDS_6,
            "newsFeeds6.greaterThanOrEqual=" + UPDATED_NEWS_FEEDS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 is less than or equal to
        defaultFrontpageconfigFiltering(
            "newsFeeds6.lessThanOrEqual=" + DEFAULT_NEWS_FEEDS_6,
            "newsFeeds6.lessThanOrEqual=" + SMALLER_NEWS_FEEDS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 is less than
        defaultFrontpageconfigFiltering("newsFeeds6.lessThan=" + UPDATED_NEWS_FEEDS_6, "newsFeeds6.lessThan=" + DEFAULT_NEWS_FEEDS_6);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByNewsFeeds6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where newsFeeds6 is greater than
        defaultFrontpageconfigFiltering("newsFeeds6.greaterThan=" + SMALLER_NEWS_FEEDS_6, "newsFeeds6.greaterThan=" + DEFAULT_NEWS_FEEDS_6);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 equals to
        defaultFrontpageconfigFiltering("usefulLinks1.equals=" + DEFAULT_USEFUL_LINKS_1, "usefulLinks1.equals=" + UPDATED_USEFUL_LINKS_1);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 in
        defaultFrontpageconfigFiltering(
            "usefulLinks1.in=" + DEFAULT_USEFUL_LINKS_1 + "," + UPDATED_USEFUL_LINKS_1,
            "usefulLinks1.in=" + UPDATED_USEFUL_LINKS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 is not null
        defaultFrontpageconfigFiltering("usefulLinks1.specified=true", "usefulLinks1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks1.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_1,
            "usefulLinks1.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks1.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_1,
            "usefulLinks1.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks1.lessThan=" + UPDATED_USEFUL_LINKS_1,
            "usefulLinks1.lessThan=" + DEFAULT_USEFUL_LINKS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks1 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks1.greaterThan=" + SMALLER_USEFUL_LINKS_1,
            "usefulLinks1.greaterThan=" + DEFAULT_USEFUL_LINKS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 equals to
        defaultFrontpageconfigFiltering("usefulLinks2.equals=" + DEFAULT_USEFUL_LINKS_2, "usefulLinks2.equals=" + UPDATED_USEFUL_LINKS_2);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 in
        defaultFrontpageconfigFiltering(
            "usefulLinks2.in=" + DEFAULT_USEFUL_LINKS_2 + "," + UPDATED_USEFUL_LINKS_2,
            "usefulLinks2.in=" + UPDATED_USEFUL_LINKS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 is not null
        defaultFrontpageconfigFiltering("usefulLinks2.specified=true", "usefulLinks2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks2.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_2,
            "usefulLinks2.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks2.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_2,
            "usefulLinks2.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks2.lessThan=" + UPDATED_USEFUL_LINKS_2,
            "usefulLinks2.lessThan=" + DEFAULT_USEFUL_LINKS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks2 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks2.greaterThan=" + SMALLER_USEFUL_LINKS_2,
            "usefulLinks2.greaterThan=" + DEFAULT_USEFUL_LINKS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 equals to
        defaultFrontpageconfigFiltering("usefulLinks3.equals=" + DEFAULT_USEFUL_LINKS_3, "usefulLinks3.equals=" + UPDATED_USEFUL_LINKS_3);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 in
        defaultFrontpageconfigFiltering(
            "usefulLinks3.in=" + DEFAULT_USEFUL_LINKS_3 + "," + UPDATED_USEFUL_LINKS_3,
            "usefulLinks3.in=" + UPDATED_USEFUL_LINKS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 is not null
        defaultFrontpageconfigFiltering("usefulLinks3.specified=true", "usefulLinks3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks3.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_3,
            "usefulLinks3.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks3.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_3,
            "usefulLinks3.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks3.lessThan=" + UPDATED_USEFUL_LINKS_3,
            "usefulLinks3.lessThan=" + DEFAULT_USEFUL_LINKS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks3 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks3.greaterThan=" + SMALLER_USEFUL_LINKS_3,
            "usefulLinks3.greaterThan=" + DEFAULT_USEFUL_LINKS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 equals to
        defaultFrontpageconfigFiltering("usefulLinks4.equals=" + DEFAULT_USEFUL_LINKS_4, "usefulLinks4.equals=" + UPDATED_USEFUL_LINKS_4);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 in
        defaultFrontpageconfigFiltering(
            "usefulLinks4.in=" + DEFAULT_USEFUL_LINKS_4 + "," + UPDATED_USEFUL_LINKS_4,
            "usefulLinks4.in=" + UPDATED_USEFUL_LINKS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 is not null
        defaultFrontpageconfigFiltering("usefulLinks4.specified=true", "usefulLinks4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks4.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_4,
            "usefulLinks4.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks4.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_4,
            "usefulLinks4.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks4.lessThan=" + UPDATED_USEFUL_LINKS_4,
            "usefulLinks4.lessThan=" + DEFAULT_USEFUL_LINKS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks4 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks4.greaterThan=" + SMALLER_USEFUL_LINKS_4,
            "usefulLinks4.greaterThan=" + DEFAULT_USEFUL_LINKS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 equals to
        defaultFrontpageconfigFiltering("usefulLinks5.equals=" + DEFAULT_USEFUL_LINKS_5, "usefulLinks5.equals=" + UPDATED_USEFUL_LINKS_5);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 in
        defaultFrontpageconfigFiltering(
            "usefulLinks5.in=" + DEFAULT_USEFUL_LINKS_5 + "," + UPDATED_USEFUL_LINKS_5,
            "usefulLinks5.in=" + UPDATED_USEFUL_LINKS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 is not null
        defaultFrontpageconfigFiltering("usefulLinks5.specified=true", "usefulLinks5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks5.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_5,
            "usefulLinks5.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks5.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_5,
            "usefulLinks5.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks5.lessThan=" + UPDATED_USEFUL_LINKS_5,
            "usefulLinks5.lessThan=" + DEFAULT_USEFUL_LINKS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks5 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks5.greaterThan=" + SMALLER_USEFUL_LINKS_5,
            "usefulLinks5.greaterThan=" + DEFAULT_USEFUL_LINKS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 equals to
        defaultFrontpageconfigFiltering("usefulLinks6.equals=" + DEFAULT_USEFUL_LINKS_6, "usefulLinks6.equals=" + UPDATED_USEFUL_LINKS_6);
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 in
        defaultFrontpageconfigFiltering(
            "usefulLinks6.in=" + DEFAULT_USEFUL_LINKS_6 + "," + UPDATED_USEFUL_LINKS_6,
            "usefulLinks6.in=" + UPDATED_USEFUL_LINKS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 is not null
        defaultFrontpageconfigFiltering("usefulLinks6.specified=true", "usefulLinks6.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks6.greaterThanOrEqual=" + DEFAULT_USEFUL_LINKS_6,
            "usefulLinks6.greaterThanOrEqual=" + UPDATED_USEFUL_LINKS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 is less than or equal to
        defaultFrontpageconfigFiltering(
            "usefulLinks6.lessThanOrEqual=" + DEFAULT_USEFUL_LINKS_6,
            "usefulLinks6.lessThanOrEqual=" + SMALLER_USEFUL_LINKS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 is less than
        defaultFrontpageconfigFiltering(
            "usefulLinks6.lessThan=" + UPDATED_USEFUL_LINKS_6,
            "usefulLinks6.lessThan=" + DEFAULT_USEFUL_LINKS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByUsefulLinks6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where usefulLinks6 is greater than
        defaultFrontpageconfigFiltering(
            "usefulLinks6.greaterThan=" + SMALLER_USEFUL_LINKS_6,
            "usefulLinks6.greaterThan=" + DEFAULT_USEFUL_LINKS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos1.equals=" + DEFAULT_RECENT_VIDEOS_1,
            "recentVideos1.equals=" + UPDATED_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 in
        defaultFrontpageconfigFiltering(
            "recentVideos1.in=" + DEFAULT_RECENT_VIDEOS_1 + "," + UPDATED_RECENT_VIDEOS_1,
            "recentVideos1.in=" + UPDATED_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 is not null
        defaultFrontpageconfigFiltering("recentVideos1.specified=true", "recentVideos1.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos1.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_1,
            "recentVideos1.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos1.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_1,
            "recentVideos1.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos1.lessThan=" + UPDATED_RECENT_VIDEOS_1,
            "recentVideos1.lessThan=" + DEFAULT_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos1 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos1.greaterThan=" + SMALLER_RECENT_VIDEOS_1,
            "recentVideos1.greaterThan=" + DEFAULT_RECENT_VIDEOS_1
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos2.equals=" + DEFAULT_RECENT_VIDEOS_2,
            "recentVideos2.equals=" + UPDATED_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 in
        defaultFrontpageconfigFiltering(
            "recentVideos2.in=" + DEFAULT_RECENT_VIDEOS_2 + "," + UPDATED_RECENT_VIDEOS_2,
            "recentVideos2.in=" + UPDATED_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 is not null
        defaultFrontpageconfigFiltering("recentVideos2.specified=true", "recentVideos2.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos2.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_2,
            "recentVideos2.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos2.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_2,
            "recentVideos2.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos2.lessThan=" + UPDATED_RECENT_VIDEOS_2,
            "recentVideos2.lessThan=" + DEFAULT_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos2 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos2.greaterThan=" + SMALLER_RECENT_VIDEOS_2,
            "recentVideos2.greaterThan=" + DEFAULT_RECENT_VIDEOS_2
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos3.equals=" + DEFAULT_RECENT_VIDEOS_3,
            "recentVideos3.equals=" + UPDATED_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 in
        defaultFrontpageconfigFiltering(
            "recentVideos3.in=" + DEFAULT_RECENT_VIDEOS_3 + "," + UPDATED_RECENT_VIDEOS_3,
            "recentVideos3.in=" + UPDATED_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 is not null
        defaultFrontpageconfigFiltering("recentVideos3.specified=true", "recentVideos3.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos3.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_3,
            "recentVideos3.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos3.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_3,
            "recentVideos3.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos3.lessThan=" + UPDATED_RECENT_VIDEOS_3,
            "recentVideos3.lessThan=" + DEFAULT_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos3 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos3.greaterThan=" + SMALLER_RECENT_VIDEOS_3,
            "recentVideos3.greaterThan=" + DEFAULT_RECENT_VIDEOS_3
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos4.equals=" + DEFAULT_RECENT_VIDEOS_4,
            "recentVideos4.equals=" + UPDATED_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 in
        defaultFrontpageconfigFiltering(
            "recentVideos4.in=" + DEFAULT_RECENT_VIDEOS_4 + "," + UPDATED_RECENT_VIDEOS_4,
            "recentVideos4.in=" + UPDATED_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 is not null
        defaultFrontpageconfigFiltering("recentVideos4.specified=true", "recentVideos4.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos4.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_4,
            "recentVideos4.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos4.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_4,
            "recentVideos4.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos4.lessThan=" + UPDATED_RECENT_VIDEOS_4,
            "recentVideos4.lessThan=" + DEFAULT_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos4 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos4.greaterThan=" + SMALLER_RECENT_VIDEOS_4,
            "recentVideos4.greaterThan=" + DEFAULT_RECENT_VIDEOS_4
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos5.equals=" + DEFAULT_RECENT_VIDEOS_5,
            "recentVideos5.equals=" + UPDATED_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 in
        defaultFrontpageconfigFiltering(
            "recentVideos5.in=" + DEFAULT_RECENT_VIDEOS_5 + "," + UPDATED_RECENT_VIDEOS_5,
            "recentVideos5.in=" + UPDATED_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 is not null
        defaultFrontpageconfigFiltering("recentVideos5.specified=true", "recentVideos5.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos5.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_5,
            "recentVideos5.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos5.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_5,
            "recentVideos5.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos5.lessThan=" + UPDATED_RECENT_VIDEOS_5,
            "recentVideos5.lessThan=" + DEFAULT_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos5 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos5.greaterThan=" + SMALLER_RECENT_VIDEOS_5,
            "recentVideos5.greaterThan=" + DEFAULT_RECENT_VIDEOS_5
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 equals to
        defaultFrontpageconfigFiltering(
            "recentVideos6.equals=" + DEFAULT_RECENT_VIDEOS_6,
            "recentVideos6.equals=" + UPDATED_RECENT_VIDEOS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 in
        defaultFrontpageconfigFiltering(
            "recentVideos6.in=" + DEFAULT_RECENT_VIDEOS_6 + "," + UPDATED_RECENT_VIDEOS_6,
            "recentVideos6.in=" + UPDATED_RECENT_VIDEOS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 is not null
        defaultFrontpageconfigFiltering("recentVideos6.specified=true", "recentVideos6.specified=false");
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 is greater than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos6.greaterThanOrEqual=" + DEFAULT_RECENT_VIDEOS_6,
            "recentVideos6.greaterThanOrEqual=" + UPDATED_RECENT_VIDEOS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 is less than or equal to
        defaultFrontpageconfigFiltering(
            "recentVideos6.lessThanOrEqual=" + DEFAULT_RECENT_VIDEOS_6,
            "recentVideos6.lessThanOrEqual=" + SMALLER_RECENT_VIDEOS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 is less than
        defaultFrontpageconfigFiltering(
            "recentVideos6.lessThan=" + UPDATED_RECENT_VIDEOS_6,
            "recentVideos6.lessThan=" + DEFAULT_RECENT_VIDEOS_6
        );
    }

    @Test
    @Transactional
    void getAllFrontpageconfigsByRecentVideos6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        // Get all the frontpageconfigList where recentVideos6 is greater than
        defaultFrontpageconfigFiltering(
            "recentVideos6.greaterThan=" + SMALLER_RECENT_VIDEOS_6,
            "recentVideos6.greaterThan=" + DEFAULT_RECENT_VIDEOS_6
        );
    }

    private void defaultFrontpageconfigFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFrontpageconfigShouldBeFound(shouldBeFound);
        defaultFrontpageconfigShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFrontpageconfigShouldBeFound(String filter) throws Exception {
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frontpageconfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].topNews1").value(hasItem(DEFAULT_TOP_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].topNews2").value(hasItem(DEFAULT_TOP_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].topNews3").value(hasItem(DEFAULT_TOP_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].topNews4").value(hasItem(DEFAULT_TOP_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].topNews5").value(hasItem(DEFAULT_TOP_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].latestNews1").value(hasItem(DEFAULT_LATEST_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].latestNews2").value(hasItem(DEFAULT_LATEST_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].latestNews3").value(hasItem(DEFAULT_LATEST_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].latestNews4").value(hasItem(DEFAULT_LATEST_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].latestNews5").value(hasItem(DEFAULT_LATEST_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].breakingNews1").value(hasItem(DEFAULT_BREAKING_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts1").value(hasItem(DEFAULT_RECENT_POSTS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts2").value(hasItem(DEFAULT_RECENT_POSTS_2.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts3").value(hasItem(DEFAULT_RECENT_POSTS_3.intValue())))
            .andExpect(jsonPath("$.[*].recentPosts4").value(hasItem(DEFAULT_RECENT_POSTS_4.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles1").value(hasItem(DEFAULT_FEATURED_ARTICLES_1.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles2").value(hasItem(DEFAULT_FEATURED_ARTICLES_2.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles3").value(hasItem(DEFAULT_FEATURED_ARTICLES_3.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles4").value(hasItem(DEFAULT_FEATURED_ARTICLES_4.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles5").value(hasItem(DEFAULT_FEATURED_ARTICLES_5.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles6").value(hasItem(DEFAULT_FEATURED_ARTICLES_6.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles7").value(hasItem(DEFAULT_FEATURED_ARTICLES_7.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles8").value(hasItem(DEFAULT_FEATURED_ARTICLES_8.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles9").value(hasItem(DEFAULT_FEATURED_ARTICLES_9.intValue())))
            .andExpect(jsonPath("$.[*].featuredArticles10").value(hasItem(DEFAULT_FEATURED_ARTICLES_10.intValue())))
            .andExpect(jsonPath("$.[*].popularNews1").value(hasItem(DEFAULT_POPULAR_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].popularNews2").value(hasItem(DEFAULT_POPULAR_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].popularNews3").value(hasItem(DEFAULT_POPULAR_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].popularNews4").value(hasItem(DEFAULT_POPULAR_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].popularNews5").value(hasItem(DEFAULT_POPULAR_NEWS_5.intValue())))
            .andExpect(jsonPath("$.[*].popularNews6").value(hasItem(DEFAULT_POPULAR_NEWS_6.intValue())))
            .andExpect(jsonPath("$.[*].popularNews7").value(hasItem(DEFAULT_POPULAR_NEWS_7.intValue())))
            .andExpect(jsonPath("$.[*].popularNews8").value(hasItem(DEFAULT_POPULAR_NEWS_8.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews1").value(hasItem(DEFAULT_WEEKLY_NEWS_1.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews2").value(hasItem(DEFAULT_WEEKLY_NEWS_2.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews3").value(hasItem(DEFAULT_WEEKLY_NEWS_3.intValue())))
            .andExpect(jsonPath("$.[*].weeklyNews4").value(hasItem(DEFAULT_WEEKLY_NEWS_4.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds1").value(hasItem(DEFAULT_NEWS_FEEDS_1.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds2").value(hasItem(DEFAULT_NEWS_FEEDS_2.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds3").value(hasItem(DEFAULT_NEWS_FEEDS_3.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds4").value(hasItem(DEFAULT_NEWS_FEEDS_4.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds5").value(hasItem(DEFAULT_NEWS_FEEDS_5.intValue())))
            .andExpect(jsonPath("$.[*].newsFeeds6").value(hasItem(DEFAULT_NEWS_FEEDS_6.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks1").value(hasItem(DEFAULT_USEFUL_LINKS_1.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks2").value(hasItem(DEFAULT_USEFUL_LINKS_2.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks3").value(hasItem(DEFAULT_USEFUL_LINKS_3.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks4").value(hasItem(DEFAULT_USEFUL_LINKS_4.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks5").value(hasItem(DEFAULT_USEFUL_LINKS_5.intValue())))
            .andExpect(jsonPath("$.[*].usefulLinks6").value(hasItem(DEFAULT_USEFUL_LINKS_6.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos1").value(hasItem(DEFAULT_RECENT_VIDEOS_1.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos2").value(hasItem(DEFAULT_RECENT_VIDEOS_2.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos3").value(hasItem(DEFAULT_RECENT_VIDEOS_3.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos4").value(hasItem(DEFAULT_RECENT_VIDEOS_4.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos5").value(hasItem(DEFAULT_RECENT_VIDEOS_5.intValue())))
            .andExpect(jsonPath("$.[*].recentVideos6").value(hasItem(DEFAULT_RECENT_VIDEOS_6.intValue())));

        // Check, that the count call also returns 1
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFrontpageconfigShouldNotBeFound(String filter) throws Exception {
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFrontpageconfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFrontpageconfig() throws Exception {
        // Get the frontpageconfig
        restFrontpageconfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFrontpageconfig() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frontpageconfig
        Frontpageconfig updatedFrontpageconfig = frontpageconfigRepository.findById(frontpageconfig.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFrontpageconfig are not directly saved in db
        em.detach(updatedFrontpageconfig);
        updatedFrontpageconfig
            .creationDate(UPDATED_CREATION_DATE)
            .topNews1(UPDATED_TOP_NEWS_1)
            .topNews2(UPDATED_TOP_NEWS_2)
            .topNews3(UPDATED_TOP_NEWS_3)
            .topNews4(UPDATED_TOP_NEWS_4)
            .topNews5(UPDATED_TOP_NEWS_5)
            .latestNews1(UPDATED_LATEST_NEWS_1)
            .latestNews2(UPDATED_LATEST_NEWS_2)
            .latestNews3(UPDATED_LATEST_NEWS_3)
            .latestNews4(UPDATED_LATEST_NEWS_4)
            .latestNews5(UPDATED_LATEST_NEWS_5)
            .breakingNews1(UPDATED_BREAKING_NEWS_1)
            .recentPosts1(UPDATED_RECENT_POSTS_1)
            .recentPosts2(UPDATED_RECENT_POSTS_2)
            .recentPosts3(UPDATED_RECENT_POSTS_3)
            .recentPosts4(UPDATED_RECENT_POSTS_4)
            .featuredArticles1(UPDATED_FEATURED_ARTICLES_1)
            .featuredArticles2(UPDATED_FEATURED_ARTICLES_2)
            .featuredArticles3(UPDATED_FEATURED_ARTICLES_3)
            .featuredArticles4(UPDATED_FEATURED_ARTICLES_4)
            .featuredArticles5(UPDATED_FEATURED_ARTICLES_5)
            .featuredArticles6(UPDATED_FEATURED_ARTICLES_6)
            .featuredArticles7(UPDATED_FEATURED_ARTICLES_7)
            .featuredArticles8(UPDATED_FEATURED_ARTICLES_8)
            .featuredArticles9(UPDATED_FEATURED_ARTICLES_9)
            .featuredArticles10(UPDATED_FEATURED_ARTICLES_10)
            .popularNews1(UPDATED_POPULAR_NEWS_1)
            .popularNews2(UPDATED_POPULAR_NEWS_2)
            .popularNews3(UPDATED_POPULAR_NEWS_3)
            .popularNews4(UPDATED_POPULAR_NEWS_4)
            .popularNews5(UPDATED_POPULAR_NEWS_5)
            .popularNews6(UPDATED_POPULAR_NEWS_6)
            .popularNews7(UPDATED_POPULAR_NEWS_7)
            .popularNews8(UPDATED_POPULAR_NEWS_8)
            .weeklyNews1(UPDATED_WEEKLY_NEWS_1)
            .weeklyNews2(UPDATED_WEEKLY_NEWS_2)
            .weeklyNews3(UPDATED_WEEKLY_NEWS_3)
            .weeklyNews4(UPDATED_WEEKLY_NEWS_4)
            .newsFeeds1(UPDATED_NEWS_FEEDS_1)
            .newsFeeds2(UPDATED_NEWS_FEEDS_2)
            .newsFeeds3(UPDATED_NEWS_FEEDS_3)
            .newsFeeds4(UPDATED_NEWS_FEEDS_4)
            .newsFeeds5(UPDATED_NEWS_FEEDS_5)
            .newsFeeds6(UPDATED_NEWS_FEEDS_6)
            .usefulLinks1(UPDATED_USEFUL_LINKS_1)
            .usefulLinks2(UPDATED_USEFUL_LINKS_2)
            .usefulLinks3(UPDATED_USEFUL_LINKS_3)
            .usefulLinks4(UPDATED_USEFUL_LINKS_4)
            .usefulLinks5(UPDATED_USEFUL_LINKS_5)
            .usefulLinks6(UPDATED_USEFUL_LINKS_6)
            .recentVideos1(UPDATED_RECENT_VIDEOS_1)
            .recentVideos2(UPDATED_RECENT_VIDEOS_2)
            .recentVideos3(UPDATED_RECENT_VIDEOS_3)
            .recentVideos4(UPDATED_RECENT_VIDEOS_4)
            .recentVideos5(UPDATED_RECENT_VIDEOS_5)
            .recentVideos6(UPDATED_RECENT_VIDEOS_6);
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(updatedFrontpageconfig);

        restFrontpageconfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frontpageconfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frontpageconfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFrontpageconfigToMatchAllProperties(updatedFrontpageconfig);
    }

    @Test
    @Transactional
    void putNonExistingFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frontpageconfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frontpageconfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frontpageconfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frontpageconfigDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrontpageconfigWithPatch() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frontpageconfig using partial update
        Frontpageconfig partialUpdatedFrontpageconfig = new Frontpageconfig();
        partialUpdatedFrontpageconfig.setId(frontpageconfig.getId());

        partialUpdatedFrontpageconfig
            .topNews2(UPDATED_TOP_NEWS_2)
            .latestNews5(UPDATED_LATEST_NEWS_5)
            .recentPosts4(UPDATED_RECENT_POSTS_4)
            .featuredArticles1(UPDATED_FEATURED_ARTICLES_1)
            .featuredArticles2(UPDATED_FEATURED_ARTICLES_2)
            .featuredArticles4(UPDATED_FEATURED_ARTICLES_4)
            .featuredArticles7(UPDATED_FEATURED_ARTICLES_7)
            .featuredArticles8(UPDATED_FEATURED_ARTICLES_8)
            .featuredArticles9(UPDATED_FEATURED_ARTICLES_9)
            .featuredArticles10(UPDATED_FEATURED_ARTICLES_10)
            .popularNews1(UPDATED_POPULAR_NEWS_1)
            .popularNews3(UPDATED_POPULAR_NEWS_3)
            .popularNews4(UPDATED_POPULAR_NEWS_4)
            .popularNews5(UPDATED_POPULAR_NEWS_5)
            .weeklyNews1(UPDATED_WEEKLY_NEWS_1)
            .weeklyNews3(UPDATED_WEEKLY_NEWS_3)
            .newsFeeds2(UPDATED_NEWS_FEEDS_2)
            .newsFeeds4(UPDATED_NEWS_FEEDS_4)
            .newsFeeds5(UPDATED_NEWS_FEEDS_5)
            .newsFeeds6(UPDATED_NEWS_FEEDS_6)
            .usefulLinks3(UPDATED_USEFUL_LINKS_3)
            .usefulLinks6(UPDATED_USEFUL_LINKS_6)
            .recentVideos1(UPDATED_RECENT_VIDEOS_1);

        restFrontpageconfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrontpageconfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFrontpageconfig))
            )
            .andExpect(status().isOk());

        // Validate the Frontpageconfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFrontpageconfigUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFrontpageconfig, frontpageconfig),
            getPersistedFrontpageconfig(frontpageconfig)
        );
    }

    @Test
    @Transactional
    void fullUpdateFrontpageconfigWithPatch() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frontpageconfig using partial update
        Frontpageconfig partialUpdatedFrontpageconfig = new Frontpageconfig();
        partialUpdatedFrontpageconfig.setId(frontpageconfig.getId());

        partialUpdatedFrontpageconfig
            .creationDate(UPDATED_CREATION_DATE)
            .topNews1(UPDATED_TOP_NEWS_1)
            .topNews2(UPDATED_TOP_NEWS_2)
            .topNews3(UPDATED_TOP_NEWS_3)
            .topNews4(UPDATED_TOP_NEWS_4)
            .topNews5(UPDATED_TOP_NEWS_5)
            .latestNews1(UPDATED_LATEST_NEWS_1)
            .latestNews2(UPDATED_LATEST_NEWS_2)
            .latestNews3(UPDATED_LATEST_NEWS_3)
            .latestNews4(UPDATED_LATEST_NEWS_4)
            .latestNews5(UPDATED_LATEST_NEWS_5)
            .breakingNews1(UPDATED_BREAKING_NEWS_1)
            .recentPosts1(UPDATED_RECENT_POSTS_1)
            .recentPosts2(UPDATED_RECENT_POSTS_2)
            .recentPosts3(UPDATED_RECENT_POSTS_3)
            .recentPosts4(UPDATED_RECENT_POSTS_4)
            .featuredArticles1(UPDATED_FEATURED_ARTICLES_1)
            .featuredArticles2(UPDATED_FEATURED_ARTICLES_2)
            .featuredArticles3(UPDATED_FEATURED_ARTICLES_3)
            .featuredArticles4(UPDATED_FEATURED_ARTICLES_4)
            .featuredArticles5(UPDATED_FEATURED_ARTICLES_5)
            .featuredArticles6(UPDATED_FEATURED_ARTICLES_6)
            .featuredArticles7(UPDATED_FEATURED_ARTICLES_7)
            .featuredArticles8(UPDATED_FEATURED_ARTICLES_8)
            .featuredArticles9(UPDATED_FEATURED_ARTICLES_9)
            .featuredArticles10(UPDATED_FEATURED_ARTICLES_10)
            .popularNews1(UPDATED_POPULAR_NEWS_1)
            .popularNews2(UPDATED_POPULAR_NEWS_2)
            .popularNews3(UPDATED_POPULAR_NEWS_3)
            .popularNews4(UPDATED_POPULAR_NEWS_4)
            .popularNews5(UPDATED_POPULAR_NEWS_5)
            .popularNews6(UPDATED_POPULAR_NEWS_6)
            .popularNews7(UPDATED_POPULAR_NEWS_7)
            .popularNews8(UPDATED_POPULAR_NEWS_8)
            .weeklyNews1(UPDATED_WEEKLY_NEWS_1)
            .weeklyNews2(UPDATED_WEEKLY_NEWS_2)
            .weeklyNews3(UPDATED_WEEKLY_NEWS_3)
            .weeklyNews4(UPDATED_WEEKLY_NEWS_4)
            .newsFeeds1(UPDATED_NEWS_FEEDS_1)
            .newsFeeds2(UPDATED_NEWS_FEEDS_2)
            .newsFeeds3(UPDATED_NEWS_FEEDS_3)
            .newsFeeds4(UPDATED_NEWS_FEEDS_4)
            .newsFeeds5(UPDATED_NEWS_FEEDS_5)
            .newsFeeds6(UPDATED_NEWS_FEEDS_6)
            .usefulLinks1(UPDATED_USEFUL_LINKS_1)
            .usefulLinks2(UPDATED_USEFUL_LINKS_2)
            .usefulLinks3(UPDATED_USEFUL_LINKS_3)
            .usefulLinks4(UPDATED_USEFUL_LINKS_4)
            .usefulLinks5(UPDATED_USEFUL_LINKS_5)
            .usefulLinks6(UPDATED_USEFUL_LINKS_6)
            .recentVideos1(UPDATED_RECENT_VIDEOS_1)
            .recentVideos2(UPDATED_RECENT_VIDEOS_2)
            .recentVideos3(UPDATED_RECENT_VIDEOS_3)
            .recentVideos4(UPDATED_RECENT_VIDEOS_4)
            .recentVideos5(UPDATED_RECENT_VIDEOS_5)
            .recentVideos6(UPDATED_RECENT_VIDEOS_6);

        restFrontpageconfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrontpageconfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFrontpageconfig))
            )
            .andExpect(status().isOk());

        // Validate the Frontpageconfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFrontpageconfigUpdatableFieldsEquals(
            partialUpdatedFrontpageconfig,
            getPersistedFrontpageconfig(partialUpdatedFrontpageconfig)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frontpageconfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(frontpageconfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(frontpageconfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrontpageconfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frontpageconfig.setId(longCount.incrementAndGet());

        // Create the Frontpageconfig
        FrontpageconfigDTO frontpageconfigDTO = frontpageconfigMapper.toDto(frontpageconfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrontpageconfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(frontpageconfigDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frontpageconfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrontpageconfig() throws Exception {
        // Initialize the database
        insertedFrontpageconfig = frontpageconfigRepository.saveAndFlush(frontpageconfig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the frontpageconfig
        restFrontpageconfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, frontpageconfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return frontpageconfigRepository.count();
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

    protected Frontpageconfig getPersistedFrontpageconfig(Frontpageconfig frontpageconfig) {
        return frontpageconfigRepository.findById(frontpageconfig.getId()).orElseThrow();
    }

    protected void assertPersistedFrontpageconfigToMatchAllProperties(Frontpageconfig expectedFrontpageconfig) {
        assertFrontpageconfigAllPropertiesEquals(expectedFrontpageconfig, getPersistedFrontpageconfig(expectedFrontpageconfig));
    }

    protected void assertPersistedFrontpageconfigToMatchUpdatableProperties(Frontpageconfig expectedFrontpageconfig) {
        assertFrontpageconfigAllUpdatablePropertiesEquals(expectedFrontpageconfig, getPersistedFrontpageconfig(expectedFrontpageconfig));
    }
}
