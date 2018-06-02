package com.vlavik.urlshortener.controller

import com.vlavik.urlshortener.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("add")
class AddController {

    @Autowired
    lateinit var service: KeyMapperService

    @PostMapping
    @ResponseBody
    fun add(@RequestBody request: AddRequest) =
        ResponseEntity.ok(AddResponse(request.link, service.add(request.link)))

    data class AddRequest(val link: String)
    data class AddResponse(val link: String, val key: String)
}