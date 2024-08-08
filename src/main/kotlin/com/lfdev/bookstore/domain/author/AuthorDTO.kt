package com.lfdev.bookstore.domain.author

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class AuthorDTO (
    val id: Long?,
    val name: String,
    val age: Int,
    val description: String,
    val image: String
)