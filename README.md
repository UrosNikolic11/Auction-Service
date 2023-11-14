# Auction-Service

## Project description

### Backend service for bidding on active auctions.

I have 3 types of entities: FootballManagers, Auctions and Players.
FootballManagers and Auctions are in many-to-many relation. Auctions and Players are in one-to-many relation because Players are unique (there can't be 2 or more auctions with same player).

Registerd managers can only bid on active Auctions. Because there are lot of users I have implemented pessimistic locking strategy to try to prevent possible problems while updating Auctions.

I have also implemented 2 new Threads one for creating new Auctions and one for finishing Auctions.

My idea for refreshing data is to use WebSocket. It seems like better solution for big number of users then refreshing page every second and calling database.
Because this is only Backend solution of the problem I have implemented WebSocket logic for sending refresh messages, there is no frontend that can subscribe. 
(Project specification wasn't very clear what type of solution should we use knowing that we will submit only backend solution).

## Used technologies

Database - Postgresql

Build tool - Maven

Framework - Spring Boot

Programming language - Java 17
