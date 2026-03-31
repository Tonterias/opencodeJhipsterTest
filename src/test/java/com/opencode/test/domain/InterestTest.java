package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.InterestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InterestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interest.class);
        Interest interest1 = getInterestSample1();
        Interest interest2 = new Interest();
        assertThat(interest1).isNotEqualTo(interest2);

        interest2.setId(interest1.getId());
        assertThat(interest1).isEqualTo(interest2);

        interest2 = getInterestSample2();
        assertThat(interest1).isNotEqualTo(interest2);
    }

    @Test
    void appuserTest() {
        Interest interest = getInterestRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        interest.addAppuser(appuserBack);
        assertThat(interest.getAppusers()).containsOnly(appuserBack);

        interest.removeAppuser(appuserBack);
        assertThat(interest.getAppusers()).doesNotContain(appuserBack);

        interest.appusers(new HashSet<>(Set.of(appuserBack)));
        assertThat(interest.getAppusers()).containsOnly(appuserBack);

        interest.setAppusers(new HashSet<>());
        assertThat(interest.getAppusers()).doesNotContain(appuserBack);
    }
}
