package com.lfdev.bookstore.domain.book

import com.lfdev.bookstore.domain.author.AuthorDTO
import jakarta.persistence.*

data class BookDTO(
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author: AuthorDTO
)