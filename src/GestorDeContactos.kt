// Definición de constructor

data class Contacto(
    val id: Int,
    var nombre: String,
    var telefono: String,
    var email: String,
    var favorito: Boolean = false
)

// Definición excepciones

sealed class ContactoException(msg: String) : Exception(msg) {
    class ContactoNoEncontradoException(id: String) :
        ContactoException("No se encontró un contacto con ID $id")
}

// Creación de listas
val listaContactos = mutableListOf<Contacto>()
var siguienteId = 1


// Validación mail

fun validarEmail1(email: String): Result<String> {
    return if (email.contains("@") && email.contains(".")) {
        Result.success(email)
    } else {
        Result.failure(Exception("El email no valido"))

    }
}

// Vaidación telefono

fun validarTelefono(telefono: String): Result<String> {
    val regex = Regex("^\\d{9}$")
    return if (telefono.matches(regex)) Result.success(telefono)
    else Result.failure(Exception("El teléfono debe tener exactamente 9 dígitos"))
}

//Validación nombre

fun validarNombre1(nombre: String): Result<String> =
    if (nombre.length < 3) Result.failure(Exception("El nombre debe tener al menos 3 caracteres"))
    else Result.success(nombre)

//Creación contacto

fun crearContacto(nombre: String, telefono: String, email: String): Result<Contacto> {
    validarNombre(nombre).onFailure { return Result.failure(it) }
    validarTelefono(telefono).onFailure { return Result.failure(it) }
    validarEmail(email).onFailure { return Result.failure(it) }

    val nuevo = Contacto(siguienteId++, nombre, telefono, email)
    listaContactos.add(nuevo)
    return Result.success(nuevo)
}

// Buscar por nombre

fun buscarPorNombre(nombre: String): List<Contacto> =
    if (nombre.isBlank()) listaContactos
    else listaContactos.filter { it.nombre.contains(nombre, ignoreCase = true) }

// Obtener favoritos

fun obtenerFavoritos(): List<Contacto> =
    listaContactos.filter { it.favorito }

// Obtener orddenados

fun obtenerOrdenados(): List<Contacto> =
    listaContactos.sortedBy { it.nombre }

// toggle de favoritos

fun toggleFavorito(id: Int): Result<Unit> {
    val contacto = listaContactos.find { it.id == id }
        ?: return Result.failure(ContactoException.ContactoNoEncontradoException(id.toString()))

    contacto.favorito = !contacto.favorito
    return Result.success(Unit)
}

fun editarContacto(id: Int, nombre: String, telefono: String, email: String): Result<Contacto> {
    validarNombre(nombre).onFailure { return Result.failure(it) }
    validarTelefono(telefono).onFailure { return Result.failure(it) }
    validarEmail(email).onFailure { return Result.failure(it) }

    val contacto = listaContactos.find { it.id == id }
        ?: return Result.failure(Exception("No se encontró el contacto con ID $id"))

    contacto.nombre = nombre
    contacto.telefono = telefono
    contacto.email = email

    return Result.success(contacto)
}

//Eliminar contacto

fun eliminarContacto(id: Int): Result<Unit> {
    val eliminado = listaContactos.removeIf { it.id == id }
    return if (eliminado) Result.success(Unit)
    else Result.failure(ContactoException.ContactoNoEncontradoException(id.toString()))
}


// Mostrar contactos

fun mostrarContacto(c: Contacto) {
    val fav = if (c.favorito) "⭐" else ""
    println("[$fav ID: ${c.id}] ${c.nombre} | Tel: ${c.telefono} | Email: ${c.email}")
}


// Menu interactivo

fun menuInteractivo1() {
    var continuar = true

    while (continuar) {
        println(
            """
           
                   ===MENÚ DE CONTACTOS===
           
            1. Añadir contacto
            2. Buscar por nombre
            3. Mostrar todos (ordenados)
            4. Mostrar favoritos
            5. Alternar favorito
            6. Editar contacto
            7. Eliminar contacto
            0. Salir
        """.trimIndent()
        )

        print("Opción: ")
        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            1 -> {
                print("Nombre: "); val n = readLine() ?: ""
                print("Teléfono: "); val t = readLine() ?: ""
                print("Email: "); val e = readLine() ?: ""

                crearContacto(n, t, e)
                    .onSuccess { println("Contacto creado (ID ${it.id})") }
                    .onFailure { println("${it.message}") }
            }

            2 -> {
                print("Buscar nombre: "); val n = readLine() ?: ""
                val resultados = buscarPorNombre(n)
                if (resultados.isEmpty()) println("No hay resultados.")
                else resultados.forEach(::mostrarContacto)
            }

            3 -> obtenerOrdenados().forEach(::mostrarContacto)

            4 -> obtenerFavoritos().forEach(::mostrarContacto)

            5 -> {
                print("ID: "); val id = readLine()?.toIntOrNull() ?: 0
                toggleFavorito(id)
                    .onSuccess { println("Favorito cambiado") }
                    .onFailure { println("${it.message}") }
            }

            6 -> {
                print("ID: "); val id = readLine()?.toIntOrNull() ?: 0
                print("Nuevo nombre: "); val n = readLine() ?: ""
                print("Nuevo teléfono: "); val t = readLine() ?: ""
                print("Nuevo email: "); val e = readLine() ?: ""

                editarContacto(id, n, t, e)
                    .onSuccess { println("Contacto actualizado") }
                    .onFailure { println(" ${it.message}") }
            }

            7 -> {
                print("ID: "); val id = readLine()?.toIntOrNull() ?: 0
                eliminarContacto(id)
                    .onSuccess { println(" Contacto eliminado") }
                    .onFailure { println(" ${it.message}") }
            }

            0 -> {
                println("Saliendo...")
                continuar = false
            }

            else -> println("Opción inválida.")
        }
    }
}



// Datos de prueba


fun inicializarDatosPrueba() {
    crearContacto("Ana García", "600111222", "ana@test.com")
    crearContacto("Carlos López", "610333444", "carlos@test.com")
    crearContacto("María Pérez", "620555666", "maria@test.com")
}


// MAIN


fun main() {
    inicializarDatosPrueba()
    menuInteractivo1()
}
