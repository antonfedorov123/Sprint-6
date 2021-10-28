package ru.sber.adressbook.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class ApiController @Autowired constructor(val bookService: BookService) {

    @PostMapping("/add")
    fun addAddress(@RequestBody book: DataBook): ResponseEntity<*> {
        return ResponseEntity(bookService.addBook(book), HttpStatus.CREATED)
    }

    @GetMapping("/list")
    fun getBooks(@RequestParam(required = false) allParams: Map<String, String>): ResponseEntity<ConcurrentHashMap<Int, DataBook>> {
        val ads = bookService.getListBooks()
        return ResponseEntity(ads, HttpStatus.OK)
    }

    @GetMapping("/{id}/view")
    fun getBook(@PathVariable("id") id: Int): ResponseEntity<DataBook> {
        val book = bookService.getBook(id)
        return ResponseEntity(book, HttpStatus.OK)
    }

    @PutMapping("/{id}/edit")
    fun updateBook(@PathVariable("id") id: Int, @RequestBody book: DataBook): ResponseEntity<*> {
        return ResponseEntity(bookService.updateBook(index = id, dataBook = book), HttpStatus.OK)
    }

    @DeleteMapping("/{id}/delete")
    fun removeBook(@PathVariable("id") id: Int): ResponseEntity<*> {
        return ResponseEntity(bookService.removeBook(id), HttpStatus.OK)
    }
}