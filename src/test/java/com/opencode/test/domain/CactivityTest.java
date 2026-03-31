package com.opencode.test.domain;

import static com.opencode.test.domain.CactivityTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CactivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cactivity.class);
        Cactivity cactivity1 = getCactivitySample1();
        Cactivity cactivity2 = new Cactivity();
        assertThat(cactivity1).isNotEqualTo(cactivity2);

        cactivity2.setId(cactivity1.getId());
        assertThat(cactivity1).isEqualTo(cactivity2);

        cactivity2 = getCactivitySample2();
        assertThat(cactivity1).isNotEqualTo(cactivity2);
    }

    @Test
    void communityTest() {
        Cactivity cactivity = getCactivityRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        cactivity.addCommunity(communityBack);
        assertThat(cactivity.getCommunities()).containsOnly(communityBack);

        cactivity.removeCommunity(communityBack);
        assertThat(cactivity.getCommunities()).doesNotContain(communityBack);

        cactivity.communities(new HashSet<>(Set.of(communityBack)));
        assertThat(cactivity.getCommunities()).containsOnly(communityBack);

        cactivity.setCommunities(new HashSet<>());
        assertThat(cactivity.getCommunities()).doesNotContain(communityBack);
    }
}
