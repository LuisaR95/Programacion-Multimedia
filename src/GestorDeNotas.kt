import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// File → permite leer/escribir archivos.
//LocalDateTime → para guardar la fecha/hora de creación de una nota.
//DateTimeFormatter → da formato legible a las fechas.
data class Nota(
    val id: Int,
    var titulo: String,
    var contenido: String,
    val fechaCreacion: LocalDateTime = LocalDateTime.now(),
    var esImportante: Boolean = false
)

// StringBuilder para iterar y construir el texto de exportación
fun main() {
    // Variables de estado
    //notas: lista donde se guardan todas las notas.
    //nextId: ID autoincremental para cada nueva nota.
    //exportFileName: archivo donde se exportarán las notas.
    //formatter: formato legible de fecha.
    val notas = mutableListOf<Nota>()
    var nextId = 1
    val exportFileName = "notas_exportadas.txt"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Función auxiliar para obtener una nota por ID
    fun obtenerNotaPorId(id: Int): Nota? {
        return notas.find { it.id == id }
    }

    // --- Funciones de Gestión ---

    // Crear nota con título y contenido
    //Pide título y contenido al usuario.
    //Verifica que no estén vacíos.
    //Crea una nueva Nota con ID autoincremental.
    //La agrega a la lista.
    fun crearNota() {
        println("\n--- Crear Nueva Nota ---")
        print("Título: ")
        val titulo = readLine().orEmpty()
        print("Contenido: ")
        val contenido = readLine().orEmpty()

        if (titulo.isBlank() || contenido.isBlank()) {
            println("El título y el contenido no pueden estar vacíos.")
            return
        }

        val nuevaNota = Nota(id = nextId++, titulo = titulo, contenido = contenido)
        notas.add(nuevaNota)
        println("✅ Nota ID ${nuevaNota.id} creada con éxito.")
    }

    // Mostrar notas por fecha de creación
    fun mostrarNotas() {
        if (notas.isEmpty()) {
            println("\n🚫 No hay notas para mostrar.")
            return
        }

        println("\n--- Lista de Notas (Ordenadas por Fecha de Creación) ---")
        // Ordenar notas por fecha de creación (ascendente)
        notas.sortedBy { it.fechaCreacion }.forEach { nota ->
            val importancia = if (nota.esImportante) "⭐ IMPORTANTE" else ""
            println("----------------------------------------")
            println("ID: ${nota.id} | Título: **${nota.titulo}** $importancia")
            println("Fecha: ${nota.fechaCreacion.format(formatter)}")
            println("Contenido: ${nota.contenido.take(50)}${if (nota.contenido.length > 50) "..." else ""}")
        }
        println("----------------------------------------")
    }

    // Buscar por título o contenido (Requisito: filter y map)
    //Pide un término.
    //Convierte todo a minúsculas.
    //Filtra si aparece en título o contenido.
    //Muestra los resultados formateados (usando map para convertir objetos en texto).
    fun buscarNotas() {
        print("\nIntroduce el término de búsqueda (título o contenido): ")
        val termino = readLine().orEmpty().lowercase()

        if (termino.isBlank()) {
            println("El término de búsqueda no puede estar vacío.")
            return
        }

        // Búsqueda con filter
        val resultados = notas.filter {
            it.titulo.lowercase().contains(termino) || it.contenido.lowercase().contains(termino)
        }

        if (resultados.isEmpty()) {
            println("No se encontraron notas que contengan '$termino'.")
            return
        }

        println("\n--- Resultados de Búsqueda (${resultados.size} encontradas) ---")
        // Mostrar resultados usando map para simplificar la salida
        resultados.map {
            val importancia = if (it.esImportante) "⭐" else ""
            "ID: ${it.id} | Título: **${it.titulo}** $importancia | Creada: ${it.fechaCreacion.format(formatter)}"
        }.forEach(::println)
        println("----------------------------------------")
    }

    // Modificar nota
    fun modificarNota() {
        print("\nIntroduce el ID de la nota a modificar: ")
        val id = readLine()?.toIntOrNull() ?: return

        val nota = obtenerNotaPorId(id)

        if (nota == null) {
            println("Nota con ID $id no encontrada.")
            return
        }

        println("Modificando Nota ID ${nota.id} (Título actual: ${nota.titulo})")

        print("Nuevo Título (dejar vacío para no cambiar): ")
        val nuevoTitulo = readLine().orEmpty()
        if (nuevoTitulo.isNotBlank()) {
            nota.titulo = nuevoTitulo
        }

        print("Nuevo Contenido (dejar vacío para no cambiar): ")
        val nuevoContenido = readLine().orEmpty()
        if (nuevoContenido.isNotBlank()) {
            nota.contenido = nuevoContenido
        }

        println("✅ Nota ID ${nota.id} modificada con éxito.")
    }

    // Marcar/Desmarcar como importante
    //Alterna entre verdadero/falso.
    //Muestra mensaje de confirmación
    fun marcarImportante() {
        print("\nIntroduce el ID de la nota para cambiar su estado de importancia: ")
        val id = readLine()?.toIntOrNull() ?: return

        val nota = obtenerNotaPorId(id)

        if (nota == null) {
            println("Nota con ID $id no encontrada.")
            return
        }

        nota.esImportante = !nota.esImportante
        val estado = if (nota.esImportante) "marcada como importante" else "desmarcada como importante"
        println("✅ Nota ID ${nota.id} $estado.")
    }

    // Eliminar nota
    //Alterna entre verdadero/falso.
    //Muestra mensaje de confirmación
    fun eliminarNota() {
        print("\nIntroduce el ID de la nota a ELIMINAR: ")
        val id = readLine()?.toIntOrNull() ?: return

        val eliminada = notas.removeIf { it.id == id }

        if (eliminada) {
            println("Nota ID $id eliminada con éxito.")
        } else {
            println("Nota con ID $id no encontrada.")
        }
    }

    // Exportar notas a formato texto (Requisito: writeText)
    fun exportarNotas() {
        if (notas.isEmpty()) {
            println("\nNo hay notas para exportar.")
            return
        }

        //StringBuilder para iterar y construir el texto
        val contenidoExportacion = StringBuilder()

        contenidoExportacion.append("--- EXPORTACIÓN DE NOTAS (${LocalDateTime.now().format(formatter)}) ---\n\n")

        notas.sortedBy { it.fechaCreacion }.forEach { nota ->
            val importancia = if (nota.esImportante) " ⭐ (Importante)" else ""
            contenidoExportacion.append("========================================\n")
            contenidoExportacion.append("ID: ${nota.id}\n")
            contenidoExportacion.append("TÍTULO: ${nota.titulo}$importancia\n")
            contenidoExportacion.append("FECHA CREACIÓN: ${nota.fechaCreacion.format(formatter)}\n")
            contenidoExportacion.append("CONTENIDO:\n")
            contenidoExportacion.append(nota.contenido).append("\n")
        }

        try {
            // Requisito: Manejo de ficheros básico y writeText
            File(exportFileName).writeText(contenidoExportacion.toString())
            println("\nExportación completada. Notas guardadas en el archivo: **$exportFileName**")
        } catch (e: Exception) {
            println("Error al exportar las notas: ${e.message}")
        }
    }

    // --- Menú Interactivo ---
    var ejecutando = true
    while (ejecutando) {

        println("  APLICACIÓN DE NOTAS RÁPIDAS - MENÚ")
        println("1. Crear Nota")
        println("2. Mostrar Todas las Notas (por fecha)")
        println("3. Buscar Notas (por título/contenido)")
        println("4. Modificar Nota")
        println("5. Marcar/Desmarcar como Importante")
        println("6. Eliminar Nota")
        println("7. Exportar Notas a Texto")
        println("0. Salir")
        print("Elige una opción: ")

        when (readLine()) {
            "1" -> crearNota()
            "2" -> mostrarNotas()
            "3" -> buscarNotas()
            "4" -> modificarNota()
            "5" -> marcarImportante()
            "6" -> eliminarNota()
            "7" -> exportarNotas()
            "0" -> ejecutando = false
            else -> println(" Opción no válida. Inténtalo de nuevo.")
        }
    }
    println("\n👋 Aplicación finalizada.")
}