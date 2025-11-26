package com.example.lecturadeenergia.Composables


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
//FUNCION QUE CREA UNA BARRA DE NAVEGACION INFERIROR QUE RECIBE A NAVCONTROLLER COMO UN CONTROL
fun BarraDeNavegacionDeBotonesAdmin(navegacionControlada: NavController){

    //SE CREA UN VALOR QUE ALMACENA EL VALOR DE LA PANTALLA ACTUAL USADA(currentBackStackEntry)
    //PARA CONVERTILA EN UN ESTADO (AsState()), QUEDANDO (currentBackStackEntryAsState())
    //BY SE USA PARA QUE MI VARIABLE SE SUSCRIBA A LO OBTENIDO
    val obsevadorDeNavegacionOculto by navegacionControlada.currentBackStackEntryAsState()
    //SE CREA UNA VARIABLE QUE LIMPIA TTODO LO ANTERIOR PARA OBTENER EL NOMBRE D EA RUTA,
    //-> SE VERIFICA QUE LO OBTENIDO NO SEA NULO(obsevadorDeNavegacionOculto?) PIDIENDOLE
    //-> EL DESTINO (destination?) SEGUN UNA RUTA(route)
    val rutaActual = obsevadorDeNavegacionOculto?.destination?.route

    //FUNCION QUE GENERA LAS CAJAS PARA LA BARRA DE ABAJO
    NavigationBar {
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "home"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("home") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Default.Home, "Home") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Home") }
        )
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "rangos"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("rangos") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Filled.Tune, "Rangos") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Rangos") }
        )
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "historial"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("historial") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Filled.History, "Historial") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Historial") }
        )
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "cortar"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("cortar") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Filled.Bolt, "Cortar") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Energia") }
        )
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "horarios"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("horarios") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Filled.AvTimer, "Horarios") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Horarios") }
        )
        //LA CAJA QUE TENDRA EL CONTENIDO
        NavigationBarItem(
            //EL SELECT SIRVE PARA VER SI ESTA RUTA ES LA CORRECTA PARA SER FALSE O TRUE
            //CUANDO EL OBSERVADOR CREADO (RUTAACTUAL) PARA SABER A CUAL SE LLAMA
            selected = (rutaActual == "rangoAlerta"),
            //LO QUE OCURRE CUANDO SE LLAMA AL BOTON(ONCLICK), SE LLAMA AL NAVCONTROLLER
            //PARA NAVEGAR(NAVIAGTE) A LA RUTA(")
            onClick = { navegacionControlada.navigate("rangoAlerta") },
            //ICONO QUE APARECERA SEGUN IMPLEMENTACION(ICON) PARA TENER ICONOS PARA LA BARRA
            //CON ICONS, LO DEMAS ES DE DONDE SE SACARA
            icon = { Icon(Icons.Filled.Notifications, "RangoAlerta") },
            //TEXTO QUE APARECERA EN LA BARRA DE ABAJO SEGUN EL BOTON
            label = { Text("Alertas") }
        )
    }
}