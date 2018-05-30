package com.vlavik.urlshortener.service

import com.vlavik.urlshortener.service.impl.DefaultKeyConverterService
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DefaultKeyConverterServiceTest {

    val service: KeyConverterService = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertableBothWays() {
        val rand = Random()
        for (i in 0..1_000L) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)
            assertEquals(initialId, id)
        }
    }
}