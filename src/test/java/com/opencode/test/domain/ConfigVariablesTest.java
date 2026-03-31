package com.opencode.test.domain;

import static com.opencode.test.domain.ConfigVariablesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigVariablesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigVariables.class);
        ConfigVariables configVariables1 = getConfigVariablesSample1();
        ConfigVariables configVariables2 = new ConfigVariables();
        assertThat(configVariables1).isNotEqualTo(configVariables2);

        configVariables2.setId(configVariables1.getId());
        assertThat(configVariables1).isEqualTo(configVariables2);

        configVariables2 = getConfigVariablesSample2();
        assertThat(configVariables1).isNotEqualTo(configVariables2);
    }
}
