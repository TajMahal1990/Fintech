package com.achievemeaalk.freedjf.permission


/*
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat

class PermissionUseCase(
    private val context: Context,
    private val activity: Activity,
    private val permissionRepository: IPermissionRepository,
) {

    operator fun invoke() {
        var permissions: Array<String> = permissionRepository.getDefaultPermissions()
        if(!permissionRepository.checkPostNotification(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions += (Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if(!permissionRepository.checkBluetoothConnect(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissions += (Manifest.permission.BLUETOOTH_CONNECT)
            }
        }

        if(!permissionRepository.checkManageDevicePolicyRunInBackground(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                permissions += (Manifest.permission.MANAGE_DEVICE_POLICY_ACROSS_USERS)
                permissions += (Manifest.permission.MANAGE_DEVICE_POLICY_RUN_IN_BACKGROUND)
            }
        }
        if(!permissionRepository.checkIgnoreBatteryOptimize(context)) {
            permissionRepository.actionRequestIgnoreOptimizations(context)
        }
        if(!permissionRepository.checkPushNotificationListener(context)) {
            permissionRepository.actionNotificationListenerSettings(context)
        }
        if(!permissionRepository.checkForegroundServiceCamera(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                permissions += (Manifest.permission.FOREGROUND_SERVICE_CAMERA)
            }
        }
        if(!permissionRepository.checkForegroundServiceMediaPlayback(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                permissions += (Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)
            }
        }
        if(!permissionRepository.checkCanDrawOverlays(context)) {
            permissionRepository.actionManageOverlayPermission(context)
        }
        if(!permissionRepository.checkReceiveBootCompleted(context)) {
            permissionRepository.actionManageOverlayPermission(context)
        }
        if(!permissionRepository.checkReadMediaImages(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions += (Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
        permissionRepository.request(activity, permissions)
        //permissionRepository.actionTest(context)
    }

}

 */