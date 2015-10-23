Setting up a local microservices development environment with Lattice.

This week I started working on the Spring Cloud Services team. 

Taking POC app and migrating it to SCS.

Deploying locally:

1. Install Lattice
2. Run Rabbit MQ
3. Build droplets for configServer, serviceDiscovery and circuitBreakerDashboard


2
. Get config server, eureka server and hystrix running on Lattice

SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/mikegehard/user-management-config-repo

Using a config server and testing changes locally:

From Spencer, for certain tweaks you could post the change directly to `/env`

or you can use an application.yml file for local development and then use the configuration server to override configuration settings in the cloud environment.


Using Lattice Droplets:

./ltc build-droplet userManagementConfigServer https://github.com/cloudfoundry/java-buildpack --path=../spring/userManagementEvolution/platform/configServer/build/libs/config-server-0.0.1-SNAPSHOT.jar --env "SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/mikegehard/user-management-config-repo"

./ltc launch-droplet userManagementConfigServer userManagementConfigServer --ports=8888

http://usermanagementconfigserver.192.168.11.11.xip.io/billing/default

./ltc build-droplet userManagementServiceDiscovery java --path=../spring/userManagementEvolution/platform/serviceDiscovery/build/libs/service-discovery-0.0.1-SNAPSHOT.jar

./ltc launch-droplet userManagementServiceDiscovery userManagementServiceDiscovery --ports=8761

http://usermanagementservicediscovery.192.168.11.11.xip.io/

./ltc build-droplet userManagementCircuitBreakerDashboard java --path=../spring/userManagementEvolution/platform/circuitBreakerDashboard/build/libs/hystrix-dashboard-0.0.1-SNAPSHOT.jar

./ltc launch-droplet userManagementCircuitBreakerDashboard userManagementCircuitBreakerDashboard --ports=7979

http://usermanagementcircuitbreakerdashboard.192.168.11.11.xip.io/hystrix

./ltc build-droplet userManagementEmail java --path=../spring/userManagementEvolution/applications/email/build/libs/email-0.0.1-SNAPSHOT.jar

./ltc launch-droplet userManagementEmail userManagementEmail

http://usermanagementcircuitbreakerdashboard.192.168.11.11.xip.io/hystrix