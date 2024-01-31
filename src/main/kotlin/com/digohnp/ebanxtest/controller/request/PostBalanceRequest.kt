package com.digohnp.ebanxtest.controller.request

import java.math.BigDecimal

class PostBalanceRequest(
    var destination: String? = null,
    var origin: String? = null,
    var amount: BigDecimal,
    var type: String
)
