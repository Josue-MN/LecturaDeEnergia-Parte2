package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.Loggeo
@Composable
fun LoginScreen(navegacionControlada: NavController){
    //VAR ES PARA VARIABLES QUE CAMBIAN, VAL PAR ALAS QUE NO CAMBIAN

    //BY REMEBER LE DICE QUE RECUERDE DICHA VARIABLE
    //MUTABLESTATEOF CREA UNA VARIABLE CON MEMORIA EN BASE A UN STRING "", REMEMBER
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //VALOR DONDE SE MOSTRARAN LOS ERRORES COMO SI FUERA DISPENSABLE
    val contextoDelLogin = LocalContext.current


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
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = "Acceso a Cuenta",
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 16.dp),
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 30.sp,
            )
            // --> OutlinedTextField LE DA UN CONTORNO DE COLOR Y FONDO TRANSPARENTE
            OutlinedTextField(
                // --> VALUE ES EL TEXTO QUE SE DEBE MOSTRAR EN OUTLINEDTEXTFIELD
                value = usuario,
                // --> VALOR DE LA VARIABLE QUE SE MODIFICA CON EL VALUE Y IT
                onValueChange = {usuario = it},
                // --> LABEL ES LA ETIQUETA FANTSAMA QUE SE MUESTRA
                label = { Text("Usuario (ejemplo@gmail.com)") },
                //SE LLAMA SOLO AL USO DE TECLADO DE EMAILS
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                // --> VALUE ES EL TEXTO QUE SE DEBE MOSTRAR EN OUTLINEDTEXTFIELD
                value = password,
                // --> VALOR DE LA VARIABLE QUE SE MODIFICA CON EL VALUE Y IT
                onValueChange = {password = it},
                // --> LABEL ES LA ETIQUETA FANTSAMA QUE SE MUESTRA
                label = {Text("Contraseña")},
                //OCULTA LA CONTRASEÑA POR CARACTERES NULOS
                visualTransformation = PasswordVisualTransformation(),
                //SE LLAMA SOLO AL USO DE TECLADO DE CONTRASEÑAS
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            //BUTTON ES PARA CREAR UN BUTON
            Button(
                modifier = Modifier
                    .padding(top = 8.dp),
                //FUNCION QUE ESPERA A SER CLIKEADA
                onClick = {
                    //IF QUE VALIDA LOS NULOS DE LOS TIPOS
                    if (usuario.isBlank() || password.isBlank()){
                        //MUESTRA UN WIGET EMERGENTE CON TOAST EN PANTALLA CON MAKETEXT EN BASE AL VALUE CREADO ARRIBA
                        //DE DURACION CORTA CON LENGTH_SHORT Y SE MUESTRA CON SHOW
                        Toast.makeText(contextoDelLogin,
                            "Asegurate de ingresar un usuario y contraseña.",
                            Toast.LENGTH_SHORT).show()
                    }
                    //SI NO
                    else{
                        //LLAMA A LA FUNCION LOGGEO Y LE PASA LOS VALORES A SER USADOS
                        Loggeo(
                            //SE APLICA QUE EL TEXTO SE VUELVA EN MINUSCULAS
                            usuario = usuario.lowercase(),
                            password = password,
                            //ONSUCCES LE PASA LA VARIABLE CARGO PARA HACER LA DIFERENCIA DE INTERFAZ
                            onSuccess = { cargo ->
                                //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                Toast.makeText(contextoDelLogin,
                                    "Bienvenido a la app, $cargo",
                                    Toast.LENGTH_LONG).show()
                                if (cargo == "administrador"){
                                    navegacionControlada.navigate("rutasDeUAdmin") {
                                        // Esto borra la pantalla de Login del historial para que no pueda volver
                                        // Asumiendo que la ruta de tu login se llama "login_screen"
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                }
                                if (cargo == "basico"){
                                    navegacionControlada.navigate("rutasDeUBasico"){
                                        popUpTo("login"){
                                            inclusive = true
                                        }
                                    }
                                }
                            },
                            //ONERROR LE PASA LA VARIABLE DEL ERROR
                            onError = { mensajeError ->
                                //EL WIDGET EMERGENTE SE CREA CON TOAST, MAKETEXT MUSTRA EL TEXTO EMERGENTE
                                //Y CON LENGTH_LONG HACE QUE EL MENSAJE SE DEMORE MAS EN DESAPARECER
                                Toast.makeText(contextoDelLogin,
                                    mensajeError,
                                    Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                }
            ) {
                //ES EL TEXTO QUE APARECERA EN EL BOTON
                Text("Iniciar sesion")
            }
        }
    }
}