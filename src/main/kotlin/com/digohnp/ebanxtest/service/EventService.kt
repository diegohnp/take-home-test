package com.digohnp.ebanxtest.service

import com.digohnp.ebanxtest.controller.request.PostBalanceRequest
import com.digohnp.ebanxtest.controller.response.PostEventResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class EventService(
    private val balanceService: BalanceService
) {

    fun sendEvent(request: PostBalanceRequest): PostEventResponse? {
        if (!canExecuteEvent(request)) return null

        return when (request.type.lowercase(Locale.getDefault())) {
            "deposit" -> balanceService.deposit(request)
            "withdraw" -> balanceService.withdraw(request)
            "transfer" -> balanceService.transfer(request)
            else -> null
        }
    }

    private fun canExecuteEvent(request: PostBalanceRequest): Boolean {
        return "deposit" == request.type.lowercase(Locale.getDefault())
                || "withdraw" == request.type.lowercase(Locale.getDefault())
                || "transfer" == request.type.lowercase(Locale.getDefault())
    }
}