package com.digohnp.ebanxtest.controller.response

import java.math.BigDecimal

class GetBalanceResponse(
    var balance: BigDecimal? = BigDecimal.ZERO
)