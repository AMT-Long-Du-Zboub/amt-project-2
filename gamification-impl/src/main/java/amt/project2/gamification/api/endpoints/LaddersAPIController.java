package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.api.LaddersApi;
import amt.project2.gamification.api.dto.Ladder;
import amt.project2.gamification.api.dto.LadderSummary;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.LadderEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.repositories.LadderRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LaddersAPIController implements LaddersApi {

    @Autowired
    LadderRepository ladderRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    public ResponseEntity<List<LadderSummary>> getLadders(@RequestHeader(value = "x-gamification-token") String xGamificationToken){
        List<LadderSummary> ladders = new ArrayList<>();

        ApplicationEntity targetApp = applicationRepository.findByApiKey(xGamificationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        for (LadderEntity ladderEntity : ladderRepository.findByApplicationEntityName(targetApp.getName())) {
            ladders.add(toLadder(ladderEntity));
        }
        return ResponseEntity.ok(ladders);
    }

    public ResponseEntity addLadder(@RequestHeader(value = "x-gamification-token") String xGamificationToken, @ApiParam(value = "", required = true) @Valid @RequestBody Ladder ladder){
        ApplicationEntity targetApp = applicationRepository.findByApiKey(xGamificationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        LadderEntity newLadderEntity = toLadderEntity(targetApp, ladder);

        try {
            if (ladderRepository.findByApplicationEntityNameAndLevel(targetApp.getName(), newLadderEntity.getLevel()).isEmpty()) {
                ladderRepository.save(newLadderEntity);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    private LadderSummary toLadder(LadderEntity entity) {
        LadderSummary ladder = new LadderSummary();
        ladder.setTitle(entity.getTitle());
        ladder.setLevel(entity.getLevel());
        ladder.setNbrPoint(entity.getNbrPoint());
        return ladder;
    }

    private LadderEntity toLadderEntity(ApplicationEntity targetApp, Ladder ladder) {
        LadderEntity entity = new LadderEntity();

        entity.setLevel(ladder.getLevel());
        entity.setTitle(ladder.getTitle());
        entity.setNbrPoint(ladder.getNbrPoint());
        entity.setApplicationEntity(targetApp);


        return entity;
    }
}
