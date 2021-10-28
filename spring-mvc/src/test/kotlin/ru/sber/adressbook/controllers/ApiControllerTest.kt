package ru.sber.adressbook.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class ApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun addAddress() {
    }

    @Test
    fun getBooks() {
    }

    @Test
    fun getBook() {
    }

    @Test
    fun updateBook() {
    }

    @Test
    fun removeBook() {
    }

    @Test
    fun getBookService() {
    }
}