package com.opencode.test.web.rest;

import static com.opencode.test.domain.PostAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Blog;
import com.opencode.test.domain.Post;
import com.opencode.test.domain.Tag;
import com.opencode.test.domain.Topic;
import com.opencode.test.repository.PostRepository;
import com.opencode.test.service.PostService;
import com.opencode.test.service.dto.PostDTO;
import com.opencode.test.service.mapper.PostMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
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
 * Integration tests for the {@link PostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PUBLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_HEADLINE = "BBBBBBBBBB";

    private static final String DEFAULT_LEADTEXT = "AAAAAAAAAA";
    private static final String UPDATED_LEADTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_BODYTEXT = "AAAAAAAAAA";
    private static final String UPDATED_BODYTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PostRepository postRepository;

    @Mock
    private PostRepository postRepositoryMock;

    @Autowired
    private PostMapper postMapper;

    @Mock
    private PostService postServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    private Post insertedPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .creationDate(DEFAULT_CREATION_DATE)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
            .headline(DEFAULT_HEADLINE)
            .leadtext(DEFAULT_LEADTEXT)
            .bodytext(DEFAULT_BODYTEXT)
            .quote(DEFAULT_QUOTE)
            .conclusion(DEFAULT_CONCLUSION)
            .linkText(DEFAULT_LINK_TEXT)
            .linkURL(DEFAULT_LINK_URL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        post.setAppuser(appuser);
        // Add required entity
        Blog blog;
        if (TestUtil.findAll(em, Blog.class).isEmpty()) {
            blog = BlogResourceIT.createEntity(em);
            em.persist(blog);
            em.flush();
        } else {
            blog = TestUtil.findAll(em, Blog.class).get(0);
        }
        post.setBlog(blog);
        return post;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post updatedPost = new Post()
            .creationDate(UPDATED_CREATION_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .headline(UPDATED_HEADLINE)
            .leadtext(UPDATED_LEADTEXT)
            .bodytext(UPDATED_BODYTEXT)
            .quote(UPDATED_QUOTE)
            .conclusion(UPDATED_CONCLUSION)
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        updatedPost.setAppuser(appuser);
        // Add required entity
        Blog blog;
        if (TestUtil.findAll(em, Blog.class).isEmpty()) {
            blog = BlogResourceIT.createUpdatedEntity(em);
            em.persist(blog);
            em.flush();
        } else {
            blog = TestUtil.findAll(em, Blog.class).get(0);
        }
        updatedPost.setBlog(blog);
        return updatedPost;
    }

    @BeforeEach
    void initTest() {
        post = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedPost != null) {
            postRepository.delete(insertedPost);
            insertedPost = null;
        }
    }

    @Test
    @Transactional
    void createPost() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        var returnedPostDTO = om.readValue(
            restPostMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PostDTO.class
        );

        // Validate the Post in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPost = postMapper.toEntity(returnedPostDTO);
        assertPostUpdatableFieldsEquals(returnedPost, getPersistedPost(returnedPost));

        insertedPost = returnedPost;
    }

    @Test
    @Transactional
    void createPostWithExistingId() throws Exception {
        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        post.setCreationDate(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHeadlineIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        post.setHeadline(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBodytextIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        post.setBodytext(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPosts() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE)))
            .andExpect(jsonPath("$.[*].leadtext").value(hasItem(DEFAULT_LEADTEXT)))
            .andExpect(jsonPath("$.[*].bodytext").value(hasItem(DEFAULT_BODYTEXT)))
            .andExpect(jsonPath("$.[*].quote").value(hasItem(DEFAULT_QUOTE)))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPost() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc
            .perform(get(ENTITY_API_URL_ID, post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.headline").value(DEFAULT_HEADLINE))
            .andExpect(jsonPath("$.leadtext").value(DEFAULT_LEADTEXT))
            .andExpect(jsonPath("$.bodytext").value(DEFAULT_BODYTEXT))
            .andExpect(jsonPath("$.quote").value(DEFAULT_QUOTE))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION))
            .andExpect(jsonPath("$.linkText").value(DEFAULT_LINK_TEXT))
            .andExpect(jsonPath("$.linkURL").value(DEFAULT_LINK_URL))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        Long id = post.getId();

        defaultPostFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPostFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPostFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPostsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where creationDate equals to
        defaultPostFiltering("creationDate.equals=" + DEFAULT_CREATION_DATE, "creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllPostsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where creationDate in
        defaultPostFiltering(
            "creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE,
            "creationDate.in=" + UPDATED_CREATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllPostsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where creationDate is not null
        defaultPostFiltering("creationDate.specified=true", "creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByPublicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where publicationDate equals to
        defaultPostFiltering("publicationDate.equals=" + DEFAULT_PUBLICATION_DATE, "publicationDate.equals=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    void getAllPostsByPublicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where publicationDate in
        defaultPostFiltering(
            "publicationDate.in=" + DEFAULT_PUBLICATION_DATE + "," + UPDATED_PUBLICATION_DATE,
            "publicationDate.in=" + UPDATED_PUBLICATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllPostsByPublicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where publicationDate is not null
        defaultPostFiltering("publicationDate.specified=true", "publicationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where headline equals to
        defaultPostFiltering("headline.equals=" + DEFAULT_HEADLINE, "headline.equals=" + UPDATED_HEADLINE);
    }

    @Test
    @Transactional
    void getAllPostsByHeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where headline in
        defaultPostFiltering("headline.in=" + DEFAULT_HEADLINE + "," + UPDATED_HEADLINE, "headline.in=" + UPDATED_HEADLINE);
    }

    @Test
    @Transactional
    void getAllPostsByHeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where headline is not null
        defaultPostFiltering("headline.specified=true", "headline.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHeadlineContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where headline contains
        defaultPostFiltering("headline.contains=" + DEFAULT_HEADLINE, "headline.contains=" + UPDATED_HEADLINE);
    }

    @Test
    @Transactional
    void getAllPostsByHeadlineNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where headline does not contain
        defaultPostFiltering("headline.doesNotContain=" + UPDATED_HEADLINE, "headline.doesNotContain=" + DEFAULT_HEADLINE);
    }

    @Test
    @Transactional
    void getAllPostsByLeadtextIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where leadtext equals to
        defaultPostFiltering("leadtext.equals=" + DEFAULT_LEADTEXT, "leadtext.equals=" + UPDATED_LEADTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLeadtextIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where leadtext in
        defaultPostFiltering("leadtext.in=" + DEFAULT_LEADTEXT + "," + UPDATED_LEADTEXT, "leadtext.in=" + UPDATED_LEADTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLeadtextIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where leadtext is not null
        defaultPostFiltering("leadtext.specified=true", "leadtext.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByLeadtextContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where leadtext contains
        defaultPostFiltering("leadtext.contains=" + DEFAULT_LEADTEXT, "leadtext.contains=" + UPDATED_LEADTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLeadtextNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where leadtext does not contain
        defaultPostFiltering("leadtext.doesNotContain=" + UPDATED_LEADTEXT, "leadtext.doesNotContain=" + DEFAULT_LEADTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByBodytextIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where bodytext equals to
        defaultPostFiltering("bodytext.equals=" + DEFAULT_BODYTEXT, "bodytext.equals=" + UPDATED_BODYTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByBodytextIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where bodytext in
        defaultPostFiltering("bodytext.in=" + DEFAULT_BODYTEXT + "," + UPDATED_BODYTEXT, "bodytext.in=" + UPDATED_BODYTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByBodytextIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where bodytext is not null
        defaultPostFiltering("bodytext.specified=true", "bodytext.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByBodytextContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where bodytext contains
        defaultPostFiltering("bodytext.contains=" + DEFAULT_BODYTEXT, "bodytext.contains=" + UPDATED_BODYTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByBodytextNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where bodytext does not contain
        defaultPostFiltering("bodytext.doesNotContain=" + UPDATED_BODYTEXT, "bodytext.doesNotContain=" + DEFAULT_BODYTEXT);
    }

    @Test
    @Transactional
    void getAllPostsByQuoteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where quote equals to
        defaultPostFiltering("quote.equals=" + DEFAULT_QUOTE, "quote.equals=" + UPDATED_QUOTE);
    }

    @Test
    @Transactional
    void getAllPostsByQuoteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where quote in
        defaultPostFiltering("quote.in=" + DEFAULT_QUOTE + "," + UPDATED_QUOTE, "quote.in=" + UPDATED_QUOTE);
    }

    @Test
    @Transactional
    void getAllPostsByQuoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where quote is not null
        defaultPostFiltering("quote.specified=true", "quote.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByQuoteContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where quote contains
        defaultPostFiltering("quote.contains=" + DEFAULT_QUOTE, "quote.contains=" + UPDATED_QUOTE);
    }

    @Test
    @Transactional
    void getAllPostsByQuoteNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where quote does not contain
        defaultPostFiltering("quote.doesNotContain=" + UPDATED_QUOTE, "quote.doesNotContain=" + DEFAULT_QUOTE);
    }

    @Test
    @Transactional
    void getAllPostsByConclusionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where conclusion equals to
        defaultPostFiltering("conclusion.equals=" + DEFAULT_CONCLUSION, "conclusion.equals=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllPostsByConclusionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where conclusion in
        defaultPostFiltering("conclusion.in=" + DEFAULT_CONCLUSION + "," + UPDATED_CONCLUSION, "conclusion.in=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllPostsByConclusionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where conclusion is not null
        defaultPostFiltering("conclusion.specified=true", "conclusion.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByConclusionContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where conclusion contains
        defaultPostFiltering("conclusion.contains=" + DEFAULT_CONCLUSION, "conclusion.contains=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllPostsByConclusionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where conclusion does not contain
        defaultPostFiltering("conclusion.doesNotContain=" + UPDATED_CONCLUSION, "conclusion.doesNotContain=" + DEFAULT_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllPostsByLinkTextIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkText equals to
        defaultPostFiltering("linkText.equals=" + DEFAULT_LINK_TEXT, "linkText.equals=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLinkTextIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkText in
        defaultPostFiltering("linkText.in=" + DEFAULT_LINK_TEXT + "," + UPDATED_LINK_TEXT, "linkText.in=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLinkTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkText is not null
        defaultPostFiltering("linkText.specified=true", "linkText.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByLinkTextContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkText contains
        defaultPostFiltering("linkText.contains=" + DEFAULT_LINK_TEXT, "linkText.contains=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLinkTextNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkText does not contain
        defaultPostFiltering("linkText.doesNotContain=" + UPDATED_LINK_TEXT, "linkText.doesNotContain=" + DEFAULT_LINK_TEXT);
    }

    @Test
    @Transactional
    void getAllPostsByLinkURLIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkURL equals to
        defaultPostFiltering("linkURL.equals=" + DEFAULT_LINK_URL, "linkURL.equals=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllPostsByLinkURLIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkURL in
        defaultPostFiltering("linkURL.in=" + DEFAULT_LINK_URL + "," + UPDATED_LINK_URL, "linkURL.in=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllPostsByLinkURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkURL is not null
        defaultPostFiltering("linkURL.specified=true", "linkURL.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByLinkURLContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkURL contains
        defaultPostFiltering("linkURL.contains=" + DEFAULT_LINK_URL, "linkURL.contains=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    void getAllPostsByLinkURLNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        // Get all the postList where linkURL does not contain
        defaultPostFiltering("linkURL.doesNotContain=" + UPDATED_LINK_URL, "linkURL.doesNotContain=" + DEFAULT_LINK_URL);
    }

    @Test
    @Transactional
    void getAllPostsByAppuserIsEqualToSomething() throws Exception {
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            appuser = AppuserResourceIT.createEntity(em);
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        em.persist(appuser);
        em.flush();
        post.setAppuser(appuser);
        postRepository.saveAndFlush(post);
        Long appuserId = appuser.getId();
        // Get all the postList where appuser equals to appuserId
        defaultPostShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the postList where appuser equals to (appuserId + 1)
        defaultPostShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByBlogIsEqualToSomething() throws Exception {
        Blog blog;
        if (TestUtil.findAll(em, Blog.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            blog = BlogResourceIT.createEntity(em);
        } else {
            blog = TestUtil.findAll(em, Blog.class).get(0);
        }
        em.persist(blog);
        em.flush();
        post.setBlog(blog);
        postRepository.saveAndFlush(post);
        Long blogId = blog.getId();
        // Get all the postList where blog equals to blogId
        defaultPostShouldBeFound("blogId.equals=" + blogId);

        // Get all the postList where blog equals to (blogId + 1)
        defaultPostShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByTagIsEqualToSomething() throws Exception {
        Tag tag;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            tag = TagResourceIT.createEntity();
        } else {
            tag = TestUtil.findAll(em, Tag.class).get(0);
        }
        em.persist(tag);
        em.flush();
        post.addTag(tag);
        postRepository.saveAndFlush(post);
        Long tagId = tag.getId();
        // Get all the postList where tag equals to tagId
        defaultPostShouldBeFound("tagId.equals=" + tagId);

        // Get all the postList where tag equals to (tagId + 1)
        defaultPostShouldNotBeFound("tagId.equals=" + (tagId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByTopicIsEqualToSomething() throws Exception {
        Topic topic;
        if (TestUtil.findAll(em, Topic.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            topic = TopicResourceIT.createEntity();
        } else {
            topic = TestUtil.findAll(em, Topic.class).get(0);
        }
        em.persist(topic);
        em.flush();
        post.addTopic(topic);
        postRepository.saveAndFlush(post);
        Long topicId = topic.getId();
        // Get all the postList where topic equals to topicId
        defaultPostShouldBeFound("topicId.equals=" + topicId);

        // Get all the postList where topic equals to (topicId + 1)
        defaultPostShouldNotBeFound("topicId.equals=" + (topicId + 1));
    }

    private void defaultPostFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPostShouldBeFound(shouldBeFound);
        defaultPostShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostShouldBeFound(String filter) throws Exception {
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE)))
            .andExpect(jsonPath("$.[*].leadtext").value(hasItem(DEFAULT_LEADTEXT)))
            .andExpect(jsonPath("$.[*].bodytext").value(hasItem(DEFAULT_BODYTEXT)))
            .andExpect(jsonPath("$.[*].quote").value(hasItem(DEFAULT_QUOTE)))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostShouldNotBeFound(String filter) throws Exception {
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPost() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .creationDate(UPDATED_CREATION_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .headline(UPDATED_HEADLINE)
            .leadtext(UPDATED_LEADTEXT)
            .bodytext(UPDATED_BODYTEXT)
            .quote(UPDATED_QUOTE)
            .conclusion(UPDATED_CONCLUSION)
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc
            .perform(put(ENTITY_API_URL_ID, postDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPostToMatchAllProperties(updatedPost);
    }

    @Test
    @Transactional
    void putNonExistingPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(put(ENTITY_API_URL_ID, postDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostWithPatch() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .creationDate(UPDATED_CREATION_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .leadtext(UPDATED_LEADTEXT)
            .quote(UPDATED_QUOTE)
            .conclusion(UPDATED_CONCLUSION)
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPost, post), getPersistedPost(post));
    }

    @Test
    @Transactional
    void fullUpdatePostWithPatch() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .creationDate(UPDATED_CREATION_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .headline(UPDATED_HEADLINE)
            .leadtext(UPDATED_LEADTEXT)
            .bodytext(UPDATED_BODYTEXT)
            .quote(UPDATED_QUOTE)
            .conclusion(UPDATED_CONCLUSION)
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostUpdatableFieldsEquals(partialUpdatedPost, getPersistedPost(partialUpdatedPost));
    }

    @Test
    @Transactional
    void patchNonExistingPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setId(longCount.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePost() throws Exception {
        // Initialize the database
        insertedPost = postRepository.saveAndFlush(post);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the post
        restPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, post.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return postRepository.count();
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

    protected Post getPersistedPost(Post post) {
        return postRepository.findById(post.getId()).orElseThrow();
    }

    protected void assertPersistedPostToMatchAllProperties(Post expectedPost) {
        assertPostAllPropertiesEquals(expectedPost, getPersistedPost(expectedPost));
    }

    protected void assertPersistedPostToMatchUpdatableProperties(Post expectedPost) {
        assertPostAllUpdatablePropertiesEquals(expectedPost, getPersistedPost(expectedPost));
    }
}
