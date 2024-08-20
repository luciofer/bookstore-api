package com.lfdev.bookstore.domain.book.entities

import com.lfdev.bookstore.domain.author.entities.AuthorEntity
import jakarta.persistence.*

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    @Column(name = "isbn")
    val isbn: String,

    val title: String,

    val description: String,

    val image: String,

    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "author_id")
    val author: AuthorEntity
)