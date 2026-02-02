package com.achievemeaalk.freedjf.permission

/*


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
//import com.bp.antifs.pushnotification.PushNotificationService


interface IPermissionRepository {
    fun getDefaultPermissions(): Array<String>
    fun checkReadPhoneState(context: Context): Boolean
    fun checkPostNotification(context: Context): Boolean
    fun checkBluetoothConnect(context: Context): Boolean
    fun checkCamera(context: Context): Boolean
    fun checkManageDevicePolicyRunInBackground(context: Context): Boolean
    fun checkIgnoreBatteryOptimize(context: Context): Boolean
    fun checkPushNotificationListener(context: Context): Boolean
    fun checkReadSms(context: Context): Boolean
    fun checkBroadcastSms(context: Context): Boolean
    fun checkReceiveSms(context: Context): Boolean
    fun checkForegroundServiceCamera(context: Context): Boolean
    fun checkForegroundServiceMediaPlayback(context: Context): Boolean
    fun checkAccessFineLocation(context: Context): Boolean
    fun checkAccessCoarseLocation(context: Context): Boolean
    fun checkAccessBackgroundLocation(context: Context): Boolean
    fun checkReceiveBootCompleted(context: Context): Boolean
    fun checkReadExternalStorage(context: Context): Boolean
    fun checkCanDrawOverlays(context: Context): Boolean
    fun checkReadMediaImages(context: Context): Boolean
    fun request(activity: Activity, permissions: Array<String>)
    fun actionRequestIgnoreOptimizations(context: Context)
    fun actionManageOverlayPermission(context: Context)
    fun actionApplicationDetailsSettings(context: Context)
    fun actionNotificationListenerSettings(context: Context)
    fun actionTest(context: Context)
}

class PermissionRepository(context: Context): IPermissionRepository {

    private var permissions: Array<String> = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_SMS,
        Manifest.permission.BROADCAST_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.SYSTEM_ALERT_WINDOW,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //Manifest.permission.READ_PHONE_NUMBERS, # не работает не хуавей
        //Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        "com.huawei.permission.external_app_settings.USE_COMPONENT",
    )

    override fun getDefaultPermissions(): Array<String> {
        return permissions
    }

    override fun checkReadPhoneState(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkPostNotification(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkBluetoothConnect(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkCamera(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkManageDevicePolicyRunInBackground(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.MANAGE_DEVICE_POLICY_RUN_IN_BACKGROUND
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkIgnoreBatteryOptimize(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }
/*
    override fun checkPushNotificationListener(context: Context): Boolean {
     //   val pushNotificationListenerComponentName = ComponentName(context, PushNotificationService::class.java)
        val enabledListeners =
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        if (enabledListeners.isEmpty()) return false
        return enabledListeners.split(":").map {
            ComponentName.unflattenFromString(it)
        }.any {componentName->
      //      pushNotificationListenerComponentName == componentName
        }
    }

 */

    override fun checkReadSms(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkBroadcastSms(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BROADCAST_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkReceiveSms(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkForegroundServiceCamera(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.FOREGROUND_SERVICE_CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkForegroundServiceMediaPlayback(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkAccessFineLocation(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkAccessCoarseLocation(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkAccessBackgroundLocation(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkReceiveBootCompleted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkReadExternalStorage(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun checkReadMediaImages(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    override fun checkCanDrawOverlays(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    override fun request(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(
            activity,
            permissions, 0,
        )
    }

    @SuppressLint("BatteryLife")
    override fun actionRequestIgnoreOptimizations(context: Context) {
        context.startActivity(
            Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:${context.packageName}")
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    override fun actionManageOverlayPermission(context: Context) {
        context.startActivity(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    override fun actionApplicationDetailsSettings(context: Context) {
        context.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${context.packageName}")
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    override fun actionNotificationListenerSettings(context: Context) {
        context.startActivity(
            Intent(
                Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS,
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    override fun actionTest(context: Context) {
        println("################### actionTest ${context.packageName} ${Build.BRAND}")
        return
        /*context.startActivity(
            Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:${context.packageName}")
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )*/

        val intent = Intent()
        if (Build.BRAND.equals("xiaomi", ignoreCase = true)) {
            intent.component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity")
            context.startActivity(intent)

        } else if (Build.BRAND.equals("huawei", ignoreCase = true)) {
            intent.component = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.optimize.process.ProtectActivity")
            context.startActivity(intent)
        }
    }

}

 */