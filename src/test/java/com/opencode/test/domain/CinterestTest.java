package com.opencode.test.domain;

import static com.opencode.test.domain.CinterestTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CinterestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cinterest.class);
        Cinterest cinterest1 = getCinterestSample1();
        Cinterest cinterest2 = new Cinterest();
        assertThat(cinterest1).isNotEqualTo(cinterest2);

        cinterest2.setId(cinterest1.getId());
        assertThat(cinterest1).isEqualTo(cinterest2);

        cinterest2 = getCinterestSample2();
        assertThat(cinterest1).isNotEqualTo(cinterest2);
    }

    @Test
    void communityTest() {
        Cinterest cinterest = getCinterestRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        cinterest.addCommunity(communityBack);
        assertThat(cinterest.getCommunities()).containsOnly(communityBack);

        cinterest.removeCommunity(communityBack);
        assertThat(cinterest.getCommunities()).doesNotContain(communityBack);

        cinterest.communities(new HashSet<>(Set.of(communityBack)));
        assertThat(cinterest.getCommunities()).containsOnly(communityBack);

        cinterest.setCommunities(new HashSet<>());
        assertThat(cinterest.getCommunities()).doesNotContain(communityBack);
    }
}
