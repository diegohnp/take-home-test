package com.digohnp.ebanxtest.service

import com.digohnp.ebanxtest.controller.request.PostBalanceRequest
import com.digohnp.ebanxtest.controller.response.PostBalanceResponse
import com.digohnp.ebanxtest.controller.response.PostEventResponse
import com.digohnp.ebanxtest.enums.BalanceType
import com.digohnp.ebanxtest.model.Balance
import org.springframework.stereotype.Service
import java.util.*

@Service
class BalanceService {
    var balances: MutableList<Balance> = arrayListOf()

    fun getBalanceByAccountId(id: Long): Balance? {
        val balanceFilter = balances.filter { it.id == id }

        return if (balanceFilter.isEmpty()) null else balanceFilter[0]
    }

    fun deposit(request: PostBalanceRequest): PostDestinationEventResponse {
        var balance = request.destination?.let { getBalanceByAccountId(it.toLong()) }
        if (balance != null) {
            balances.remove(balance)
            balance.balance += request.amount

            balances.add(balance)
        } else {
            balance = createBalance(request)

            balances.add(balance)
        }

        return PostEventResponse(destination = PostBalanceResponse(id = balance.id!!, balance = balance.balance))
    }

    private fun createBalance(request: PostBalanceRequest): Balance {
        return Balance(
            id = request.destination?.toLong(),
            balance = request.amount,
            type = BalanceType.valueOf(request.type.uppercase(Locale.getDefault())))
    }

    fun withdraw(request: PostBalanceRequest): PostEventResponse? {
        val balance: Balance = request.origin?.toLong()?.let { getBalanceByAccountId(it) } ?: return null

        balances.remove(balance)
        balance.balance = balance.balance.minus(request.amount)
        balances.add(balance)

        return PostEventResponse(origin = PostBalanceResponse(id = balance.id!!, balance = balance.balance))
    }

    fun transfer(request: PostBalanceRequest): PostEventResponse? {
        val originBalance = getBalanceByAccountId(request.origin?.toLong()!!)
        var destinationBalance = getBalanceByAccountId(request.destination?.toLong()!!)

        if (originBalance == null) return null
        balances.remove(originBalance)
        originBalance.balance = originBalance.balance.minus(request.amount)

        if (destinationBalance == null) destinationBalance = this.createBalance(
            PostBalanceRequest(
            destination = request.destination,
            amount = request.amount,
            type = "deposit"
        )) else {
            balances.remove(destinationBalance)
            destinationBalance.balance = destinationBalance.balance.plus(request.amount)
        }

        balances.add(originBalance)
        balances.add(destinationBalance)

        val postOriginBalanceResponse = PostBalanceResponse(id = originBalance.id!!, balance = originBalance.balance)
        val postDestinationBalanceResponse = PostBalanceResponse(id = destinationBalance.id!!, balance = destinationBalance.balance)

        return PostEventResponse(origin = postOriginBalanceResponse, destination = postDestinationBalanceResponse)
    }

    private fun canExecuteEvent(request: PostBalanceRequest): Boolean {
        return "deposit" == request.type.lowercase(Locale.getDefault())
                || "withdraw" == request.type.lowercase(Locale.getDefault())
                || "transfer" == request.type.lowercase(Locale.getDefault())
    }
}