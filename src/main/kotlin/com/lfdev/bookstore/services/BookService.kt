package com.lfdev.bookstore.services

import com.lfdev.bookstore.domain.book.BookSummary
import com.lfdev.bookstore.domain.book.entities.BookEntity

interface BookService {
    fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean>
}