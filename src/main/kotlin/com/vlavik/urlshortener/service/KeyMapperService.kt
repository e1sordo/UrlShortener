package com.vlavik.urlshortener.service

interface KeyMapperService {

    fun add(key: String, link: String): Add
    fun getLink(key: String): Get

    interface Add {
        data class Success(val key: String, val link: String): Add
        data class AlreadyExist(val key: String): Add
    }

    interface Get {
        data class Link(val key: String): Get
        data class NotFound(val key: String): Get
    }
}
