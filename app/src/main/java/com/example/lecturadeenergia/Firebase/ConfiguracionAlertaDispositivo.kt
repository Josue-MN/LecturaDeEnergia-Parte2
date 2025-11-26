package com.example.lecturadeenergia.Firebase

data class ConfiguracionAlertaDispositivo(
    var estado: Boolean = false,        // Indica si la alerta está activada o no
    var rango_minimo: Double = 0.0,     // Valor mínimo permitido antes de activar la alerta
    var rango_maximo: Double = 0.0
)
