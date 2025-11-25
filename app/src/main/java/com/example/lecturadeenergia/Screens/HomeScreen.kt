package com.example.lecturadeenergia.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lecturadeenergia.R

@Composable
fun HomeScreen(navegacionControlada: NavController){
    // --> SCAFFOLD ES EL MARCO SUPERIOR DE LA PANTALLA
    // --> PADDINGVALUES SON LOS VALORES O PARAMETROS PARA EL
    // ESTILO DE LA APP COMO UN CSS PARA MANEJAR LA PANTALLA
    Scaffold { paddingValues ->
        // --> COLUMN MANEJARA TTODO EL CONTENIDO QUE SE MOSTRARA
        //  EN PANTALLA DE FORMA VERTICAL
        // --> EL MODIFIER SE LEE EN ORDEN
        Column(
            modifier = Modifier
                .padding(paddingValues) // MANEJA UN MARGEN PARA EL CONTENIDO
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),// EXPANDE EL MARGEN A TODA LA PANTALLA
            verticalArrangement = Arrangement.Top, //CENTRADO AL CENTRO VERTICAL
            horizontalAlignment = Alignment.CenterHorizontally //CENTRADO AL CENTRO HORIZONTAL
        ) {
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = "¡BIENVENIDO A LA APLICACION!",
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(bottom = 16.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 30.sp,
            )
            // --> TEXT ES EL TEXTO QUE SE VA A MOSTRAR
            Text(
                text = ("En esta app podras manejar algunas de las funciones de tu producto " +
                        "de energia para la gestion de tus electrodomesticos, para ir a otras " +
                        "secciones, presiona abajo la funcion a alegir"),
                // --> MODIFIER PARA EL ESTILO DEL TEXTO
                modifier = Modifier
                    .padding(horizontal = 10.dp) //MANEJAA EL ESPACIO ENTRE UNAA PARED Y LO HORIZONTAL
                    .padding(top = 16.dp), //MANEJAA EL ESPACIO ENTRE UNA PARED Y LO QUE ESTE ABAJO
                    //.fillMaxWidth() //EXPANDE EL MARGEN DE TEXTO HASTA ABAJO
                    //.aspectRatio(2f),  //HACE QUE LA ALTURA SEA IGUAL AL ANCHO
                // --> FONTSIZE ES EL TAMAÑO DE LA LETRA
                fontSize = 22.sp,

            )
            //PARA PONER IMAGENES
            Image(
                //SE GENERA LA FUNCION (PAINTER) QUE MEDIANTE LA BUSQUEDA DE UN RECUERSO (painterResource)
                //MEDIANTE UN ID SE BUSCA UN RECUERSO(R) EN LA CARPETA Y LUEGO LA IMAGEN
                painter = painterResource(id = R.drawable.homeimage),
                //DESCRIPCION DE LA IMAGEN
                contentDescription = "Imagen del Home",
                //SE ESTABLECE MEDIANTE UN MODIFICADOR EL TAMAÑO(SIZE) DE LA IMAGEN
                modifier = Modifier
                    .size(250.dp)
                    //.background(Color.LightGray) //->SIRVE PARA VER EL TAMAÑO DE LA FOTO
            )
        }
    }
}