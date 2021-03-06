
package amt.project2.gamification.api.spec.steps;

import amt.project2.gamification.api.spec.helpers.Environment;
import amt.project2.gamification.ApiException;
import amt.project2.gamification.ApiResponse;
import amt.project2.gamification.api.DefaultApi;
import amt.project2.gamification.api.dto.Registration;
import amt.project2.gamification.api.dto.Credentials;
import amt.project2.gamification.api.dto.Token;
import amt.project2.gamification.api.dto.Badge;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BadgesSteps {

    private Environment environment;
    private DefaultApi api;

    Credentials credentials;
    Badge badge;
    Token token;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;

    public BadgesSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @When("I POST the badge payload to the /badges endpoint")
    public void i_post_the_badge_payload_to_the_badges_endpoint() {
       try {
            credentials = new Credentials()
                    .applicationName(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                    .password("pa$$w0rd");
            token = api.authenticateApplicationAndGetToken(credentials);
            lastApiResponse = api.addBadgeWithHttpInfo(badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I POST the badge payload to the \\/badges endpoint with wrong credentials")
    public void i_post_the_badge_payload_to_the_badges_with_wrong_credentials_endpoint() {
        try {
            lastApiResponse = api.addBadgeWithHttpInfo( badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code for badges")
    public void i_receive_a_status_code_for_badges(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @Given("I have a badge payload")
    public void i_have_a_badge_payload() {
        badge = new Badge()
                .name("badge")
                .description("C'est un badge");
    }

    @When("I send a GET to the /badges endpoint")
    public void i_send_a_get_to_the_badges_endpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I send a GET to the /badges endpoint with wrong credentials")
    public void i_send_a_get_to_the_badges_with_wrong_credentials_endpoint()  {
        try {
            lastApiResponse = api.addBadgeWithHttpInfo(badge);
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
