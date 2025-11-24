import kotlin.math.pow
import kotlin.math.abs // Importamos abs para la función de tendencias

// 1. Estructura de Datos para el Historial
data class RegistroIMC(
    val peso: Double, // Peso en kg
    val altura: Double, // Altura en metros
    val imc: Double,
    val clasificacion: String
)

// 2. Funciones Auxiliares de Clasificación y Cálculo

// Función para clasificar el IMC según las categorías de la OMS
fun clasificarIMC(imc: Double): String {
    // Requisito: when con rangos
    return when {
        imc < 18.5 -> "Bajo peso 😔"
        imc < 25.0 -> "Peso normal 💪" // 18.5 - 24.9
        imc < 30.0 -> "Sobrepeso 🤔" // 25.0 - 29.9
        else -> "Obesidad ⚠️" // >= 30.0
    }
}

// Función principal de cálculo
fun calcularIMC(peso: Double, altura: Double): Double {
    // Requisito: peso / (altura²)
    return peso / altura.pow(2)
}

// 3. Lógica Principal y Menú
fun main() {
    println("**Calculadora de IMC con Historial** ")


    val historial = mutableListOf<RegistroIMC>()

    // Función auxiliar para leer y validar un valor de Double
    fun leerValor(prompt: String): Double? {
        print(prompt)
        val entrada = readLine()
        val valor = entrada?.toDoubleOrNull()

        // Requisito: Validar entrada (peso y altura positivos)
        if (valor == null || valor <= 0) {
            println("Error: Por favor, introduce un valor numérico positivo.")
            return null
        }
        return valor
    }

    // 3. Función para mostrar tendencias (ganancia/pérdida de peso)
    // Definida aquí para acceder a 'historial'
    fun mostrarTendencias() {
        if (historial.size < 2) {
            println("Necesitas al menos dos registros para analizar tendencias.")
            return
        }

        // Requisito: historial.last() y Diferencia entre valores
        val ultimo = historial.last()
        val penultimo = historial[historial.size - 2] // Accede al penúltimo

        val diferenciaPeso = ultimo.peso - penultimo.peso
        val diferenciaIMC = ultimo.imc - penultimo.imc

        println("\n--- Análisis de Tendencia (vs. Registro anterior) ---")

        val simboloPeso = if (diferenciaPeso > 0) "📈 Ganancia" else if (diferenciaPeso < 0) "📉 Pérdida" else "↔️ Estable"
        val simboloIMC = if (diferenciaIMC > 0) "⬆️ Aumento" else if (diferenciaIMC < 0) "⬇️ Disminución" else "↔️ Estable"

        // Usamos kotlin.math.abs para mostrar la diferencia como valor absoluto
        println("Peso: ${simboloPeso} de **${"%.2f".format(abs(diferenciaPeso))} kg**.")
        println("IMC: ${simboloIMC} de **${"%.2f".format(abs(diferenciaIMC))} puntos**.")
        println("-------------------------------------------------------")
    }

    // 1. Función para ingresar un nuevo cálculo
    fun nuevoCalculo() {
        println("\n--- Nuevo Cálculo de IMC ---")
        val peso = leerValor("Introduce tu peso en kg: ") ?: return
        val altura = leerValor("Introduce tu altura en metros (ej: 1.75): ") ?: return

        val imc = calcularIMC(peso, altura)
        val clasificacion = clasificarIMC(imc)

        // Crear y añadir registro al historial
        val registro = RegistroIMC(peso, altura, imc, clasificacion)
        historial.add(registro)

        println("\n **Cálculo Realizado**")
        println("   IMC: **${"%.2f".format(imc)}**")
        println("   Clasificación: **$clasificacion**")

        // Mostrar tendencias después de cada cálculo
        mostrarTendencias()
    }

    // 2. Función para mostrar todo el historial
    fun mostrarHistorial() {
        if (historial.isEmpty()) {
            println("\n El historial de cálculos está vacío.")
            return
        }

        println("\n--- Historial de Mediciones (${historial.size} registros) ---")
        historial.forEachIndexed { index, reg ->
            println("${index + 1}. Peso: ${reg.peso} kg | Altura: ${reg.altura} m | IMC: ${"%.2f".format(reg.imc)} | Clasificación: ${reg.clasificacion}")
        }
        println("----------------------------------------")

        // Mostrar tendencias al ver el historial completo
        mostrarTendencias()
    }

    // --- Menú Interactivo ---
    var ejecutando = true
    while (ejecutando) {

        println("  MENÚ DE LA CALCULADORA DE IMC")
        println("1. Nuevo Cálculo de IMC")
        println("2. Mostrar Historial Completo y Tendencias")
        println("0. Salir")
        print("Elige una opción: ")

        when (readLine()) {
            "1" -> nuevoCalculo()
            "2" -> mostrarHistorial()
            "0" -> ejecutando = false
            else -> println(" Opción no válida. Inténtalo de nuevo.")
        }
    }
    println("\nGracias por usar la Calculadora de IMC.")
}