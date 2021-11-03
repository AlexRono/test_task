# Sender-Receiver project

Stack:
* Spring Boot
* Kotlin
* Maven
* Docker

It's possible to run both via IDE and through docker-compose.
In the former case you'll to set dev profile for the receiver 
module (-Dspring.profiles.active=dev), in the latter you simply call 
"docker-compose up --build".

Applications basically just send each other messages and 
if there's a problem on the sender side, receiver will try 
to reconnect.