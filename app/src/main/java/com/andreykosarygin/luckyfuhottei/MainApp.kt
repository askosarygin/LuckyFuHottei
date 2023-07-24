package com.andreykosarygin.luckyfuhottei

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.andreykosarygin.data.RepositoryGameDomainImpl
import com.andreykosarygin.data.RepositoryMainDomainImpl
import com.andreykosarygin.data.appdata.AppDataImpl
import com.andreykosarygin.game_domain.usecases.ChangeBalanceUseCase
import com.andreykosarygin.game_domain.usecases.GetBalanceUseCase
import com.andreykosarygin.game_domain.usecases.IsLevelOpenUseCase
import com.andreykosarygin.game_domain.usecases.OpenLevelUseCase
import com.andreykosarygin.main_domain.InteractorImpl
import com.andreykosarygin.main_domain.usecases.BuyTreasureUseCase
import com.andreykosarygin.main_domain.usecases.IsTreasureBoughtUseCase
import com.andreykosarygin.main_domain.usecases.LoadWelcomeBonusTimerUseCase
import com.andreykosarygin.main_domain.usecases.SaveWelcomeBonusTimerUseCase

class MainApp : Application() {
    private val sharedPreferencesName = "lucky_fu_hottei_shared_preferences"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appDataImpl: AppDataImpl
    private lateinit var repositoryMainDomainImpl: RepositoryMainDomainImpl
    lateinit var interactorImplMainDomain: InteractorImpl

    private lateinit var repositoryGameDomainImpl: RepositoryGameDomainImpl
    lateinit var interactorImplGameDomain: com.andreykosarygin.game_domain.InteractorImpl

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = this.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        appDataImpl = AppDataImpl(sharedPreferences)

        repositoryMainDomainImpl = RepositoryMainDomainImpl(appDataImpl)
        interactorImplMainDomain = InteractorImpl(
            LoadWelcomeBonusTimerUseCase(repositoryMainDomainImpl),
            SaveWelcomeBonusTimerUseCase(repositoryMainDomainImpl),
            com.andreykosarygin.main_domain.usecases.ChangeBalanceUseCase(repositoryMainDomainImpl),
            com.andreykosarygin.main_domain.usecases.GetBalanceUseCase(repositoryMainDomainImpl),
            BuyTreasureUseCase(repositoryMainDomainImpl),
            IsTreasureBoughtUseCase(repositoryMainDomainImpl)
        )

        repositoryGameDomainImpl = RepositoryGameDomainImpl(appDataImpl)
        interactorImplGameDomain = com.andreykosarygin.game_domain.InteractorImpl(
            ChangeBalanceUseCase(repositoryGameDomainImpl),
            GetBalanceUseCase(repositoryGameDomainImpl),
            IsLevelOpenUseCase(repositoryGameDomainImpl),
            OpenLevelUseCase(repositoryGameDomainImpl)
        )
    }
}