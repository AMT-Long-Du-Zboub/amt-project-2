package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.ApiException;
import amt.project2.gamification.ApiResponse;
import amt.project2.gamification.api.DefaultApi;
import amt.project2.gamification.api.dto.Rule;
import amt.project2.gamification.api.spec.helpers.Environment;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RulesSteps {
    private Environment environment;
    private DefaultApi api;

    Rule rule;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private String lastReceivedLocationHeader;

    public  RulesSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("I have a rule payload")
    public void iHaveARulePayload() {
        rule = new Rule()
                .type(UUID.randomUUID().toString())
                .awardPoint(0)
                .awardBadge(UUID.randomUUID().toString());
    }

    @When("I POST the rule payload to the \\/rules endpoint")
    public void iPOSTTheRulePayloadToTheRulesEndpoint() {
        try {
            lastApiResponse = api.addRuleWithHttpInfo(rule);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I send a GET to the \\/rules endpoint")
    public void iSendAGETToTheRulesEndpoint() {
        try {
            lastApiResponse = api.getRulesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code for rules")
    public void i_receive_a_status_code_for_rules(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }

}
