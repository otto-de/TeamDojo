package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.service.AchievableSkillService;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamAchievableSkillResource {

    private final Logger log = LoggerFactory.getLogger(TeamAchievableSkillResource.class);

    private final AchievableSkillService achievableSkillService;

    public TeamAchievableSkillResource(AchievableSkillService achievableSkillService) {
        this.achievableSkillService = achievableSkillService;
    }

    @GetMapping("/teams/{id}/achievable-skills")
    @Timed
    public ResponseEntity<List<AchievableSkillDTO>> getAchievableSkills(
        @PathVariable Long id,
        @RequestParam(name = "levelId", required = false, defaultValue = "") List<Long> levelIds,
        @RequestParam(name = "badgeId", required = false, defaultValue = "") List<Long> badgeIds,
        Pageable pageable) {
        log.debug("REST request to get AchievableSkills for Team; {}", id);
        Page<AchievableSkillDTO> page = achievableSkillService.findAllByTeamAndLevelAndBadge(id, levelIds, badgeIds, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/teams/" + id + "/achievable-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PutMapping("/teams/{id}/achievable-skills")
    @Timed
    public ResponseEntity<AchievableSkillDTO> updateAchievableSkill(@PathVariable Long id, @RequestBody AchievableSkillDTO achievableSkill) throws URISyntaxException {
        AchievableSkillDTO result = achievableSkillService.updateAchievableSkill(id, achievableSkill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("AchievableSkillDTO", result.getSkillId().toString()))
            .body(result);
    }
}
