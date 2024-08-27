package com.lfdev.bookstore

import com.lfdev.bookstore.domain.author.AuthorSummary
import com.lfdev.bookstore.domain.author.AuthorUpdateRequest
import com.lfdev.bookstore.domain.author.dto.AuthorDTO
import com.lfdev.bookstore.domain.author.dto.AuthorSummaryDTO
import com.lfdev.bookstore.domain.author.dto.AuthorUpdateRequestDTO
import com.lfdev.bookstore.domain.author.entities.AuthorEntity
import com.lfdev.bookstore.domain.book.BookSummary
import com.lfdev.bookstore.domain.book.dto.BookSummaryDTO
import com.lfdev.bookstore.domain.book.entities.BookEntity
import com.lfdev.bookstore.exceptions.InvalidAuthorException

// Similar to an arrow function, return in the same line because it's only a dto.
fun AuthorEntity.toAuthorDTO() = AuthorDTO(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorDTO.toAuthorEntity() = AuthorEntity(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun AuthorUpdateRequestDTO.toAuthorUpdateRequest() = AuthorUpdateRequest(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image
)

fun BookSummary.toBookEntity(author: AuthorEntity) = BookEntity(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = author

)

fun AuthorSummaryDTO.toAuthorSummary() = AuthorSummary(
    id = this.id,
    name = this.name,
    image = this.image
)

fun AuthorEntity.toAuthorSummaryDTO(): AuthorSummaryDTO {
    val authorId = this.id ?: throw InvalidAuthorException()
    return AuthorSummaryDTO(
        id = authorId,
        name = this.name,
        image = this.image
    )
}

fun BookSummaryDTO.toBookSummary() = BookSummary(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = this.author.toAuthorSummary()
)

fun BookEntity.toBookSummaryDTO() = BookSummaryDTO(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = this.author.toAuthorSummaryDTO()

)

