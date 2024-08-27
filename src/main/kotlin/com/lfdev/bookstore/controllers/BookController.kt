package com.lfdev.bookstore.controllers

import com.lfdev.bookstore.domain.book.dto.BookSummaryDTO
import com.lfdev.bookstore.exceptions.InvalidAuthorException
import com.lfdev.bookstore.services.BookService
import com.lfdev.bookstore.toBookSummary
import com.lfdev.bookstore.toBookSummaryDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalStateException


@RestController
@RequestMapping("/v1/books")
class BookController(private val bookService: BookService) {

    @PutMapping("/{isbn}")
    fun createFullUpdateBook(@PathVariable isbn: String, @RequestBody book: BookSummaryDTO): ResponseEntity<BookSummaryDTO>{
        try {
            val (savedBook, isCreated) = bookService.createUpdate(isbn, book.toBookSummary())

            val responseCode = if(isCreated) HttpStatus.CREATED else HttpStatus.OK

            return ResponseEntity(savedBook.toBookSummaryDTO(), responseCode)
        } catch (ex: InvalidAuthorException) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        } catch (ex: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

}