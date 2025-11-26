package com.example.lecturadeenergia.Screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.EscribirReleDataFirebase
import com.example.lecturadeenergia.Firebase.LeerReleDataFirebase
import kotlinx.coroutines.delay

@Composable
fun CortarReleScreen(navegacionControlada: NavController) {

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN VALOR, REMEMBER
    var estadoActivacionRele by remember { mutableStateOf(false) }
    var modoAutomaticoManual by remember { mutableStateOf(0) }
    var corrienteRele by remember { mutableStateOf(0.0) }

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
            //EL TIEMPO QUE DEMORA EN ACTUALIZAR
            delay(2000)
        }
    }
    LaunchedEffect(corrienteRele, modoAutomaticoManual) {
            if (corrienteRele >= 8.0) {
                //SE ACTIVA SI EL RELE ESTA EN AUTOMATICO Y EL MODO MANUAL
                if (estadoActivacionRele || modoAutomaticoManual == 0) {
                    // Apagado de emergencia
                    EscribirReleDataFirebase(
                        estadoActivacionRele = false,
                        modoAutomaticoManual = 1,
                        onSuccess = {
                            estadoActivacionRele = false
                            modoAutomaticoManual = 1
                            Toast.makeText(
                                contextoDelRele,
                                "Energia demasiado alta, modo automatico activado",
                                Toast.LENGTH_LONG
                            )
                                .show()
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
                }
                // Encendido automático
                else if (modoAutomaticoManual == 1) {
                    if (!estadoActivacionRele) {
                        EscribirReleDataFirebase(
                            estadoActivacionRele = true,
                            modoAutomaticoManual = 1,
                            onSuccess = { estadoActivacionRele = true },
                            onError = {}
                        )
                    }
                }
        }
    }





    @Composable
    fun CorrienteRele() {
        // Caja de datos de corriente
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    color = Color(0xFFEEEDED),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // Fila corriente detectada
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Voltaje",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Corriente Detectada: ${corrienteRele}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Composable
    fun EstadoRele() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            color = Color(0xFFEEEDED),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
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
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info Rele",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(26.dp),
                                tint = Color.Black
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Estado apagado o encendido
                            if (estadoActivacionRele == false) {
                                Text(
                                    text = "Estado del Relé: Apagado",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                Text(
                                    text = "Estado del Relé: Encendido",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
        }
    }
// ---------------------------------------------------------------------
// SECCIÓN 5: MODO DE USO DEL RELÉ
// Permite cambiar entre:
// - modo manual → usuario controla ON/OFF
// - modo automático → lógica automática en función de la corriente
//
// Esta es la sección más compleja por el manejo de Firebase.
// ---------------------------------------------------------------------

        @Composable
        fun ModoDeUso() {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-10).dp),
                contentAlignment = Alignment.Center
            ) {

                    // Caja contenedora de TODA la lógica de modo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(
                                color = Color(0xFFEEEDED),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(24.dp)
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(18.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            // Título
                            Text(
                                text = "Modo de uso Relé",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            // BOTÓN PARA CAMBIAR MODO (manual / automático)
                            Button(
                                onClick = {

                                    // -----------------------------------------------
                                    // CAMBIO DE MODO DEL RELÉ
                                    //
                                    // modo_uso:
                                    // 0 → manual
                                    // 1 → automático
                                    //
                                    // Se invierte el valor en Firebase:
                                    // si es 0 pasa a 1, si es 1 pasa a 0.
                                    //
                                    // Importante:
                                    // escribirFirebase() sobrescribe todo el objeto,
                                    // por eso se reconstruye toda la estructura exacta.
                                    // -----------------------------------------------
                                    val ifDelModo = if(modoAutomaticoManual == 0) 1 else 0
                                    EscribirReleDataFirebase(
                                        estadoActivacionRele = estadoActivacionRele,
                                        modoAutomaticoManual = ifDelModo,
                                        onSuccess = {
                                            modoAutomaticoManual = ifDelModo
                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                            Toast.makeText(
                                                contextoDelRele,
                                                "¡Rele activado con Exito!",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        },
                                        //ONERROR LE PASA LA VARIABLE DEL ERROR
                                        onError = { mensajeError ->
                                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                            Toast.makeText(
                                                contextoDelRele,
                                                mensajeError,
                                                Toast.LENGTH_LONG
                                            ).show()

                                        }
                                    )
                                }
                            ) {

                                // Texto dinámico del botón
                                Text(
                                    text =
                                        if (modoAutomaticoManual == 0)
                                            "Cambiar a modo automático"
                                        else
                                            "Cambiar a modo Manual"
                                )
                            }

                            // ---------------------------------------------------------
                            // LÓGICA DEL MODO MANUAL:
                            // El usuario controla directamente el estado del relé.
                            // ---------------------------------------------------------
                            if (modoAutomaticoManual == 0) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    // Si el relé está APAGADO → mostrar botón Encender
                                    if (!estadoActivacionRele) {

                                        Button(
                                            onClick = {

                                                // Se enciende el relé manualmente
                                                EscribirReleDataFirebase(
                                                    estadoActivacionRele = true,
                                                    modoAutomaticoManual = 0,
                                                    onSuccess = {
                                                        estadoActivacionRele = true
                                                        //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                        //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                        Toast.makeText(
                                                            contextoDelRele,
                                                            "¡Rele desactivado con Exito!",
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                    },
                                                    //ONERROR LE PASA LA VARIABLE DEL ERROR
                                                    onError = { mensajeError ->
                                                        //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                        //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                        Toast.makeText(
                                                            contextoDelRele,
                                                            mensajeError,
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                    }
                                                )
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Encender")
                                        }

                                    }
                                    // Si está ENCENDIDO → mostrar botón Apagar
                                    else {

                                        Button(
                                            onClick = {

                                                EscribirReleDataFirebase(
                                                    estadoActivacionRele = false,
                                                    modoAutomaticoManual = 0,
                                                    onSuccess = {
                                                        estadoActivacionRele = false
                                                        //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                        //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                        Toast.makeText(
                                                            contextoDelRele,
                                                            "¡Modo manual del Rele activado con Exito!",
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                    },
                                                    //ONERROR LE PASA LA VARIABLE DEL ERROR
                                                    onError = { mensajeError ->
                                                        //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                                        //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                                        Toast.makeText(
                                                            contextoDelRele,
                                                            mensajeError,
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                    }
                                                )
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFD32F2F),
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Apagar")
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CorrienteRele()
            EstadoRele()
            ModoDeUso()
        }
    }

