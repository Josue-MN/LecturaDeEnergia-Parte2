package com.example.lecturadeenergia.Firebase

data class Usuario(
    var cargo: String = "",
    var nombre: String = ""
){
    constructor() : this("", "")
}
