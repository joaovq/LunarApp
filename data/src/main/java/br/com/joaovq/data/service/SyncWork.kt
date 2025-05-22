package br.com.joaovq.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import br.com.joaovq.article_data.service.ArticleBookmarkSyncWork
import br.com.joaovq.article_data.service.ArticleBookmarkSyncWork.Companion
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.UUID
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncWork @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationId = 3874
        return ForegroundInfo(
            notificationId,
            getSyncNotification(appContext)
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
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Sync")
            .setContentInfo("Sync in progress")
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setProgress(0, 0, true)
            .build()
        return notification
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "sync_notification_channel"
        const val ONE_TIME_REQUEST_TAG = "article_bookmark_sync_tag"

        fun oneTimeRequest(
            uuid: UUID = UUID.randomUUID(),
            initialDelaySeconds: Long = 10,
            constraints: Constraints? = null
        ) =
            OneTimeWorkRequest
                .Builder(SyncWork::class.java)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                //.setConstraints(constraints ?: SyncConstraints)
                .addTag(ONE_TIME_REQUEST_TAG)
                .setId(uuid)
                .build()

        private val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .setRequiresDeviceIdle(false)
                .build()
    }
}