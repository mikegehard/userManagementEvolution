# Migration Evolution

## Background
This repository demonstrates a possible evolution for
a demonstration architecture. It assumes that the [bounded
contexts](http://martinfowler.com/bliki/BoundedContext.html) for the application
follow the boundaries outlined.

## Evolution tags

Tags are used to show the evolution of the application from one application
that leverages components to a distributed system of microservices.

```
git tag -ln

v1      One Application that leverages components
v2      Billing component promoted to a service
v3      Add Eureka service discovery to allow easy service communication
v4      Add Hystrix for service resiliency
v5      Add custom metrics for reporting on business metrics
v6      Add asynchronous processing with RabbitMQ
v7      Use UAA as OAuth2 provider
v8      Consumer driven PACT between JS client and UMS

```

See RUNNING.md for details on how to build/run the applications.
