package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.BlockuserTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlockuserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blockuser.class);
        Blockuser blockuser1 = getBlockuserSample1();
        Blockuser blockuser2 = new Blockuser();
        assertThat(blockuser1).isNotEqualTo(blockuser2);

        blockuser2.setId(blockuser1.getId());
        assertThat(blockuser1).isEqualTo(blockuser2);

        blockuser2 = getBlockuserSample2();
        assertThat(blockuser1).isNotEqualTo(blockuser2);
    }

    @Test
    void blockeduserTest() {
        Blockuser blockuser = getBlockuserRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        blockuser.setBlockeduser(appuserBack);
        assertThat(blockuser.getBlockeduser()).isEqualTo(appuserBack);

        blockuser.blockeduser(null);
        assertThat(blockuser.getBlockeduser()).isNull();
    }

    @Test
    void blockinguserTest() {
        Blockuser blockuser = getBlockuserRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        blockuser.setBlockinguser(appuserBack);
        assertThat(blockuser.getBlockinguser()).isEqualTo(appuserBack);

        blockuser.blockinguser(null);
        assertThat(blockuser.getBlockinguser()).isNull();
    }

    @Test
    void cblockeduserTest() {
        Blockuser blockuser = getBlockuserRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        blockuser.setCblockeduser(communityBack);
        assertThat(blockuser.getCblockeduser()).isEqualTo(communityBack);

        blockuser.cblockeduser(null);
        assertThat(blockuser.getCblockeduser()).isNull();
    }

    @Test
    void cblockinguserTest() {
        Blockuser blockuser = getBlockuserRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        blockuser.setCblockinguser(communityBack);
        assertThat(blockuser.getCblockinguser()).isEqualTo(communityBack);

        blockuser.cblockinguser(null);
        assertThat(blockuser.getCblockinguser()).isNull();
    }
}
