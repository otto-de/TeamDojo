package de.otto.teamdojo.web.rest;

import com.google.common.collect.Lists;
import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.config.ApplicationProperties;
import de.otto.teamdojo.service.OrganizationService;
import de.otto.teamdojo.service.dto.OrganizationDTO;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static de.otto.teamdojo.web.rest.ProfileInfoResource.DEFAULT_ORGANIZATION_NAME;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfileInfoResource REST controller.
 *
 * @see ProfileInfoResource
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class ProfileInfoResourceIntTest {

    @Mock
    private Environment environment;

    @Mock
    private JHipsterProperties jHipsterProperties;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private OrganizationService organizationService;

    private MockMvc restProfileMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String mockProfile[] = {"test"};
        when(applicationProperties.getDisplayOnActiveProfiles()).thenReturn(mockProfile);

        String activeProfiles[] = {"test"};
        when(environment.getDefaultProfiles()).thenReturn(activeProfiles);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);

        ProfileInfoResource profileInfoResource = new ProfileInfoResource(environment, jHipsterProperties, organizationService, applicationProperties);
        this.restProfileMockMvc = MockMvcBuilders
            .standaloneSetup(profileInfoResource)
            .build();
    }

    @Test
    public void getProfileInfoWithRibbon() throws Exception {
        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutRibbon() throws Exception {
        when(applicationProperties.getDisplayOnActiveProfiles()).thenReturn(null);

        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutActiveProfiles() throws Exception {
        String emptyProfile[] = {};
        when(environment.getDefaultProfiles()).thenReturn(emptyProfile);
        when(environment.getActiveProfiles()).thenReturn(emptyProfile);

        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileWithOrganization() throws Exception {
        String customOrganizationName = "My Org";
        OrganizationDTO organization = new OrganizationDTO();
        organization.setName(customOrganizationName);
        when(organizationService.findAll()).thenReturn(Lists.newArrayList(organization));

        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.organization.name").value(customOrganizationName));
    }

    @Test
    public void getProfileWithDefaultOrganization() throws Exception {
        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.organization.name").value(DEFAULT_ORGANIZATION_NAME));
    }
}
