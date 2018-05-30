package com.vlavik.urlshortener.service.impl

import com.vlavik.urlshortener.service.KeyConverterService
import org.springframework.stereotype.Service

@Service
class DefaultKeyConverterService : KeyConverterService {

    final val chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-_".toCharArray()
    val charToLong = (0 until chars.size)
            .map { i -> Pair(chars[i], i.toLong()) }
            .toMap()

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key
            .map { c -> charToLong[c]!! }
            .fold(0L, {a,b -> a*chars.size + b})
}