package se.vitberget.randomperson

import kotlin.random.Random

data class Person(
    val pn: Int,
    val married: Int? = null,
    val firstNames: List<String>,
    val surName: String,
    val streetName: String,
    val streetNumber: Int,
    val city: City
) {

    override fun toString(): String {

        return """
            Person: $pn ${if (married != null) "married to $married" else ""} 
            ${firstNames.joinToString(separator = " ")} $surName
            $streetName $streetNumber
            ${city.code} ${city.name}
        """.trimIndent()
    }

    fun marriedTo(partner: Person) =
        Person(
            married = partner.pn,

            pn = pn,
            firstNames = firstNames,
            surName = surName,
            streetNumber = streetNumber,
            streetName = streetName,
            city = city
        )
}

fun randomPerson(firstNames: List<String>, surNames: List<String>, streets: List<String>, cities: List<City>) =
    Person(
        pn = PNGenerator.generate(),
        firstNames = (1..Random.nextInt(1, 4))
            .map { firstNames.random() },
        surName = surNames.random(),
        streetName = streets.random(),
        streetNumber = Random.nextInt(1, 200),
        city = cities.random()
    )

fun marryRandom(persons: List<Person>, times: Int = 1): List<Person> {
    var modPersons = persons

    for (i in 1..times) {
        val unmarried = modPersons
            .filter { it.married == null }

        val p1 = unmarried.random()

        val p2 = unmarried
            .filter { it.pn != p1.pn }
            .random()

        modPersons = modPersons
            .filter { it.pn != p1.pn }
            .filter { it.pn != p2.pn } +
                p1.marriedTo(p2) +
                p2.marriedTo(p1)
    }

    return modPersons
}