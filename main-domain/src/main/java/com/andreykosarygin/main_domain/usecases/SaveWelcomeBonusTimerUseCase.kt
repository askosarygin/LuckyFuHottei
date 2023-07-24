package com.andreykosarygin.main_domain.usecases

import com.andreykosarygin.main_domain.Repository
import java.util.Date

class SaveWelcomeBonusTimerUseCase(
    private val repository: Repository
) {
    suspend fun execute(date: Date): Boolean =
        repository.saveWelcomeBonusTimer(
            date.time
        )
}