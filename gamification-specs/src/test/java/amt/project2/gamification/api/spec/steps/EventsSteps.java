package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.api.spec.helpers.Environment;
import amt.project2.gamification.ApiException;
import amt.project2.gamification.ApiResponse;
import amt.project2.gamification.api.DefaultApi;
import amt.project2.gamification.api.dto.Event;
import amt.project2.gamification.api.dto.Credentials;
import amt.project2.gamification.api.dto.Token;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventsSteps {

    private Environment environment;
    private DefaultApi api;

    Event event;
    Credentials credentials;
    Token token;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;

    public EventsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("I have an event payload")
    public void i_have_an_event_payload() {
        event = new Event()
                .type(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                .userId("1")
                .timestamp(OffsetDateTime.now())
                .properties(null);
    }

    @When("I POST the event payload to the \\/events endpoint")
    public void i_post_the_registration_payload_to_the_registrations_endpoint() {
        try {
            credentials = new Credentials()
                    .applicationName(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                    .password("pa$$w0rd");
            token = api.authenticateApplicationAndGetToken(credentials);
            lastApiResponse = api.reportEventWithHttpInfo(token.getApiKey(), event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code for events")
    public void i_receive_a_status_code_for_events(int expectedStatusCode) throws Throwable {
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