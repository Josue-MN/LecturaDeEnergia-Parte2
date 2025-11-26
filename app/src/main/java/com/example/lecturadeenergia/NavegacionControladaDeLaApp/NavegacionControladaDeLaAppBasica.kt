package com.example.lecturadeenergia.NavegacionControladaDeLaApp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lecturadeenergia.Composables.BarraDeArriba
import com.example.lecturadeenergia.Composables.BarraDeNavegacionDeBotonesBasico
import com.example.lecturadeenergia.Screens.AlertasReleScreen
import com.example.lecturadeenergia.Screens.CortarReleScreen
import com.example.lecturadeenergia.Screens.CortarScreen
import com.example.lecturadeenergia.Screens.HistorialScreen
import com.example.lecturadeenergia.Screens.HomeScreen
import com.example.lecturadeenergia.Screens.LoginScreen

@Composable
//ESTE  @ SE GENERA PARA AVISARLE A KOTLIN QUE SE QUE USARE FUNCIONES EXPERIEMNTALES Y NO
//ME GENERE ERRORES
@OptIn(ExperimentalMaterial3Api::class)
fun NavegacionControladaDeLaAppBasica(){

    //SE GENERA UNA VARIABLE PARA ALMACENAR LA NAVEGACION Y QUE RECUERDE QUE AUNQUE SE
    //GIRE LA PANTALLA DEBE MANTENERSE(rememberNavController())
    val navegacionControlada = rememberNavController()
    //SE CREA UN VALOR QUE ALMACENA EL VALOR DE LA PANTALLA ACTUAL USADA(currentBackStackEntry)
    //PARA CONVERTILA EN UN ESTADO (AsState()), QUEDANDO (currentBackStackEntryAsState())
    //BY SE USA PARA QUE MI VARIABLE SE SUSCRIBA A LO OBTENIDO
    val obsevadorDeNavegacionOculto by navegacionControlada.currentBackStackEntryAsState()
    //SE CREA UNA VARIABLE QUE LIMPIA TTODO LO ANTERIOR PARA OBTENER EL NOMBRE D EA RUTA,
    //-> SE VERIFICA QUE LO OBTENIDO NO SEA NULO(obsevadorDeNavegacionOculto?) PIDIENDOLE
    //-> EL DESTINO (destination?) SEGUN UNA RUTA(route)
    val rutaActual = obsevadorDeNavegacionOculto?.destination?.route

    // VARIABLE QUE SEGUN UN MAPOF DECIDE EL TITULO DE LA APP MEDIANTE UN VALOR
    //QUE BUSCA OTRO

    // (rutaActual) -> DONDE SE RECEPCIONA EL NOMBRE D ELA RUTA A TO B
    val tituloDePantalla = mapOf(
        "home" to "Inicio",
        "historial" to "Historial de Mediciones",
        "cortar" to "Control de Energía",
        "estadoRele" to "Estado del Rele",
        "alertasRele" to "Alertas del Rele",
    )[rutaActual] ?: "home" //ESTABLECE LA RUTA ACTUAL Y EN CASO DE ERRORES, COMO SI LA VARIABLE SE DEFINIERA POR DEFECTO


    // SE GENERA UNA VARIABLE QUE CUANDO LA RUTA SEA DISTINTA DE LOGIN
    // NO DEBE MOSTRARSE LA TOPBAR
    val mostrarBarras = rutaActual != "login"

    // --> SCAFFOLD ES EL MARCO SUPERIOR DE LA PANTALLA
    // --> PADDINGVALUES SON LOS VALORES O PARAMETROS PARA EL
    // ESTILO DE LA APP COMO UN CSS PARA MANEJAR LA PANTALLA
    Scaffold(
        //FUNCION QUE PONE ARRIBA LA TOPBAR (BARRADEARRIBA) SIEMPRE Y CUANDO
        //EL SEA DISTINRA DE LOGIN (if (mostrarBarras) {), YA QUE EL STRING DEFINIDO DE LA
        // FUNCION SERA LA RUTA ESTABLECIDA EN TITULOPANTALLA
        topBar = {
            if (mostrarBarras) {
                BarraDeArriba(
                    tituloApp = tituloDePantalla,
                    salirApp = navegacionControlada)
                }
            },
        //FUNCION QUE PONE ABAJO LA BOTTOMBAR (BarraDeNavegacionDeBotones) SIEMPRE Y CUANDO
        //EL SEA DISTINRA DE LOGIN (if (mostrarBarras) {), YA QUE EL STRING DEFINIDO DE LA
        // FUNCION SERA LA RUTA ESTABLECIDA EN NAGECACIONCONTROLADA
        bottomBar = {
            //BOTTOM BAR PAR ADMIN
            if (mostrarBarras) {
                BarraDeNavegacionDeBotonesBasico(navegacionControlada = navegacionControlada)
                }

            }
        // MANEJA UN MARGEN PARA EL CONTENIDO
        ) { paddingValues ->

        // ESTE ES EL CONTROL REMOTO DEFINIDO ANTES PARA SER MOSTRADON SEGUN
        NavHost(
            //SE LE DA EL CONTROL REMOTO AL NAVHOST
            navController = navegacionControlada,
            //SE ESTABLECE LA APP QUE SE DEBE MOSTRAR ANTES
            startDestination = "home",
            // --> EL MODIFIER SE LEE EN ORDEN
            modifier = Modifier
                .padding(paddingValues)// MANEJA UN MARGEN PARA EL CONTENIDO
            ) {
            //SE GENERAN LOS COMPOSABLES(composable) PARA QUE SEPA SEGUN LA RUTA(HOME)
            //CUAL PANTALLA (HomeScreen) LEER SEGUN LA NAVEGACION CONTROLADA


            //RUTAS BASICA
            composable("login") { LoginScreen(navegacionControlada = navegacionControlada) }
            composable("home") { HomeScreen(navegacionControlada = navegacionControlada) }
            composable("historial") { HistorialScreen(navegacionControlada = navegacionControlada) }
            composable("cortar") { CortarScreen(navegacionControlada = navegacionControlada) }
            composable("estadoRele") { CortarReleScreen(navegacionControlada = navegacionControlada) }
            composable("alertasRele") { AlertasReleScreen(navegacionControlada = navegacionControlada) }
            }
        }
    }