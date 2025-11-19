data class Tarea(val id: Int, val descripcion: String, var completada: Boolean = false)

fun main() {
    val tareas = mutableListOf<Tarea>()
    var siguienteId = 1

    while (true) {
        println("\n=== Gestor de tareas ===")
        println("1. Agregar tarea")
        println("2. Listar tareas")
        println("3. Marcar como completada")
        println("4. Eliminar tarea")
        println("5. Salir")
        print("Opción: ")

        when (readLine()?.toIntOrNull()) {

            1 -> { // Agregar tarea
                print("Descripción: ")
                val descripcion = readLine() ?: continue
                tareas.add(Tarea(siguienteId++, descripcion))
                println("Tarea agregada.")
            }

            2 -> { // Listar tareas
                if (tareas.isEmpty()) {
                    println("No hay tareas")
                } else {
                    tareas.forEach { (id, descripcion, completada) ->
                        val estado = if (completada) "[/]" else "[ ]"
                        println("$estado $id $descripcion")
                    }
                }
            }

            3 -> { // Marcar como completada
                print("ID de la tarea a completar: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val tarea = tareas.find { it.id == id }
                if (tarea != null) {
                    tarea.completada = true
                    println("Tarea marcada como completada.")
                } else {
                    println("ID no encontrado.")
                }
            }

            4 -> { // Eliminar tarea
                print("ID de la tarea a eliminar: ")
                val id = readLine()?.toIntOrNull() ?: continue

                if (tareas.removeIf { it.id == id }) {
                    println("Tarea eliminada.")
                } else {
                    println("ID no encontrado.")
                }
            }

            5 -> {
                println("Saliendo...")
                break
            }

            else -> println("Opción no válida")
        }
    }
}
