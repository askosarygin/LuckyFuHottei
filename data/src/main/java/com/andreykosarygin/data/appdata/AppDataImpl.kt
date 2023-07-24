package com.andreykosarygin.data.appdata

import android.content.SharedPreferences

class AppDataImpl (
    private val sharedPreferences: SharedPreferences
) : AppData {
    private val keyBalance = "key_balance"
    private val keyTreasureBought = "key_treasure_bought_"
    private val keyLevelIsOpen = "key_level_is_open_"
    private val keyWelcomeBonusTime = "key_welcome_bonus_time"


    override suspend fun changeBalance(earnedPoints: Int): Boolean {
        val oldBalance = getBalance()
        sharedPreferences.edit().putInt(keyBalance, oldBalance + earnedPoints).apply()
        return true
    }

    override suspend fun getBalance(): Int {
        val balance = sharedPreferences.getInt(keyBalance, 0)
        return if (balance > 0) balance else 0
    }

    override suspend fun isTreasureBought(index: Int): Boolean {
        val treasureKey = keyTreasureBought + index
        return sharedPreferences.getBoolean(treasureKey, false)
    }

    override suspend fun buyTreasure(index: Int): Boolean {
        val treasureKey = keyTreasureBought + index
        sharedPreferences.edit().putBoolean(treasureKey, true).apply()
        return true
    }

    override suspend fun levelIsOpen(index: Int): Boolean {
        val levelKey = keyLevelIsOpen + index
        return sharedPreferences.getBoolean(levelKey, false)
    }

    override suspend fun openLevel(index: Int): Boolean {
        val levelKey = keyLevelIsOpen + index
        sharedPreferences.edit().putBoolean(levelKey, true).apply()
        return true
    }

    override suspend fun saveWelcomeBonusTime(timeInMillis: Long): Boolean {
        sharedPreferences.edit().putLong(keyWelcomeBonusTime, timeInMillis).apply()
        return true
    }

    override suspend fun getWelcomeBonusTime(): Long {
        return sharedPreferences.getLong(keyWelcomeBonusTime, 0L)
    }


}