package ru.sber.adressbook.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest {

    private lateinit var mockMvc: MockMvc

    private lateinit var bookService: BookService

    private lateinit var booksController: BooksController

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun init() {
        bookService = BookService()
        booksController = BooksController(bookService)
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun list() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("allContact"))
            .andExpect(MockMvcResultMatchers.model().attribute("res", bookService.getListBooks()))
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun addBook() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("addBook"))
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
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
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
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
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
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