Feature: Basic operation on users

  Background:
    Given there is a Gamification server

    Scenario: get a user when there is none does not work
      Given I have a registration payload
      And I POST the registration payload to the /registrations endpoint
      And I have a correct authentication payload
      And I POST the authentication payload to the /auth endpoint
      When I send a GET to the /users endpoint
      Then I receive a 404 status code for users


# TODO create a user for 200
    Scenario: get the top10 of users
      Given I have a registration payload
      And I POST the registration payload to the /registrations endpoint
      And I have a correct authentication payload
      And I POST the authentication payload to the /auth endpoint
      When I send a GET to the /top10bypoint endpoint
      Then I receive a 200 status code for users


