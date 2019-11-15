package se.vitberget.randomperson

import kotlin.random.Random

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..10).map { randomPerson(firstNames, surNames, streets, cities) }

    marryRandom(persons,2)
        .forEach {
            println(it)
            println()
        }
}



