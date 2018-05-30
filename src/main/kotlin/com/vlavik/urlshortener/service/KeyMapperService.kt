package com.vlavik.urlshortener.service

interface KeyMapperService {

    fun getLink(key: String): Get
    fun add(link: String): String

    interface Get {
        data class Link(val key: String): Get
        data class NotFound(val key: String): Get
    }
}
