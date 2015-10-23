package com.example.ums;


import io.pivotal.spring.cloud.config.java.CloudConnectorsConfig;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.hystrix.amqp.HystrixConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfig extends CloudConnectorsConfig {

    /*
        We can't rely on the Spring Boot autoconfiguration here provided by the
        Spring Cloud Connectors (??) because of two Rabbit configurations.
     */
    @Bean
    @HystrixConnectionFactory
    public ConnectionFactory hystrixConnectionFactory() {
        return connectionFactory().hystrixConnectionFactory();
    }

    @Bean
    @Primary
    public ConnectionFactory rabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory();
    }

    // This has to be here to provide the proper Eureka client configuration
    @Bean
    public EurekaClientConfigBean eurekaClientConfig() {
        System.out.println("+++++++++++++++++ Using overriddent eureka client config +++++++++++++++++++++");
        return connectionFactory().eurekaClientConfig();
    }
}
