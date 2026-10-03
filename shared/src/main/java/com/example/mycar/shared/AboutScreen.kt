package com.example.mycar.shared

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Template

class AboutScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val versionName = try {
            val pInfo = carContext.packageManager.getPackageInfo(carContext.packageName, 0)
            pInfo.versionName ?: "1.0"
        } catch (e: Exception) {
            "1.0"
        }

        val desc = carContext.getString(R.string.about_desc)
        val versionText = carContext.getString(R.string.app_version, versionName)
        val fullText = "$desc\n\n$versionText"

        return MessageTemplate.Builder(fullText)
            .setTitle(carContext.getString(R.string.about_title))
            .setHeaderAction(Action.BACK)
            .build()
    }
}
