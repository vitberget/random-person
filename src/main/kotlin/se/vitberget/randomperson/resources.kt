package se.vitberget.randomperson

fun processResourceIntoLines(resource:String) = String::class.java.getResource(resource)
    .readText()
    .lines()
    .filter { it.isNotBlank() }
    .map { it.trim() }


fun getStreets() = processResourceIntoLines("/streets")
fun getSurnames() = processResourceIntoLines("/sur-names")
fun getFirstnames() = processResourceIntoLines("/first-names")

fun getCities() = processResourceIntoLines("/cities")
    .map { it.split(" ") }
    .filter { it.size == 2 }
    .filter { it[0].isNotBlank() && it[1].isNotBlank() }
    .map { list -> list.map { str -> str.trim() } }