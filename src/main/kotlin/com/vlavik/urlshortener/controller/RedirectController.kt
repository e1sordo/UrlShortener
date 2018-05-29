package com.vlavik.urlshortener.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/{key}")
class RedirectController {

    @RequestMapping()
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {
        if (key == "aAbBcCdD") {
            response.setHeader(HEADER_NAME, "http://www.eveonline.com")
            response.status = 302
        } else {
            response.sendError(404)
        }
    }

    companion object {
        private val HEADER_NAME = "Location"
    }
}

