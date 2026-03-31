package com.opencode.test.domain;

import static com.opencode.test.domain.FrontpageconfigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrontpageconfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frontpageconfig.class);
        Frontpageconfig frontpageconfig1 = getFrontpageconfigSample1();
        Frontpageconfig frontpageconfig2 = new Frontpageconfig();
        assertThat(frontpageconfig1).isNotEqualTo(frontpageconfig2);

        frontpageconfig2.setId(frontpageconfig1.getId());
        assertThat(frontpageconfig1).isEqualTo(frontpageconfig2);

        frontpageconfig2 = getFrontpageconfigSample2();
        assertThat(frontpageconfig1).isNotEqualTo(frontpageconfig2);
    }
}
