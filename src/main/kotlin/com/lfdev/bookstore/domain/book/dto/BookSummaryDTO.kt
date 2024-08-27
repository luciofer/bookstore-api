package com.lfdev.bookstore.domain.book.dto

import com.lfdev.bookstore.domain.author.dto.AuthorSummaryDTO

data class BookSummaryDTO(
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author: AuthorSummaryDTO
)