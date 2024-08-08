package com.lfdev.bookstore.controllers

import com.lfdev.bookstore.domain.author.AuthorDTO
import com.lfdev.bookstore.services.AuthorService
import com.lfdev.bookstore.toAuthorDTO
import com.lfdev.bookstore.toAuthorEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {

    @PostMapping("/v1/create/author")
    fun createAuthor(@RequestBody authorDTO: AuthorDTO): AuthorDTO{
        return authorService.save(authorDTO.toAuthorEntity()).toAuthorDTO()
    }
}