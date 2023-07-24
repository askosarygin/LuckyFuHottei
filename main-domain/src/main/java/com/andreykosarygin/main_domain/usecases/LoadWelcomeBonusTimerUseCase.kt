package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.main_domain.Repository
import java.util.Date

class LoadWelcomeBonusTimerUseCase(
    private val repository: Repository
) {
    suspend fun execute(): Date {
        val lastBonusTimeInMillis = repository.loadWelcomeBonusTimer()
        return Date(lastBonusTimeInMillis)
    }
}