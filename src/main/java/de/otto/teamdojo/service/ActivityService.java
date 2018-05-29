package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.ActivityDTO;
import de.otto.teamdojo.service.dto.BadgeDTO;
import de.otto.teamdojo.service.dto.TeamSkillDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Activity.
 */
public interface ActivityService {

    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    ActivityDTO save(ActivityDTO activityDTO);

    /**
     * Create an activity for BADGE_CREATED
     * @return the persisted entity
     */
    ActivityDTO createForNewBadge(BadgeDTO badgeDTO);

    /**
     * Create an activity for SKILL_COMPLETED
     * @param teamSkill
     * @return the persisted entity
     */
    ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill);

    /**
     * Get all the activities.
     *
     * @return the list of entities
     */
    List<ActivityDTO> findAll();


    /**
     * Get the "id" activity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActivityDTO> findOne(Long id);

    /**
     * Delete the "id" activity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
