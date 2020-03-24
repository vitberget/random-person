package se.vitberget.randomperson

import se.vitberget.randomperson.domain.`person-alternator`.makeBabies
import se.vitberget.randomperson.domain.`person-alternator`.marryTraditionalPpl
import se.vitberget.randomperson.domain.Person
import se.vitberget.randomperson.domain.randomPerson
import se.vitberget.randomperson.elastic.dropAndCreateElasticIdx
import se.vitberget.randomperson.elastic.getElasticClient
import se.vitberget.randomperson.elastic.insertIntoElastic
import se.vitberget.randomperson.mariadb.*
import java.io.FileInputStream
import java.util.*

fun main() {
    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..100).map {
        randomPerson(firstNames, surNames, streets, cities)
    }
    val moddedPersons1 = marryTraditionalPpl(persons, 20)
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

    doTheElastic(moddedPersons)
    doTheDB(moddedPersons)
}

fun doTheElastic(moddedPersons: List<Person>) {
    val props = getProps()

    val client = getElasticClient(props)
    val idx = props.getProperty("elastic.index")

    dropAndCreateElasticIdx(client, idx)

    moddedPersons
        .chunked(10)
        .forEach { insertIntoElastic(client, idx, it) }

}

private fun doTheDB(moddedPersons: List<Person>) {
    val props = getProps()
    val con = getDBConnection(props)

    dropDBSchema(con)
    initDBSchema(con)

    moddedPersons.forEach {
        insertIntoDB(it, con)
    }
}

private fun getProps(): Properties {
    FileInputStream("random-person.properties").use {
        val props = Properties()
        props.load(it)
        return props
    }
}