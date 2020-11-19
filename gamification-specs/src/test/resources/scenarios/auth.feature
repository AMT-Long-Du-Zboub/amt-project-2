Feature: Basic operations on authentication

  Background:
    Given there is a Gamification server

  Scenario: authenticate an application
    Given I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    When I POST the authentication payload to the /auth endpoint
    Then I receive a 200 status code

  Scenario: authenticate an application with wrong credentials does not work
    Given I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have an incorrect authentication payload
    When I POST the authentication payload to the /auth endpoint
    Then I receive a 401 status code
