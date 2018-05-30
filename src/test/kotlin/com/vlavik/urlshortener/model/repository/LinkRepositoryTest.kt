package com.vlavik.urlshortener.model.repository

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import com.vlavik.urlshortener.model.AbstractRepositoryTest
import com.vlavik.urlshortener.model.Link
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@DatabaseSetup(LinkRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [(LinkRepositoryTest.DATASET)])
class LinkRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    companion object {
        const val DATASET = "classpath:datasets/link-table.xml"

        private val LINK_1_ID: Long = 147L
        private val LINK_NOT_FOUND: Long = 3L
        private val LINK_1_TEXT: String = "https://www.python.org/"
        private val LINK_TBS_TEXT: String = "http://aase.eatgx.cas"
    }

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findById(LINK_1_ID)
        assertThat(got.isPresent, equalTo(true))
        val link = got.get()
        assertThat(link, equalTo(Link(LINK_1_TEXT, LINK_1_ID)))
    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findById(LINK_NOT_FOUND)
        assertThat(got.isPresent, equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved = Link(LINK_TBS_TEXT)
        val got: Link = repository.save(toBeSaved)
        val list: List<Link> = repository.findAll()

        assertThat(list, hasSize(4))
        assertThat(got.text, equalTo(LINK_TBS_TEXT))
    }
}