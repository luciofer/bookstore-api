package com.lfdev.bookstore.controllers

import com.lfdev.bookstore.domain.author.dto.AuthorDTO
import com.lfdev.bookstore.domain.author.dto.AuthorUpdateRequestDTO
import com.lfdev.bookstore.services.AuthorService
import com.lfdev.bookstore.toAuthorDTO
import com.lfdev.bookstore.toAuthorEntity
import com.lfdev.bookstore.toAuthorUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/authors/v1")
class AuthorController(private val authorService: AuthorService) {

    @PostMapping("/create")
    fun createAuthor(@RequestBody authorDTO: AuthorDTO): ResponseEntity<AuthorDTO>{
        return try {
            val createdAuthor = authorService.create(authorDTO.toAuthorEntity()).toAuthorDTO()
            ResponseEntity(createdAuthor, HttpStatus.CREATED)
        } catch (ex: IllegalArgumentException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/")
    fun getManyAuthors(): ResponseEntity<List<AuthorDTO>>{
        val authors = authorService.list().map { it.toAuthorDTO() }
        return ResponseEntity(authors, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getAuthor(@PathVariable id: Long): ResponseEntity<AuthorDTO>{
        val author = authorService.getAuthor(id)?.toAuthorDTO()
        return author?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Long, @RequestBody authorDTO: AuthorDTO): ResponseEntity<AuthorDTO>{
        return try {
            val updatedAuthor = authorService.fullUpdate(id, authorDTO.toAuthorEntity())
            ResponseEntity(updatedAuthor.toAuthorDTO(), HttpStatus.OK)
        } catch (ex: IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/{id}")
    fun partialUpdateAuthor(@PathVariable id: Long, @RequestBody authorUpdateRequestDTO: AuthorUpdateRequestDTO): ResponseEntity<AuthorDTO>{
        return try {
            val updatedAuthor = authorService.partialUpdate(id, authorUpdateRequestDTO.toAuthorUpdateRequest())
            ResponseEntity(updatedAuthor.toAuthorDTO(), HttpStatus.OK)
        } catch (ex: IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

}