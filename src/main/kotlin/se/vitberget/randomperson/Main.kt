package se.vitberget.randomperson

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..4).map { randomPerson(firstNames, surNames, streets, cities) }

    println("persons ${persons.size}")

//    println("pns ${PNGenerator.pns().sorted()}")

    val moddedPersons = marryTraditionalPpl(persons, 2)

    println("moddedPersons ${moddedPersons.size}")
    println()

    moddedPersons.forEachIndexed() { idx, person ->
            println(
                """
                |# ${idx + 1}
                |${person}
                |
                """.trimMargin()
            )
        }
}



