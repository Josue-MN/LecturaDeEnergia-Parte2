package com.example.lecturadeenergia.Firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun LeerReleDataFirebase(
    //ONSUCCES DEVUELVE LA CONFIGURACION DE RANGOS
    onSuccess: (ConfiguracionReleData) -> Unit,
    onError: (String) -> Unit
) {

    //OBTIENE LA INSTANCIA DE LA REALTIME DATABASE DE FIREBASE
    val database = Firebase.database

    var uidObtenido = obtenerUid()

    //OBTIENE LA REFERENCIA DE FIELD DEL DATO
    val consulta = database.getReference("ReleDataConfigurables/$uidObtenido")


    //CONSULTA HACE REFERENCIA DE DONDE SE VA A LEER EL DATO Y GET LE DA VALOR
    consulta.get()
        //SI ES CORRECTO SE LE PASA UNA VARIABLE (LANZAMIENTOINSTANTANEO)
        .addOnSuccessListener { lanzamiento ->
            //SE COMPRUEBA QUE REALEMNTE ENCONTRO ALGO (LANZAMIENTOINSTANTAEO.EXISTS())
            if (lanzamiento.exists()) {
                //SE CREA LA VARIABLE Y SE OBTIENE LO ENCONTRADO(GETVALUE) QUE
                //SEGUN LA CLASE (::) SE LE DA LA CLASE (CLASS) JAVA
                val datosObtenidos = lanzamiento.getValue(ConfiguracionReleData::class.java)
                //SE COMPRUEBA QUE EL CARGO NO SEA NULO (NULL)
                if (datosObtenidos != null) {
                    Log.d("ReleData", "El rele activacion obtenido es ${datosObtenidos.estadoActivacionRele}, el modo ${datosObtenidos.modoAutomaticoManual}, corriente ${datosObtenidos.corrienteRele}")
                    //PARA FINAMENTE DEVOLVER EL DATO
                    //MEDIANTE EL ONSUCCES A LA SCREEN DEL LOGIN
                    onSuccess(datosObtenidos)
                } else {
                    Log.d("ReleData", "No se puedo leer los datos de la base de datos")
                    //PARA FINAMENTE DEVOLVER EL DATO
                    //MEDIANTE EL ONSUCCES A LA SCREEN DEL LOGIN
                    onError("Error en la base da datos: No se pudieron leer los datos.")
                }
            } else {
                Log.d("ReleData", "No hay datos en la base de datos")
                //PARA FINAMENTE DEVOLVER EL DATO
                //MEDIANTE EL ONSUCCES A LA SCREEN DEL LOGIN
                onSuccess(ConfiguracionReleData(false))
            }
        }
        //SI NO SE HAY UN ERROR EN LA BASE DE DATOS SE PASA EL ERROR COMO VARIABLE
        .addOnFailureListener { error ->
            Log.e("FirebaseRead", "Error al leer rangos", error)
            //SI NO SE ENCONTRO SE INFORMA A ONERROR DEL ERROR SEGUN
            // UN MENSSAGE PARA QUE NO SE TAN LARGO EL ERROR
            onError("Error de base de datos: ${error.message}")
        }
}