package com.example.lecturadeenergia.Firebase

data class ConfiguracionDeHistorial(
    var medicion: Double = 0.0,
    var tiempoImpreso: Long = 0L //SE GUARDA LA HORA COMO LONG CON 0L
){
    //570 -> 9:30 || 1290 -> 21:30
    constructor() : this(0.0, 0)
}

