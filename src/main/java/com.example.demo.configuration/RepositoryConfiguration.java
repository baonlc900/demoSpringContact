package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.springframework.boot.orm.jpa.EntityScan;

@Configuration
@EnableAutoConfiguration
//@EntityScan(basePackages = {"com.example.demo.domain"})

@EnableTransactionManagement
public class RepositoryConfiguration {

}
