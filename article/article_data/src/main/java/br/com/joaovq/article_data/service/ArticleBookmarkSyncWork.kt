package br.com.joaovq.article_data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import br.com.joaovq.article_domain.repository.ArticleRepository
import br.com.joaovq.common.di.annotations.LunarDispatcher
import br.com.joaovq.common.di.annotations.MyDispatchers
import br.com.joaovq.common.utils.data.SyncResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

@HiltWorker
class ArticleBookmarkSyncWork @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val articleRepository: ArticleRepository,
    @LunarDispatcher(MyDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        return@withContext try {
            setForeground(createForegroundInfo())
            val isSynced = awaitAll(
                async { articleRepository.syncBookmarkArticles() }
            ).all { it is SyncResult.Success }
            if (isSynced) {
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val notificationId = 3874
        return ForegroundInfo(
            notificationId,
            getSyncNotification(appContext),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            }
        )
    }

    private fun getSyncNotification(context: Context): Notification {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Sync",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)
        // Add the cancel action to the notification which can
        // be used to cancel the worker
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Sync Bookmark")
            .setContentInfo("Sync bookmark in progress")
            .setOngoing(true)
            .setProgress(0, 0, true)
            .addAction(android.R.drawable.ic_delete, "Cancelar", intent)
            .build()
        return notification
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "sync_notification_channel"
        private const val ONE_TIME_REQUEST_TAG = "article_bookmark_sync_tag"

        fun oneTimeRequest(
            uuid: UUID = UUID.randomUUID(),
            initialDelaySeconds: Long = 10,
            constraints: Constraints? = null
        ) =
            OneTimeWorkRequest
                .Builder(ArticleBookmarkSyncWork::class.java)
                //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .setConstraints(constraints ?: SyncConstraints)
                .setInitialDelay(initialDelaySeconds, TimeUnit.SECONDS)
                .addTag(ONE_TIME_REQUEST_TAG)
                .setId(uuid)
                .setTraceTag("Sync article worker")
                .build()

        private val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                //.setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .setRequiresDeviceIdle(false)
                .build()
    }
}