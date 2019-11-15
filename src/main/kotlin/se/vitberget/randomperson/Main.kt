package se.vitberget.randomperson

import kotlin.random.Random


fun main(args: Array<String>) {
    println("Hello world")

    val firstNames = getFirstnames()
    val surNames = getSurnames()
    val streets = getStreets()
    val cities = getCities()

    val persons = (1..10).map { randomPerson(firstNames, surNames, streets, cities) }

    for (i in 1..2)
        marryRandom(persons)

    persons.forEach { println(it); println() }
}

fun marryRandom(persons: List<Person>) {
    val p1 = persons
        .filter { it.married == null }
        .random()

    val p2 = persons
        .filter { it.married == null }
        .filter { it.pn != p1.pn }
        .random()

    p1.married = p2.pn
    p2.married = p1.pn

}

private fun randomPerson(firstNames: List<String>, surNames: List<String>, streets: List<String>, cities: List<City>) =
    Person(
        pn = PNGenerator.generate(),
        firstNames = (1..Random.nextInt(1, 4))
            .map { firstNames.random() },
        surName = surNames.random(),
        streetName = streets.random(),
        streetNumber = Random.nextInt(1, 200),
        city = cities.random()
    )

