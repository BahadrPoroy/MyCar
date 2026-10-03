package com.example.mycar.shared

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.hardware.CarHardwareManager
import androidx.car.app.hardware.common.CarValue
import androidx.car.app.model.Action
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Template
import java.util.Locale

class MyCarAppScreen(carContext: CarContext) : Screen(carContext) {

    private var currentSpeedKmH: Float = 0f
    private var isSpeedAvailable: Boolean = false

    init {
        try {
            val carHardwareManager = carContext.getCarService(CarHardwareManager::class.java)
            carHardwareManager.carInfo.addSpeedListener(carContext.mainExecutor) { data ->
                if (data.rawSpeedMetersPerSecond.status == CarValue.STATUS_SUCCESS) {
                    val mps = data.rawSpeedMetersPerSecond.value ?: 0f
                    currentSpeedKmH = mps * 3.6f
                    isSpeedAvailable = true
                    invalidate()
                } else {
                    isSpeedAvailable = false
                    invalidate()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onGetTemplate(): Template {
        val speedStr = if (isSpeedAvailable) {
            String.format(Locale.getDefault(), "%.1f km/h", currentSpeedKmH)
        } else {
            carContext.getString(R.string.waiting_data)
        }

        val speedText = carContext.getString(R.string.current_speed, speedStr)

        val aboutAction = Action.Builder()
            .setTitle(carContext.getString(R.string.menu_about))
            .setOnClickListener {
                screenManager.push(AboutScreen(carContext))
            }
            .build()

        return MessageTemplate.Builder(speedText)
            .setHeaderAction(Action.APP_ICON)
            .setTitle(carContext.getString(R.string.speed_title))
            .addAction(aboutAction)
            .build()
    }
}
