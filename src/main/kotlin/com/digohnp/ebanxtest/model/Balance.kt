package com.digohnp.ebanxtest.model

import com.digohnp.ebanxtest.enums.BalanceType
import java.math.BigDecimal

class Balance(
    var id: Long? = null,

    var type: BalanceType,

    var balance: BigDecimal
) {
}