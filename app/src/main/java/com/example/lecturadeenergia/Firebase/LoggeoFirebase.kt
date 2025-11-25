package com.example.lecturadeenergia.Firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.database.database

//-> SE CREA LA FUNCION LOGGEO CON VALORES QUE RECIBE
fun Loggeo(
    usuario : String,
    password : String,
    //-> ONSUCCESS ES UNA FUNCION CALLBACK QUE LE PASARA UN CARGO A LA PANTALLA LOGIN
    //   SE PASA EL CARGO COMO UN STRING QUE ENCONTRO, SE ACTIVA SOLO SITODO SALE BIEN
    onSuccess : (cargo: String) -> Unit,
    //ONERROR FUNCION CALLBACK QUE LE PASARA UN STRING SI HAY ERRORES
    onError: (String) -> Unit
){
    //SE OBTIENE LA INSTANCIA DE LOGIN DE FIREBASE
    val autentication = Firebase.auth
    //SE OBTIENE LA INSTACIA DE LA BASE DE DATOS PARA ESTABLECER CONEXION CON FIREBASE
    val database = Firebase.database

    //SE GENERA UN LOG PARA VER EN LA CONSOLA QUE SE ESTE EJECUTANDO EL CODIGO
    Log.d("Loggeo", "Intentando iniciar sesion")


    //LLAMO A LA FUNCION DE FIREBASE CON LA VARIABLE DEFINIDA PARA INICIO DE SESION
    //PARA SABER SI EXISTE EL USUARIO Y CONTRASEÑA INGRESADOS EN LA SCREEN
    autentication.signInWithEmailAndPassword(usuario, password)
        //ESPERA EL EVENTO Y LE PASA UNA VARIABLE PARA USAR SI EL LOGGEO FUE EXITOSO
        .addOnSuccessListener{ resultadoDelLoggeo ->
            //SE CREA LA VARIBLE NO MUTABLE Y CON EL VALOR PASADO SE OBTIENE EL UID
            //DE DICHO USUARIO(USER?) CON EL UID
            val uidNombreConsultado = resultadoDelLoggeo.user?.uid

            //SE PREGUNTA SI EL NOMBRE UID NO ES NULO
            if (uidNombreConsultado == null) {
                onError("Error de loggeo, no se pudo conseguir el nombre de usuario(UID)")

                //SE DEVUELVE A LA FUNCION PARA VOLVER A PREGUNTAR
                return@addOnSuccessListener
            }

            Log.d("Loggeo", "Intento de acceder al usuario: $uidNombreConsultado")

            //SE PREPARA LA CONSULTA SEGUN UNA VARIBLE PARA OBTENER LOS USUARIOS
            //CON GETREFERENCE(OBTEN LA REFERENCIA)
            val refUsuario = database.getReference("usuarios")
            //SEGUN LA VARIBLE, ORDENA LOS USUARIOS POR NOMBRE(ORDERCHILD)
            val consulta = refUsuario.orderByChild("nombre")
                //Y SE PÍDE SOLO EL REGISTRO CON EL NOMBRE A CONSULTAR (EQUALTO)
                // ANTERIOMENTE OBTENIDO (UIDNOMBRECONSULTADO)
                .equalTo(uidNombreConsultado)
            //EN BASE A LA VARIABLE DE CONSULTA DEFINIDA(CONSULTA) SE OBTIENE EL CARGO (GET)
            consulta.get()
                //SI ES CORRECTO SE LE PASA UNA VARIABLE (LANZAMIENTOINSTANTANEO)
                .addOnSuccessListener { lanzamientoInstantaneo ->

                    //SE COMPRUEBA QUE REALEMNTE ENCONTRO ALGO (LANZAMIENTOINSTANTAEO.EXISTS())
                    //Y SE COMPRUABA SI SE OBTUVO EL HIJO (LANZAMIENTOINSTANAEO.HASCHILDREN())
                    if(lanzamientoInstantaneo.exists() && lanzamientoInstantaneo.hasChildren()){
                        //SE OBTIENE EL UNICO(CHILDREN) Y PRIMER(FIRST) USUARIO ENCONTRADO
                        // DEL LOGIN
                        val usuarioEncontrado = lanzamientoInstantaneo.children.first()

                        //SE CREA LA VARIABLE Y SE OBTIENE LO ENCONTRADO(GETVALUE) QUE
                        //SEGUN LA CLASE (USUARIO) DE LA CLASE (CLASS) JAVA
                        val usuarioCargo = usuarioEncontrado.getValue(Usuario::class.java)

                        //SE COMPRUEBA QUE EL CARGO NO SEA NULO (NULL) Y QUE NO ESTE VACIO(ISNOTBLACK)
                        if (usuarioCargo != null && usuarioCargo.cargo.isNotBlank()){
                            Log.d("Loggeo", "El cargo obtenido es ${usuarioCargo.cargo}")
                            //PARA FINAMENTE DEVOLVER EL CARGO(USUARIOCARGO.CARGO)
                            //MEDIANTE EL ONSUCCES A LA SCREEN DEL LOGIN
                            onSuccess(usuarioCargo.cargo)
                        }
                        //SI NO SE ENCONTRO SE INFORMA A ONERROR DEL ERROR
                        else{
                            Log.e("Loggeo","Usuario encontrado pero sin cargo en la base de datos")
                            onError("Usuario encontrado pero sin cargo en la base de datos")
                        }
                    }
                    //SI NO SE ENCONTRO SE INFORMA A ONERROR DEL ERROR
                    else{
                        Log.e("Loggeo","Usuario loggeado, pero no encontrado en la base de datos")
                        onError("Usuario loggeado, pero no encontrado en la base de datos")
                    }
                }
                //SI NO SE HAY UN ERROR EN LA BASE DE DATOS SE PASA EL ERROR COMO VARIABLE
                .addOnFailureListener { errorBaseDeDatos ->
                    Log.e("Loggeo", "Error de la consulta en la base de datos.", errorBaseDeDatos)
                    //SI NO SE ENCONTRO SE INFORMA A ONERROR DEL ERROR SEGUN
                    // UN MENSSAGE PARA QUE NO SE TAN LARGO EL ERROR
                    onError("Error en la base de datos: ${errorBaseDeDatos.message}")
                }

        }
        //SI EL EVENTO FALLA SE VINE PARA ACA Y LE PASA UNA VARIABLE
        .addOnFailureListener { errorLoggeo ->
            Log.e("Loggeo", "Error de Loggeo.", errorLoggeo)

            //SE ESTABLECE LA VARIABLE NO MUTABLE Y SEGUN(WHEN), Y EL ERRORlOGGEO
            //SE DIREJE A UNA DIRRECCION O OTRA(IS)
            val mensajeDeError = when (errorLoggeo){
                //SI EL ERROR ES DE USUARIO MUESTRA ESO, PERO COMO FIREBASE.AUTHETICATION
                //TIENW UN MANEJO SEGURO, ESTO NO SE MUESTRA
                is FirebaseAuthInvalidUserException -> "Usuario no registrado"
                //SI EL ERROR ES DE CONTRASEÑA
                is FirebaseAuthInvalidCredentialsException -> "Usuario o contraseña incorrectos."
                //EN CASO DE NO ENCONTRAR EL ERROR, MUESTRA EL MENSAJE DE ERROR CON MESSAGE
                //Y NO UN ERROR DEMASIADO LARGO
                else -> "Error de loggeo: ${errorLoggeo.message}"
            }
            //LLAMA A LA VARIABLE Y LE PASA DICHO ERROR
            onError(mensajeDeError)
        }
}