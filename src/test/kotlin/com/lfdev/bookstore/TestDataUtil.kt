package com.lfdev.bookstore.com.lfdev.bookstore

import com.lfdev.bookstore.domain.author.AuthorUpdateRequest
import com.lfdev.bookstore.domain.author.dto.AuthorDTO
import com.lfdev.bookstore.domain.author.dto.AuthorSummaryDTO
import com.lfdev.bookstore.domain.author.dto.AuthorUpdateRequestDTO
import com.lfdev.bookstore.domain.author.entities.AuthorEntity
import com.lfdev.bookstore.domain.book.dto.BookSummaryDTO
import com.lfdev.bookstore.domain.book.entities.BookEntity


fun testAuthorDTO(id: Long? = null) = AuthorDTO(
        id = id,
        name = "John Doe",
        age = 28,
        description =  "Some description",
        image = "author-image.png"
)

fun testAuthorEntity(id: Long? = null) = AuthorEntity(
        id = id,
        name = "John Doe",
        age = 28,
        description =  "Some description",
        image = "author-image.png"
)

fun testAuthorEntityB(id: Long? = null) = AuthorEntity(
        id = id,
        name = "Peter",
        age = 43,
        description = "cop",
        image = "peter.png"
)

fun testAuthorUpdateRequestDtoB(id: Long? = null) = AuthorUpdateRequestDTO(
        id = id,
        name = "Peter",
        age = 43,
        description = "cop",
        image = "peter.png"
)

fun testAuthorUpdateRequestB(id: Long? = null) = AuthorUpdateRequest(
        id = id,
        name = "Peter",
        age = 43,
        description = "cop",
        image = "peter.png"
)

fun testAuthorSummaryDTO(id: Long) = AuthorSummaryDTO(
        id = id,
        name = "John Doe",
        image = "author-image.png"
)

fun testBookEntityA(isbn: String, author: AuthorEntity) = BookEntity(
        isbn = isbn,
        title = "Test book A",
        description = "Test book A description",
        image = "testBookA.png",
        author = author
)

fun testBookSummaryDTO(isbn: String, author: AuthorSummaryDTO) = BookSummaryDTO(
        isbn = isbn,
        title = "Test book A",
        description = "Test book A description",
        image = "testBookA.png",
        author = author
)