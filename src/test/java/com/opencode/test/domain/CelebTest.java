package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.CelebTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CelebTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Celeb.class);
        Celeb celeb1 = getCelebSample1();
        Celeb celeb2 = new Celeb();
        assertThat(celeb1).isNotEqualTo(celeb2);

        celeb2.setId(celeb1.getId());
        assertThat(celeb1).isEqualTo(celeb2);

        celeb2 = getCelebSample2();
        assertThat(celeb1).isNotEqualTo(celeb2);
    }

    @Test
    void appuserTest() {
        Celeb celeb = getCelebRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        celeb.addAppuser(appuserBack);
        assertThat(celeb.getAppusers()).containsOnly(appuserBack);

        celeb.removeAppuser(appuserBack);
        assertThat(celeb.getAppusers()).doesNotContain(appuserBack);

        celeb.appusers(new HashSet<>(Set.of(appuserBack)));
        assertThat(celeb.getAppusers()).containsOnly(appuserBack);

        celeb.setAppusers(new HashSet<>());
        assertThat(celeb.getAppusers()).doesNotContain(appuserBack);
    }
}
