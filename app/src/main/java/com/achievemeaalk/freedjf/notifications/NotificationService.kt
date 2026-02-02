package com.achievemeaalk.freedjf.notifications


/*


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bp.antifs.BuildConfig
import com.bp.antifs.ui.activity.MainActivity
import com.bp.antifs.R
import com.bp.antifs.sse.SseService
import com.bp.antifs.state.StateService

class NotificationService : Service() {

    companion object {
        const val ID: Int = 1000
        const val CHANNEL_ID: String = BuildConfig.APPLICATION_ID
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var BUILDER: NotificationCompat.Builder? = null
        @JvmStatic
        var NOTIFICATION: Notification? = null

        var isRun = false
        fun isWorking(): Boolean {
            return isRun
        }

        fun startService(context: Context) {
            if (isWorking()) return
            context.startForegroundService(
                Intent(
                    context,
                    NotificationService::class.java
                )
            )

        }
        fun stopService(context: Context) {
            context.stopService(Intent(context, NotificationService::class.java))
        }
        fun restartService(context: Context) {
            stopService(context)
            startService(context)
        }

        @JvmStatic
        fun updateNotificationStatus(service: Service, status: String, nameDevice: String?) {
            // Обновлено: правильное извлечение строковых ресурсов
            BUILDER?.setContentTitle("${service.getString(R.string.notification_status)}: $status | ${service.getString(R.string.version)}: ${BuildConfig.VERSION_NAME}")
            if(nameDevice != null) {
                BUILDER?.setContentText("${service.getString(R.string.name_device)}: ${nameDevice}")
            } else {
                BUILDER?.setContentText(null)
            }
            NOTIFICATION = BUILDER?.build()
            val notificationManager = service.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(ID, NOTIFICATION)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("NotificationService", "onCreate")
        createNotification(getString(R.string.offline))  // Изменено на использование строкового ресурса
        NOTIFICATION?.let {
            startForeground(ID, it, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        }
        this.packageManager.setComponentEnabledSetting(
            ComponentName(
                this,
                NotificationService::class.java
            ),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("NotificationService", "onStartCommand")
        isRun = true
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        Log.d("NotificationService", "onDestroy")
        super.onDestroy()
        isRun = false
    }

    private fun createNotification(status: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        BUILDER = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_action_name)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentTitle("${getString(R.string.notification_status)}: $status | ${getString(R.string.version)}: ${BuildConfig.VERSION_NAME}") // Изменено на использование строкового ресурса

        val name = "Main"
        val descriptionText = "Main channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        NOTIFICATION = BUILDER?.build()
    }
}


 */