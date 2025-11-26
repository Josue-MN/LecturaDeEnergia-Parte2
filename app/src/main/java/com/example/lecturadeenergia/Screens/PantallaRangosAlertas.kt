package com.example.myapplication.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.lecturadeenergia.Firebase.EscribirRangosAlertasFirebase
import com.example.lecturadeenergia.Firebase.LeerRangosAlertaFirebase

// ---------------------------------------------------------------------
// PANTALLA PARA CONFIGURAR SISTEMA DE ALERTAS
//
// Esta pantalla permite:
// 1) Activar / desactivar alertas (estado boolean en Firebase)
// 2) Ingresar y validar un rango mínimo de corriente
// 3) Ingresar y validar un rango máximo de corriente
// 4) Guardar cambios en Firebase respetando la estructura TOTAL del nodo
// 5) Restablecer los rangos a 0.0
//
// Se interactúa con 3 nodos JSON dentro de dispositivo_data:
//
//  - alertas_dispositivo/
//        estado: Bool
//        rango_minimo: Double
//        rango_maximo: Double
//
//  - datos_rele/
//        estado_rele
//        modo_uso
//
//  - corriente_detectada y dispositivo_nombre se preservan siempre.
// ---------------------------------------------------------------------
@Composable
fun PantallaRangosAlertas(navController: NavController) {

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var apagadoEncendido by remember { mutableStateOf(false) }

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var rangoMinimo by remember { mutableStateOf("") }
    var rangoMaximo by remember { mutableStateOf("") }

    // Contexto necesario para usar Toasts (son APIs del sistema Android)
    val contextoDelRango = LocalContext.current




    LaunchedEffect(Unit) {
        // Llama a tu función de LÓGICA DE LECTURA
        LeerRangosAlertaFirebase(
            //PASA UNA VARIABLE DE LECTURA
            onSuccess = { ConfiguracionRangosAlertaDispositivo ->
                // ACTUALIZA LOS TEXTFIELD
                apagadoEncendido = ConfiguracionRangosAlertaDispositivo.estadoDeActivacion
                rangoMinimo = ConfiguracionRangosAlertaDispositivo.rangoMinimo.toString()
                rangoMaximo = ConfiguracionRangosAlertaDispositivo.rangoMaximo.toString()
            },
            //MANDA EL ERROR CORRESPODIENTE
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelRango,
                    mensajeError,
                    Toast.LENGTH_SHORT).show()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // -----------------------------------------------------------------
        // SECCIÓN 1 — ACTIVAR O DESACTIVAR ALERTAS
        // -----------------------------------------------------------------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp),
            verticalArrangement = Arrangement.Center
        ) {
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

                                        // Icono
                                        Icon(
                                            imageVector = Icons.Default.Notifications,
                                            contentDescription = "Alertas",
                                            modifier = Modifier
                                                .padding(start = 30.dp)
                                                .size(46.dp),
                                            tint = Color.Black
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // Texto "Alertas:"
                                        Text(
                                            text = "Alertas:",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )

                                        // Botón Encender/Apagar
                                        if (!apagadoEncendido) {

                                            // ---------------------------------------------------------
                                            // BOTÓN → ENCENDER ALERTAS
                                            //
                                            // Lógica:
                                            //   Se actualiza SOLO el nodo alertas_dispositivo.estado = true
                                            //   PERO Firebase requiere reescribir la estructura completa.
                                            //
                                            // Se preserva:
                                            // - nombre del dispositivo
                                            // - corriente detectada
                                            // - datos del relé
                                            // - rangos actuales
                                            // ---------------------------------------------------------
                                            Button(
                                                onClick = {
                                                    EscribirRangosAlertasFirebase(
                                                        estadoDeActivacion = true,
                                                        rangoMinimo = rangoMinimo.toDoubleOrNull() ?: 0.0,
                                                        rangoMaximo = rangoMaximo.toDoubleOrNull() ?: 0.0,
                                                        onSuccess = {
                                                            apagadoEncendido = true
                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                "¡Alertas encendidas con Exito!",
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        },
                                                        //ONERROR LE PASA LA VARIABLE DEL ERROR
                                                        onError = { mensajeError ->
                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                mensajeError,
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        }
                                                    )
                                                }
                                            ) {
                                                Text("Encender")
                                            }

                                        } else {

                                            // ---------------------------------------------------------
                                            // BOTÓN → APAGAR ALERTAS
                                            // ---------------------------------------------------------
                                            Button(
                                                onClick = {
                                                    //SE ESTABLECE VALORES QUE PUEDEN SER INT O NULO
                                                    val rangoMinimoNull = rangoMinimo.toDoubleOrNull() ?: 0.0
                                                    val rangoMaximoNull = rangoMaximo.toDoubleOrNull() ?: 0.0

                                                    EscribirRangosAlertasFirebase(
                                                        estadoDeActivacion = false,
                                                        rangoMinimo = rangoMinimoNull,
                                                        rangoMaximo = rangoMaximoNull,
                                                        onSuccess = {
                                                            apagadoEncendido = false
                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                "¡Alertas apagada con Exito!",
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        },
                                                        //ONERROR LE PASA LA VARIABLE DEL ERROR
                                                        onError = { mensajeError ->
                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                mensajeError,
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        }
                                                    )
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFFD32F2F),
                                                    contentColor = Color.White
                                                )
                                            ) {
                                                Text("Apagar")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

        // -----------------------------------------------------------------
        // SECCIÓN 2 — ACTUALIZAR RANGO MÍNIMO Y MÁXIMO DE ALERTA
        // -----------------------------------------------------------------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp),
            verticalArrangement = Arrangement.Center
        ) {

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
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {

                                    // Título de la sección
                                    Text(
                                        text = "Establecer rangos de alertas",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )

                                    // ---------------------------------------------------------
                                    // INPUT RANGO MIN
                                    // Permite SOLO números y punto decimal
                                    // ---------------------------------------------------------
                                    OutlinedTextField(
                                        value = rangoMinimo,
                                        onValueChange = { input ->
                                            if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                                rangoMinimo = input
                                            }
                                        },
                                        label = { Text("Rango mínimo de corriente (A)") },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth(0.9f)
                                    )

                                    // ---------------------------------------------------------
                                    // INPUT RANGO MAX
                                    // ---------------------------------------------------------
                                    OutlinedTextField(
                                        value = rangoMaximo,
                                        onValueChange = { input ->
                                            if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                                rangoMaximo = input
                                            }
                                        },
                                        label = { Text("Rango máximo de corriente (A)") },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth(0.9f)
                                    )

                                    // ---------------------------------------------------------
                                    // BOTÓN GUARDAR RANGOS
                                    //
                                    // Validaciones aplicadas:
                                    // - ambos campos deben tener números válidos
                                    // - min < max (no se permiten min >= max)
                                    //
                                    // Si es válido → se escribe el nodo COMPLETO en Firebase.
                                    // ---------------------------------------------------------
                                    Button(
                                        onClick = {
                                            val min = rangoMinimo.toDoubleOrNull()
                                            val max = rangoMaximo.toDoubleOrNull()

                                            when {
                                                min == null || max == null -> {
                                                    Toast.makeText(contextoDelRango, "Por favor ingresa ambos rangos numéricos", Toast.LENGTH_SHORT).show()
                                                }

                                                min >= max -> {
                                                    Toast.makeText(contextoDelRango, "El rango mínimo no puede ser mayor o igual al rango máximo", Toast.LENGTH_SHORT).show()
                                                }

                                                else -> {

                                                    // Escritura en Firebase de toda la estructura
                                                    EscribirRangosAlertasFirebase(
                                                        estadoDeActivacion = apagadoEncendido,
                                                        //SE APLICA QUE EL TEXTO SE VUELVA EN MINUSCULAS
                                                        rangoMinimo = rangoMinimo.toDouble(),
                                                        rangoMaximo = rangoMaximo.toDouble(),
                                                        //ONSUCCES LE PASA LA VARIABLE CARGO PARA HACER LA DIFERENCIA DE INTERFAZ
                                                        onSuccess = {

                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                "¡rangos de alerta ingresados con Exito!",
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        },
                                                        //ONERROR LE PASA LA VARIABLE DEL ERROR
                                                        onError = { mensajeError ->
                                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                            Toast.makeText(
                                                                contextoDelRango,
                                                                mensajeError,
                                                                Toast.LENGTH_LONG
                                                            ).show()

                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    ) {
                                        Text("Establecer")
                                    }
                                }
                            }
                        }
                    }
                }
            }
