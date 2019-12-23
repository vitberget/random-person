package se.vitberget.randomperson

import se.vitberget.randomperson.mariadb.getConnection
import se.vitberget.randomperson.mariadb.initDBSchema
import se.vitberget.randomperson.mariadb.insertIntoDB

fun main(args: Array<String>) {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..4).map { randomPerson(firstNames, surNames, streets, cities) }

    println("persons ${persons.size}")

    val moddedPersons1 = marryTraditionalPpl(persons, 0)

    println("moddedPersons ${moddedPersons1.size}")
    println()

    val moddedPersons = makeBabies( moddedPersons1, 1 )

    moddedPersons.forEachIndexed { idx, person ->
        println(
            """
                |# ${idx + 1}
                |${person}
                |
                """.trimMargin()
        )
    }


    try {
        initDBSchema()
    } catch (e: Exception) {
        println(e)
    }

    val con = getConnection()

    moddedPersons.forEach {
        insertIntoDB(it, con)
    }
}



