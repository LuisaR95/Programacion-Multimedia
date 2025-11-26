import java.io.File
import kotlin.math.roundToInt
import kotlin.math.abs

// Estructura de Datos y Texto de Prueba

data class EstadisticasTexto(
    val totalCaracteres: Int,
    val totalPalabras: Int,
    val longitudPromedioPalabras: Double,
    val palabraMasFrecuente: String,
    val frecuenciaPalabras: Map<String, Int>
)

const val TEXTO_EJEMPLO = """
Kotlin es un lenguaje de programación moderno y conciso. 
Kotlin corre sobre la Máquina Virtual de Java (JVM). 
Es totalmente interoperable con Java, lo que facilita 
su adopción. Kotlin es el lenguaje preferido para 
el desarrollo de aplicaciones Android.
"""

// Funciones de Transformación y Análisis

// Normaliza el texto: minúsculas y remueve puntuación
fun normalizarTexto(texto: String): String {
    //reemplaza cualquier cosa que NO sea letra/dígito/espacio
    val sinPuntuacion = texto.lowercase().replace(Regex("[^\\w\\s]"), " ")

    // Reemplaza múltiples espacios por uno solo
    return sinPuntuacion.replace(Regex("\\s+"), " ").trim()
}

// Cuenta caracteres (sin espacios)
fun contarCaracteres(textoNormalizado: String): Int {
    return textoNormalizado.replace(" ", "").length
}

// Obtiene la lista limpia de palabras. Divide el texto por espacios y devuelve una lista:
fun obtenerListaPalabras(textoNormalizado: String): List<String> {
    return textoNormalizado
        .split(' ') // Divide por espacios
        .filter { it.isNotBlank() } // Filtra palabras vacías
}

// Encuentra la palabra más frecuente usando groupBy y maxByOrNull
// groupBy { it } → agrupa palabras iguales
//mapValues { it.value.size } → cuenta cuántas veces aparece cada una
//maxByOrNull { it.value } → busca la más repetida
fun encontrarPalabraMasFrecuente(palabras: List<String>): String {
    val frecuencia = palabras.groupBy { it }
        .mapValues { it.value.size }

    val masFrecuente = frecuencia.maxByOrNull { it.value }

    return masFrecuente?.key ?: "N/A"
}

// Toma cada palabra, obtiene su longitud y calcula un promedio.
fun longitudPromedioPalabras(palabras: List<String>): Double {
    if (palabras.isEmpty()) return 0.0

    return palabras
        .map { it.length } // Lista de longitudes
        .average() // Promedio
}

// Permite buscar palabras con expresiones regulares.

fun analizarTexto(texto: String): EstadisticasTexto {
    val textoNormalizado = normalizarTexto(texto)
    val palabras = obtenerListaPalabras(textoNormalizado)
    val frecuenciaMap = palabras.groupBy { it }.mapValues { it.value.size }

    return EstadisticasTexto(
        totalCaracteres = contarCaracteres(textoNormalizado),
        totalPalabras = palabras.size,
        longitudPromedioPalabras = longitudPromedioPalabras(palabras),
        palabraMasFrecuente = encontrarPalabraMasFrecuente(palabras),
        frecuenciaPalabras = frecuenciaMap
    )
}

// Busca palabras con un patrón usando Expresiones Regulares
fun buscarPalabrasConPatron(palabras: List<String>, patronRegex: String): List<String> {
    val regex = Regex(patronRegex)
    return palabras.filter { it.matches(regex) }
}

// Exportar análisis a archivo
// trimIndent() — Formatear texto multilínea
//joinToString() — Convertir una colección en texto

fun exportarAnalisis(stats: EstadisticasTexto) {
    val fileName = "analisis_texto.txt"
    val fileContent = """
        --- ANÁLISIS DE TEXTO ---
        Total de Palabras: ${stats.totalPalabras}
        Promedio de Longitud: ${"%.2f".format(stats.longitudPromedioPalabras)}
        Palabra Más Frecuente: ${stats.palabraMasFrecuente}

        --- FRECUENCIAS DETALLADAS ---
        ${stats.frecuenciaPalabras.entries.joinToString("\n") { "${it.key}: ${it.value}" }}
    """.trimIndent()

    try {
        File(fileName).writeText(fileContent)
        println("\nAnálisis exportado a: **$fileName**")
    } catch (e: Exception) {
        println("Error al exportar el análisis: ${e.message}")
    }
}

// Función Principal (main)
fun main() {
    println("**Iniciando Análisis de Texto**")
    val estadisticas = analizarTexto(TEXTO_EJEMPLO)


    println("RESULTADOS DEL ANÁLISIS ESTADÍSTICO")
    println("Total de Palabras: ${estadisticas.totalPalabras}")
    println("Total de Caracteres (sin espacios): ${estadisticas.totalCaracteres}")
    println("Longitud Promedio de Palabras: ${"%.2f".format(estadisticas.longitudPromedioPalabras)} caracteres")
    println("Palabra Más Frecuente: ${estadisticas.palabraMasFrecuente}")
    println("-----------------------------------------")

    // Mostrar top 5 frecuencias de palabras
    println("Top 5 Palabras por Frecuencia:")
    estadisticas.frecuenciaPalabras
        .entries
        .sortedByDescending { it.value }
        .take(5)
        .forEach { (palabra, conteo) ->
            println("  - $palabra: $conteo veces")
        }

    // --- BÚSQUEDA DE PATRONES (Regex) ---
    println("\n=========================================")
    println("BÚSQUEDA DE PATRONES")

    val palabrasNormalizadas = obtenerListaPalabras(normalizarTexto(TEXTO_EJEMPLO))

    // Ejemplo 1: Palabras que comienzan con la letra 'c'
    val patronC = "^c.*"
    val resultadosC = buscarPalabrasConPatron(palabrasNormalizadas, patronC)

    println("Patrón '$patronC' (Comienza con 'c'):")
    println("  - Encontradas (${resultadosC.size}): ${resultadosC.joinToString(", ")}")

    // Ejemplo 2: Palabras que terminan con 'a'
    val patronA = ".*a$"
    val resultadosA = buscarPalabrasConPatron(palabrasNormalizadas, patronA)

    println("Patrón '$patronA' (Termina con 'a'):")
    println("  - Encontradas (${resultadosA.size}): ${resultadosA.joinToString(", ")}")

    // --- EXPORTAR ---
    exportarAnalisis(estadisticas)
}