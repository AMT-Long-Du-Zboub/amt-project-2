Feature: Basic operation on users

  Background:
    Given there is a Gamification server

    Scenario: get a user
      When I send a GET to the /users endpoint
      Then I receive a 200 status code

    Scenario: two users don't have the same id
      When I send a GET to the /users endpoint
      And I send a GET to the /users endpoint
      Then I don't get the same ids

