package com.lfdev.bookstore.services

import com.lfdev.bookstore.domain.author.AuthorEntity

interface AuthorService {
    fun save(authorEntity: AuthorEntity): AuthorEntity
}