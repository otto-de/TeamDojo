package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

}
