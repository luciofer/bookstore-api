package com.lfdev.bookstore.domain.book

import com.lfdev.bookstore.domain.author.AuthorSummary

data class BookSummary(
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author: AuthorSummary
)