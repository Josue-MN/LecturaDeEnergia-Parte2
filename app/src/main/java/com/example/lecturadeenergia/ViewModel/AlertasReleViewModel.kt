package com.example.lecturadeenergia.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerReleDataFirebase
import com.example.lecturadeenergia.Notificaciones.enviarNotificacion
import kotlinx.coroutines.launch

class AlertasReleViewModel : ViewModel() {

    private var corrienteRele = 0.0
    private var rangoMinimo = 0.0
    private var rangoMaximo = 0.0
    private var ultimoEstado = ""

    fun iniciarMonitoreo(context: Context) {

        viewModelScope.launch {

            LeerReleDataFirebase(
                onSuccess = { configuracion ->
                    corrienteRele = configuracion.corrienteRele
                    evaluarEstado(context)
                },
                onError = {}
            )

            LeerRangosFirebase(
                onSuccess = { configuracion ->
                    rangoMinimo = configuracion.rangoMinimo
                    rangoMaximo = configuracion.rangoMaximo
                    evaluarEstado(context)
                },
                onError = {}
            )
        }
    }

    private fun evaluarEstado(context: Context) {

        if (rangoMinimo == 0.0 && rangoMaximo == 0.0) return

        val estadoActual = when {
            corrienteRele < rangoMinimo -> "BAJO"
            corrienteRele > rangoMaximo -> "ALTO"
            else -> "NORMAL"
        }

        if (estadoActual != ultimoEstado) {
            ultimoEstado = estadoActual

            when (estadoActual) {
                "BAJO" -> enviarNotificacion(
                    context,
                    "⚠️ Alerta de corriente",
                    "La corriente está por debajo del rango mínimo"
                )

                "NORMAL" -> enviarNotificacion(
                    context,
                    "✅ Corriente estable",
                    "La corriente está dentro del rango permitido"
                )

                "ALTO" -> enviarNotificacion(
                    context,
                    "🚨 Alerta crítica",
                    "La corriente supera el rango máximo"
                )
            }
        }
    }
}
