package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
    ApplicationEntity findByName(String name);
}
