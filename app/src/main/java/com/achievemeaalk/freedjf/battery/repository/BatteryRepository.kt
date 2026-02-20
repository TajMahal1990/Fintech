package com.achievemeaalk.freedjf.battery.repository


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.achievemeaalk.freedjf.battery.model.BatteryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import kotlinx.coroutines.channels.awaitClose

class BatteryRepository(
    private val context: Context
) : IBatteryRepository {

    override fun observeBattery(): Flow<BatteryState> = callbackFlow {

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

                if (level <= 0 || scale <= 0) return

                val percent = (level * 100) / scale

                val isCharging =
                    status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL

                trySend(
                    BatteryState(
                        percent = percent,
                        isCharging = isCharging
                    )
                )
            }
        }

        context.registerReceiver(receiver, filter)

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }
}




interface IBatteryRepository {
    fun observeBattery(): Flow<BatteryState>
}