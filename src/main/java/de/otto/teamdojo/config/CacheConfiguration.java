package de.otto.teamdojo.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(de.otto.teamdojo.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Skill.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".badges", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".levels", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Team.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Team.class.getName() + ".participations", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Team.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.TeamSkill.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Level.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Level.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Badge.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.BadgeSkill.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.LevelSkill.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
