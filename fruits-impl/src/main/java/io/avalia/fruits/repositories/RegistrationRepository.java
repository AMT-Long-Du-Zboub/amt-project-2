package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.RegistrationEntity;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {
    RegistrationEntity findByApplicationName(String name);
}
