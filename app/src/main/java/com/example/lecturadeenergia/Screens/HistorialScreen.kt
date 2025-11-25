package com.example.lecturadeenergia.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lecturadeenergia.Firebase.LeerHistorialFirebase
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.lecturadeenergia.Firebase.ConfiguracionDeHistorial
import com.example.lecturadeenergia.Firebase.formatoDeTimestamp

@Composable
fun HistorialScreen(navegacionControlada: NavController){
    //SE CREA UN VALOR QUE GENERA UNA LISTA VACIA SEGUN UN DATA CLASS
    var historialDeMediciones by remember { mutableStateOf<List<ConfiguracionDeHistorial>>(emptyList()) }
    val contextoDelHistorial = LocalContext.current

    //ESPIA QUE CONECTA LA LOGICA CON LA UI
    //CARGA SIEMPRE Y CUANDO LA SCREEN SEA CARGADA
    LaunchedEffect(Unit) {
        LeerHistorialFirebase(
            onSuccess = { nuevoHistorialDeMediaciones ->
                //SE ESTABLECE LA NUEVA LISTA SI FIREBASE INGRESA UN CAMBIO
                historialDeMediciones = nuevoHistorialDeMediaciones
            },
            onError = { mensajeError ->
                Toast.makeText(
                    contextoDelHistorial,
                    mensajeError,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }

    // --> SCAFFOLD ES EL MARCO SUPERIOR DE LA PANTALLA
    // --> PADDINGVALUES SON LOS VALORES O PARAMETROS PARA EL
    // ESTILO DE LA APP COMO UN CSS PARA MANEJAR LA PANTALLA
    Scaffold { paddingValues ->

        //MUESTRA UNA CANTIDAD LIMITADA DE MEDICIONES
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                //.verticalScroll(rememberScrollState()), //->NO SE PUEDE USAR UN SCROOL DENTRO DE UN SCROLL
            contentPadding = PaddingValues(12.dp) // Un margen para toda la lista
        ) {
            //AÑADE UN ELEMENTO A LA LISTA
            item {
                Text(
                    text = ("En este apartado podras ver tu historial de mediaciones"),
                    // --> MODIFIER PARA EL ESTILO DEL TEXTO
                    modifier = Modifier
                        .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                        .padding(top = 16.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                        //.fillMaxWidth(), //HACE QUE LA EL TAMAÑO SEA MAXIMO
                    // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                    fontSize = 22.sp,

                    )
            }
            //BUCLE DE ITEMS QUE SEGUN UN HISTORIAL GENERA UNA NUEVA LISTA DE MEDCIONES
            //SEGUN LA FUNCION ITEMHISTORIAL
            items(historialDeMediciones) { medicionEncontrada ->
                ItemDeMedicionDeHistorial(medicionEncontrada = medicionEncontrada)
            }
        }
    }
}


//SE CREA UN MODEL PARA LAS MEDIACIONES
@Composable
fun ItemDeMedicionDeHistorial(medicionEncontrada: ConfiguracionDeHistorial) {
    //CONTENEDOR DE TARJETA
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Espacio entre cada tarjeta
        //LE DA A LA TARJETA UNA SOMBRA SEGUN (CardDefaults.cardElevation)
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        //CONTENEDOR DE FILA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, //PONE LAS COSAS LADO ALADO
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                //PASA EL NUMERO LARGO PAR APASARLO A FECHA
                text = formatoDeTimestamp(medicionEncontrada.tiempoImpreso), // Usa el ayudante
                fontSize = 14.sp
            )
            Text(
                text = "${medicionEncontrada.medicion} V",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}