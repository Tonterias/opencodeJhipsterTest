package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    void appuserTest() {
        Notification notification = getNotificationRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        notification.setAppuser(appuserBack);
        assertThat(notification.getAppuser()).isEqualTo(appuserBack);

        notification.appuser(null);
        assertThat(notification.getAppuser()).isNull();
    }
}
