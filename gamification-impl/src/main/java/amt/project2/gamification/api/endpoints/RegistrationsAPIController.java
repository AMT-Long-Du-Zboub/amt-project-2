package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.api.RegistrationsApi;
import amt.project2.gamification.api.dto.Registration;
import amt.project2.gamification.api.dto.RegistrationSummary;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class RegistrationsAPIController implements RegistrationsApi {
    @Autowired
    ApplicationRepository applicationRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addApplication(@ApiParam(value = "", required = true) @Valid @RequestBody Registration registration) {
        ApplicationEntity newApplicationEntity = toRegistrationEntity(registration);
        try {
            applicationRepository.save(newApplicationEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
    
    public ResponseEntity<List<RegistrationSummary>> getApplications() {
        List<RegistrationSummary> registrations = new ArrayList<>();
        for (ApplicationEntity applicationEntity : applicationRepository.findAll()) {
            registrations.add(toRegistration(applicationEntity));
        }
        return ResponseEntity.ok(registrations);
    }

    private ApplicationEntity toRegistrationEntity(Registration registration) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setName(registration.getApplicationName());
        entity.setApiKey(UUID.randomUUID().toString());
        entity.setPassword(registration.getPassword());
        return entity;
    }

    private RegistrationSummary toRegistration(ApplicationEntity entity) {
        RegistrationSummary registration = new RegistrationSummary();
        registration.applicationName(entity.getName());
        return registration;
    }
}
