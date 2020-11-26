package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {
    Collection<RuleEntity> findByApplicationEntityName(String ApplicationEntityName);
    Optional<RuleEntity> findByApplicationEntityNameAndType(String targetApplication, String ruleType);
}
