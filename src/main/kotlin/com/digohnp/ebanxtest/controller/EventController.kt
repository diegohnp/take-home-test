package com.digohnp.ebanxtest.controller

import com.digohnp.ebanxtest.controller.request.PostBalanceRequest
import com.digohnp.ebanxtest.controller.response.GetBalanceResponse
import com.digohnp.ebanxtest.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/event")
class EventController(
    private val eventService: EventService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun sendEvent(@RequestBody request: PostBalanceRequest): Any {
        return eventService.sendEvent(request)
            ?: return ResponseEntity(GetBalanceResponse().balance, HttpStatus.NOT_FOUND)
    }
}