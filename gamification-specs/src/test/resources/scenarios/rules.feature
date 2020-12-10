Feature: Basic operations on rules

  Background:
    Given there is a Gamification server
    And I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    And I POST the authentication payload to the /auth endpoint

  Scenario: register a new rule in an application works
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code for rules


  Scenario: get the list of rules
    Given I have a rule payload
    And I POST the rule payload to the /rules endpoint
    When I send a GET to the /rules endpoint
    Then I receive a 200 status code for rules

