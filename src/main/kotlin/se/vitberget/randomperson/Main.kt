package se.vitberget.randomperson

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..1_000).map { randomPerson(firstNames, surNames, streets, cities) }

    println("persons ${persons.size}")

//    println("pns ${PNGenerator.pns().sorted()}")

    marryRandom(persons, 200)
        .forEachIndexed { idx, person ->
            println(
                """
                |# ${idx + 1}
                |${person}
                |
                """.trimMargin()
            )
        }
}



