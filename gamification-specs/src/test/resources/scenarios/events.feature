Feature: Basic operations on events

  Background:
    Given there is a Gamification server
  Scenario: report an event is successful
    Given I have a registration payload
    And I POST the registration payload to the /registrations endpoint
    And I have a correct authentication payload
    And I POST the authentication payload to the /auth endpoint
    And I have a ladder payload
    And I POST the ladder payload to the /ladder endpoint
    And I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 202 status code for events
