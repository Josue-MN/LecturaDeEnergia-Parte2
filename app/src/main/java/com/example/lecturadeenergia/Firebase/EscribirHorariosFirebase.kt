package com.example.lecturadeenergia.Firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.database

//OPERACION ASINCRONICA CON PARAMETROS A RECIBIR
fun EscribirHorariosFirebase(
    horarioDeActivacion: Int,
    horarioDeDesactivacion: Int,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    //OBTIENE LA INSTANCIA DE LA REALTIME DATABASE DE FIREBASE
    val database = Firebase.database

    var uidObtenido = obtenerUid()

    //OBTIENE LA REFERENCIA DE FIELD DEL DATO
    val consulta = database.getReference("horariosConfigurables/$uidObtenido")


    //EMPAQUETA EN UNA VARIABLE LOS VaLORES SEGUN UN DATA CLASS(ConfiguracionDeRangos)
    val datosObtenidos = ConfiguracionDeHorarios(horarioDeActivacion, horarioDeDesactivacion)

    //CONSULTA HACE REFERENCIA DE DONDE SE VA A GUARDAR EL DATO Y SETVALUE LE VALOR
    consulta.setValue(datosObtenidos)
        //SE EJECUTA SI FUE UN EXITO
        .addOnSuccessListener {
            Log.d("Rangos", "Datos escritos correctamente en: $consulta")
            //LLAMA A LA FUNCION SUCCES PARA GUARDAR LO RECIBIDO
            onSuccess()
        }
        //SE EJECUTA SI FUE UN ERROR
        .addOnFailureListener { error ->
            //REGISTRA EL ERROR EN EL LOGCAT E
            Log.e("Rangos", "Error de ingreso de datos en: $consulta", error)
            //LLAMA A LA FUNCION Y GUARDA EL ERROR
            onError("Error: ${error.message}")
        }
}