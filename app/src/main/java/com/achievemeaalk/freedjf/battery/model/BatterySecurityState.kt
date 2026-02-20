package com.achievemeaalk.freedjf.battery.model


sealed class BatterySecurityState {
    object Allowed : BatterySecurityState()
    object Warning : BatterySecurityState()
    object Blocked : BatterySecurityState()
}