package com.lfdev.bookstore.com.lfdev.bookstore.services.impl


import com.lfdev.bookstore.repositories.AuthorRepository
import com.lfdev.bookstore.services.impl.AuthorServiceImpl
import com.lfdev.bookstore.com.lfdev.bookstore.testAuthorEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(private val underTest: AuthorServiceImpl, private val authorRepository: AuthorRepository) {

    @Test
    fun `test that save persists the Author in the database`() {
        val savedAuthor = underTest.save(testAuthorEntity())
        assertThat(savedAuthor.id).isNotNull()

        val recalledAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(recalledAuthor).isNotNull()

        assertThat(recalledAuthor!!).isEqualTo(testAuthorEntity(id=savedAuthor.id))
    }

    @Test
    fun `test that list returns empty when no authors in the database`(){
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    @DirtiesContext
    fun `test that list returns authors when authors in the database`(){
        val savedAuthor = authorRepository.save(testAuthorEntity())
        val expected = listOf(savedAuthor)
        val result = underTest.list()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test that get Author returns null when author not in the database`(){
        val result = underTest.getAuthor(999)
        assertThat(result).isNull()
    }

    @Test
    fun `test that get Author returns author when author in the database`(){
        val savedAuthor = authorRepository.save(testAuthorEntity())
        val result = underTest.getAuthor(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)

    }

}