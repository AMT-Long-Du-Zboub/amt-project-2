package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.ApiException;
import amt.project2.gamification.ApiResponse;
import amt.project2.gamification.api.DefaultApi;
import amt.project2.gamification.api.dto.Ladder;
import amt.project2.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LaddersSteps {

    private Environment environment;
    private DefaultApi api;

    private Ladder ladder;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;

    public LaddersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @And("I have a ladder payload")
    public void iHaveALadderPayload() {
        ladder = new Ladder()
                .level(0)
                .title("title")
                .nbrPoint(0);
    }

    @And("I POST the ladder payload to the /ladder endpoint")
    public void iPOSTTheLadderPayloadToTheLadderEndpoint() {
        try {
            lastApiResponse = api.addLadderWithHttpInfo(ladder);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I send a GET to the \\/ladder endpoint")
    public void i_send_a_get_to_the_ladder_endpoint() {
        try {
            lastApiResponse = api.getLaddersWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }


    @Then("I receive a {int} status code for ladders")
    public void i_receive_a_status_code_for_ladders(int expectedStatusCode) throws Throwable {
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
