package com.lfdev.bookstore

import com.lfdev.bookstore.domain.author.AuthorUpdateRequest
import com.lfdev.bookstore.domain.author.dto.AuthorDTO
import com.lfdev.bookstore.domain.author.dto.AuthorUpdateRequestDTO
import com.lfdev.bookstore.domain.author.entities.AuthorEntity

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