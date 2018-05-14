package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Level entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelRepository extends JpaRepository<Level, Long>, JpaSpecificationExecutor<Level> {
    Page<Level> findByIdIn(List<Long> levelIds, Pageable pageable);

}
