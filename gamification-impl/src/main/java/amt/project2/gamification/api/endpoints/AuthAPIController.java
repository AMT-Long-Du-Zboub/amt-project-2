package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.api.AuthApi;
import amt.project2.gamification.api.dto.Credentials;
import amt.project2.gamification.api.dto.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;


@Controller
public class AuthAPIController implements AuthApi {

    @Autowired
    ApplicationRepository applicationRepository;

    public ResponseEntity authenticateApplicationAndGetToken(@RequestBody Credentials credentials) {
        String appName = credentials.getApplicationName();
        String password = credentials.getPassword();

        ApplicationEntity targetApp = applicationRepository.findByName(appName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(targetApp != null) {
            Token token = new Token();
            token.setApiKey(targetApp.getApiKey());
            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
