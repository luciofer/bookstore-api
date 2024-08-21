package com.lfdev.bookstore.com.lfdev.bookstore.services.impl


import com.lfdev.bookstore.com.lfdev.bookstore.*
import com.lfdev.bookstore.domain.author.AuthorUpdateRequest
import com.lfdev.bookstore.domain.author.entities.AuthorEntity
import com.lfdev.bookstore.repositories.AuthorRepository
import com.lfdev.bookstore.services.impl.AuthorServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(private val underTest: AuthorServiceImpl, private val authorRepository: AuthorRepository) {

    @Test
    fun `test that create persists the Author in the database`() {
        val savedAuthor = underTest.create(testAuthorEntity())
        assertThat(savedAuthor.id).isNotNull()

        val recalledAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(recalledAuthor).isNotNull()

        assertThat(recalledAuthor!!).isEqualTo(testAuthorEntity(id=savedAuthor.id))
    }

    @Test
    fun `test that an Author with an ID throws an IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            val existingAuthor = testAuthorEntity(id = 1)
            underTest.create(existingAuthor)
        }
    }

    @Test
    fun `test that list returns empty when no authors in the database`() {
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    @DirtiesContext
    fun `test that list returns authors when authors in the database`() {
        val savedAuthor = authorRepository.save(testAuthorEntity())
        val expected = listOf(savedAuthor)
        val result = underTest.list()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test that get Author returns null when author not in the database`() {
        val result = underTest.getAuthor(999)
        assertThat(result).isNull()
    }

    @Test
    @DirtiesContext
    fun `test that get Author returns author when author in the database`() {
        val savedAuthor = authorRepository.save(testAuthorEntity())
        val result = underTest.getAuthor(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)

    }

    @Test
    @DirtiesContext
    fun `test that full update successfully updates the author in the database`(){
        val existingAuthor = authorRepository.save(testAuthorEntity())

        val existingAuthorId = existingAuthor.id!!

        val updatedAuthor = testAuthorEntityB(existingAuthorId)

        val result = underTest.fullUpdate(existingAuthorId, updatedAuthor)
        assertThat(result).isEqualTo(updatedAuthor)

        val retrievedAuthor = authorRepository.findByIdOrNull(existingAuthorId)
        assertThat(retrievedAuthor).isNotNull()
        assertThat(retrievedAuthor).isEqualTo(updatedAuthor)

    }

    @Test
    fun `test that full update Author throws IllegalStateException when author doesn't exist in the database`(){
        assertThrows<IllegalStateException> {
            val nonExistingAuthorId = 999L
            val updatedAuthor = testAuthorEntityB(nonExistingAuthorId)
            underTest.fullUpdate(nonExistingAuthorId, updatedAuthor)
        }
    }

    @Test
    fun `test that partial update throws IllegalStateException when Author is not in the database`(){
        assertThrows<IllegalStateException> {
            val nonExistingAuthor = 333L
            val updatedRequest = testAuthorUpdateRequestB(id = nonExistingAuthor)
            underTest.partialUpdate(nonExistingAuthor, updatedRequest)
        }
    }

    @Test
    fun `test that partial update Author does not update author when all values are null`(){
        val existingAuthor = authorRepository.save(testAuthorEntity())
        val updatedAuthor = underTest.partialUpdate(existingAuthor.id!!, AuthorUpdateRequest())
        assertThat(updatedAuthor).isEqualTo(existingAuthor)
    }

    @Test
    fun `test  that partial update function updates Author name`(){

        val newName = "New name"

        val existingAuthor = testAuthorEntity()

        val expectedAuthor = existingAuthor.copy(
            name = newName
        )

        val authorUpdateRequest = AuthorUpdateRequest(
            name = newName
        )

        assertThatAuthorPartialUpdateIsUpdated(existingAuthor = existingAuthor, expectedAuthor = expectedAuthor, authorUpdateRequest = authorUpdateRequest )
    }

    @Test
    fun `test  that partial update function updates Author age`(){

        val newAge = 33

        val existingAuthor = testAuthorEntity()

        val expectedAuthor = existingAuthor.copy(
            age = newAge
        )

        val authorUpdateRequest = AuthorUpdateRequest(
            age = newAge
        )

        assertThatAuthorPartialUpdateIsUpdated(existingAuthor = existingAuthor, expectedAuthor = expectedAuthor, authorUpdateRequest = authorUpdateRequest )
    }

    @Test
    fun `test  that partial update function updates Author description`(){

        val newDescription = "Updated description"

        val existingAuthor = testAuthorEntity()

        val expectedAuthor = existingAuthor.copy(
            description = newDescription
        )

        val authorUpdateRequest = AuthorUpdateRequest(
            description = newDescription
        )

        assertThatAuthorPartialUpdateIsUpdated(existingAuthor = existingAuthor, expectedAuthor = expectedAuthor, authorUpdateRequest = authorUpdateRequest )
    }

    @Test
    fun `test  that partial update function updates Author image`(){

        val newImage = "updated-img.jpg"

        val existingAuthor = testAuthorEntity()

        val expectedAuthor = existingAuthor.copy(
            image = newImage
        )

        val authorUpdateRequest = AuthorUpdateRequest(
            image = newImage
        )

        assertThatAuthorPartialUpdateIsUpdated(existingAuthor = existingAuthor, expectedAuthor = expectedAuthor, authorUpdateRequest = authorUpdateRequest )
    }

    private fun assertThatAuthorPartialUpdateIsUpdated(
        existingAuthor: AuthorEntity,
        expectedAuthor: AuthorEntity,
        authorUpdateRequest: AuthorUpdateRequest
    ) {
        // Save an existing author
        val savedExistingAuthor = authorRepository.save(existingAuthor)
        val existingAuthorId = savedExistingAuthor.id!!

        // Update an existing author
        val updatedAuthor = underTest.partialUpdate(existingAuthorId, authorUpdateRequest)

        // Set up the expected author
        val expected = expectedAuthor.copy(id=existingAuthorId)

        assertThat(updatedAuthor).isEqualTo(expected)

        val retrievedAuthor = authorRepository.findByIdOrNull(existingAuthorId)
        assertThat(retrievedAuthor).isNotNull()
        assertThat(retrievedAuthor).isEqualTo(expected)
    }

}