package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Image;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {

    Optional<Image> findByName(String name);
}
