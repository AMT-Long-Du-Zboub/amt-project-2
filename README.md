
# Gamification API REST
Welcome to this Gamification API REST. We will explain how use this API in three point.
1. How build and run the API
2. How access to the database
3. How use the API


# Build and run

# Database link and access

# API REST Documentation

## How to use
When everything is launched you can use the API. Here we will provide some more information to use it.

First you need to register your application and obtain your ``API KEY``, for this use the endpoint ``/registrations``. 
Beware to save it somewere because most of the action need this api-key, only authentification and registration don't need it. When you will make a request don't forget to add in the header the api-key. If you lost it you can use the endpoint ``/auth`` to re-get the ``API-KEY``.
Each ``API-KEY `` is unique and allows to identify the application.

After this you can create your ladder and badge for your application. Beware the ladders start at the level 0, you need to create this one. You can create your rule with their rewards, badge and/or point, and a type for the event can reconize witch rule he must use.

## Controller and endpoint
### [auth-api-controller](http://localhost:8080/swagger-ui/#/auth-api-controller)
Endpoint : ``/auth``
Request who can be performed :
``POST`` : Send the name of the application at the endpoint to obtain the linked api-key.

### [badges-api-controller](http://localhost:8080/swagger-ui/#/badges-api-controller)
Endpoint : ``/badges``

Request who can be performed :

``GET`` : Obtain the badge list recorded to the application

``POST`` : Add a new badge in the list for the application

### [events-api-controller](http://localhost:8080/swagger-ui/#/events-api-controller)
Endpoint : ``/events/``
Request who can be performed :
``POST`` : Send an event to the API-REST who will deal with it and apply the right rule

### [ladders-api-controller](http://localhost:8080/swagger-ui/#/ladders-api-controller)
Endpoint : ``/ladders``
Request who can be performed :
``GET`` : To obtain the list of the ladder of the application
``POST`` : Add a new ladder of the application

### [registrations-api-controller](http://localhost:8080/swagger-ui/#/registrations-api-controller)
Endpoint : ``/registrations``
Request who can be performed :
``GET`` : List of the application registered in the Gamification API
``POST`` : Add a new application in the Gamification API and return the linked API-KEY

### [rules-api-controller](http://localhost:8080/swagger-ui/#/rules-api-controller)
Endpoint : ``/rules``
Request who can be performed :
``GET`` : To Obtain the list of the rules created by an application
``POST`` : Create a new rule for the application

### [users-api-controller](http://localhost:8080/swagger-ui/#/users-api-controller)
Endpoint : ``/users/{id}``
Request who can be performed :
``GET`` : Obtain the details of an user

Endpoint : ``/users/{id}/awardedBadgeHistory``
Request who can be performed :
``GET`` : Obtain the badge history of an user 

Endpoint : ``/users/{id}/awardedPointHistory``
Request who can be performed :
``GET`` : Obtain the point history of an user

Endpoint : ``/users/top10bypoint``
Request who can be performed :
``GET`` : Obtain the ten player who has the most points


