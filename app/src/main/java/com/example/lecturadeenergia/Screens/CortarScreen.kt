package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.EscribirCortarFirebase
import com.example.lecturadeenergia.Firebase.EscribirRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerCortesFirebase
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase

@Composable
fun CortarScreen(navegacionControlada: NavController){
    //VAR ES PARA VARIABLES QUE CAMBIAN, VAL PAR ALAS QUE NO CAMBIAN

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var apagadoEncendido by remember { mutableStateOf(false) }

    //VALOR DONDE SE MOSTRARAN LOS ERRORES COMO SI FUERA DISPENSABLE
    val contextoDelCorte = LocalContext.current


    LaunchedEffect(Unit) {
        // Llama a tu función de LÓGICA DE LECTURA
        LeerCortesFirebase(
            //PASA UNA VARIABLE DE LECTURA
            onSuccess = { configuracionGuardada ->
                // ACTUALIZA LOS TEXTFIELD
                apagadoEncendido = configuracionGuardada.apagadoEncendido
            },
            //MANDA EL ERROR CORRESPODIENTE
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelCorte,
                    mensajeError,
                    Toast.LENGTH_SHORT).show()
            }
        )
    }

    // --> SCAFFOLD ES EL MARCO SUPERIOR DE LA PANTALLA
    // --> PADDINGVALUES SON LOS VALORES O PARAMETROS PARA EL
    // ESTILO DE LA APP COMO UN CSS PARA MANEJAR LA PANTALLA
    Scaffold { paddingValues ->
        // --> COLUMN MANEJARA TTODO EL CONTENIDO QUE SE MOSTRARA
        //  EN PANTALLA DE FORMA VERTICAL
        // --> EL MODIFIER SE LEE EN ORDEN
        Column(modifier = Modifier
            .padding(paddingValues) // MANEJA UN MARGEN PARA EL CONTENIDO
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),// EXPANDE EL MARGEN A TODA LA PANTALLA
            verticalArrangement = Arrangement.Center, //CENTRADO AL CENTRO VERTICAL
            horizontalAlignment = Alignment.CenterHorizontally //CENTRADO AL CENTRO HORIZONTAL
        ) {
            Text(
                text = ("En este apartado podras manejar el encendido y apagado " +
                        "de la corriente del sensor por lo que no enviara datos de voltaje"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 10.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.fillMaxWidth() //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.background(Color.Red)
                    //.aspectRatio(2f),  //HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

                )
            Text(
                text = (" "),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 10.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                fontSize = 22.sp,

                )
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = "Configuracion del Corte de Corriente:",
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 2.dp) //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.background(Color.Green)
                    .padding(bottom = 18.dp),
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 30.sp,
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    //.aspectRatio(2f),
            ) {
                Text(
                    text = if (apagadoEncendido) "ENCENDIDO" else "APAGADO",
                    color = if (apagadoEncendido) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Switch(
                    // 'checked' = le dice al Switch si debe estar ON u OFF
                    checked = apagadoEncendido,


                    // 'onCheckedChange' = se dispara cuando el usuario lo toca
                    // 'it' contiene el nuevo valor (true o false)
                    onCheckedChange = { nuevoValor ->
                        apagadoEncendido = nuevoValor
                        EscribirCortarFirebase(
                            //SE APLICA QUE EL TEXTO SE VUELVA EN MINUSCULAS
                            apagadoEncendido = apagadoEncendido,
                            //ONSUCCES LE PASA LA VARIABLE CARGO PARA HACER LA DIFERENCIA DE INTERFAZ
                            onSuccess = {

                                //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                Toast.makeText(
                                    contextoDelCorte,
                                    "¡corte ingresados con Exito!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            },
                            //ONERROR LE PASA LA VARIABLE DEL ERROR
                            onError = { mensajeError ->
                                //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                Toast.makeText(
                                    contextoDelCorte,
                                    mensajeError,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }

                )
            }
        }
    }

}