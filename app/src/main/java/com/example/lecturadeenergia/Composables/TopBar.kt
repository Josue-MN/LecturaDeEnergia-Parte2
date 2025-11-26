package com.example.lecturadeenergia.Composables

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.lecturadeenergia.MainActivity

@ExperimentalMaterial3Api
@Composable
//SE LLAMA A LA FUNCION QUE MEDIANTE UN DATO QUE ESPERA RECIBIR QUE ES UN STRING
//GENERA UNA BARRA TOP (TOPAPPBAR) LA CUAL ES UNA FUNCION EXPERIMENTAL, QUE MEDIANTE
//UN TITULO SE GENERA EL TITULODE LA APP QUE APARECRA ARRIBA DE LA APLICACION
fun BarraDeArriba(
    tituloApp: String,
    salirApp: NavController){ //ESPERA EL TIPO DE DATO NAVCONTROL PARA VOLVER AL LOGIN

    // 1. OBTENEMOS EL CONTEXTO ACTUAL DE LA APLICACIÓN
    val contexto = LocalContext.current
    TopAppBar(
        title = {
            Text(tituloApp)
        },
        actions = {
            //BUTON QUE GENERA LO SIGUIENTE APENAS ES CLICKEADO
            Button(onClick = {
                //LOGICA DIRECTA CON FIREBASE QUE CIERRA LA SESION
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()

                // 3. REINICIO "NUCLEAR" DE LA APP
                // En vez de navegar, creamos un Intent para abrir MainActivity de nuevo
                val intent = Intent(contexto, MainActivity::class.java)

                // Estas banderas borran toda la actividad anterior y crean una nueva limpia
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                // Iniciamos la actividad
                contexto.startActivity(intent)
            }) {
                Text("Cerrar Sesion")
            }
        }
    )
}