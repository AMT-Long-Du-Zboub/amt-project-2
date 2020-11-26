package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.service.EventProcessor;
import amt.project2.gamification.api.EventsApi;
import amt.project2.gamification.api.dto.Event;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class EventsAPIController implements EventsApi {

    @Autowired
    EventProcessor eventProcessor;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    HttpServletRequest req;

    @Override
    public ResponseEntity reportEvent(@ApiParam(value = "", required = true) @Valid @RequestBody Event event) {
        String targetEndUserId = event.getUserId();
        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");
        if(targetApp == null || targetEndUserId == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        eventProcessor.processEvent(targetApp, event);
        return ResponseEntity.accepted().build();
    }
}
