package com.opencode.test.domain;

import static com.opencode.test.domain.AppphotoTestSamples.*;
import static com.opencode.test.domain.AppuserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppphotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appphoto.class);
        Appphoto appphoto1 = getAppphotoSample1();
        Appphoto appphoto2 = new Appphoto();
        assertThat(appphoto1).isNotEqualTo(appphoto2);

        appphoto2.setId(appphoto1.getId());
        assertThat(appphoto1).isEqualTo(appphoto2);

        appphoto2 = getAppphotoSample2();
        assertThat(appphoto1).isNotEqualTo(appphoto2);
    }

    @Test
    void appuserTest() {
        Appphoto appphoto = getAppphotoRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        appphoto.setAppuser(appuserBack);
        assertThat(appphoto.getAppuser()).isEqualTo(appuserBack);

        appphoto.appuser(null);
        assertThat(appphoto.getAppuser()).isNull();
    }
}
