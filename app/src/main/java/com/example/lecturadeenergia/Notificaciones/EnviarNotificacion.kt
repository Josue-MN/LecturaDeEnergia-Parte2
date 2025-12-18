package com.example.lecturadeenergia.Notificaciones

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.lecturadeenergia.R

fun enviarNotificacion(
    context: Context,
    titulo: String,
    mensaje: String
) {
    val notificacion = NotificationCompat.Builder(context, "alertas_rele")
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(titulo)
        .setContentText(mensaje)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    manager.notify(System.currentTimeMillis().toInt(), notificacion)
}
