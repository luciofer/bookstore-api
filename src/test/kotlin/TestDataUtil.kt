package com.lfdev.bookstore

import com.lfdev.bookstore.domain.author.AuthorDTO
import com.lfdev.bookstore.domain.author.AuthorEntity


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
