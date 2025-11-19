fun main() {
    println("== Calculadora ==")

    // Pedimos primer numero
    println("Primer numero: ")
    val a = readLine()?.toDoubleOrNull() ?: return

    // pedimos la operacion
    println("Operacion: +, - /, *")
    val operacion = readLine()?: return

    // Pedimos segundo numero
    println("Segundo numero: ")
    val b = readLine()?.toDoubleOrNull() ?: return

    // Calcular el resultado de la operación

    val resultado = when (operacion) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if(b != 0.0) a / b else { println("Error división por cero"); return }
        else -> { print("Operación desconocida"); return }
    }

    println("Resultado: $resultado")
}
