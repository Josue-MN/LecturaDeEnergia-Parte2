package com.example.lecturadeenergia.NavegacionControladaDeLaApp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lecturadeenergia.Screens.LoginScreen

@Composable
fun NavegacionSegunCargo(){
    //SE GENERA UNA VARIABLE PARA ALMACENAR LA NAVEGACION Y QUE RECUERDE QUE AUNQUE SE
    //GIRE LA PANTALLA DEBE MANTENERSE(rememberNavController())
    val navegacionControlada = rememberNavController()

    //SE LE DA EL CONTROL REMOTO AL NAVHOST
    NavHost(navController = navegacionControlada,
        //SE ESTABLECE LA APP QUE SE DEBE MOSTRAR ANTES
        startDestination = "login",
        // --> EL MODIFIER SE LEE EN ORDEN
        ) {
        //SE GENERA EL COMPOSABLE DEL LOGIN PAR AINICAR SESION
        // GENERANDO LOS COMPOSABLES SE ESTABLCE QUE RUTA ES (composable("login"))
        //PARA QUE MUESTRE LA PANTALLA (LoginScreen(navegacionControlada = navegacionControlada) })
        // QUE CORRESPONDE SEGUN LOS VALORES QUE PIDE DICHA FUNCION

        //RUTA DE LOGIN PARA VOLVER CUANDO SE CIERRE SESION
        composable("login") { LoginScreen(navegacionControlada = navegacionControlada) }
        //RUTAS ADMIN
        composable("rutasDeUAdmin") { NavegacionControladaDeLaAppAdmin() }
        composable("rutasDeUBasico") { NavegacionControladaDeLaAppBasica() }
    }
}