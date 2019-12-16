package se.vitberget.randomperson

import java.time.Year
import kotlin.random.Random

fun randomPerson(firstNames: List<String>, surNames: List<String>, streets: List<String>, cities: List<City>) =
    Person(
        pn = PNGenerator.generate(),
        firstNames = (1..Random.nextInt(1, 4))
            .map { firstNames.random() },
        surName = surNames.random(),
        streetName = streets.random(),
        streetNumber = Random.nextInt(1, 200),
        city = cities.random(),
        born = Year.now().value - Random.nextInt(20, 80)
    )


fun modifyPeople(
    modderFn: (List<Person>) -> List<Person>,
    persons: List<Person>,
    times: Int
): List<Person> {
    val modPersons = persons.toMutableList()
    val unModded = persons.filter { it.married == null }.toMutableList()
    val moddedByMe = mutableListOf<Person>()

    (1..times).forEach {
        if (it % 100 == 0)
            println("married $it/$times")

        val p1 = unModded.random()
        val p2 = unModded.random()

        unModded.remove(p1)
        unModded.remove(p2)

        modPersons.remove(p1)
        modPersons.remove(p2)

        moddedByMe.addAll(modderFn(listOf(p1, p2)))
    }

    return modPersons + moddedByMe
}

fun marryCouple(betrohed: List<Person>) =
    listOf(
        betrohed[0].copy(married = betrohed[1].pn),
        betrohed[1].copy(married = betrohed[0].pn)
    )

fun <A, B, C, D> curryPeople(f: (A, B, C) -> D): (A) -> (B, C) -> D =
    { a ->
        { b, c ->
            f(a, b, c)
        }
    }

val marryPpl = curryPeople(::modifyPeople)(::marryCouple)
