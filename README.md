

# Gamification API REST

# 1 Implementation

# 2 Test

# 3 Functionality
All the endpoint of the API works and you can register, with the endpoint ``/registrations`` an application to obtain an ``API-KEY`` or recover it with the endpoint ``/auth``. You need this ``API-KEY`` in the header to perform the others action because it's with it that we can recongnize the registered app.

Endpoint : ``/registrations``

Request who can be performed :

``GET`` : List of the application registered in the Gamification API

``POST`` : Add a new application in the Gamification API and return the linked API-KEY

Endpoint : ``/auth``

Request who can be performed :

``POST`` : Send the name of the application at the endpoint to obtain the linked api-key.

You can create ladders, and badges with the endpoints ``/ladders`` and ``/badges``. Don't forget for the ladders you need to start at the level 0 and it's progressive, 0->1->2->3 etc...

Endpoint : ``/ladders``

Request who can be performed :

``GET`` : To obtain the list of the ladder of the application

``POST`` : Add a new ladder of the application

Endpoint : ``/badges``

Request who can be performed :

``GET`` : Obtain the badge list recorded to the application

``POST`` : Add a new badge in the list for the application

You can create rules via the endpoint ``/rules`` with their rewards, experience for the level or/and badges, who will be readed and executed by the endpoint ``/events/``. The user is automaticaly create when ``/events/`` is called if the user already exist he jumps this step.

Endpoint : ``/rules``

Request who can be performed :

``GET`` : To Obtain the list of the rules created by an application

``POST`` : Create a new rule for the application

Endpoint : ``/events/``

Request who can be performed :

``POST`` : Send an event to the API-REST who will deal with it and apply the right rule

The endpoint for get information of an user, his history of point etc... are functional and you can call it . The only method on this endpoints is ``GET``

Endpoint : ``/users/{id}``

Endpoint : ``/users/{id}/awardedBadgeHistory``

Endpoint : ``/users/{id}/awardedPointHistory``

Endpoint : ``/users/top10bypoint``

# 4 Build and run

## 4.1 Manual

For development purposes, you should go in `gamification-impl` and run `mvn spring-boot:run` which will make Swagger available at [http://localhost:8080](http://localhost:8080).

## 4.2 Automatic

For ease of use with [amt-project-1](https://github.com/AMT-Long-Du-Zboub/amt-project-1), you can use the `run_gamification.sh` script which will get the database IP address and make the gamification engine use it.

NOTE: make sure to have deployed the Docker stack beforehand (`./run.sh` in amt-project-1).

You can use the [Adminer page](http://localhost:8081) to access the database contents from a Web page.

# 5 Database link and access

Our engine directly talks to an **existing** database named `amt2`, so ensure this exists on the target database server beforehand.

Simply issue:

```
DROP DATABASE IF EXISTS amt2;
CREATE DATABASE amt2;
```

NOTE: the database hostname/IP should be set in the `MYSQL_HOST` environment variable. For instance:

```
export MYSQL_HOST=127.0.0.1
```
