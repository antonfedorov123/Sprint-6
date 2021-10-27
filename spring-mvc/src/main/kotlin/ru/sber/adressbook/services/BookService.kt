package ru.sber.adressbook.services

import org.springframework.stereotype.Service
import ru.sber.adressbook.vo.DataBook
import java.util.concurrent.ConcurrentHashMap

@Service
class BookService() {
    private val booksMap = ConcurrentHashMap<Int, DataBook>()

    fun addBook(dataBook: DataBook) {
        booksMap[booksMap.size] = dataBook
    }

    fun updateBook(dataBook: DataBook, index: Int): DataBook? {
        return booksMap.put(index, dataBook)
    }

    fun getListBooks(): ConcurrentHashMap<Int, DataBook> {
        return booksMap
    }

    fun removeBook(index: Int){
        booksMap.remove(index)
    }

    fun getBook(index: Int): DataBook {
        return booksMap[index]!!
    }

}