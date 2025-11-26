fun main() {
    println(" **Juego de Adivinanza de Números** 🎉")
    println("----------------------------------------")

    // Generar número aleatorio entre 1 y 100
    val numeroSecreto = (1..100).random()

    // Variables de estado
    var intentosActuales = 0
    var mejorRecord = Int.MAX_VALUE // Usado para inicializar el récord (máximo posible)
    var jugarDeNuevo = true

    //Cada ronda empieza sin intentos y marcando que el número aún no ha sido adivinado.
    while (jugarDeNuevo) {
        println("\nHe generado un número secreto entre 1 y 100 ¡Intenta adivinarlo!")
        intentosActuales = 0
        var adivinado = false

        // Bucle para múltiples intentos
        do {
            print("Introduce tu suposición: ")
            val entrada = readLine()

            // Manejo de entrada no numérica o nula
            if (entrada == null) {
                println("Error: Entrada nula.")
                continue
            }

            // El programa verifica:
            //Que la entrada no es nula
            //Que el jugador introdujo un número
            //Que está en el rango válido (1 a 100)
            //Si no cumple, pide nuevamente la suposición.
            val suposicion = entrada.toIntOrNull()

            if (suposicion == null || suposicion < 1 || suposicion > 100) {
                println(" ¡Entrada no válida! Por favor, introduce un número entre 1 y 100.")
                continue // Vuelve al inicio del bucle
            }

            intentosActuales++

            // Dar pistas (mayor/menor)
            if (suposicion < numeroSecreto) {
                println("¡Demasiado bajo! Intenta con un número **mayor**.")
            } else if (suposicion > numeroSecreto) {
                println("¡Demasiado alto! Intenta con un número **menor**.")
            } else {
                // El número fue adivinado
                adivinado = true
                println("\n¡Felicidades! ¡Adivinaste el número secreto ($numeroSecreto)")
                println("Te ha tomado $intentosActuales intentos.")

                // Actualizar y mostrar récord
                if (intentosActuales < mejorRecord) {
                    mejorRecord = intentosActuales
                    println("¡NUEVO RÉCORD PERSONAL! **$mejorRecord** intentos.")
                } else if (mejorRecord != Int.MAX_VALUE) {
                    println("El mejor récord hasta ahora es: **$mejorRecord** intentos.")
                }
            }
        } while (!adivinado) // Continuar mientras el número no se haya adivinado

        // Preguntar si quiere volver a jugar
        print("\n¿Quieres jugar de nuevo? (s/n): ")
        val respuesta = readLine()?.lowercase()

        if (respuesta != "s") {
            jugarDeNuevo = false
            println("\n¡Gracias por jugar! ¡Hasta la próxima!")
        } else {
         
            println("\n--- Reiniciando el juego ---")
        }
    }
}