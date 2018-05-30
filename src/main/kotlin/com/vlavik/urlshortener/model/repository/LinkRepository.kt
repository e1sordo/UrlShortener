package com.vlavik.urlshortener.model.repository

import com.vlavik.urlshortener.model.Link
import org.springframework.data.repository.Repository
import java.util.*

interface LinkRepository : Repository<Link, Long> {

    fun findById(id: Long?): Optional<Link>
    fun save(link: Link): Link
    fun findAll(): List<Link>
}