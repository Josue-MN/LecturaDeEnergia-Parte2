package com.example.lecturadeenergia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lecturadeenergia.NavegacionControladaDeLaApp.NavegacionSegunCargo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            //ESTABLECE EL COLOR DE LOS ICONOS SEGUN EL TEMA
            //SEGUN UN ESTADO DE BARRA (STATUSBARSTYLE) SE GENERA UNA BARRA DE SISTEMA
            // CON ESTILO (SUSTEMBARSTYLE) QUE SEA DE LUZ (LIGHT)
            statusBarStyle = SystemBarStyle.light(
                //PARA LUEGO BASICAMENTE LLAMAR A ANDROID QUE SEGUN LOS GRAFICOS
                //SE DEFINA UN COLOR Y CON FONDO TRANSPARENTE
                android.graphics.Color.TRANSPARENT,  //LIGHT
                android.graphics.Color.TRANSPARENT  //DARK
            )
        )
        setContent {
            NavegacionSegunCargo()
        }
    }
}