package com.example.lecturadeenergia

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lecturadeenergia.NavegacionControladaDeLaApp.NavegacionSegunCargo
import com.example.lecturadeenergia.Notificaciones.crearCanalNotificaciones
import com.example.lecturadeenergia.ViewModel.AlertasReleViewModel

class MainActivity : ComponentActivity() {

    // ViewModel GLOBAL (vive mientras la app esté abierta)
    private val alertasReleViewModel: AlertasReleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // CREAR CANAL DE NOTIFICACIONES
        crearCanalNotificaciones(this)

        // PEDIR PERMISO DE NOTIFICACIONES (ANDROID 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        // INICIAR MONITOREO GLOBAL DE ALERTAS
        alertasReleViewModel.iniciarMonitoreo(applicationContext)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            NavegacionSegunCargo()
        }
    }
}
