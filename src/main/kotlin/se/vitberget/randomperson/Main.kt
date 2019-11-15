package se.vitberget.randomperson

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..1_000_00).map { randomPerson(firstNames, surNames, streets, cities) }

    println("pns ${PNGenerator.pns().sorted()}")

    marryRandom(persons, 10_000)
        .forEach {
            println(it)
            println()
        }
}



