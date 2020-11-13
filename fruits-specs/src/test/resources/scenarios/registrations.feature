Feature: Basic operations on registrations

  Background:
    Given there is a Fruits server

  Scenario: register an application
    Given I have a registration payload
    When I POST the registration payload to the /registrations endpoint
    Then I receive a 201 status code

  Scenario: get the list of registered applications
    When I send a GET to the /registrations endpoint
    Then I receive a 200 status code