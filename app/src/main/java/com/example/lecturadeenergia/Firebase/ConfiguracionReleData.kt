package com.example.lecturadeenergia.Firebase

data class ConfiguracionReleData(
    var estadoActivacionRele: Boolean = false,  // Estado actual del relé (true = encendido)
    var modoAutomaticoManual: Int = 0,
    var corrienteRele: Double = 0.0
)
