package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.ConfiguracionDispositivoData
import com.example.lecturadeenergia.Firebase.ConfiguracionReleData
import com.example.lecturadeenergia.Firebase.LeerRangosAlertaFirebase
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerReleDataFirebase
import kotlinx.coroutines.delay

@Composable
fun AlertasReleScreen(navegacionControlada : NavController){

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN VALOR, REMEMBER
    var estadoActivacionRele by remember { mutableStateOf(false) }
    var modoAutomaticoManual by remember { mutableStateOf(0) }
    var corrienteRele by remember { mutableStateOf(0.0) }
    var rangoMinimo by remember { mutableStateOf(0.0) }
    var rangoMaximo by remember { mutableStateOf(0.0) }

    // Contexto necesario para usar Toasts (son APIs del sistema Android)
    val contextoDelRele = LocalContext.current

    LaunchedEffect(Unit) {
        //HACE QUE SE GENERE UN CCICLO INFINITO PAR AACTUALIZACION AUTOMATICA
        while (true) {
            // Llama a tu función de LÓGICA DE LECTURA
            LeerReleDataFirebase(
                //PASA UNA VARIABLE DE LECTURA
                onSuccess = { ConfiguracionReleDataDispositivo ->
                    // ACTUALIZA LOS TEXTFIELD
                    estadoActivacionRele = ConfiguracionReleDataDispositivo.estadoActivacionRele
                    modoAutomaticoManual = ConfiguracionReleDataDispositivo.modoAutomaticoManual
                    corrienteRele = ConfiguracionReleDataDispositivo.corrienteRele
                },
                //MANDA EL ERROR CORRESPODIENTE
                onError = { mensajeError ->
                    Toast.makeText(
                        contextoDelRele,
                        mensajeError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            LeerRangosFirebase(
                //PASA UNA VARIABLE DE LECTURA
                onSuccess = { configuracionGuardada ->
                    // ACTUALIZA LOS TEXTFIELD
                    rangoMinimo = configuracionGuardada.rangoMinimo
                    rangoMaximo = configuracionGuardada.rangoMaximo
                },
                //MANDA EL ERROR CORRESPODIENTE
                onError = { mensajeError ->
                    Toast.makeText(
                        contextoDelRele,
                        mensajeError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            //EL TIEMPO QUE DEMORA EN ACTUALIZAR
            delay(9000)
        }
    }

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
                                    contentDescription = "Establecer Alertas",
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .size(46.dp),
                                    tint = Color.Black
                                )

                                Spacer(modifier = Modifier.width(12.dp))


                                // Si los rangos están sin configurar
                                if (rangoMinimo == 0.0 && rangoMaximo == 0.0) {
                                    Text("No hay un rango mínimo y máximo establecidos")
                                } else {

                                    // Comparaciones de rango
                                    if (corrienteRele < rangoMinimo) {
                                        Text("La corriente está por debajo del rango mínimo")
                                    }

                                    if (corrienteRele >= rangoMinimo && corrienteRele <= rangoMaximo) {
                                        Text("La corriente está dentro del umbral establecido")
                                    }

                                    if (corrienteRele > rangoMaximo) {
                                        Text("La corriente está por arriba del rango máximo")
                                    }
                                }
                            }
                        }
                    }

    }
}