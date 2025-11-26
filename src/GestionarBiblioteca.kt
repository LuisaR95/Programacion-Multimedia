// Define data class Libro
data class Libro(val titulo: String, val autor: String, val año: Int, val estado: String)

// lista de libros
val biblioteca = listOf(
    Libro("Cien años de soledad", "Gabriel García Márquez", 1967, "Disponible"),
    Libro("La casa de los espíritus", "Isabel Allende", 1982, "Prestado"),
    Libro("El amor en los tiempos del cólera", "Gabriel García Márquez", 1985, "Disponible"),
    Libro("Ficciones", "Jorge Luis Borges", 1944, "Disponible"),
    Libro("Eva Luna", "Isabel Allende", 1985, "Prestado"),
    Libro("Rayuela", "Julio Cortázar", 1963, "Disponible")
)

// Funciones de Búsqueda y Filtrado

// Implementa buscarLibrosPorAutor()
// Busca todos los libros cuyo autor coincida total o parcialmente
//con la palabra que escribe el usuario.
fun buscarLibrosPorAutor(autor: String, libros: List<Libro>): Result<List<Libro>> {
    if (autor.isBlank()) {
        return Result.failure(Exception("El nombre del autor no puede estar vacío."))
    }

    // Filtra la lista y convierte a minúsculas para una búsqueda insensible a mayúsculas.
    // Si el usuario deja el campo vacío → devuelve un error (Result.failure).
    //Filtra los libros donde autor contenga el texto buscado:
    val librosEncontrados = libros.filter { it.autor.contains(autor, ignoreCase = true) }

    return if (librosEncontrados.isNotEmpty()) {
        Result.success(librosEncontrados) //Si encuentra resultados
    } else {
        Result.failure(Exception("No se encontraron libros del autor '$autor'."))  //Si no encuentra → error con mensaje.
    }
}

// Implementa buscarLibrosPorRangoAño() ← Usa filter con rango
fun buscarLibrosPorRangoAño(inicio: Int, fin: Int, libros: List<Libro>): Result<List<Libro>> {
    if (inicio > fin || inicio <= 0) {
        return Result.failure(Exception("El rango de años es inválido."))
    }

    // Filtra los libros cuyo año está dentro del rango
    val librosEncontrados = libros.filter { it.año in inicio..fin }

    return if (librosEncontrados.isNotEmpty()) {
        Result.success(librosEncontrados)
    } else {
        Result.failure(Exception("No se encontraron libros publicados entre $inicio y $fin."))
    }
}

// Implementa librosDisponibles
// Devuelve solo los libros cuyo estado es "Disponible"
fun librosDisponibles(libros: List<Libro>): Result<List<Libro>> {
    // Filtra los libros que tienen el estado "Disponible"
    val disponibles = libros.filter { it.estado == "Disponible" }

    return if (disponibles.isNotEmpty()) {
        Result.success(disponibles)
    } else {
        Result.failure(Exception("Actualmente no hay libros disponibles."))
    }
}

// Implementa calcularEstadisticas()
fun calcularEstadisticas(libros: List<Libro>): Result<String> {
    if (libros.isEmpty()) {
        return Result.failure(Exception("La lista de libros está vacía."))
    }

    // Agrupa los libros por autor y calcula el conteo de libros de cada autor
    val conteoPorAutor = libros.groupBy { it.autor } //Agrupa por autor
        .mapValues { (_, lista) -> lista.size } //Cuenta libros por autor

    // Encuentra el autor con más libros, manejando nulos con maxByOrNull() y ?.
    val autorMasProductivo = conteoPorAutor.maxByOrNull { it.value }?.key ?: "N/A" //Encuentra el autor con más libros

    val mensajeEstadisticas = """
        --- Estadísticas de la Biblioteca ---
        Total de libros: ${libros.size}
        Autores únicos: ${conteoPorAutor.size}
        Autor más productivo: $autorMasProductivo (con ${conteoPorAutor[autorMasProductivo]} libros)
    """.trimIndent()

    return Result.success(mensajeEstadisticas)
}



// Agregar búsqueda por título
fun buscarLibrosPorTitulo(titulo: String, libros: List<Libro>): Result<List<Libro>> { //Busca libros cuyo título contenga una palabra clave.
    if (titulo.isBlank()) {
        return Result.failure(Exception("El título no puede estar vacío."))
    }

    val librosEncontrados = libros.filter { it.titulo.contains(titulo, ignoreCase = true) }

    return if (librosEncontrados.isNotEmpty()) {
        Result.success(librosEncontrados)
    } else {
        Result.failure(Exception("No se encontraron libros con el título '$titulo'."))
    }
}

//Mostrar libros ordenados por año
// ascendente = true → del más viejo al más nuevo
//ascendente = false → del más nuevo al más viejo
fun ordenarLibrosPorAño(libros: List<Libro>, ascendente: Boolean = true): List<Libro> {
    return if (ascendente) {
        libros.sortedBy { it.año }
    } else {
        libros.sortedByDescending { it.año }
    }
}



// Función para manejar la impresión de resultados
fun <T> manejarResultado(titulo: String, resultado: Result<T>) { // Imprime de forma ordenada cualquier resultado obtenido con Result.
    println("\n=== $titulo ===")
    if (resultado.isFailure) {
        println("ERROR: ${resultado.exceptionOrNull()?.message}")
    } else {
        // En este punto, sabemos que es éxito, podemos usar getOrThrow() o getOrNull()
        println("ÉXITO:")
        // Si el resultado es una lista o un mapa, lo imprimimos de forma limpia
        when (val valor = resultado.getOrNull()) {
            is List<*> -> valor.forEach { println("  - $it") }
            is String -> println(valor)
            else -> println("  $valor")
        }
    }
}

// 7. Prueba llamando las funciones (Completado)
fun main() {


        println("===SISTEMA DE GESTIÓN DE BIBLIOTECA===")


        // Búsqueda por Autor
        print("\nIntroduce el nombre del autor a buscar: ")
        val autorBuscado = readLine() ?: ""
        val resultadoAutor = buscarLibrosPorAutor(autorBuscado, biblioteca)
        manejarResultado("Búsqueda por Autor ('$autorBuscado')", resultadoAutor)

        // Búsqueda por Rango de Año
        print("\n Introduce el año de inicio del rango (ej. 1960): ")
        val rangoInicio = readLine()?.toIntOrNull() ?: 0 // Si no es un número, usa 0

        print("Introduce el año de fin del rango (ej. 1980): ")
        val rangoFin = readLine()?.toIntOrNull() ?: 0 // Si no es un número, usa 0

        val resultadoRango = buscarLibrosPorRangoAño(rangoInicio, rangoFin, biblioteca)
        manejarResultado("Búsqueda por Rango de Año ($rangoInicio-$rangoFin)", resultadoRango)

        // Búsqueda por Título
        print("\n Introduce una palabra clave del título a buscar: ")
        val tituloBuscado = readLine() ?: ""
        val resultadoTitulo = buscarLibrosPorTitulo(tituloBuscado, biblioteca)
        manejarResultado("Búsqueda por Título ('$tituloBuscado')", resultadoTitulo)


        // Funciones sin entrada de usuario

        val resultadoDisponibles = librosDisponibles(biblioteca)
        manejarResultado("Libros Disponibles", resultadoDisponibles)

        val resultadoEstadisticas = calcularEstadisticas(biblioteca)
        manejarResultado("Estadísticas de la Biblioteca", resultadoEstadisticas)

        // Mostrar libros ordenados
        val librosOrdenados = ordenarLibrosPorAño(biblioteca, ascendente = true)
        println("\n=== Libros Ordenados por Año (Ascendente) ===")
        librosOrdenados.forEach { println("  - ${it.año}: ${it.titulo} (${it.autor})") }
    }
