# Billing service

## Background

This application is a POC billing service. It will act as a
gateway to the billing provider of choice.

It will present a consistent consumer API regardless of billing provider
used. This will allow the billing provider to changed without
the rest of the application being affected by this change via Spring
dependency injection.

## Service Metrics and Spring Boot Actuator

This service will demonstrate the use of [Spring Boot Actuator](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready)
custom [metrics](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-metrics).
In this case, the paymentCreated event is recorded in the service when the POST /reocurringPayment
endpoint is hit. This metric could be used as a key performance indicator for the success of the application
or as a way to reconcile payments the service thinks it created with the
payments that a 3rd party received. These metrics can be exported to a number of data stores such
as StatsD or OpenTSDB.

Metrics endpoint: [http://localhost:8081/metrics](localhost:8081/metrics).
