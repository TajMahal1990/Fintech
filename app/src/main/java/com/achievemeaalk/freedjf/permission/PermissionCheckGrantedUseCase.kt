package com.achievemeaalk.freedjf.permission


import android.content.Context

class PermissionCheckGrantedUseCase(
    private val context: Context,
    private val permissionRepository: IPermissionRepository,
) {

    operator fun invoke(): Boolean {
        return permissionRepository.checkReadSms(context)
                && permissionRepository.checkCamera(context)
                && permissionRepository.checkReadPhoneState(context)
                && permissionRepository.checkPostNotification(context)
                && permissionRepository.checkPushNotificationListener(context)
                && permissionRepository.checkCanDrawOverlays(context)
    }

}