package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Badge;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Badge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long>, JpaSpecificationExecutor<Badge> {

    @Query(value = "select distinct badge from Badge badge left join fetch badge.dimensions",
        countQuery = "select count(distinct badge) from Badge badge")
    Page<Badge> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct badge from Badge badge left join fetch badge.dimensions")
    List<Badge> findAllWithEagerRelationships();

    @Query("select badge from Badge badge left join fetch badge.dimensions where badge.id =:id")
    Optional<Badge> findOneWithEagerRelationships(@Param("id") Long id);

    List<Badge> findAllByDimensionsIsNull();

    Page<Badge> findByIdIn(List<Long> badgeIds, Pageable pageable);

}
