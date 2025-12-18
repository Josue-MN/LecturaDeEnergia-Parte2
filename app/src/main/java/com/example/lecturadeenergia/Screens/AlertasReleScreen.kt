package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerReleDataFirebase
import com.example.lecturadeenergia.Notificaciones.enviarNotificacion

@Composable
fun AlertasReleScreen(navegacionControlada: NavController) {

    // Estados principales
    var corrienteRele by remember { mutableStateOf(0.0) }
    var rangoMinimo by remember { mutableStateOf(0.0) }
    var rangoMaximo by remember { mutableStateOf(0.0) }

    // Para evitar notificaciones repetidas
    var ultimoEstado by remember { mutableStateOf("") }

    val contextoDelRele = LocalContext.current

    // 🔹 Lectura inicial desde Firebase
    LaunchedEffect(Unit) {

        LeerReleDataFirebase(
            onSuccess = { configuracion ->
                corrienteRele = configuracion.corrienteRele
            },
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelRele,
                    mensajeError,
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        LeerRangosFirebase(
            onSuccess = { configuracion ->
                rangoMinimo = configuracion.rangoMinimo
                rangoMaximo = configuracion.rangoMaximo
            },
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelRele,
                    mensajeError,
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    // 🔔 Lógica de notificaciones (efecto secundario CORRECTO)
    LaunchedEffect(corrienteRele, rangoMinimo, rangoMaximo) {

        if (rangoMinimo == 0.0 && rangoMaximo == 0.0) return@LaunchedEffect

        val estadoActual = when {
            corrienteRele < rangoMinimo -> "BAJO"
            corrienteRele > rangoMaximo -> "ALTO"
            else -> "NORMAL"
        }

        if (estadoActual != ultimoEstado) {
            ultimoEstado = estadoActual

            when (estadoActual) {
                "BAJO" -> enviarNotificacion(
                    contextoDelRele,
                    "⚠️ Alerta de corriente",
                    "La corriente está por debajo del rango mínimo"
                )

                "NORMAL" -> enviarNotificacion(
                    contextoDelRele,
                    "✅ Corriente estable",
                    "La corriente está dentro del rango permitido"
                )

                "ALTO" -> enviarNotificacion(
                    contextoDelRele,
                    "🚨 Alerta crítica",
                    "La corriente supera el rango máximo"
                )
            }
        }
    }

    // 🔹 UI
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color(0xFFEEEDED), RoundedCornerShape(16.dp))
                .border(2.dp, Color.Transparent, RoundedCornerShape(16.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Alertas del Relé",
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .size(46.dp),
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    when {
                        rangoMinimo == 0.0 && rangoMaximo == 0.0 -> {
                            Text("No hay un rango mínimo y máximo establecidos")
                        }

                        corrienteRele < rangoMinimo -> {
                            Text("La corriente está por debajo del rango mínimo")
                        }

                        corrienteRele > rangoMaximo -> {
                            Text("La corriente está por arriba del rango máximo")
                        }

                        else -> {
                            Text("La corriente está dentro del umbral establecido")
                        }
                    }
                }
            }
        }
    }
}
