package com.vlavik.urlshortener.service.impl

import com.vlavik.urlshortener.service.KeyConverterService
import com.vlavik.urlshortener.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class DefaultKeyMapperService : KeyMapperService {

    private val map: MutableMap<Long, String> = ConcurrentHashMap()

    @Autowired
    lateinit var converter: KeyConverterService

    val sequence = AtomicLong(10_000_000L)

    override fun add(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)
        map[id] = link
        return key
    }

    override fun getLink(key: String): KeyMapperService.Get {
        val id = converter.keyToId(key)
        val result = map[id]
        if (result == null) {
            return KeyMapperService.Get.NotFound(key)
        } else {
            return KeyMapperService.Get.Link(result)
        }
    }
}