package de.otto.teamdojo.config;

import de.otto.teamdojo.repository.UserRepository;
import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = {WebConfigurer.class, LiquibaseConfiguration.class})
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
            JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

            jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                    ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".levels", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".badges", jcacheConfiguration);
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
            cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".dimensions", jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.BadgeSkill.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.LevelSkill.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Organization.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Report.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(de.otto.teamdojo.domain.Image.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
