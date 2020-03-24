package se.vitberget.randomperson.elastic

import se.vitberget.randomperson.domain.City
import se.vitberget.randomperson.domain.Person

fun jsonIfy(person: Person): String =
    listOf(
        "\"pn\": ${person.pn}",
        "\"born\": ${person.born}",
        "\"firstNames\": ${jsonIfyFirstNames(person.firstNames)}",
        "\"surName\": \"${person.surName}\"",
        "\"streetName\": \"${person.streetName}\"",
        "\"streetNumber\": ${person.streetNumber}",
        "\"city\": ${jsonIfyCity(person.city)}",
        jsonIfyMarried(person.married),
        jsonIfyParents(person.parents)
    )
        .filter { it.isNotEmpty() }
        .joinToString(
            prefix = "{",
            postfix = "}"
        )

fun jsonIfyParents(parents: List<Long>?): String =
    parents?.joinToString(
        prefix = "\"parents\":[",
        postfix = "]"
    ) ?: ""


fun jsonIfyMarried(married: Long?): String =
    if (married != null)
        "\"married\":$married"
    else
        ""

fun jsonIfyFirstNames(firstNames: List<String>): String =
    firstNames.map { "\"$it\"" }
        .joinToString(
            prefix = "[",
            postfix = "]"
        )

fun jsonIfyCity(city: City): String =
    "{\"code\": ${city.code}, \"name\": \"${city.name}\"}"


