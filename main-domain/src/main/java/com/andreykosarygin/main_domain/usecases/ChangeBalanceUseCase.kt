package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.common.BalanceInfo
import com.andreykosarygin.main_domain.Repository

class ChangeBalanceUseCase(
    private val repository: Repository
) {
    suspend fun execute(balanceInfo: BalanceInfo): Boolean =
        repository.changeBalance(balanceInfo.value)
}