package com.example.lecturadeenergia.Firebase

data class ConfiguracionDispositivoData(
    var dispositivo_nombre: String = "",                   // Nombre asignado al dispositivo
    var estado_agregado: Int = 0,                          // Indica si ya fue configurado (0 = no, 1 = sí)
    var corriente_detectada: Double = 0.0,                 // Corriente actual medida por el sensor

    // Objeto que contiene los datos de las alertas configuradas
    // Firebase interpreta este campo como un sub-objeto JSON automáticamente
    var alertas_dispositivo: ConfiguracionRangosAlertaDispositivo = ConfiguracionRangosAlertaDispositivo(),

    // Objeto que contiene los datos del relé
    // También es interpretado como un sub-objeto JSON
    var datos_rele: ConfiguracionReleData = ConfiguracionReleData()
) {

    // Constructor vacío requerido por Firebase
    // Firebase necesita este constructor sin parámetros para reconstruir
    // los objetos al leer información desde la base de datos
    constructor() : this(
        "",              // dispositivo_nombre
        0,               // estado_agregado
        0.0,             // corriente_detectada
        ConfiguracionRangosAlertaDispositivo(),
        ConfiguracionReleData()
    )
}
