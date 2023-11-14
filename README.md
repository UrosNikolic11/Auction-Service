# Auction-Service

## Project description

### Backend service for bidding on active auctions.
There are 3 types of entities: FootballManagers, Auctions and Players. FootballManagers and Auctions are in many-to-many relation.
Auctions and Players are in one-to-many relation because Players are unique (there can't be 2 or more auctions with the same player at the same time).

Registered managers can only bid on active Auctions. Because there are lot of users a pessimistic locking strategy has been implemented in order to prevent possible problems while updating Auctions.

Two new Threads have been implemented: 

one for creating new Auctions 

one for finishing Auctions.

The idea for refreshing data is to use WebSocket. It seems like better solution for big number of users then refreshing page every second and making calls to the database.
Because this is only a backend solution of the problem an implementation of WebSocket logic was used for sending refresh messages and also there is no frontend that can subscribe to it. 
(Project specification wasn't very clear what type of solution should we use knowing that we will submit only backend solution).

## Used technologies

Database - Postgresql

Build tool - Maven

Framework - Spring Boot

Programming language - Java 17
