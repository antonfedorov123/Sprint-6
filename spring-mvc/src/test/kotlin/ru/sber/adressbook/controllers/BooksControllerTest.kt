package ru.sber.adressbook.controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class BooksControllerTest {

    private lateinit var mockMvc: MockMvc

    private lateinit var bookService: BookService

    private lateinit var booksController: BooksController

    @BeforeEach
    fun beforeEach() {
        bookService = BookService()
        booksController = BooksController(bookService)
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build()
    }

    @Test
    fun list() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("allContact"))
            .andExpect(MockMvcResultMatchers.model().attribute("res", bookService.getListBooks()))
    }

    @Test
    fun addBook() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("addBook"))
    }

    @Test
    fun deleteBook() {

        bookService.addBook(DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        ))

        val before = bookService.getListBooks().size

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

        val after = bookService.getListBooks().size

        assertEquals(before, after + 1)
    }

    @Test
    fun getBook() {

        bookService.addBook(DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("contactBook"))
    }

    @Test
    fun editBook() {

        bookService.addBook(DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("editBook"))

    }
}