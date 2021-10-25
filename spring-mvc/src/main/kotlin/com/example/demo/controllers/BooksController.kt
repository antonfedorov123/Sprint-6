package com.example.demo.controllers

import com.example.demo.services.BookService
import com.example.demo.vo.DataBook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class BooksController {

    @Autowired
    lateinit var bookService: BookService

    @GetMapping("/add")
    fun getNoteAddForm(): String {
        return "addBook"
    }

    @PostMapping("/add")
    fun addNote(@ModelAttribute dataBook: DataBook, model: Model): String {
        bookService.addBook(dataBook)
        model.addAttribute("res", "Контакт добавлен")
        return "response"
    }


    @GetMapping("/{index}/remove")
    fun deleteNote(@PathVariable index: Int, model: Model): String {
        bookService.removeBook(index)
        model.addAttribute("res", "Контакт удален")
        return "response"
    }

    @GetMapping("/{index}/get")
    fun getBook(@PathVariable index: Int, model: Model): String {
        val view = bookService.getBook(index)
        model.addAttribute("res", view)
        return "contactBook"
    }

    @GetMapping("/getAll")
    fun getAllContact(@RequestParam query: Map<String, String>, model: Model): String {
        val searchResult = bookService.getListBooks()

        model.addAttribute("res", searchResult)
        model.addAttribute("list", "Вот что нашлось:")
        return "allContact"
    }
    @GetMapping("/{index}/update")
    fun updateFormContact(@PathVariable index: Int): String{
        return "updateBook"
    }

    @PostMapping("/{index}/update")
    fun updateContact(@PathVariable index: Int, @ModelAttribute dataBook: DataBook, model: Model):String {
        bookService.updateBook(dataBook, index)
        model.addAttribute("res","Контакт изменен")
        return "response"
    }

}