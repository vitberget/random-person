package se.vitberget.randomperson

import kotlin.random.Random


fun main(args: Array<String>) {
    println("Hello world")

    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    for (i in 1..10) {
        val firstName = firstNames.random()
        val surName = surNames.random()
        val street = streets.random()
        val streetNumber = Random.nextInt(1, 100)
        val city = cities.random()

        println("# Person $i")
        println("$firstName $surName")
        println("$street $streetNumber")
        println("${city[0]} ${city[1]}")
        println()
    }
}
