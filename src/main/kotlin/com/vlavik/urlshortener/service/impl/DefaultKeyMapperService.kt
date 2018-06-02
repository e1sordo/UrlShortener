package com.vlavik.urlshortener.service.impl

import com.vlavik.urlshortener.model.Link
import com.vlavik.urlshortener.model.repository.LinkRepository
import com.vlavik.urlshortener.service.KeyConverterService
import com.vlavik.urlshortener.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    @Autowired
    lateinit var repository: LinkRepository

    @Transactional
    override fun add(link: String) =
            converter.idToKey(repository.save(Link(link)).id)

    override fun getLink(key: String): KeyMapperService.Get {
        val id = converter.keyToId(key)
        val result = repository.findById(id)

        return if (result.isPresent) {
            KeyMapperService.Get.Link(result.get().text)
        } else {
            return KeyMapperService.Get.NotFound(key)
        }
    }
}