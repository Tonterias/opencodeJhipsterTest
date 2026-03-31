package com.opencode.test.domain;

import static com.opencode.test.domain.UrllinkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UrllinkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Urllink.class);
        Urllink urllink1 = getUrllinkSample1();
        Urllink urllink2 = new Urllink();
        assertThat(urllink1).isNotEqualTo(urllink2);

        urllink2.setId(urllink1.getId());
        assertThat(urllink1).isEqualTo(urllink2);

        urllink2 = getUrllinkSample2();
        assertThat(urllink1).isNotEqualTo(urllink2);
    }
}
