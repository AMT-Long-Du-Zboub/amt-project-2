package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.RegistrationsApi;
import io.avalia.fruits.api.model.Registration;
import io.avalia.fruits.api.model.RegistrationSummary;
import io.avalia.fruits.entities.RegistrationEntity;
import io.avalia.fruits.repositories.RegistrationRepository;
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


@Controller
public class RegistrationsAPIController implements RegistrationsApi {
    @Autowired
    RegistrationRepository registrationRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addApplication(@ApiParam(value = "", required = true) @Valid @RequestBody Registration registration) {
        RegistrationEntity newRegistrationEntity = toRegistrationEntity(registration);
        try {
            registrationRepository.save(newRegistrationEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
    
    public ResponseEntity<List<RegistrationSummary>> getApplications() {
        List<RegistrationSummary> registrations = new ArrayList<>();
        for (RegistrationEntity registrationEntity : registrationRepository.findAll()) {
            registrations.add(toRegistration(registrationEntity));
        }
        return ResponseEntity.ok(registrations);
    }

    private RegistrationEntity toRegistrationEntity(Registration registration) {
        RegistrationEntity entity = new RegistrationEntity();
        entity.setApplicationName(registration.getApplicationName());
        entity.setPassword(registration.getPassword());
        return entity;
    }

    private RegistrationSummary toRegistration(RegistrationEntity entity) {
        RegistrationSummary registration = new RegistrationSummary();
        registration.applicationName(entity.getApplicationName());
        return registration;
    }
}
