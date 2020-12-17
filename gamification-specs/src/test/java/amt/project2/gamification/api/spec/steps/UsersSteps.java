
package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.api.spec.helpers.Environment;
import amt.project2.gamification.ApiException;
import amt.project2.gamification.ApiResponse;
import amt.project2.gamification.api.DefaultApi;
import amt.project2.gamification.api.dto.Registration;
import amt.project2.gamification.api.dto.Credentials;
import amt.project2.gamification.api.dto.Token;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UsersSteps {

    private Environment environment;
    private DefaultApi api;

    Credentials credentials;
    Token token;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Registration lastReceivedRegistration;

    public UsersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Then("I receive a {int} status code for users")
    public void i_receive_a_status_code_for_users(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @When("I send a GET to the /users endpoint")
    public void i_send_a_get_to_the_users_endpoint() {
        try {
            credentials = new Credentials()
                    .applicationName(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                    .password("pa$$w0rd");
            token = api.authenticateApplicationAndGetToken(credentials);
            lastApiResponse = api.getUserIdWithHttpInfo(UUID.randomUUID().toString());
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
