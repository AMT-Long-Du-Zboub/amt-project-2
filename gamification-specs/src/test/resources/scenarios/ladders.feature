Feature: Basic operations on ladders

  Background:
    Given there is a Gamification server
    And I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    And I POST the authentication payload to the /auth endpoint

  Scenario: register a new level of ladders in an application
    Given I have a ladder payload
    When I POST the ladder payload to the /ladder endpoint
    Then I receive a 201 status code for ladders


  Scenario: get the list of all level on a ladder of point
    Given I have a ladder payload
    And I POST the ladder payload to the /ladder endpoint
    When I send a GET to the /ladder endpoint
    Then I receive a 200 status code for ladders

