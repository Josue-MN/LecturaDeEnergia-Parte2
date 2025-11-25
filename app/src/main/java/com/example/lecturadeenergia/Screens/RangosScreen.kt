package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.lecturadeenergia.Firebase.EscribirRangosFirebase
import com.example.lecturadeenergia.Firebase.LeerRangosFirebase


@Composable
fun RangosScreen(navegacionControlada: NavController){
    //VAR ES PARA VARIABLES QUE CAMBIAN, VAL PAR ALAS QUE NO CAMBIAN

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var rangoMinimo by remember { mutableStateOf("") }
    var rangoMaximo by remember { mutableStateOf("") }

    //VALOR DONDE SE MOSTRARAN LOS ERRORES COMO SI FUERA DISPENSABLE
    val contextoDelRango = LocalContext.current


    LaunchedEffect(Unit) {
        // Llama a tu función de LÓGICA DE LECTURA
        LeerRangosFirebase(
            //PASA UNA VARIABLE DE LECTURA
            onSuccess = { configuracionGuardada ->
                // ACTUALIZA LOS TEXTFIELD
                rangoMinimo = configuracionGuardada.rangoMinimo.toString()
                rangoMaximo = configuracionGuardada.rangoMaximo.toString()
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
                text = ("En este apartado podras manejar los rangos minimos y maximos del apagado " +
                        "del equipo de energia del electrodomestico"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 10.dp) //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    .fillMaxWidth() //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.background(Color.Red)
                    .aspectRatio(3f),  //HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

                )
            Text(
                text = ("Importante: No debe superar el valor de 10.0"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 2.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.fillMaxWidth() //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.background(Color.LightGray)
                    //.aspectRatio(3f),  //HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

                )
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = "Configura el rango:",
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 2.dp) //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.background(Color.Green)
                    .padding(bottom = 18.dp),
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 30.sp,
            )
            // --> OutlinedTextField LE DA UN CONTORNO DE COLOR Y FONDO TRANSPARENTE
            OutlinedTextField(
                // --> VALUE ES EL TEXTO QUE SE DEBE MOSTRAR EN OUTLINEDTEXTFIELD
                value = rangoMinimo,
                // --> VALOR DE LA VARIABLE QUE SE MODIFICA CON EL VALUE Y IT
                onValueChange = {rangoMinimo = it},
                // --> LABEL ES LA ETIQUETA FANTSAMA QUE SE MUESTRA
                label = { Text("Valor minimo del rango") },
                placeholder = {Text("Ej: 1.0")},
                //SE LLAMA SOLO AL USO DE TECLADO DE DECIMAL
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                // --> VALUE ES EL TEXTO QUE SE DEBE MOSTRAR EN OUTLINEDTEXTFIELD
                value = rangoMaximo,
                // --> VALOR DE LA VARIABLE QUE SE MODIFICA CON EL VALUE Y IT
                onValueChange = {rangoMaximo = it},
                // --> LABEL ES LA ETIQUETA FANTSAMA QUE SE MUESTRA
                label = { Text("Valor maximo del rango") },
                placeholder = {Text("Ej: 5.0")},
                //SE LLAMA SOLO AL USO DE TECLADO DE DECIMAL
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            //BUTTON ES PARA CREAR UN BUTON
            Button(
                modifier = Modifier
                    .padding(top = 8.dp),
                //FUNCION QUE ESPERA A SER CLIKEADA
                onClick = {
                    //IF QUE VALIDA LOS NULOS DE LOS TIPOS
                    if (rangoMinimo.isBlank() || rangoMaximo.isBlank()) {
                        //MUESTRA UN WIGET EMERGENTE CON TOAST EN PANTALLA CON MAKETEXT EN BASE AL VALUE CREADO ARRIBA
                        //DE DURACION CORTA CON LENGTH_SHORT Y SE MUESTRA CON SHOW
                        Toast.makeText(
                            contextoDelRango,
                            "Asegurate de ingresar ambos valores.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    //ESTABLECE QUE QUE EL RANGO PUEDA SER DECIAML O NULO
                    val rangoMinimoANumero = rangoMinimo.toDoubleOrNull()
                    val rangoMaximoANumero = rangoMaximo.toDoubleOrNull()


                    if (rangoMinimoANumero == null || rangoMaximoANumero == null) {
                        Toast.makeText(
                            contextoDelRango,
                            "Asegurate de ingresar valores validos.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (rangoMinimoANumero >= rangoMaximoANumero) {
                        Toast.makeText(
                            contextoDelRango,
                            "Asegurate de que el valor minimo sea menor que el maximo.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (rangoMinimoANumero < 0) {
                        Toast.makeText(
                            contextoDelRango,
                            "El valor minimo no pueden ser negativo.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (rangoMaximoANumero < 0) {
                        Toast.makeText(
                            contextoDelRango,
                            "El valor maximo no pueden ser negativo.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (rangoMinimoANumero > 10.0) {
                        Toast.makeText(
                            contextoDelRango,
                            "Asegurate de que el valor minimo no sean mayor a 10.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (rangoMaximoANumero > 10.0) {
                        Toast.makeText(
                            contextoDelRango,
                            "Asegurate de que el valor maximo no sean mayor a 10",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    //LLAMA A LA FUNCION LOGGEO Y LE PASA LOS VALORES A SER USADOS
                    EscribirRangosFirebase(
                        //SE APLICA QUE EL TEXTO SE VUELVA EN MINUSCULAS
                        rangoMinimo = rangoMinimoANumero,
                        rangoMaximo = rangoMaximoANumero,
                        //ONSUCCES LE PASA LA VARIABLE CARGO PARA HACER LA DIFERENCIA DE INTERFAZ
                        onSuccess = {

                            //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                            //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                            Toast.makeText(
                                contextoDelRango,
                                "¡rangos ingresados con Exito!",
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
                //ES EL TEXTO QUE APARECERA EN EL BOTON
                Text("Enviar Rangos")


            }
        }
    }
}