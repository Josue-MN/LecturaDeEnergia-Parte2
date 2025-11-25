package com.example.lecturadeenergia.Firebase

data class ConfiguracionDeRangos(
    var rangoMinimo: Double = 0.0,
    var rangoMaximo: Double = 0.0
){
    constructor() : this(0.0, 0.0)
}

