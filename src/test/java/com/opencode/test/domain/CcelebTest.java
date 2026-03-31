package com.opencode.test.domain;

import static com.opencode.test.domain.CcelebTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CcelebTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cceleb.class);
        Cceleb cceleb1 = getCcelebSample1();
        Cceleb cceleb2 = new Cceleb();
        assertThat(cceleb1).isNotEqualTo(cceleb2);

        cceleb2.setId(cceleb1.getId());
        assertThat(cceleb1).isEqualTo(cceleb2);

        cceleb2 = getCcelebSample2();
        assertThat(cceleb1).isNotEqualTo(cceleb2);
    }

    @Test
    void communityTest() {
        Cceleb cceleb = getCcelebRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        cceleb.addCommunity(communityBack);
        assertThat(cceleb.getCommunities()).containsOnly(communityBack);

        cceleb.removeCommunity(communityBack);
        assertThat(cceleb.getCommunities()).doesNotContain(communityBack);

        cceleb.communities(new HashSet<>(Set.of(communityBack)));
        assertThat(cceleb.getCommunities()).containsOnly(communityBack);

        cceleb.setCommunities(new HashSet<>());
        assertThat(cceleb.getCommunities()).doesNotContain(communityBack);
    }
}
