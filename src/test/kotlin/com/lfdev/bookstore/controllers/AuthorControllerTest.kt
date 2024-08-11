package com.lfdev.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lfdev.bookstore.domain.author.AuthorEntity
import com.lfdev.bookstore.services.AuthorService
import com.lfdev.bookstore.testAuthorDTO
import com.lfdev.bookstore.testAuthorEntity
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post



private const val  AUTHORS_BASE_URL = "/authors/v1"

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
        mockMvc.post("${AUTHORS_BASE_URL}/create"){
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
        mockMvc.post("${AUTHORS_BASE_URL}/create"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO())
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test that list returns an empty list and http 200 when no author in the database`(){
        every {
            authorService.list()
        } answers {
            emptyList()
        }

        mockMvc.get("/${AUTHORS_BASE_URL}"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `test that list returns authors and http 200 when authors in the database`(){
        every {
            authorService.list()
        } answers {
            listOf(testAuthorEntity(id = 1))
        }

        mockMvc.get("/${AUTHORS_BASE_URL}"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", CoreMatchers.equalTo(1)) }
            content { jsonPath("$[0].name", CoreMatchers.equalTo("John Doe")) }
            content { jsonPath("$[0].description", CoreMatchers.equalTo("Some description")) }
            content { jsonPath("$[0].image", CoreMatchers.equalTo("author-image.png")) }
        }
    }


}