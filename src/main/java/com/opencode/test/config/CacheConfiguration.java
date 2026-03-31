package com.opencode.test.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        var ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.opencode.test.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.opencode.test.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.opencode.test.domain.User.class.getName());
            createCache(cm, com.opencode.test.domain.Authority.class.getName());
            createCache(cm, com.opencode.test.domain.User.class.getName() + ".authorities");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName());
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".blogs");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".communities");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".notifications");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".comments");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".posts");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".followeds");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".followings");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".blockedusers");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".blockingusers");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".interests");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".activities");
            createCache(cm, com.opencode.test.domain.Appuser.class.getName() + ".celebs");
            createCache(cm, com.opencode.test.domain.Blog.class.getName());
            createCache(cm, com.opencode.test.domain.Blog.class.getName() + ".posts");
            createCache(cm, com.opencode.test.domain.Post.class.getName());
            createCache(cm, com.opencode.test.domain.Post.class.getName() + ".comments");
            createCache(cm, com.opencode.test.domain.Post.class.getName() + ".tags");
            createCache(cm, com.opencode.test.domain.Post.class.getName() + ".topics");
            createCache(cm, com.opencode.test.domain.Topic.class.getName());
            createCache(cm, com.opencode.test.domain.Topic.class.getName() + ".posts");
            createCache(cm, com.opencode.test.domain.Tag.class.getName());
            createCache(cm, com.opencode.test.domain.Tag.class.getName() + ".posts");
            createCache(cm, com.opencode.test.domain.Comment.class.getName());
            createCache(cm, com.opencode.test.domain.Notification.class.getName());
            createCache(cm, com.opencode.test.domain.Appphoto.class.getName());
            createCache(cm, com.opencode.test.domain.Community.class.getName());
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".blogs");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cfolloweds");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cfollowings");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cblockedusers");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cblockingusers");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cinterests");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".cactivities");
            createCache(cm, com.opencode.test.domain.Community.class.getName() + ".ccelebs");
            createCache(cm, com.opencode.test.domain.Follow.class.getName());
            createCache(cm, com.opencode.test.domain.Blockuser.class.getName());
            createCache(cm, com.opencode.test.domain.Interest.class.getName());
            createCache(cm, com.opencode.test.domain.Interest.class.getName() + ".appusers");
            createCache(cm, com.opencode.test.domain.Activity.class.getName());
            createCache(cm, com.opencode.test.domain.Activity.class.getName() + ".appusers");
            createCache(cm, com.opencode.test.domain.Celeb.class.getName());
            createCache(cm, com.opencode.test.domain.Celeb.class.getName() + ".appusers");
            createCache(cm, com.opencode.test.domain.Cinterest.class.getName());
            createCache(cm, com.opencode.test.domain.Cinterest.class.getName() + ".communities");
            createCache(cm, com.opencode.test.domain.Cactivity.class.getName());
            createCache(cm, com.opencode.test.domain.Cactivity.class.getName() + ".communities");
            createCache(cm, com.opencode.test.domain.Cceleb.class.getName());
            createCache(cm, com.opencode.test.domain.Cceleb.class.getName() + ".communities");
            createCache(cm, com.opencode.test.domain.Urllink.class.getName());
            createCache(cm, com.opencode.test.domain.Frontpageconfig.class.getName());
            createCache(cm, com.opencode.test.domain.Feedback.class.getName());
            createCache(cm, com.opencode.test.domain.ConfigVariables.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
