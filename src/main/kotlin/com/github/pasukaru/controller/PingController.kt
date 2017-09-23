package com.github.pasukaru.controller

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("ping")
class PingController : BaseController {

    open class PingResponse(val message: String)
    class PingResponseWithParam(message: String, val paramValue: UUID) : PingResponse(message)

    @GetMapping
    fun ping(): PingResponse {
        return PingResponse("pong")
    }

    @PostMapping("/{uuid}")
    fun pingWithParam(@RequestBody body: PingResponse, @PathVariable uuid: UUID): PingResponseWithParam {
        return PingResponseWithParam(body.message, uuid)
    }

}