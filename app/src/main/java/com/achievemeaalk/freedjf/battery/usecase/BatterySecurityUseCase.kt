package com.achievemeaalk.freedjf.battery.usecase


import com.achievemeaalk.freedjf.battery.model.BatterySecurityState
import com.achievemeaalk.freedjf.battery.repository.IBatteryRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.distinctUntilChanged




class BatterySecurityUseCase(
    private val repository: IBatteryRepository
) {

    operator fun invoke(): Flow<BatterySecurityState> {
        return repository.observeBattery()
            .map { state ->
                when {
                    state.percent <= 15 && !state.isCharging ->
                        BatterySecurityState.Blocked

                    state.percent <= 20 && !state.isCharging ->
                        BatterySecurityState.Warning

                    else ->
                        BatterySecurityState.Allowed
                }
            }
            .distinctUntilChanged() // не спамим одинаковые состояния
    }
}