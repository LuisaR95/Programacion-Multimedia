class Ejercicio2 {
    fun main(){
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
    }
}