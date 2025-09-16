package br.com.joaovq.ui.utils.image_loader

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade

object CoilImageLoader {
    private const val MEMORY_CACHE_MAX_SIZE_PERCENT = .25
    private const val DISK_CACHE_MAX_SIZE_PERCENT = .02
    private const val DISK_CACHE_RELATIVE_PATH = "image_cache"
    fun getDefaultImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCache { getDefaultMemoryCache(context) }
            .diskCache { getDefaultDiskCache(context) }
            .build()
    }

    private fun getDefaultMemoryCache(context: PlatformContext) = MemoryCache.Builder()
        .maxSizePercent(context, MEMORY_CACHE_MAX_SIZE_PERCENT)
        .build()

    private fun getDefaultDiskCache(context: PlatformContext) = DiskCache.Builder()
        .directory(context.cacheDir.resolve(DISK_CACHE_RELATIVE_PATH))
        .maxSizePercent(DISK_CACHE_MAX_SIZE_PERCENT)
        .build()
}