package com.andreykosarygin.game_domain.usecases

import com.andreykosarygin.common.Level
import com.andreykosarygin.game_domain.Repository

class IsLevelOpenUseCase(
    private val repository: Repository
) {
    suspend fun execute(level: Level): Boolean =
        repository.levelIsOpen(level.index)
}