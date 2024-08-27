package com.lfdev.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lfdev.bookstore.com.lfdev.bookstore.testAuthorEntity
import com.lfdev.bookstore.com.lfdev.bookstore.testAuthorSummaryDTO
import com.lfdev.bookstore.com.lfdev.bookstore.testBookEntityA
import com.lfdev.bookstore.com.lfdev.bookstore.testBookSummaryDTO
import com.lfdev.bookstore.services.BookService
import com.lfdev.bookstore.toAuthorSummaryDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest @Autowired constructor(private val mockMvc: MockMvc, @MockkBean val bookService: BookService){

    val objectMapper = ObjectMapper()

    @Test
    fun `test that createFullUpdateBook returns http 201 when book is created`(){
        val isbn = "978-002-794636-8339"
        val author = testAuthorEntity(id = 52)
        val savedBook = testBookEntityA(isbn, author)

        val authorSummaryDTO = testAuthorSummaryDTO(id = 52)
        val bookSummaryDTO = testBookSummaryDTO(isbn, authorSummaryDTO)

        every {
            bookService.createUpdate(isbn, any())
        } answers {
            Pair(savedBook, true)
        }

        mockMvc.put("/v1/books/${isbn}"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bookSummaryDTO)
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test that createFullUpdateBook returns http 500 when author in the database does not have an ID`() {
        val isbn = "978-002-794636-8339"
        val author = testAuthorEntity()
        val savedBook = testBookEntityA(isbn, author)

        val authorSummaryDTO = testAuthorSummaryDTO(id = 52)
        val bookSummaryDTO = testBookSummaryDTO(isbn, authorSummaryDTO)

        every {
            bookService.createUpdate(isbn, any())
        } answers {
            Pair(savedBook, true)
        }

        mockMvc.put("/v1/books/${isbn}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bookSummaryDTO)
        }.andExpect {
            status { isInternalServerError() }
        }
    }

}