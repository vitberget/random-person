package se.vitberget.randomperson

import se.vitberget.randomperson.mariadb.*

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..100).map { randomPerson(firstNames, surNames, streets, cities) }

    println("persons ${persons.size}")

    val moddedPersons1 = marryTraditionalPpl(persons, 20)

    println("moddedPersons ${moddedPersons1.size}")
    println()

    val moddedPersons = makeBabies(moddedPersons1, 10)

    moddedPersons.forEachIndexed { idx, person ->
        println(
            """
                |# ${idx + 1}
                |${person}
                |
                """.trimMargin()
        )
    }


    val con = getConnection()
    dropDBSchema(con)
    initDBSchema(con)


    moddedPersons.forEach {
        insertIntoDB(it, con)
    }
}



