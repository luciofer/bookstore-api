package com.lfdev.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lfdev.bookstore.domain.author.AuthorDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    val objectMapper = ObjectMapper()

    @Test
    fun `test that create Author return a HTTP 201 status on a successful create`(){
        mockMvc.post("/authors/v1/create"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                AuthorDTO(
                    id = null,
                    name = "John Doe",
                    age = 28,
                    description = "Test ",
                    image = "Testt"
                )
            )
        }.andExpect {
            status { isCreated() }
        }
    }


}