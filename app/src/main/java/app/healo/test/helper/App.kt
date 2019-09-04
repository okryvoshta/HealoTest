package app.healo.test.helper

import android.app.Application

class App: Application() {
    companion object {
        private lateinit var sInstance: App
        fun getInstance() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}