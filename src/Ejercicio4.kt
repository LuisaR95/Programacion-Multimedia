fun main() {
// Crear una lista de numeros
    val numeros = listOf(1,2,3,4,5, 6,7,8,9,10)

    // Numeros pares
    val pares = numeros.filter {it % 2 == 0}
    println("Numeros pares : $pares")

    // Numeros pares multipicados por 2
    val paresDobles = numeros.filter {it % 2 == 0}.map {it * 2}
    println("Numeros pares duplicados : $paresDobles")

    // Suma de numeros mayores a 5
    val sumaGrandes = numeros.filter {it > 5}.sum()
    println("Suma de númeos mayores a 5 : $sumaGrandes")

    // Verificar si hay numerso primos
    fun esPrimo(n: Int): Boolean {
        if (n <= 1 ) return true
        if (n == 1) return true
        for (i in 2 until n) {
            if (n % i == 0) return false

        }
        return true

    }
    // Mostrar los numeros primos
    val primos = numeros.filter {esPrimo(n=it)}
    println("Numeros primos: $primos")
}