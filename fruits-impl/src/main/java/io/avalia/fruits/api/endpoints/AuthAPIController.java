package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.AuthApi;
import io.avalia.fruits.api.model.Credentials;
import io.avalia.fruits.api.model.Token;
import io.avalia.fruits.entities.RegistrationEntity;
import io.avalia.fruits.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AuthAPIController implements AuthApi {

    @Autowired
    RegistrationRepository registrationRepository;

    public ResponseEntity authenticateApplicationAndGetToken(@RequestBody Credentials credentials) {
        String appName = credentials.getApplicationName();
        String password = credentials.getPassword();
        RegistrationEntity targetApp = registrationRepository.findByApplicationName(appName);
        if(targetApp != null) {
            Token token = new Token();
            token.setApplicationName(targetApp.getApplicationName());
            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
