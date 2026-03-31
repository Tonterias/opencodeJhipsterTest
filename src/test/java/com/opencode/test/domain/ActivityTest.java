package com.opencode.test.domain;

import static com.opencode.test.domain.ActivityTestSamples.*;
import static com.opencode.test.domain.AppuserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = getActivitySample1();
        Activity activity2 = new Activity();
        assertThat(activity1).isNotEqualTo(activity2);

        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);

        activity2 = getActivitySample2();
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    void appuserTest() {
        Activity activity = getActivityRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        activity.addAppuser(appuserBack);
        assertThat(activity.getAppusers()).containsOnly(appuserBack);

        activity.removeAppuser(appuserBack);
        assertThat(activity.getAppusers()).doesNotContain(appuserBack);

        activity.appusers(new HashSet<>(Set.of(appuserBack)));
        assertThat(activity.getAppusers()).containsOnly(appuserBack);

        activity.setAppusers(new HashSet<>());
        assertThat(activity.getAppusers()).doesNotContain(appuserBack);
    }
}
