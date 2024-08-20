package com.lfdev.bookstore.domain.book.dto

import com.lfdev.bookstore.domain.author.dto.AuthorDTO

data class BookDTO(
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author: AuthorDTO
)