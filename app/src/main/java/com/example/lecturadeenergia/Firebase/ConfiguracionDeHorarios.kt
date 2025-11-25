package com.example.lecturadeenergia.Firebase

data class ConfiguracionDeHorarios(
    var horarioDeActivacion: Int = 0,
    var horarioDeDesactivacion: Int = 0
){
    //570 -> 9:30 || 1290 -> 21:30
    constructor() : this(1290, 570)
}

