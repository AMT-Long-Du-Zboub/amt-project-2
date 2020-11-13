package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.EventsApi;
import io.avalia.fruits.api.model.Event;
import io.avalia.fruits.entities.RegistrationEntity;
import io.avalia.fruits.repositories.RegistrationRepository;
import io.avalia.fruits.service.EventProcessor;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;


@Controller
public class EventsAPIController implements EventsApi {

    @Autowired
    EventProcessor eventProcessor;

    @Autowired
    RegistrationRepository registrationRepository;

    @Override
    public ResponseEntity reportEvent(@RequestHeader(value = "x-gamification-token") String xGamificationToken, @ApiParam(value = "", required = true) @Valid @RequestBody Event event) {
        String targetApplicationName = xGamificationToken;
        String targetEndUserId = event.getUserId();
        RegistrationEntity targetApp = registrationRepository.findByApplicationName(targetApplicationName);
        if(targetApp == null || targetEndUserId == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        eventProcessor.processEvent(targetApp,event);
        return ResponseEntity.accepted().build();
    }
}
