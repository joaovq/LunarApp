package br.com.joaovq.lunarappcompose

import android.app.Application
import br.com.joaovq.lunarappcompose.core.ui.utils.image_loader.CoilImageLoader
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class LunarApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return CoilImageLoader.getDefaultImageLoader(context)
    }
}