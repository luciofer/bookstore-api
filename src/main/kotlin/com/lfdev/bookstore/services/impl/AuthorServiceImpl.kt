package com.lfdev.bookstore.services.impl

import com.lfdev.bookstore.domain.author.AuthorEntity
import com.lfdev.bookstore.repositories.AuthorRepository
import com.lfdev.bookstore.services.AuthorService
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository): AuthorService {

    override fun save(authorEntity: AuthorEntity): AuthorEntity{
        return authorRepository.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

}