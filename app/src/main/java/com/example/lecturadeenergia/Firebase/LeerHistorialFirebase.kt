package com.example.lecturadeenergia.Firebase

import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun LeerHistorialFirebase(
    //ONSUCCES DEVUELVE LA CONFIGURACION DE RANGOS
    onSuccess: (List<ConfiguracionDeHistorial>) -> Unit,
    onError: (String) -> Unit
) {

    //OBTIENE LA INSTANCIA DE LA REALTIME DATABASE DE FIREBASE
    val database = Firebase.database

    var uidObtenido = obtenerUid()

    //OBTIENE LA REFERENCIA DE FIELD DEL DATO
    val consulta = database.getReference("historialDeMedicion/$uidObtenido")

    //CONSULTA HACE REFERENCIA DE DONDE SE VA A LEER EL DATO Y GET LE DA VALOR
    consulta.get()
        .addOnSuccessListener { lanzamiento ->
            //SE GENERA UNA VARIABLE QUE EN BASE A UN LANZAMIENTO SE BUSCA UN HIJO EN UN MAPA
            //QUE NO SEA NULO, Y CON UN GET VALUE SE OBTIENE EL HISTORIAL
            val listaEncontrada = lanzamiento.children.mapNotNull {
                it.getValue(ConfiguracionDeHistorial::class.java)
            }

            //SI TTODO SALE BIEN EN LA BUSQUEDA DE LISTAENCONTRADA SE MUESTRA EL MAPA(REVERSED)
            onSuccess(listaEncontrada.reversed())
        }
}

fun formatoDeTimestamp(tiempoImpreso: Long): String {
    //SE INTENTA CORRER UNA FUNCION PELIGROSA COMO CONVERSION DE UN NUMERO LARGO
    //CON RUNCATCHING
    return runCatching {
        //SE ESTABLECE COMO QUIERO LA FECHA SEGUN UN PATRON Y LA LOCALIDAD DE FECHA
        val formateador = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
        //SE CREA UNA VARIABLE QUE OBTIENE LA FECHA CON EL FORMATO CORRECTO SEGUN TIMESTAP
        val fecha = Date(tiempoImpreso)
        formateador.format(fecha)
        // .getOrDefault() devuelve el resultado si tuvo éxito,
        // o "fecha inválida" si falló (catch).
    //ESPERA EN CASO DE QUE HAYA UN ERROR
    }.getOrDefault("fecha inválida")
}