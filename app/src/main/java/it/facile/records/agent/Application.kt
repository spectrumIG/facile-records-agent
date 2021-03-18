package it.facile.records.agent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import it.facile.records.agent.library.android.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class RecordAgentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
