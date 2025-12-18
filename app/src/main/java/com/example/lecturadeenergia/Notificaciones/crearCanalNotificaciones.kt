package com.example.lecturadeenergia.Notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

fun crearCanalNotificaciones(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val canal = NotificationChannel(
            "alertas_rele",
            "Alertas del Relé",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alertas por variaciones de corriente del relé"
        }

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(canal)
    }
}
