package com.lfdev.bookstore.repositories

import com.lfdev.bookstore.domain.author.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<AuthorEntity, Long?> {
}