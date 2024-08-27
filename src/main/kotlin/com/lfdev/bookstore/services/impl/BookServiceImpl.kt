package com.lfdev.bookstore.services.impl

import com.lfdev.bookstore.domain.book.BookSummary
import com.lfdev.bookstore.domain.book.entities.BookEntity
import com.lfdev.bookstore.repositories.AuthorRepository
import com.lfdev.bookstore.repositories.BookRepository
import com.lfdev.bookstore.services.BookService
import com.lfdev.bookstore.toBookEntity
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(private val bookRepository: BookRepository, private val authorRepository: AuthorRepository): BookService {

    @Transactional
    override fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean> {
        val normalizedBook = bookSummary.copy(isbn = isbn)
        val isExists = bookRepository.existsById(isbn)

        val author = authorRepository.findByIdOrNull(normalizedBook.author.id)
        checkNotNull(author)

        val savedBook = bookRepository.save(normalizedBook.toBookEntity(author))

        return Pair(savedBook, !isExists)
    }
}