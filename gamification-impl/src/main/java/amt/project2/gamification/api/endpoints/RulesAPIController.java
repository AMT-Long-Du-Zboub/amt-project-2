package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.api.RulesApi;
import amt.project2.gamification.api.dto.Rule;
import amt.project2.gamification.api.dto.RuleSummary;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.RuleEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.repositories.RuleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RulesAPIController implements RulesApi {
    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    HttpServletRequest req;

    public ResponseEntity<List<RuleSummary>> getRules(){
        List<RuleSummary> rules = new ArrayList<>();

        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        for (RuleEntity ruleEntity : ruleRepository.findByApplicationEntityName(targetApp.getName())) {
            rules.add(toRule(ruleEntity));
        }
        return ResponseEntity.ok(rules);
    }

    public ResponseEntity addRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule){
        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");
        RuleEntity newRuleEntity = toRuleEntity(targetApp, rule);

        try {
            ruleRepository.save(newRuleEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    private RuleSummary toRule(RuleEntity entity) {
        RuleSummary rule = new RuleSummary();
        rule.setType(entity.getType());
        rule.setAwardBadge(entity.getAwardBadge());
        rule.setAwardPoint(entity.getAwardPoint());
        return rule;
    }

    private RuleEntity toRuleEntity(ApplicationEntity targetApp, Rule rule) {
        RuleEntity entity = new RuleEntity();

        entity.setType(rule.getType());
        entity.setAwardBadge(rule.getAwardBadge());
        entity.setAwardPoint(rule.getAwardPoint());
        entity.setApplicationEntity(targetApp);

        return entity;
    }
}
