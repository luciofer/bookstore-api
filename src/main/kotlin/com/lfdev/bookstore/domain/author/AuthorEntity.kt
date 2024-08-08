package com.lfdev.bookstore.domain.author

import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class AuthorEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    val id: Long?,

    val name: String,

    val age: Int,

    val description: String,

    val image: String
)