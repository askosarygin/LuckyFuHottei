package com.andreykosarygin.game_domain.usecases

import com.andreykosarygin.common.Level
import com.andreykosarygin.game_domain.Repository

class OpenLevelUseCase(
    private val repository: Repository
) {
    suspend fun execute(level: Level): Boolean {
        val levelIsOpen = repository.levelIsOpen(level.index)
        return if (!levelIsOpen) {
            repository.openLevel(level.index)
            true
        } else {
            false
        }
    }
}