package com.opencode.test.web.rest;

import static com.opencode.test.domain.ConfigVariablesAsserts.*;
import static com.opencode.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencode.test.IntegrationTest;
import com.opencode.test.domain.ConfigVariables;
import com.opencode.test.repository.ConfigVariablesRepository;
import com.opencode.test.service.dto.ConfigVariablesDTO;
import com.opencode.test.service.mapper.ConfigVariablesMapper;
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
 * Integration tests for the {@link ConfigVariablesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigVariablesResourceIT {

    private static final Long DEFAULT_CONFIG_VAR_LONG_1 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_1 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_1 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_2 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_2 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_2 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_3 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_3 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_3 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_4 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_4 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_4 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_5 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_5 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_5 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_6 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_6 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_6 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_7 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_7 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_7 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_8 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_8 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_8 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_9 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_9 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_9 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_10 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_10 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_10 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_11 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_11 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_11 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_12 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_12 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_12 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_13 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_13 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_13 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_14 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_14 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_14 = 1L - 1L;

    private static final Long DEFAULT_CONFIG_VAR_LONG_15 = 1L;
    private static final Long UPDATED_CONFIG_VAR_LONG_15 = 2L;
    private static final Long SMALLER_CONFIG_VAR_LONG_15 = 1L - 1L;

    private static final Boolean DEFAULT_CONFIG_VAR_BOOLEAN_16 = false;
    private static final Boolean UPDATED_CONFIG_VAR_BOOLEAN_16 = true;

    private static final Boolean DEFAULT_CONFIG_VAR_BOOLEAN_17 = false;
    private static final Boolean UPDATED_CONFIG_VAR_BOOLEAN_17 = true;

    private static final Boolean DEFAULT_CONFIG_VAR_BOOLEAN_18 = false;
    private static final Boolean UPDATED_CONFIG_VAR_BOOLEAN_18 = true;

    private static final String DEFAULT_CONFIG_VAR_STRING_19 = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VAR_STRING_19 = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_VAR_STRING_20 = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VAR_STRING_20 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/config-variables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConfigVariablesRepository configVariablesRepository;

    @Autowired
    private ConfigVariablesMapper configVariablesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigVariablesMockMvc;

    private ConfigVariables configVariables;

    private ConfigVariables insertedConfigVariables;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigVariables createEntity() {
        return new ConfigVariables()
            .configVarLong1(DEFAULT_CONFIG_VAR_LONG_1)
            .configVarLong2(DEFAULT_CONFIG_VAR_LONG_2)
            .configVarLong3(DEFAULT_CONFIG_VAR_LONG_3)
            .configVarLong4(DEFAULT_CONFIG_VAR_LONG_4)
            .configVarLong5(DEFAULT_CONFIG_VAR_LONG_5)
            .configVarLong6(DEFAULT_CONFIG_VAR_LONG_6)
            .configVarLong7(DEFAULT_CONFIG_VAR_LONG_7)
            .configVarLong8(DEFAULT_CONFIG_VAR_LONG_8)
            .configVarLong9(DEFAULT_CONFIG_VAR_LONG_9)
            .configVarLong10(DEFAULT_CONFIG_VAR_LONG_10)
            .configVarLong11(DEFAULT_CONFIG_VAR_LONG_11)
            .configVarLong12(DEFAULT_CONFIG_VAR_LONG_12)
            .configVarLong13(DEFAULT_CONFIG_VAR_LONG_13)
            .configVarLong14(DEFAULT_CONFIG_VAR_LONG_14)
            .configVarLong15(DEFAULT_CONFIG_VAR_LONG_15)
            .configVarBoolean16(DEFAULT_CONFIG_VAR_BOOLEAN_16)
            .configVarBoolean17(DEFAULT_CONFIG_VAR_BOOLEAN_17)
            .configVarBoolean18(DEFAULT_CONFIG_VAR_BOOLEAN_18)
            .configVarString19(DEFAULT_CONFIG_VAR_STRING_19)
            .configVarString20(DEFAULT_CONFIG_VAR_STRING_20);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigVariables createUpdatedEntity() {
        return new ConfigVariables()
            .configVarLong1(UPDATED_CONFIG_VAR_LONG_1)
            .configVarLong2(UPDATED_CONFIG_VAR_LONG_2)
            .configVarLong3(UPDATED_CONFIG_VAR_LONG_3)
            .configVarLong4(UPDATED_CONFIG_VAR_LONG_4)
            .configVarLong5(UPDATED_CONFIG_VAR_LONG_5)
            .configVarLong6(UPDATED_CONFIG_VAR_LONG_6)
            .configVarLong7(UPDATED_CONFIG_VAR_LONG_7)
            .configVarLong8(UPDATED_CONFIG_VAR_LONG_8)
            .configVarLong9(UPDATED_CONFIG_VAR_LONG_9)
            .configVarLong10(UPDATED_CONFIG_VAR_LONG_10)
            .configVarLong11(UPDATED_CONFIG_VAR_LONG_11)
            .configVarLong12(UPDATED_CONFIG_VAR_LONG_12)
            .configVarLong13(UPDATED_CONFIG_VAR_LONG_13)
            .configVarLong14(UPDATED_CONFIG_VAR_LONG_14)
            .configVarLong15(UPDATED_CONFIG_VAR_LONG_15)
            .configVarBoolean16(UPDATED_CONFIG_VAR_BOOLEAN_16)
            .configVarBoolean17(UPDATED_CONFIG_VAR_BOOLEAN_17)
            .configVarBoolean18(UPDATED_CONFIG_VAR_BOOLEAN_18)
            .configVarString19(UPDATED_CONFIG_VAR_STRING_19)
            .configVarString20(UPDATED_CONFIG_VAR_STRING_20);
    }

    @BeforeEach
    void initTest() {
        configVariables = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedConfigVariables != null) {
            configVariablesRepository.delete(insertedConfigVariables);
            insertedConfigVariables = null;
        }
    }

    @Test
    @Transactional
    void createConfigVariables() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);
        var returnedConfigVariablesDTO = om.readValue(
            restConfigVariablesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configVariablesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConfigVariablesDTO.class
        );

        // Validate the ConfigVariables in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConfigVariables = configVariablesMapper.toEntity(returnedConfigVariablesDTO);
        assertConfigVariablesUpdatableFieldsEquals(returnedConfigVariables, getPersistedConfigVariables(returnedConfigVariables));

        insertedConfigVariables = returnedConfigVariables;
    }

    @Test
    @Transactional
    void createConfigVariablesWithExistingId() throws Exception {
        // Create the ConfigVariables with an existing ID
        configVariables.setId(1L);
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigVariablesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configVariablesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfigVariableses() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configVariables.getId().intValue())))
            .andExpect(jsonPath("$.[*].configVarLong1").value(hasItem(DEFAULT_CONFIG_VAR_LONG_1.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong2").value(hasItem(DEFAULT_CONFIG_VAR_LONG_2.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong3").value(hasItem(DEFAULT_CONFIG_VAR_LONG_3.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong4").value(hasItem(DEFAULT_CONFIG_VAR_LONG_4.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong5").value(hasItem(DEFAULT_CONFIG_VAR_LONG_5.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong6").value(hasItem(DEFAULT_CONFIG_VAR_LONG_6.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong7").value(hasItem(DEFAULT_CONFIG_VAR_LONG_7.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong8").value(hasItem(DEFAULT_CONFIG_VAR_LONG_8.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong9").value(hasItem(DEFAULT_CONFIG_VAR_LONG_9.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong10").value(hasItem(DEFAULT_CONFIG_VAR_LONG_10.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong11").value(hasItem(DEFAULT_CONFIG_VAR_LONG_11.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong12").value(hasItem(DEFAULT_CONFIG_VAR_LONG_12.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong13").value(hasItem(DEFAULT_CONFIG_VAR_LONG_13.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong14").value(hasItem(DEFAULT_CONFIG_VAR_LONG_14.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong15").value(hasItem(DEFAULT_CONFIG_VAR_LONG_15.intValue())))
            .andExpect(jsonPath("$.[*].configVarBoolean16").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_16)))
            .andExpect(jsonPath("$.[*].configVarBoolean17").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_17)))
            .andExpect(jsonPath("$.[*].configVarBoolean18").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_18)))
            .andExpect(jsonPath("$.[*].configVarString19").value(hasItem(DEFAULT_CONFIG_VAR_STRING_19)))
            .andExpect(jsonPath("$.[*].configVarString20").value(hasItem(DEFAULT_CONFIG_VAR_STRING_20)));
    }

    @Test
    @Transactional
    void getConfigVariables() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get the configVariables
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL_ID, configVariables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configVariables.getId().intValue()))
            .andExpect(jsonPath("$.configVarLong1").value(DEFAULT_CONFIG_VAR_LONG_1.intValue()))
            .andExpect(jsonPath("$.configVarLong2").value(DEFAULT_CONFIG_VAR_LONG_2.intValue()))
            .andExpect(jsonPath("$.configVarLong3").value(DEFAULT_CONFIG_VAR_LONG_3.intValue()))
            .andExpect(jsonPath("$.configVarLong4").value(DEFAULT_CONFIG_VAR_LONG_4.intValue()))
            .andExpect(jsonPath("$.configVarLong5").value(DEFAULT_CONFIG_VAR_LONG_5.intValue()))
            .andExpect(jsonPath("$.configVarLong6").value(DEFAULT_CONFIG_VAR_LONG_6.intValue()))
            .andExpect(jsonPath("$.configVarLong7").value(DEFAULT_CONFIG_VAR_LONG_7.intValue()))
            .andExpect(jsonPath("$.configVarLong8").value(DEFAULT_CONFIG_VAR_LONG_8.intValue()))
            .andExpect(jsonPath("$.configVarLong9").value(DEFAULT_CONFIG_VAR_LONG_9.intValue()))
            .andExpect(jsonPath("$.configVarLong10").value(DEFAULT_CONFIG_VAR_LONG_10.intValue()))
            .andExpect(jsonPath("$.configVarLong11").value(DEFAULT_CONFIG_VAR_LONG_11.intValue()))
            .andExpect(jsonPath("$.configVarLong12").value(DEFAULT_CONFIG_VAR_LONG_12.intValue()))
            .andExpect(jsonPath("$.configVarLong13").value(DEFAULT_CONFIG_VAR_LONG_13.intValue()))
            .andExpect(jsonPath("$.configVarLong14").value(DEFAULT_CONFIG_VAR_LONG_14.intValue()))
            .andExpect(jsonPath("$.configVarLong15").value(DEFAULT_CONFIG_VAR_LONG_15.intValue()))
            .andExpect(jsonPath("$.configVarBoolean16").value(DEFAULT_CONFIG_VAR_BOOLEAN_16))
            .andExpect(jsonPath("$.configVarBoolean17").value(DEFAULT_CONFIG_VAR_BOOLEAN_17))
            .andExpect(jsonPath("$.configVarBoolean18").value(DEFAULT_CONFIG_VAR_BOOLEAN_18))
            .andExpect(jsonPath("$.configVarString19").value(DEFAULT_CONFIG_VAR_STRING_19))
            .andExpect(jsonPath("$.configVarString20").value(DEFAULT_CONFIG_VAR_STRING_20));
    }

    @Test
    @Transactional
    void getConfigVariablesesByIdFiltering() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        Long id = configVariables.getId();

        defaultConfigVariablesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultConfigVariablesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultConfigVariablesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 equals to
        defaultConfigVariablesFiltering(
            "configVarLong1.equals=" + DEFAULT_CONFIG_VAR_LONG_1,
            "configVarLong1.equals=" + UPDATED_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 in
        defaultConfigVariablesFiltering(
            "configVarLong1.in=" + DEFAULT_CONFIG_VAR_LONG_1 + "," + UPDATED_CONFIG_VAR_LONG_1,
            "configVarLong1.in=" + UPDATED_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 is not null
        defaultConfigVariablesFiltering("configVarLong1.specified=true", "configVarLong1.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong1.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_1,
            "configVarLong1.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong1.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_1,
            "configVarLong1.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 is less than
        defaultConfigVariablesFiltering(
            "configVarLong1.lessThan=" + UPDATED_CONFIG_VAR_LONG_1,
            "configVarLong1.lessThan=" + DEFAULT_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong1 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong1.greaterThan=" + SMALLER_CONFIG_VAR_LONG_1,
            "configVarLong1.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_1
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 equals to
        defaultConfigVariablesFiltering(
            "configVarLong2.equals=" + DEFAULT_CONFIG_VAR_LONG_2,
            "configVarLong2.equals=" + UPDATED_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 in
        defaultConfigVariablesFiltering(
            "configVarLong2.in=" + DEFAULT_CONFIG_VAR_LONG_2 + "," + UPDATED_CONFIG_VAR_LONG_2,
            "configVarLong2.in=" + UPDATED_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 is not null
        defaultConfigVariablesFiltering("configVarLong2.specified=true", "configVarLong2.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong2.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_2,
            "configVarLong2.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong2.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_2,
            "configVarLong2.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 is less than
        defaultConfigVariablesFiltering(
            "configVarLong2.lessThan=" + UPDATED_CONFIG_VAR_LONG_2,
            "configVarLong2.lessThan=" + DEFAULT_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong2 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong2.greaterThan=" + SMALLER_CONFIG_VAR_LONG_2,
            "configVarLong2.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_2
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 equals to
        defaultConfigVariablesFiltering(
            "configVarLong3.equals=" + DEFAULT_CONFIG_VAR_LONG_3,
            "configVarLong3.equals=" + UPDATED_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 in
        defaultConfigVariablesFiltering(
            "configVarLong3.in=" + DEFAULT_CONFIG_VAR_LONG_3 + "," + UPDATED_CONFIG_VAR_LONG_3,
            "configVarLong3.in=" + UPDATED_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 is not null
        defaultConfigVariablesFiltering("configVarLong3.specified=true", "configVarLong3.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong3.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_3,
            "configVarLong3.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong3.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_3,
            "configVarLong3.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 is less than
        defaultConfigVariablesFiltering(
            "configVarLong3.lessThan=" + UPDATED_CONFIG_VAR_LONG_3,
            "configVarLong3.lessThan=" + DEFAULT_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong3 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong3.greaterThan=" + SMALLER_CONFIG_VAR_LONG_3,
            "configVarLong3.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_3
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 equals to
        defaultConfigVariablesFiltering(
            "configVarLong4.equals=" + DEFAULT_CONFIG_VAR_LONG_4,
            "configVarLong4.equals=" + UPDATED_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 in
        defaultConfigVariablesFiltering(
            "configVarLong4.in=" + DEFAULT_CONFIG_VAR_LONG_4 + "," + UPDATED_CONFIG_VAR_LONG_4,
            "configVarLong4.in=" + UPDATED_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 is not null
        defaultConfigVariablesFiltering("configVarLong4.specified=true", "configVarLong4.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong4.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_4,
            "configVarLong4.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong4.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_4,
            "configVarLong4.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 is less than
        defaultConfigVariablesFiltering(
            "configVarLong4.lessThan=" + UPDATED_CONFIG_VAR_LONG_4,
            "configVarLong4.lessThan=" + DEFAULT_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong4 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong4.greaterThan=" + SMALLER_CONFIG_VAR_LONG_4,
            "configVarLong4.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_4
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 equals to
        defaultConfigVariablesFiltering(
            "configVarLong5.equals=" + DEFAULT_CONFIG_VAR_LONG_5,
            "configVarLong5.equals=" + UPDATED_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 in
        defaultConfigVariablesFiltering(
            "configVarLong5.in=" + DEFAULT_CONFIG_VAR_LONG_5 + "," + UPDATED_CONFIG_VAR_LONG_5,
            "configVarLong5.in=" + UPDATED_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 is not null
        defaultConfigVariablesFiltering("configVarLong5.specified=true", "configVarLong5.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong5.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_5,
            "configVarLong5.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong5.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_5,
            "configVarLong5.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 is less than
        defaultConfigVariablesFiltering(
            "configVarLong5.lessThan=" + UPDATED_CONFIG_VAR_LONG_5,
            "configVarLong5.lessThan=" + DEFAULT_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong5 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong5.greaterThan=" + SMALLER_CONFIG_VAR_LONG_5,
            "configVarLong5.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_5
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 equals to
        defaultConfigVariablesFiltering(
            "configVarLong6.equals=" + DEFAULT_CONFIG_VAR_LONG_6,
            "configVarLong6.equals=" + UPDATED_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 in
        defaultConfigVariablesFiltering(
            "configVarLong6.in=" + DEFAULT_CONFIG_VAR_LONG_6 + "," + UPDATED_CONFIG_VAR_LONG_6,
            "configVarLong6.in=" + UPDATED_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 is not null
        defaultConfigVariablesFiltering("configVarLong6.specified=true", "configVarLong6.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong6.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_6,
            "configVarLong6.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong6.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_6,
            "configVarLong6.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 is less than
        defaultConfigVariablesFiltering(
            "configVarLong6.lessThan=" + UPDATED_CONFIG_VAR_LONG_6,
            "configVarLong6.lessThan=" + DEFAULT_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong6 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong6.greaterThan=" + SMALLER_CONFIG_VAR_LONG_6,
            "configVarLong6.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_6
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 equals to
        defaultConfigVariablesFiltering(
            "configVarLong7.equals=" + DEFAULT_CONFIG_VAR_LONG_7,
            "configVarLong7.equals=" + UPDATED_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 in
        defaultConfigVariablesFiltering(
            "configVarLong7.in=" + DEFAULT_CONFIG_VAR_LONG_7 + "," + UPDATED_CONFIG_VAR_LONG_7,
            "configVarLong7.in=" + UPDATED_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 is not null
        defaultConfigVariablesFiltering("configVarLong7.specified=true", "configVarLong7.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong7.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_7,
            "configVarLong7.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong7.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_7,
            "configVarLong7.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 is less than
        defaultConfigVariablesFiltering(
            "configVarLong7.lessThan=" + UPDATED_CONFIG_VAR_LONG_7,
            "configVarLong7.lessThan=" + DEFAULT_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong7IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong7 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong7.greaterThan=" + SMALLER_CONFIG_VAR_LONG_7,
            "configVarLong7.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_7
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 equals to
        defaultConfigVariablesFiltering(
            "configVarLong8.equals=" + DEFAULT_CONFIG_VAR_LONG_8,
            "configVarLong8.equals=" + UPDATED_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 in
        defaultConfigVariablesFiltering(
            "configVarLong8.in=" + DEFAULT_CONFIG_VAR_LONG_8 + "," + UPDATED_CONFIG_VAR_LONG_8,
            "configVarLong8.in=" + UPDATED_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 is not null
        defaultConfigVariablesFiltering("configVarLong8.specified=true", "configVarLong8.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong8.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_8,
            "configVarLong8.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong8.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_8,
            "configVarLong8.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 is less than
        defaultConfigVariablesFiltering(
            "configVarLong8.lessThan=" + UPDATED_CONFIG_VAR_LONG_8,
            "configVarLong8.lessThan=" + DEFAULT_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong8IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong8 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong8.greaterThan=" + SMALLER_CONFIG_VAR_LONG_8,
            "configVarLong8.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_8
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 equals to
        defaultConfigVariablesFiltering(
            "configVarLong9.equals=" + DEFAULT_CONFIG_VAR_LONG_9,
            "configVarLong9.equals=" + UPDATED_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 in
        defaultConfigVariablesFiltering(
            "configVarLong9.in=" + DEFAULT_CONFIG_VAR_LONG_9 + "," + UPDATED_CONFIG_VAR_LONG_9,
            "configVarLong9.in=" + UPDATED_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 is not null
        defaultConfigVariablesFiltering("configVarLong9.specified=true", "configVarLong9.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong9.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_9,
            "configVarLong9.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong9.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_9,
            "configVarLong9.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 is less than
        defaultConfigVariablesFiltering(
            "configVarLong9.lessThan=" + UPDATED_CONFIG_VAR_LONG_9,
            "configVarLong9.lessThan=" + DEFAULT_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong9IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong9 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong9.greaterThan=" + SMALLER_CONFIG_VAR_LONG_9,
            "configVarLong9.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_9
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 equals to
        defaultConfigVariablesFiltering(
            "configVarLong10.equals=" + DEFAULT_CONFIG_VAR_LONG_10,
            "configVarLong10.equals=" + UPDATED_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 in
        defaultConfigVariablesFiltering(
            "configVarLong10.in=" + DEFAULT_CONFIG_VAR_LONG_10 + "," + UPDATED_CONFIG_VAR_LONG_10,
            "configVarLong10.in=" + UPDATED_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 is not null
        defaultConfigVariablesFiltering("configVarLong10.specified=true", "configVarLong10.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong10.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_10,
            "configVarLong10.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong10.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_10,
            "configVarLong10.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 is less than
        defaultConfigVariablesFiltering(
            "configVarLong10.lessThan=" + UPDATED_CONFIG_VAR_LONG_10,
            "configVarLong10.lessThan=" + DEFAULT_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong10IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong10 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong10.greaterThan=" + SMALLER_CONFIG_VAR_LONG_10,
            "configVarLong10.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_10
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 equals to
        defaultConfigVariablesFiltering(
            "configVarLong11.equals=" + DEFAULT_CONFIG_VAR_LONG_11,
            "configVarLong11.equals=" + UPDATED_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 in
        defaultConfigVariablesFiltering(
            "configVarLong11.in=" + DEFAULT_CONFIG_VAR_LONG_11 + "," + UPDATED_CONFIG_VAR_LONG_11,
            "configVarLong11.in=" + UPDATED_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 is not null
        defaultConfigVariablesFiltering("configVarLong11.specified=true", "configVarLong11.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong11.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_11,
            "configVarLong11.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong11.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_11,
            "configVarLong11.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 is less than
        defaultConfigVariablesFiltering(
            "configVarLong11.lessThan=" + UPDATED_CONFIG_VAR_LONG_11,
            "configVarLong11.lessThan=" + DEFAULT_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong11IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong11 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong11.greaterThan=" + SMALLER_CONFIG_VAR_LONG_11,
            "configVarLong11.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_11
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 equals to
        defaultConfigVariablesFiltering(
            "configVarLong12.equals=" + DEFAULT_CONFIG_VAR_LONG_12,
            "configVarLong12.equals=" + UPDATED_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 in
        defaultConfigVariablesFiltering(
            "configVarLong12.in=" + DEFAULT_CONFIG_VAR_LONG_12 + "," + UPDATED_CONFIG_VAR_LONG_12,
            "configVarLong12.in=" + UPDATED_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 is not null
        defaultConfigVariablesFiltering("configVarLong12.specified=true", "configVarLong12.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong12.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_12,
            "configVarLong12.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong12.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_12,
            "configVarLong12.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 is less than
        defaultConfigVariablesFiltering(
            "configVarLong12.lessThan=" + UPDATED_CONFIG_VAR_LONG_12,
            "configVarLong12.lessThan=" + DEFAULT_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong12IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong12 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong12.greaterThan=" + SMALLER_CONFIG_VAR_LONG_12,
            "configVarLong12.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_12
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 equals to
        defaultConfigVariablesFiltering(
            "configVarLong13.equals=" + DEFAULT_CONFIG_VAR_LONG_13,
            "configVarLong13.equals=" + UPDATED_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 in
        defaultConfigVariablesFiltering(
            "configVarLong13.in=" + DEFAULT_CONFIG_VAR_LONG_13 + "," + UPDATED_CONFIG_VAR_LONG_13,
            "configVarLong13.in=" + UPDATED_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 is not null
        defaultConfigVariablesFiltering("configVarLong13.specified=true", "configVarLong13.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong13.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_13,
            "configVarLong13.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong13.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_13,
            "configVarLong13.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 is less than
        defaultConfigVariablesFiltering(
            "configVarLong13.lessThan=" + UPDATED_CONFIG_VAR_LONG_13,
            "configVarLong13.lessThan=" + DEFAULT_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong13IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong13 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong13.greaterThan=" + SMALLER_CONFIG_VAR_LONG_13,
            "configVarLong13.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_13
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 equals to
        defaultConfigVariablesFiltering(
            "configVarLong14.equals=" + DEFAULT_CONFIG_VAR_LONG_14,
            "configVarLong14.equals=" + UPDATED_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 in
        defaultConfigVariablesFiltering(
            "configVarLong14.in=" + DEFAULT_CONFIG_VAR_LONG_14 + "," + UPDATED_CONFIG_VAR_LONG_14,
            "configVarLong14.in=" + UPDATED_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 is not null
        defaultConfigVariablesFiltering("configVarLong14.specified=true", "configVarLong14.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong14.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_14,
            "configVarLong14.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong14.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_14,
            "configVarLong14.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 is less than
        defaultConfigVariablesFiltering(
            "configVarLong14.lessThan=" + UPDATED_CONFIG_VAR_LONG_14,
            "configVarLong14.lessThan=" + DEFAULT_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong14IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong14 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong14.greaterThan=" + SMALLER_CONFIG_VAR_LONG_14,
            "configVarLong14.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_14
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 equals to
        defaultConfigVariablesFiltering(
            "configVarLong15.equals=" + DEFAULT_CONFIG_VAR_LONG_15,
            "configVarLong15.equals=" + UPDATED_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 in
        defaultConfigVariablesFiltering(
            "configVarLong15.in=" + DEFAULT_CONFIG_VAR_LONG_15 + "," + UPDATED_CONFIG_VAR_LONG_15,
            "configVarLong15.in=" + UPDATED_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 is not null
        defaultConfigVariablesFiltering("configVarLong15.specified=true", "configVarLong15.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 is greater than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong15.greaterThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_15,
            "configVarLong15.greaterThanOrEqual=" + UPDATED_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 is less than or equal to
        defaultConfigVariablesFiltering(
            "configVarLong15.lessThanOrEqual=" + DEFAULT_CONFIG_VAR_LONG_15,
            "configVarLong15.lessThanOrEqual=" + SMALLER_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 is less than
        defaultConfigVariablesFiltering(
            "configVarLong15.lessThan=" + UPDATED_CONFIG_VAR_LONG_15,
            "configVarLong15.lessThan=" + DEFAULT_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarLong15IsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarLong15 is greater than
        defaultConfigVariablesFiltering(
            "configVarLong15.greaterThan=" + SMALLER_CONFIG_VAR_LONG_15,
            "configVarLong15.greaterThan=" + DEFAULT_CONFIG_VAR_LONG_15
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean16IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean16 equals to
        defaultConfigVariablesFiltering(
            "configVarBoolean16.equals=" + DEFAULT_CONFIG_VAR_BOOLEAN_16,
            "configVarBoolean16.equals=" + UPDATED_CONFIG_VAR_BOOLEAN_16
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean16IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean16 in
        defaultConfigVariablesFiltering(
            "configVarBoolean16.in=" + DEFAULT_CONFIG_VAR_BOOLEAN_16 + "," + UPDATED_CONFIG_VAR_BOOLEAN_16,
            "configVarBoolean16.in=" + UPDATED_CONFIG_VAR_BOOLEAN_16
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean16IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean16 is not null
        defaultConfigVariablesFiltering("configVarBoolean16.specified=true", "configVarBoolean16.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean17IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean17 equals to
        defaultConfigVariablesFiltering(
            "configVarBoolean17.equals=" + DEFAULT_CONFIG_VAR_BOOLEAN_17,
            "configVarBoolean17.equals=" + UPDATED_CONFIG_VAR_BOOLEAN_17
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean17IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean17 in
        defaultConfigVariablesFiltering(
            "configVarBoolean17.in=" + DEFAULT_CONFIG_VAR_BOOLEAN_17 + "," + UPDATED_CONFIG_VAR_BOOLEAN_17,
            "configVarBoolean17.in=" + UPDATED_CONFIG_VAR_BOOLEAN_17
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean17IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean17 is not null
        defaultConfigVariablesFiltering("configVarBoolean17.specified=true", "configVarBoolean17.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean18IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean18 equals to
        defaultConfigVariablesFiltering(
            "configVarBoolean18.equals=" + DEFAULT_CONFIG_VAR_BOOLEAN_18,
            "configVarBoolean18.equals=" + UPDATED_CONFIG_VAR_BOOLEAN_18
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean18IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean18 in
        defaultConfigVariablesFiltering(
            "configVarBoolean18.in=" + DEFAULT_CONFIG_VAR_BOOLEAN_18 + "," + UPDATED_CONFIG_VAR_BOOLEAN_18,
            "configVarBoolean18.in=" + UPDATED_CONFIG_VAR_BOOLEAN_18
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarBoolean18IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarBoolean18 is not null
        defaultConfigVariablesFiltering("configVarBoolean18.specified=true", "configVarBoolean18.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString19IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString19 equals to
        defaultConfigVariablesFiltering(
            "configVarString19.equals=" + DEFAULT_CONFIG_VAR_STRING_19,
            "configVarString19.equals=" + UPDATED_CONFIG_VAR_STRING_19
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString19IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString19 in
        defaultConfigVariablesFiltering(
            "configVarString19.in=" + DEFAULT_CONFIG_VAR_STRING_19 + "," + UPDATED_CONFIG_VAR_STRING_19,
            "configVarString19.in=" + UPDATED_CONFIG_VAR_STRING_19
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString19IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString19 is not null
        defaultConfigVariablesFiltering("configVarString19.specified=true", "configVarString19.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString19ContainsSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString19 contains
        defaultConfigVariablesFiltering(
            "configVarString19.contains=" + DEFAULT_CONFIG_VAR_STRING_19,
            "configVarString19.contains=" + UPDATED_CONFIG_VAR_STRING_19
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString19NotContainsSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString19 does not contain
        defaultConfigVariablesFiltering(
            "configVarString19.doesNotContain=" + UPDATED_CONFIG_VAR_STRING_19,
            "configVarString19.doesNotContain=" + DEFAULT_CONFIG_VAR_STRING_19
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString20IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString20 equals to
        defaultConfigVariablesFiltering(
            "configVarString20.equals=" + DEFAULT_CONFIG_VAR_STRING_20,
            "configVarString20.equals=" + UPDATED_CONFIG_VAR_STRING_20
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString20IsInShouldWork() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString20 in
        defaultConfigVariablesFiltering(
            "configVarString20.in=" + DEFAULT_CONFIG_VAR_STRING_20 + "," + UPDATED_CONFIG_VAR_STRING_20,
            "configVarString20.in=" + UPDATED_CONFIG_VAR_STRING_20
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString20IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString20 is not null
        defaultConfigVariablesFiltering("configVarString20.specified=true", "configVarString20.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString20ContainsSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString20 contains
        defaultConfigVariablesFiltering(
            "configVarString20.contains=" + DEFAULT_CONFIG_VAR_STRING_20,
            "configVarString20.contains=" + UPDATED_CONFIG_VAR_STRING_20
        );
    }

    @Test
    @Transactional
    void getAllConfigVariablesesByConfigVarString20NotContainsSomething() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        // Get all the configVariablesList where configVarString20 does not contain
        defaultConfigVariablesFiltering(
            "configVarString20.doesNotContain=" + UPDATED_CONFIG_VAR_STRING_20,
            "configVarString20.doesNotContain=" + DEFAULT_CONFIG_VAR_STRING_20
        );
    }

    private void defaultConfigVariablesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultConfigVariablesShouldBeFound(shouldBeFound);
        defaultConfigVariablesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfigVariablesShouldBeFound(String filter) throws Exception {
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configVariables.getId().intValue())))
            .andExpect(jsonPath("$.[*].configVarLong1").value(hasItem(DEFAULT_CONFIG_VAR_LONG_1.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong2").value(hasItem(DEFAULT_CONFIG_VAR_LONG_2.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong3").value(hasItem(DEFAULT_CONFIG_VAR_LONG_3.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong4").value(hasItem(DEFAULT_CONFIG_VAR_LONG_4.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong5").value(hasItem(DEFAULT_CONFIG_VAR_LONG_5.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong6").value(hasItem(DEFAULT_CONFIG_VAR_LONG_6.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong7").value(hasItem(DEFAULT_CONFIG_VAR_LONG_7.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong8").value(hasItem(DEFAULT_CONFIG_VAR_LONG_8.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong9").value(hasItem(DEFAULT_CONFIG_VAR_LONG_9.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong10").value(hasItem(DEFAULT_CONFIG_VAR_LONG_10.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong11").value(hasItem(DEFAULT_CONFIG_VAR_LONG_11.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong12").value(hasItem(DEFAULT_CONFIG_VAR_LONG_12.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong13").value(hasItem(DEFAULT_CONFIG_VAR_LONG_13.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong14").value(hasItem(DEFAULT_CONFIG_VAR_LONG_14.intValue())))
            .andExpect(jsonPath("$.[*].configVarLong15").value(hasItem(DEFAULT_CONFIG_VAR_LONG_15.intValue())))
            .andExpect(jsonPath("$.[*].configVarBoolean16").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_16)))
            .andExpect(jsonPath("$.[*].configVarBoolean17").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_17)))
            .andExpect(jsonPath("$.[*].configVarBoolean18").value(hasItem(DEFAULT_CONFIG_VAR_BOOLEAN_18)))
            .andExpect(jsonPath("$.[*].configVarString19").value(hasItem(DEFAULT_CONFIG_VAR_STRING_19)))
            .andExpect(jsonPath("$.[*].configVarString20").value(hasItem(DEFAULT_CONFIG_VAR_STRING_20)));

        // Check, that the count call also returns 1
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfigVariablesShouldNotBeFound(String filter) throws Exception {
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfigVariablesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfigVariables() throws Exception {
        // Get the configVariables
        restConfigVariablesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfigVariables() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the configVariables
        ConfigVariables updatedConfigVariables = configVariablesRepository.findById(configVariables.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConfigVariables are not directly saved in db
        em.detach(updatedConfigVariables);
        updatedConfigVariables
            .configVarLong1(UPDATED_CONFIG_VAR_LONG_1)
            .configVarLong2(UPDATED_CONFIG_VAR_LONG_2)
            .configVarLong3(UPDATED_CONFIG_VAR_LONG_3)
            .configVarLong4(UPDATED_CONFIG_VAR_LONG_4)
            .configVarLong5(UPDATED_CONFIG_VAR_LONG_5)
            .configVarLong6(UPDATED_CONFIG_VAR_LONG_6)
            .configVarLong7(UPDATED_CONFIG_VAR_LONG_7)
            .configVarLong8(UPDATED_CONFIG_VAR_LONG_8)
            .configVarLong9(UPDATED_CONFIG_VAR_LONG_9)
            .configVarLong10(UPDATED_CONFIG_VAR_LONG_10)
            .configVarLong11(UPDATED_CONFIG_VAR_LONG_11)
            .configVarLong12(UPDATED_CONFIG_VAR_LONG_12)
            .configVarLong13(UPDATED_CONFIG_VAR_LONG_13)
            .configVarLong14(UPDATED_CONFIG_VAR_LONG_14)
            .configVarLong15(UPDATED_CONFIG_VAR_LONG_15)
            .configVarBoolean16(UPDATED_CONFIG_VAR_BOOLEAN_16)
            .configVarBoolean17(UPDATED_CONFIG_VAR_BOOLEAN_17)
            .configVarBoolean18(UPDATED_CONFIG_VAR_BOOLEAN_18)
            .configVarString19(UPDATED_CONFIG_VAR_STRING_19)
            .configVarString20(UPDATED_CONFIG_VAR_STRING_20);
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(updatedConfigVariables);

        restConfigVariablesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configVariablesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(configVariablesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConfigVariablesToMatchAllProperties(updatedConfigVariables);
    }

    @Test
    @Transactional
    void putNonExistingConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configVariablesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(configVariablesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(configVariablesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configVariablesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigVariablesWithPatch() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the configVariables using partial update
        ConfigVariables partialUpdatedConfigVariables = new ConfigVariables();
        partialUpdatedConfigVariables.setId(configVariables.getId());

        partialUpdatedConfigVariables
            .configVarLong3(UPDATED_CONFIG_VAR_LONG_3)
            .configVarLong5(UPDATED_CONFIG_VAR_LONG_5)
            .configVarLong6(UPDATED_CONFIG_VAR_LONG_6)
            .configVarLong8(UPDATED_CONFIG_VAR_LONG_8)
            .configVarLong14(UPDATED_CONFIG_VAR_LONG_14)
            .configVarLong15(UPDATED_CONFIG_VAR_LONG_15)
            .configVarBoolean16(UPDATED_CONFIG_VAR_BOOLEAN_16)
            .configVarBoolean18(UPDATED_CONFIG_VAR_BOOLEAN_18)
            .configVarString19(UPDATED_CONFIG_VAR_STRING_19);

        restConfigVariablesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigVariables.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfigVariables))
            )
            .andExpect(status().isOk());

        // Validate the ConfigVariables in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfigVariablesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConfigVariables, configVariables),
            getPersistedConfigVariables(configVariables)
        );
    }

    @Test
    @Transactional
    void fullUpdateConfigVariablesWithPatch() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the configVariables using partial update
        ConfigVariables partialUpdatedConfigVariables = new ConfigVariables();
        partialUpdatedConfigVariables.setId(configVariables.getId());

        partialUpdatedConfigVariables
            .configVarLong1(UPDATED_CONFIG_VAR_LONG_1)
            .configVarLong2(UPDATED_CONFIG_VAR_LONG_2)
            .configVarLong3(UPDATED_CONFIG_VAR_LONG_3)
            .configVarLong4(UPDATED_CONFIG_VAR_LONG_4)
            .configVarLong5(UPDATED_CONFIG_VAR_LONG_5)
            .configVarLong6(UPDATED_CONFIG_VAR_LONG_6)
            .configVarLong7(UPDATED_CONFIG_VAR_LONG_7)
            .configVarLong8(UPDATED_CONFIG_VAR_LONG_8)
            .configVarLong9(UPDATED_CONFIG_VAR_LONG_9)
            .configVarLong10(UPDATED_CONFIG_VAR_LONG_10)
            .configVarLong11(UPDATED_CONFIG_VAR_LONG_11)
            .configVarLong12(UPDATED_CONFIG_VAR_LONG_12)
            .configVarLong13(UPDATED_CONFIG_VAR_LONG_13)
            .configVarLong14(UPDATED_CONFIG_VAR_LONG_14)
            .configVarLong15(UPDATED_CONFIG_VAR_LONG_15)
            .configVarBoolean16(UPDATED_CONFIG_VAR_BOOLEAN_16)
            .configVarBoolean17(UPDATED_CONFIG_VAR_BOOLEAN_17)
            .configVarBoolean18(UPDATED_CONFIG_VAR_BOOLEAN_18)
            .configVarString19(UPDATED_CONFIG_VAR_STRING_19)
            .configVarString20(UPDATED_CONFIG_VAR_STRING_20);

        restConfigVariablesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigVariables.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfigVariables))
            )
            .andExpect(status().isOk());

        // Validate the ConfigVariables in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfigVariablesUpdatableFieldsEquals(
            partialUpdatedConfigVariables,
            getPersistedConfigVariables(partialUpdatedConfigVariables)
        );
    }

    @Test
    @Transactional
    void patchNonExistingConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configVariablesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(configVariablesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(configVariablesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigVariables() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        configVariables.setId(longCount.incrementAndGet());

        // Create the ConfigVariables
        ConfigVariablesDTO configVariablesDTO = configVariablesMapper.toDto(configVariables);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigVariablesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(configVariablesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigVariables in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigVariables() throws Exception {
        // Initialize the database
        insertedConfigVariables = configVariablesRepository.saveAndFlush(configVariables);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the configVariables
        restConfigVariablesMockMvc
            .perform(delete(ENTITY_API_URL_ID, configVariables.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return configVariablesRepository.count();
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

    protected ConfigVariables getPersistedConfigVariables(ConfigVariables configVariables) {
        return configVariablesRepository.findById(configVariables.getId()).orElseThrow();
    }

    protected void assertPersistedConfigVariablesToMatchAllProperties(ConfigVariables expectedConfigVariables) {
        assertConfigVariablesAllPropertiesEquals(expectedConfigVariables, getPersistedConfigVariables(expectedConfigVariables));
    }

    protected void assertPersistedConfigVariablesToMatchUpdatableProperties(ConfigVariables expectedConfigVariables) {
        assertConfigVariablesAllUpdatablePropertiesEquals(expectedConfigVariables, getPersistedConfigVariables(expectedConfigVariables));
    }
}
