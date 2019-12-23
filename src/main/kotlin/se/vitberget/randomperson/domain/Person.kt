package se.vitberget.randomperson.domain

import java.time.Year
import kotlin.random.Random

data class Person(
    val pn: Long,
    val married: Long? = null,
    val parents: List<Long>? = null,
    val firstNames: List<String>,
    val surName: String,
    val streetName: String,
    val streetNumber: Int,
    val city: City,
    val born: Int
) {

    override fun toString(): String {
        return """
            |Person: $pn, born $born${if (married != null) " married to $married" else ""}${if (parents!=null) " child of "+parents.joinToString() else ""}
            |${firstNames.joinToString(separator = " ")} $surName
            |$streetName $streetNumber
            |${city.code} ${city.name}
        """.trimMargin()
    }
}

fun randomPerson(firstNames: List<String>, surNames: List<String>, streets: List<String>, cities: List<City>) =
    Person(
        pn = PNGenerator.generate(),
        firstNames = (1..Random.nextInt(1, 4))
            .fold(listOf(), { acc, i -> acc + (firstNames - acc).random() }),
        surName = surNames.random(),
        streetName = streets.random(),
        streetNumber = Random.nextInt(1, 200),
        city = cities.random(),
        born = Year.now().value - Random.nextInt(20, 80)
    )