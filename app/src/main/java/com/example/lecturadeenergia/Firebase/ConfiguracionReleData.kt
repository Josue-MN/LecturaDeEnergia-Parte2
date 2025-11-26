package com.example.lecturadeenergia.Firebase

data class ConfiguracionReleData(
    var estado_rele: Boolean = false,  // Estado actual del relé (true = encendido)
    var modo_uso: Int = 0
)
