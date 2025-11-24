import kotlin.math.abs

//Definición de Clases y Excepciones

data class Temperatura(val valor: Double, val escala: String)

sealed class TemperaturaException(message: String) : Exception(message) {
    class TemperaturaImposibleException(valor: Double, escala: String) :
        TemperaturaException("Error: $valor $escala es físicamente imposible. La temperatura más baja posible es el cero absoluto.")

    class EntradaInvalidaException(entrada: String) :
        TemperaturaException("Error: '$entrada' no es un número válido.")
}

// Constantes
const val CERO_ABSOLUTO_CELSIUS = -273.15
const val CERO_ABSOLUTO_KELVIN = 0.0

// Funciones de Conversión


// Implementa celsiusAFahrenheit()

fun celsiusAFahrenheit(celsius: Double): Double {

    return celsius * (9.0 / 5.0) + 32.0
}


 //Implementa kelvinACelsius()

fun kelvinACelsius(kelvin: Double): Double {

    return kelvin - 273.15
}


 //Extra: Conversión inversa Fahrenheit a Celsius

fun fahrenheitACelsius(fahrenheit: Double): Double {

    return (fahrenheit - 32.0) * (5.0 / 9.0)
}

// Función de Validación


fun validarTemperatura(celsius: Double): Result<Double> {
    // Comprueba si la temperatura es menor que el cero absoluto (-273.15 °C)
    return if (celsius < CERO_ABSOLUTO_CELSIUS) {
        Result.failure(TemperaturaException.TemperaturaImposibleException(celsius, "°C"))
    } else {
        Result.success(celsius)
    }
}

// Implementa convertir()


fun convertir(valor: String, opcion: Int): Result<Temperatura> {
    // Manejar toDoubleOrNull() y errores comunes
    val tempInicial = valor.toDoubleOrNull()
        ?: return Result.failure(TemperaturaException.EntradaInvalidaException(valor))

    return when (opcion) {
        1 -> { // Celsius -> Fahrenheit
            validarTemperatura(tempInicial)
                .onSuccess { celsius ->
                    val fahrenheit = celsiusAFahrenheit(celsius)
                    // Alerta de Cero Absoluto
                    if (abs(celsius - CERO_ABSOLUTO_CELSIUS) < 0.01) {
                        println("ALERTA: Estás convirtiendo el Cero Absoluto (-273.15 °C).")
                    }
                    return Result.success(Temperatura(fahrenheit, "°F"))
                }
                .onFailure {
                    return Result.failure(it)
                }
            Result.failure(Exception("Error inesperado en conversión C->F."))
        }

        2 -> { // Kelvin -> Celsius
            // Validación de cero absoluto para Kelvin (0K)
            if (tempInicial < CERO_ABSOLUTO_KELVIN) {
                return Result.failure(TemperaturaException.TemperaturaImposibleException(tempInicial, "K"))
            }
            // Alerta de Cero Absoluto
            if (abs(tempInicial - CERO_ABSOLUTO_KELVIN) < 0.01) {
                println("⚠ALERTA: Estás convirtiendo el Cero Absoluto (0 K).")
            }

            val celsius = kelvinACelsius(tempInicial)
            return Result.success(Temperatura(celsius, "°C"))
        }

        3 -> { // Fahrenheit -> Celsius (Extra)
            val celsiusEquivalente = fahrenheitACelsius(tempInicial)

            validarTemperatura(celsiusEquivalente)
                .onSuccess { celsius ->
                    return Result.success(Temperatura(celsius, "°C"))
                }
                .onFailure {
                    // Muestra el valor original de Fahrenheit en el error
                    return Result.failure(TemperaturaException.TemperaturaImposibleException(tempInicial, "°F"))
                }
            Result.failure(Exception("Error inesperado en conversión F->C."))
        }

        else -> Result.failure(Exception("Opción no válida."))
    }
}

// Funciones Auxiliares


fun Double.format(digits: Int) = String.format("%.${digits}f", this)


fun manejarResultadoPrueba(resultado: Result<Temperatura>) {
    if (resultado.isSuccess) {
        val temp = resultado.getOrThrow()
        println("PRUEBA ÉXITO: ${temp.valor.format(2)} ${temp.escala}")
    } else {
        println("PRUEBA FALLO: ${resultado.exceptionOrNull()?.message}")
    }
}

// Implementa menuInteractivo

fun menuInteractivo() {
    val historial = mutableListOf<String>()
    var continuar = true

    while (continuar) {
        println("\n===Conversor de Temperaturas===")
        println("1. Celsius (°C) a Fahrenheit (°F)")
        println("2. Kelvin (K) a Celsius (°C)")
        println("3. Fahrenheit (°F) a Celsius (°C)")
        println("4. Mostrar Historial ")
        println("0. Salir")
        print("Elige una opción: ")

        // Capturar la opción leída para evitar el error 'it'
        val opcionSeleccionada = readLine()?.toIntOrNull()

        when (opcionSeleccionada) {
            1, 2, 3 -> {
                print("Introduce la temperatura a convertir: ")
                val entrada = readLine() ?: ""

                // Usamos la variable capturada: opcionSeleccionada
                val resultado = convertir(entrada, opcionSeleccionada ?: 0)

                // Procesa el resultado
                if (resultado.isSuccess) {
                    val temp = resultado.getOrThrow()
                    val mensaje = "✅ CONVERSIÓN EXITOSA: ${entrada} -> ${temp.valor.format(2)} ${temp.escala}"
                    println(mensaje)
                    historial.add(mensaje) // Guardar historial
                } else {
                    val mensajeError =  resultado.exceptionOrNull()?.message
                    println(mensajeError)
                    historial.add("FALLO: $mensajeError")
                }
            }

            4 -> { // Mostrar Historial
                println("\n=== Historial de Conversiones ===")
                if (historial.isEmpty()) {
                    println("El historial está vacío.")
                } else {
                    historial.forEachIndexed { index, item ->
                        println("${index + 1}. $item")
                    }
                }
            }

            0 -> {
                println("Saliendo del conversor. ¡Adiós!")
                continuar = false
            }

            else -> println("Opción no válida. Por favor, introduce 0, 1, 2, 3 o 4.")
        }
    }
}

// Función main()

fun main() {
    println("--- Pruebas Iniciales (Cero Absoluto) ---")
    manejarResultadoPrueba(convertir("-273.16", 1)) // Debe fallar: Imposible
    manejarResultadoPrueba(convertir("-273.15", 1)) // Debe tener éxito: Cero Absoluto
    manejarResultadoPrueba(convertir("-1", 2))      // Debe fallar: Kelvin Imposible

    // Iniciar la interfaz interactiva
    menuInteractivo()
}