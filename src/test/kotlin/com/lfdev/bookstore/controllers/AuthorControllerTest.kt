package com.lfdev.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lfdev.bookstore.com.lfdev.bookstore.*
import com.lfdev.bookstore.domain.author.entities.AuthorEntity
import com.lfdev.bookstore.services.AuthorService
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
import org.springframework.test.web.servlet.*


private const val  AUTHORS_BASE_URL = "/authors/v1"

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest @Autowired constructor(private val mockMvc: MockMvc, @MockkBean val authorService: AuthorService) {

    val objectMapper = ObjectMapper()


    @BeforeEach
    fun  beforeEach(){
        every {
            authorService.create(any())
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

        verify { authorService.create(expected) }
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
    fun `test that create Author returns http 400 when IllegalArgumentException is thrown`(){
        every {
            authorService.create(any())
        } throws (IllegalArgumentException())

        mockMvc.post("${AUTHORS_BASE_URL}/create"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO())
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test that list returns an empty list and http 200 when no author in the database`(){
        every {
            authorService.list()
        } answers {
            emptyList()
        }

        mockMvc.get("${AUTHORS_BASE_URL}/"){
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

        mockMvc.get("${AUTHORS_BASE_URL}/"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", CoreMatchers.equalTo(1)) }
            content { jsonPath("$[0].name", CoreMatchers.equalTo("John Doe")) }
            content { jsonPath("$[0].age", CoreMatchers.equalTo(28)) }
            content { jsonPath("$[0].description", CoreMatchers.equalTo("Some description")) }
            content { jsonPath("$[0].image", CoreMatchers.equalTo("author-image.png")) }
        }
    }

    @Test
    fun `test that get Author returns http 404 when author not found`(){
        every {
            authorService.getAuthor(any())
        } answers {
            null
        }

        mockMvc.get("${AUTHORS_BASE_URL}/1"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `test that get returns http 200 and Author, when author found in the database`(){
        every {
            authorService.getAuthor(any())
        } answers {
            testAuthorEntity(id = 999)
        }

        mockMvc.get("${AUTHORS_BASE_URL}/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", CoreMatchers.equalTo(999)) }
            content { jsonPath("$.name", CoreMatchers.equalTo("John Doe")) }
            content { jsonPath("$.age", CoreMatchers.equalTo(28)) }
            content { jsonPath("$.description", CoreMatchers.equalTo("Some description")) }
            content { jsonPath("$.image", CoreMatchers.equalTo("author-image.png")) }
        }
    }

    @Test
    fun `test that full update Author returns HTTP 200 and updated Author on successful call `(){
        every {
            authorService.fullUpdate(any(), any())
        } answers {
            secondArg()
        }

        mockMvc.put("${AUTHORS_BASE_URL}/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO(id = 999))
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", CoreMatchers.equalTo(999)) }
            content { jsonPath("$.name", CoreMatchers.equalTo("John Doe")) }
            content { jsonPath("$.age", CoreMatchers.equalTo(28)) }
            content { jsonPath("$.description", CoreMatchers.equalTo("Some description")) }
            content { jsonPath("$.image", CoreMatchers.equalTo("author-image.png")) }
        }

    }

    @Test
    fun `test that full update Author returns HTTP 400 when IllegalStateException is thrown`(){
        every {
            authorService.fullUpdate(any(), any())
        } throws IllegalStateException()

        mockMvc.put("${AUTHORS_BASE_URL}/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDTO(id = 999))
        }.andExpect {
            status { isBadRequest() }
        }

    }

    @Test
    fun `test that partial update Author returns http 400 on IllegalStateException`(){
        every {
            authorService.partialUpdate(any(), any())
        } throws IllegalStateException()

        mockMvc.patch("${AUTHORS_BASE_URL}/21"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorUpdateRequestDtoB(id = 21))
        }.andExpect {
            status { isBadRequest()}
        }
    }

    @Test
    fun `test that partial update returns http 200 and updated author`(){
        every {
            authorService.partialUpdate(any(), any())
        } answers {
            testAuthorEntityB(id = 21)
        }

        mockMvc.patch("${AUTHORS_BASE_URL}/21"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorUpdateRequestDtoB(id = 21))
        }.andExpect {
            status { isOk()}
            content { jsonPath("$.id", CoreMatchers.equalTo(21)) }
            content { jsonPath("$.name", CoreMatchers.equalTo("Peter")) }
            content { jsonPath("$.age", CoreMatchers.equalTo(43)) }
            content { jsonPath("$.description", CoreMatchers.equalTo("cop")) }
            content { jsonPath("$.image", CoreMatchers.equalTo("peter.png")) }
        }
    }


}