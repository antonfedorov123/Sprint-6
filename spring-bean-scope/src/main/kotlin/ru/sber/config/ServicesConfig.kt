package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {

    @Bean
    @Scope(scopeName = "singleton")
    fun singletonService(): SingletonService {
        return SingletonService()
    }

    @Bean
    @Scope(scopeName = "prototype")
    fun prototypeService(): PrototypeService {
        return PrototypeService()
    }

    class SingletonService
    class PrototypeService

}