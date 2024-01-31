package com.digohnp.ebanxtest.service

import org.springframework.stereotype.Service

@Service
class EventService {
class EventService(
    val balanceService: BalanceService
) {

    private fun canExecuteEvent(request: PostBalanceRequest): Boolean {
        return "deposit" == request.type.lowercase(Locale.getDefault())
                || "withdraw" == request.type.lowercase(Locale.getDefault())
                || "transfer" == request.type.lowercase(Locale.getDefault())
    }
}