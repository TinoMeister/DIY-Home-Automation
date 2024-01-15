package com.example.diyhomeautomation.models

/**
 * Data class representing restrictions in the DIY Home Automation system.
 *
 * @property id The unique identifier for the restriction.
 * @property name The name of the restriction.
 * @property state The general state of the restriction.
 * @property condition The type of condition for the restriction.
 * @property primarySensorId The ID of the primary sensor associated with the restriction.
 * @property primarySensorState The general state of the primary sensor.
 * @property primarySensorValue The value of the primary sensor.
 * @property secondarySensorId The ID of the secondary sensor associated with the restriction.
 * @property secondarySensorState The general state of the secondary sensor.
 * @property secondarySensorValue The value of the secondary sensor (nullable).
 *
 * @constructor Creates an instance of Restriction with the provided properties.
 */
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