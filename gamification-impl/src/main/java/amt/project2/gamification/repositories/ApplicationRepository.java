package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
    Optional<ApplicationEntity> findByName(String name);
    Optional<ApplicationEntity> findByApiKey(String apiKey);
}
