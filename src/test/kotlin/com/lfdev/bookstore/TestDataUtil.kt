package com.lfdev.bookstore.com.lfdev.bookstore

import com.lfdev.bookstore.domain.author.AuthorUpdateRequest
import com.lfdev.bookstore.domain.author.dto.AuthorDTO
import com.lfdev.bookstore.domain.author.dto.AuthorUpdateRequestDTO
import com.lfdev.bookstore.domain.author.entities.AuthorEntity


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