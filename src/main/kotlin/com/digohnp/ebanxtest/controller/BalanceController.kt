package com.digohnp.ebanxtest.controller

import com.digohnp.ebanxtest.service.BalanceService
import com.digohnp.ebanxtest.controller.request.PostBalanceRequest
import com.digohnp.ebanxtest.controller.response.GetBalanceResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BalanceController(
    private val balanceService: BalanceService
) {

    @GetMapping("/balance")
    fun getBalance(@RequestParam("account_id") id: Long): ResponseEntity<out Any> {
        val balance = balanceService.getBalanceByAccountId(id)
            ?: return ResponseEntity(GetBalanceResponse().balance, HttpStatus.NOT_FOUND)

        return ResponseEntity(balance.balance, HttpStatus.OK)
    }

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendEvent(@RequestBody request: PostBalanceRequest): Any {
        return balanceService.sendEvent(request)
            ?: return ResponseEntity(GetBalanceResponse().balance, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/reset")
    fun reset(): ResponseEntity<out Any> {
        balanceService.balances.clear()

        return ResponseEntity.ok("OK")
    }
}