package com.achievemeaalk.freedjf.battery.usecase


import com.achievemeaalk.freedjf.battery.model.BatteryState
import com.achievemeaalk.freedjf.battery.repository.IBatteryRepository

import kotlinx.coroutines.flow.Flow

class ObserveBatteryUseCase(
    private val repository: IBatteryRepository
) {
    operator fun invoke(): Flow<BatteryState> {
        return repository.observeBattery()
    }
}