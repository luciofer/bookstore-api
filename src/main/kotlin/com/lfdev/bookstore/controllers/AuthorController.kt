package com.lfdev.bookstore.controllers

import com.lfdev.bookstore.domain.author.AuthorDTO
import com.lfdev.bookstore.services.AuthorService
import com.lfdev.bookstore.toAuthorDTO
import com.lfdev.bookstore.toAuthorEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {

    @PostMapping("/v1/create")
    fun createAuthor(@RequestBody authorDTO: AuthorDTO): ResponseEntity<AuthorDTO>{
        val createdAuthor = authorService.save(authorDTO.toAuthorEntity()).toAuthorDTO()
        return ResponseEntity(createdAuthor, HttpStatus.CREATED)
    }

    @GetMapping("/v1")
    fun getManyAuthors(): ResponseEntity<List<AuthorDTO>>{
        val authors = authorService.list().map { it.toAuthorDTO() }
        return ResponseEntity(authors, HttpStatus.OK)
    }


}