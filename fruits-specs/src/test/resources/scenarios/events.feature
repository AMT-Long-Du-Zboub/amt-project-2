Feature: Basic operations on registrations

  Background:
    Given there is a Fruits server

  Scenario: create an event
    Given I have an event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 202 status code

