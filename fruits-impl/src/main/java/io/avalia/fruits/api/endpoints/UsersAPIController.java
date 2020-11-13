package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.UsersApi;
import io.avalia.fruits.api.model.User;
import io.avalia.fruits.entities.UserEntity;
import io.avalia.fruits.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UsersAPIController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> getUserId(@RequestHeader(value = "x-gamification-token") String xGamificationToken, @PathVariable("id") String userId) {
        String targetAppName = xGamificationToken;
        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        UserEntity userEntity = userRepository.findByRegistrationEntityApplicationNameAndIdInGamifiedApplication(
                targetAppName, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(userEntity));
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setUserId(entity.getIdInGamifiedApplication());
        user.setNumberOfEvents(entity.getNumberOfEvents());
        return user;
    }
}
