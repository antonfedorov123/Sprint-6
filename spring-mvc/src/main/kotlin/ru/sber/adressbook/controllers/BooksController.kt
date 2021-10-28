package ru.sber.adressbook.controllers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook

@Controller
@RequestMapping("/")
class BooksController {

    constructor(bookService: BookService) {
        this.bookService = bookService
    }

    @Autowired
    lateinit var bookService: BookService

    @GetMapping("/app")
    fun home(model: Model): String {
        model.addAttribute("index", bookService.getLastIndex())
        return "home"
    }

    @GetMapping("/app/add")
    fun getAddBookForm(): String {
        return "addBook"
    }

    @PostMapping("/app/add")
    fun addBook(@ModelAttribute dataBook: DataBook, model: Model): String {
        val index = bookService.addBook(dataBook)
        model.addAttribute("res", "Контакт добавлен")
        return "redirect:/app/list"
    }

    @GetMapping("/app/{index}/delete")
    fun deleteBook(@PathVariable index: Int, model: Model): String {
        bookService.removeBook(index)
        model.addAttribute("res", "Контакт удален")
        return "redirect:/app/list"
    }

    @GetMapping("/app/{index}/view")
    fun getBook(@PathVariable index: Int, model: Model): String {
        val view = bookService.getBook(index)
        model.addAttribute("res", view)
        return "contactBook"
    }

    @GetMapping("/app/list")
    fun list(@RequestParam query: Map<String, String>, model: Model): String {
        val searchResult = bookService.getListBooks()

        model.addAttribute("res", searchResult)
        model.addAttribute("list", "Вот что нашлось:")
        return "allContact"
    }
    @GetMapping("/app/{index}/edit")
    fun geteditBookForm(@PathVariable index: Int, model: Model): String {
        val view = bookService.getBook(index)
        model.addAttribute("res", view)
        return "editBook"
    }

    @PostMapping("/app/{index}/edit")
    fun editBook(@PathVariable index: Int, @ModelAttribute dataBook: DataBook, model: Model):String {
        bookService.editBook(dataBook, index)
        model.addAttribute("res","Контакт изменен")
        return "redirect:/app/list"
    }

}