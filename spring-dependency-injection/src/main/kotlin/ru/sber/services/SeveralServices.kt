package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

interface ServiceInterface

@Component
@Qualifier("serviceInterface")
@Order(1)
class FirstServiceImpl : ServiceInterface {
    override fun toString(): String {
        return "FirstServiceImpl"
    }
}

@Component
@Qualifier("serviceInterface")
@Order(2)
class SecondServiceImpl : ServiceInterface {
    override fun toString(): String {
        return "SecondServiceImpl"
    }
}

@Component
class SeveralBeanInjectionService {
    @Autowired
    @Qualifier("serviceInterface")
    lateinit var services: List<ServiceInterface>

    override fun toString(): String {
        return "SeveralBeanInjectionService(services=$services)"
    }
}

@Configuration
@ComponentScan("ru.sber.services")
class SeveralServicesConfig {
    @Bean
    fun services(): ArrayList<ServiceInterface> {
        return arrayListOf(FirstServiceImpl())
    }
}