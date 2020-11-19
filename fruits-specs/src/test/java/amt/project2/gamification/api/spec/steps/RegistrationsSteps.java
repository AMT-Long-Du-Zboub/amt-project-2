package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.api.spec.helpers.Environment;
import io.avalia.fruits.ApiException;
import io.avalia.fruits.ApiResponse;
import io.avalia.fruits.api.DefaultApi;
import io.avalia.fruits.api.dto.Registration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegistrationsSteps {

    private Environment environment;
    private DefaultApi api;

    Registration registration;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Registration lastReceivedRegistration;

    public RegistrationsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("I have a registration payload")
    public void i_have_a_registration_payload() {
        registration = new io.avalia.fruits.api.dto.Registration()
                .applicationName("application")
                .password("pa$$w0rd");
    }

    @When("I POST the registration payload to the \\/registrations endpoint")
    public void i_post_the_registration_payload_to_the_registrations_endpoint() {
        try {
            lastApiResponse = api.addApplicationWithHttpInfo(registration);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I send a GET to the \\/registrations endpoint")
    public void i_send_a_get_to_the_registrations_endpoint() {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo();
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