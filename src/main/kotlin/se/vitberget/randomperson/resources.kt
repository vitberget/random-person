package se.vitberget.randomperson

import se.vitberget.randomperson.domain.City

fun processResourceIntoLines(resource: String) =
    String::class.java.getResource(resource)
        .readText()
        .lines()
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .filterNot { it.startsWith("#") }


fun getStreets() = processResourceIntoLines("/streets")
fun getSurnames() = processResourceIntoLines("/sur-names")
fun getFirstnames() = processResourceIntoLines("/first-names")


fun getCities() = processResourceIntoLines("/cities")
    .map { it.split(" ") }
    .filter { it.size == 2 }
    .filterNot { it[0].isBlank() }
    .filterNot { it[1].isBlank() }
    .map {
        City(
            name = it[1].trim(),
            code = it[0].trim().toInt()
        )
    }