package de.otto.dojo.repository;

import de.otto.dojo.domain.Dimension;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Dimension entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Long>, JpaSpecificationExecutor<Dimension> {

}
