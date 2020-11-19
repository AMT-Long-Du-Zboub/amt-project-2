Feature: Basic operations on authentication

  Background:
    Given there is a Gamification server

  Scenario: register a badge in an application
    Given I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    And I POST the authentication payload to the /auth endpoint
    And I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  Scenario: list badges of an application
    Given I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    And I POST the authentication payload to the /auth endpoint
    And I have a badge payload
    And I POST the badge payload to the /badges endpoint
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code

  Scenario: register a badge in a non existant application does not work
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint with wrong credentials
    Then I receive a 401 status code

  Scenario: list badges of a non registered application does not work
    Given I have a badge payload
    And I POST the badge payload to the /badges endpoint
    When I send a GET to the /badges endpoint with wrong credentials
    Then I receive a 404 status code