package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.api.spec.helpers.Environment;
import io.avalia.fruits.ApiException;
import io.avalia.fruits.ApiResponse;
import io.avalia.fruits.api.DefaultApi;
import io.avalia.fruits.api.dto.Event;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventsSteps {

    private Environment environment;
    private DefaultApi api;

    Event event;

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
        event = new io.avalia.fruits.api.dto.Event()
                .type("application")
                .userId("45247")
                .timestamp(OffsetDateTime.now())
                .properties(null);
    }

    @When("I POST the event payload to the \\/events endpoint")
    public void i_post_the_registration_payload_to_the_registrations_endpoint() {
        try {
            lastApiResponse = api.reportEventWithHttpInfo("x-gamification-token", event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
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