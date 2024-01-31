package com.digohnp.ebanxtest.controller

import com.digohnp.ebanxtest.service.BalanceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reset")
class ResetController(
    val balanceService: BalanceService
) {
    @PostMapping
    fun reset(): ResponseEntity<out Any> {
        balanceService.balances.clear()

        return ResponseEntity.ok("OK")
    }
}