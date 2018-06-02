package com.vlavik.urlshortener

import org.mockito.Mockito

fun <T> whenever(call: T) = Mockito.`when`(call)