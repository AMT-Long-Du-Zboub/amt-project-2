Feature: Basic operations on registrations

  Background:
    Given there is a Gamification server

  Scenario: register a new rule in an application works
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 200 status code for rules

  Scenario: get the list of rules
    When I send a GET to the /rules endpoint
    Then I receive a 200 status code for rules

