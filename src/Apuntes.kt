// (INMUTABLES - No pueden cambiar) Declaración de variables con tipo explicito

val nombre1: String = "Maria"
val edad1: Int = 25
val altura1: Double = 1.75

// Declaración de variables con inferencia de tipos (recomendados)

val ciudad = "Madrid" // Se deduce que es String
val contador = 42 //     Se deduce que es Int

// Valores constantes a nivel de clase

const val PI = 3.14

// (MUTABLES - PUEDEN CAMBIAR) Declaración de variables con tipo explicito

var nombre: String = "Marias"
var edad: Int = 25
var altura: Double = 1.75

// Conversón explicita

val entero: Int = 42

// Nullable vs Non-Nullable

var nombre2: String? = "Maria"

//Tipos de String

val multilinea = """
    primera linea
    segunda linea
    tercera linea
""".trimIndent()
val presentacion = "Me llamo $nombre1 y tengo $edad1 años"
val a = 5
val b = 3
val resultado = "El resultado de $a + $b es ${a + b}"
val lista = listOf(1,2,3) // List&lt
val descripcion = "La lista tiene ${lista.size} elementos"
val primera = "El elemento es ${lista.firstOrNull() ?: "Vacia"}"

//(EXPLICITA) Declarar funciones con parametros y valor de retorno

fun suma(a: Int, b: Int = 0): Int  {
    return a + b
}

//(IMPLICITA) Declarar funciones con parametros y valor de retorno

fun multiplicar(a: Int, b: Int): Int = a * b

// (EXPLICITA) Funcion sin valor de retorno

fun saludar(): Unit {
    println("Hola a todos")
}

// (IMPLICITA) Funcion sin valor de retorno

fun despedir(nombre: String){
    println("$nombre despedido")
}
// Funcion con cuerpo de expresion sin parametros

fun obtenerPI(): Double = 1.34

// Función que permite varias veces el mismo tipo de parametro

fun sumarTodo(vararg numeros: Int): Int{
    var suma = 0

// Declaración de un for-in, el cual numeros es el array
// y numero cada uno de los numeros del array

    for(numero in numeros){
        suma += numero
    }
    return suma
}

// sentencia WHEB basica con valores
fun obtenerDia(numero: Int): String = when(numero){
    1 -> "Lunes"
    2 -> "Martes"
    3 -> "Miercoles"
    4 -> "Jueves"
    5 -> "Viernes"
    6 -> "Sabado"
    7 -> "Domingo"
    else -> "Invalido"
}

// Sentencia WHEN basica con rangos

fun clasificarEdad(numero: Int): String = when(numero){
    in 0..12 -> "Niñ@"
    in 13..19 -> "Adolescente"
    in 20..59 -> "Adulto"
    in 60..150 -> "Adulto mayor"
    else -> "Invalido"
}

//Sentencia WHEN con tipos

fun describir(objeto: Any): String = when (objeto) {
    is String -> " Cadena de texto $objeto"
    is Int -> " Cadena de numero $objeto"
    is Double -> " Cadena de numero decimal $objeto"
    is Boolean -> " Cadena de boole $objeto"
    else -> "Tipo desconocido"
}

// Sentencia WHEN co expresioanes complejas

fun procesarEstado(estado: String): String = when{
    estado.isEmpty() -> "El estado esta vacio"
    estado.length > 100 -> "Estado demasiado largo"
    estado.contains("Error") -> "Estado contiene error"
    else -> "Estado invalido: $estado"
}

//Operadores logicos
// Operador OR(||)
// Operador AND(&&)
// Operador NOT(!)
// Operador IN
// Operador IS


// Reasignar valores

fun main(){ // Equivalnte a public static void main en java
    nombre = "Maria"
    edad = 24

    // Conversión de entero
    val decimal: Double = entero.toDouble()
    val texto: String = entero.toString()
    val booleano: Boolean = entero != 0

    nombre2 = null

    // Llamadas con diferentes combinaciones

    suma(a = 1)
    suma(a = 1, b = 2)
    suma(a = 1, b = 3)

    // Uso con diferentes cantidades de argumentos
    println(sumarTodo())
    println(sumarTodo(1, 2))
    println(sumarTodo(1, 2, 3, 4, 5))

    var array = intArrayOf(10, 20, 30)
    println(sumarTodo(*array))

    // condicionales

    val edad = 21

//IF

    if (edad >= 18) {
        println("Eres mayor de edad, tienes $edad años")

// IF - ELSE

        if (edad >= 18) {
            println("Eres mayor de edad, tienes $edad años")
        } else {
            println("Eres menor de edad, tienes $edad años")

// If como expresion

val resultado = if (edad >= 18) "Mayor de edad" else "Menor de edad"


// Bucles - for
// Sobre rangos
            for (i in 1..5){
                println(i)
            }

// Sobre exclusivo

            for (i in 1..<5){
                println(i)
            }

// Rango descendente

for (i in 5 downTo 1){
    println(i)
}

for (i in 1..10 step 2){
    println(i)
}

// for sobre lista

val nombres = listOf("Ana", "Carlos", "Maria")
for (nombre in nombres) {
    println(nombre)
}

// for sobre mapa

val mapa = mapOf("es" to "Español", "en" to "Ingles", "fr" to "Frances")
            for ((codigo, idioma) in mapa) {
                println("Código: $codigo, Idioma: $idioma")
            }


        }

    }
}

