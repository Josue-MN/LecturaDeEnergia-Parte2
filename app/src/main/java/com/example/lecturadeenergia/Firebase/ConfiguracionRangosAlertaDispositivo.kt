package com.example.lecturadeenergia.Firebase

data class ConfiguracionRangosAlertaDispositivo(
    var estadoDeActivacion: Boolean = false,        // Indica si la alerta está activada o no
    var rangoMinimo: Double = 0.0,     // Valor mínimo permitido antes de activar la alerta
    var rangoMaximo: Double = 0.0
)
