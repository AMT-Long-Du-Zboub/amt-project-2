package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.LadderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface LadderRepository extends CrudRepository<LadderEntity, Long> {
    Collection<LadderEntity> findByApplicationEntityName(String ApplicationEntityName);
}
