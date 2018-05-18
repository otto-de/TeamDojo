package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.config.DefaultProfileUtil;
import de.otto.teamdojo.service.OrganizationService;
import de.otto.teamdojo.service.dto.OrganizationDTO;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    private static final String DEFAULT_ORGANIZATION_NAME = "Organization";
    private final Logger log = LoggerFactory.getLogger(ProfileInfoResource.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    private final OrganizationService organizationService;

    public ProfileInfoResource(Environment env, JHipsterProperties jHipsterProperties, OrganizationService organizationService) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
        this.organizationService = organizationService;

    }

    @GetMapping("/profile-info")
    public ProfileInfoVM getActiveProfiles() {
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        OrganizationDTO organization = getOrganization();
        return new ProfileInfoVM(activeProfiles, getRibbonEnv(activeProfiles), organization);
    }

    private String getRibbonEnv(String[] activeProfiles) {
        String[] displayOnActiveProfiles = jHipsterProperties.getRibbon().getDisplayOnActiveProfiles();
        if (displayOnActiveProfiles == null) {
            return null;
        }
        List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
        List<String> springBootProfiles = Arrays.asList(activeProfiles);
        ribbonProfiles.retainAll(springBootProfiles);
        if (!ribbonProfiles.isEmpty()) {
            return ribbonProfiles.get(0);
        }
        return null;
    }

    private OrganizationDTO getOrganization() {
        List<OrganizationDTO> organizations = organizationService.findAll();
        if (organizations.isEmpty()) {
            return getDefaultOrganization();
        } else {
            if (organizations.size() > 1) {
                log.warn("There exists more than one organization");
            }
            return organizations.get(0);
        }
    }

    private OrganizationDTO getDefaultOrganization() {
        OrganizationDTO organization = new OrganizationDTO();
        organization.setName(DEFAULT_ORGANIZATION_NAME);
        return organization;
    }

    class ProfileInfoVM {

        private String[] activeProfiles;

        private String ribbonEnv;

        private OrganizationDTO organization;

        ProfileInfoVM(String[] activeProfiles, String ribbonEnv, OrganizationDTO organization) {
            this.activeProfiles = activeProfiles;
            this.ribbonEnv = ribbonEnv;
            this.organization = organization;
        }

        public String[] getActiveProfiles() {
            return activeProfiles;
        }

        public String getRibbonEnv() {
            return ribbonEnv;
        }

        public OrganizationDTO getOrganization() {
            return organization;
        }
    }
}
