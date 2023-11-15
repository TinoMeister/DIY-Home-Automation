package com.example.diyhomeautomation.models

data class Restriction(
    var id: Int,
    var name: String,
    var state: GeneralState,
    var condition: TypeCondition,
    var primarySensorId: Int,
    var primarySensorState: GeneralState,
    var primarySensorValue: Double,
    var secondarySensorId: Int,
    var secondarySensorState: GeneralState,
    var secondarySensorValue: Double?
)