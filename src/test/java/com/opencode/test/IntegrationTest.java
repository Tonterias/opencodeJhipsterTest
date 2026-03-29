package com.opencode.test;

import com.opencode.test.config.AsyncSyncConfiguration;
import com.opencode.test.config.EmbeddedSQL;
import com.opencode.test.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        OpencodetestApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        com.opencode.test.config.JacksonHibernateConfiguration.class,
    }
)
@EmbeddedSQL
public @interface IntegrationTest {}
