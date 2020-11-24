package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.LadderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface LadderRepository extends CrudRepository<LadderEntity, Long> {
    Collection<LadderEntity> findByApplicationEntityName(String ApplicationEntityName);
    Optional<LadderEntity> findByApplicationEntityNameAndLevel(String targetApplication, int level);
}
