package com.example.lecturadeenergia.Firebase

data class ConfiguracionDeCoriente(
    var apagadoEncendido: Boolean = false
){
    constructor() : this(false)
}

