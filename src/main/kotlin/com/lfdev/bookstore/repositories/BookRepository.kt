package com.lfdev.bookstore.repositories

import com.lfdev.bookstore.domain.book.entities.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<BookEntity, String> {
}