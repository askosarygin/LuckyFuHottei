package com.andreykosarygin.common

import java.io.Serializable

data class NavigationLevelInfo(
    val moveLimiterCount: Int,
    val elementsVariations: Int,
    val openLevelIfWin: Int,
    val currentLevel: Int,
    val prize: Int
) : Serializable
