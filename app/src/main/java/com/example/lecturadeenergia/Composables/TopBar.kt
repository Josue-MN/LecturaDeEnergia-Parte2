package com.example.lecturadeenergia.Composables

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
//SE LLAMA A LA FUNCION QUE MEDIANTE UN DATO QUE ESPERA RECIBIR QUE ES UN STRING
//GENERA UNA BARRA TOP (TOPAPPBAR) LA CUAL ES UNA FUNCION EXPERIMENTAL, QUE MEDIANTE
//UN TITULO SE GENERA EL TITULODE LA APP QUE APARECRA ARRIBA DE LA APLICACION
fun BarraDeArriba(
    tituloApp: String,
    salirApp: NavController){ //ESPERA EL TIPO DE DATO NAVCONTROL PARA VOLVER AL LOGIN
    TopAppBar(
        title = {
            Text(tituloApp)
        },
        actions = {
            //BUTON QUE GENERA LO SIGUIENTE APENAS ES CLICKEADO
            Button(onClick = {
                //LOGICA DIRECTA CON FIREBASE QUE CIERRA LA SESION
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()

                salirApp.navigate("login") { //TE LLEVA AL LOGIN
                    popUpTo(0) //DESTRUYE EL HISTORIAL PARA QUE CUANDO QUIERA VOLVER ATRAS NO ME DEJE
                }
            }) {
                Text("Cerrar Sesion")
            }
        }
    )
}