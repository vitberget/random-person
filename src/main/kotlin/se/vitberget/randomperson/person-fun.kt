package se.vitberget.randomperson

import kotlin.random.Random

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
    val modPersons = persons.toMutableList()
    val unmarried = persons.filter { it.married == null }.toMutableList()
    val marriedByMe = mutableListOf<Person>()

    (1..times).forEach {
        val p1 = unmarried.random()
        unmarried.remove(p1)

        val p2 = unmarried.random()
        unmarried.remove(p2)

        modPersons.remove(p1)
        modPersons.remove(p2)

        marriedByMe.add(p1.marriedTo(p2))
        marriedByMe.add(p2.marriedTo(p1))
    }

    return modPersons + marriedByMe
}
