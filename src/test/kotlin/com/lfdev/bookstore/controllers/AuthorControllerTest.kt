package com.lfdev.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lfdev.bookstore.domain.author.AuthorEntity
import com.lfdev.bookstore.services.AuthorService
import com.lfdev.bookstore.testAuthorDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest @Autowired constructor(private val mockMvc: MockMvc, @MockkBean val authorService: AuthorService) {

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun  beforeEach(){
        every {
            authorService.save(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `test that create Author saves the Author`(){
        mockMvc.post("/authors/v1/create"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO())
        }

        val expected = AuthorEntity(
            id = null,
            name = "John Doe",
            age = 28,
            description =  "Some description",
            image = "author-image.png"
        )

        verify { authorService.save(expected) }
    }

    @Test
    fun `test that create Author return a HTTP 201 status on a successful create`(){
        mockMvc.post("/authors/v1/create"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO())
        }.andExpect {
            status { isCreated() }
        }
    }


}