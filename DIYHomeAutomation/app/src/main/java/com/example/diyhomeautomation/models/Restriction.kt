package com.example.diyhomeautomation.models

data class Restriction(
    var id: Int,
    var name: String,
    var state: GeneralState,
    var condition: TypeCondition,
    var primaryDeviceId: Int,
    var primaryDeviceState: GeneralState,
    var primaryDeviceValue: Double,
    var secondaryDeviceId: Int,
    var secondaryDeviceState: GeneralState,
    var secondaryDeviceValue: Double?
)