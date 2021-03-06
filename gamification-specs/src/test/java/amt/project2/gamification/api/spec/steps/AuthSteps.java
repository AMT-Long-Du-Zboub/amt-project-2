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
import io.cucumber.java.en.And;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthSteps {

    private Environment environment;
    private DefaultApi api;

    Credentials credentials;

    private ApiResponse<Token> lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    public static Token apiKey;

    private String lastReceivedLocationHeader;

    public AuthSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @And("I have a correct authentication payload")
    public void i_have_a_correct_authentication_payload() {
        credentials = new Credentials()
                .applicationName(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                .password("pa$$w0rd");
    }

    @And("I have an incorrect authentication payload")
    public void i_have_an_incorrect_authentication_payload() {
        credentials = new Credentials()
                .applicationName("notCorrectAtAll")
                .password("pa$$w0rd");
    }
    @And("I have an authentication payload with wrong password")
    public void i_have_an_authentication_payload_with_wrong_password() {
        credentials = new Credentials()
                .applicationName(RegistrationsSteps.lastReceivedRegistration.getApplicationName())
                .password("wrong");
    }
    
    @When("I POST the authentication payload to the \\/auth endpoint")
    public void i_post_the_authentication_payload_to_the_auth_endpoint() {
        try {
            lastApiResponse = api.authenticateApplicationAndGetTokenWithHttpInfo(credentials);
            apiKey = (Token) lastApiResponse.getData();
            api.getApiClient().addDefaultHeader("X-API-KEY", apiKey.getApiKey());
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }
    @Then("I receive a {int} status code for auth")
    public void i_receive_a_status_code_for_auth(int expectedStatusCode) throws Throwable {
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