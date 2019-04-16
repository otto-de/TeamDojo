package de.otto.teamdojo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Teamdojo.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String[] displayOnActiveProfiles = null;

    public String[] getDisplayOnActiveProfiles() {
        return displayOnActiveProfiles;
    }

    public void setDisplayOnActiveProfiles(String[] displayOnActiveProfiles) {
        this.displayOnActiveProfiles = displayOnActiveProfiles;
    }

}
