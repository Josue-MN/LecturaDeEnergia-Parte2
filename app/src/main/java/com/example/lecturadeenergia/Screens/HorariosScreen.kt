package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.EscribirHorariosFirebase
import com.example.lecturadeenergia.Firebase.EscribirRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerHorariosFirebase
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase

@Composable
fun HorariosScreen(navegacionControlada: NavController){
    //VAR ES PARA VARIABLES QUE CAMBIAN, VAL PAR ALAS QUE NO CAMBIAN

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var horaActivacion by remember { mutableStateOf("") }
    var minActivacion by remember { mutableStateOf("") }
    var horaDesactivacion by remember { mutableStateOf("") }
    var minDesactivacion by remember { mutableStateOf("") }

    //VALOR DONDE SE MOSTRARAN LOS ERRORES COMO SI FUERA DISPENSABLE
    val contextoDelHorario = LocalContext.current


    LaunchedEffect(Unit) {
        // Llama a tu función de LÓGICA DE LECTURA
        LeerHorariosFirebase(
            //PASA UNA VARIABLE DE LECTURA
            onSuccess = { configuracionGuardada ->
                // ACTUALIZA LOS TEXTFIELD
                //TRASNFORMA LOS 630(configuracionGuardada.horarioDeActivacion) AL DIVIDRLOS POR 60 A ENTEROS DE 1
                horaActivacion = (configuracionGuardada.horarioDeActivacion / 60).toString()
                //TRANFORMA LOS SOBRANTES 630 0 30 A 30 MINUTOOS, % ES UN MODULO
                minActivacion = (configuracionGuardada.horarioDeActivacion % 60).toString()
                horaDesactivacion = (configuracionGuardada.horarioDeDesactivacion / 60).toString()
                minDesactivacion = (configuracionGuardada.horarioDeDesactivacion % 60).toString()
            },
            //MANDA EL ERROR CORRESPODIENTE
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelHorario,
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
            .verticalScroll(rememberScrollState()), // EXPANDE EL MARGEN A TODA LA PANTALLA
            verticalArrangement = Arrangement.Center, //CENTRADO AL CENTRO VERTICAL
            horizontalAlignment = Alignment.CenterHorizontally //CENTRADO AL CENTRO HORIZONTAL
        ) {
            Text(
                text = ("En este apartado podras manejar los horarios de encendido y apagado " +
                        "del equipo de energia del electrodomestico"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 10.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.fillMaxWidth(), //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.background(Color.Red)
                    //.aspectRatio(2f),  //HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

                )
            Text(
                text = ("Importante: Se usa horario de 24:00HRS"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 30.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.aspectRatio(2f)//HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                    //.fillMaxWidth(), //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.background(Color.LightGray)

                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

                )
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = "Configura el Horario de Encendido/Apagado:",
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 40.dp) //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.background(Color.Green)
                    .padding(bottom = 18.dp),
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 30.sp,
            )
            Text("Hora de Activacion",
                modifier = Modifier.
                padding(top = 16.dp))
            //ROW PONE LOS COMPONENTES UNO AL LADO DEL OTRO
            //VERTICALALIGNMENT ALINIA TTODO VERTICALMENTE AL CENTRO (Alignment.CenterVertically)
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = horaActivacion,
                    onValueChange = { horaActivacion = it },
                    label = { Text("HH") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(70.dp)
                )
                Text(":",
                    fontSize = 30.sp, //LE DA UN TAMAÑO SIMILAR AL DE LAS CAJAS DE HRS Y MINS
                    modifier = Modifier.
                    padding(horizontal = 8.dp))
                OutlinedTextField(
                    value = minActivacion,
                    onValueChange = { minActivacion = it },
                    label = { Text("MM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(70.dp)
                )
            }

            //ROW PONE LOS COMPONENTES UNO AL LADO DEL OTRO
            //VERTICALALIGNMENT ALINIA TTODO VERTICALMENTE AL CENTRO (Alignment.CenterVertically)---
            Text("Hora de Desactivación",
                modifier = Modifier.
                padding(top = 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = horaDesactivacion,
                    onValueChange = { horaDesactivacion = it },
                    label = { Text("HH") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(70.dp)
                )
                Text(":",
                    fontSize = 30.sp, //LE DA UN TAMAÑO SIMILAR AL DE LAS CAJAS DE HRS Y MINS
                    modifier = Modifier
                        .padding(horizontal = 8.dp))
                OutlinedTextField(
                    value = minDesactivacion,
                    onValueChange = { minDesactivacion = it },
                    label = { Text("MM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(70.dp)
                )
            }
            //BUTTON ES PARA CREAR UN BUTON
            Button(
                modifier = Modifier
                    .padding(top = 8.dp),
                //FUNCION QUE ESPERA A SER CLIKEADA
                onClick = {
                    //SE ESTABLECE VALORES QUE PUEDEN SER INT O NULO
                    val hrsActivacion = horaActivacion.toIntOrNull()
                    val minActivacion = minActivacion.toIntOrNull()
                    val hrsDesactivacion = horaDesactivacion.toIntOrNull()
                    val minDesactivacion = minDesactivacion.toIntOrNull()

                    //IF QUE VALIDA LOS NULOS DE LOS TIPOS
                    if (hrsActivacion == null || minActivacion == null || hrsDesactivacion == null || minDesactivacion == null) {
                        Toast.makeText(
                            contextoDelHorario,
                            "Formato de número incorrecto.",
                            Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }

                    if (hrsActivacion > 23) {
                        Toast.makeText(contextoDelHorario,
                            "La hora de activacion no puede ser mayor a 23.",
                            Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }
                    if (hrsDesactivacion > 23) {
                        Toast.makeText(contextoDelHorario,
                            "La hora de desactivacion no puede ser mayor a 23.",
                            Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }

                    // 4. Revisar el rango MÁXIMO de los minutos
                    if (minActivacion > 59) {
                        Toast.makeText(contextoDelHorario,
                            "Los minutos de activacion no pueden ser mayores a 59.",
                            Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }
                    if (minDesactivacion > 59) {
                        Toast.makeText(contextoDelHorario,
                            "Los minutos de desactivacion no pueden ser mayores a 59.",
                            Toast.LENGTH_LONG)
                            .show()
                        return@Button
                    }
                    // SE CONVIERTEN LOS HORAS Y MINUTOS PASADOS A INT A HORA(120)|MINUTO(30)=(150) NUEVAMENTE
                    val activacionTotalMinutos = (hrsActivacion * 60) + minActivacion
                    val desactivacionTotalMinutos = (hrsDesactivacion * 60) + minDesactivacion

                    if (activacionTotalMinutos == desactivacionTotalMinutos) {
                        Toast.makeText(contextoDelHorario, "Las horas no pueden ser iguales", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    //LLAMA A LA FUNCION LOGGEO Y LE PASA LOS VALORES A SER USADOS
                    EscribirHorariosFirebase(
                        //SE APLICA QUE EL STRING SE VUELVA EN INT
                        horarioDeActivacion = activacionTotalMinutos,
                        horarioDeDesactivacion = desactivacionTotalMinutos,
                        //ONSUCCES LE PASA LA VARIABLE CARGO PARA HACER LA DIFERENCIA DE INTERFAZ
                        onSuccess = {

                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                            Toast.makeText(
                                contextoDelHorario,
                                "¡horario ingresado con Exito!",
                                Toast.LENGTH_LONG
                            ).show()

                        },
                        //ONERROR LE PASA LA VARIABLE DEL ERROR
                        onError = { mensajeError ->
                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                            Toast.makeText(
                                contextoDelHorario,
                                mensajeError,
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    )
                }
            ) {
                //ES EL TEXTO QUE APARECERA EN EL BOTON
                Text("Enviar Horarios")


            }
        }
    }
}
