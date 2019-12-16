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

fun marryCouple(betrothed: List<Person>) =
    listOf(
        betrothed[0].copy(married = betrothed[1].pn),
        betrothed[1].copy(married = betrothed[0].pn)
    )

fun sameSurname(family: List<Person>) = family.map { it.copy(surName = family[0].surName) }

fun <P1, P2, P3, RET> curryPeople(f: (P1, P2, P3) -> RET): (P1) -> (P2, P3) -> RET =
    { p1 ->
        { p2, p3 ->
            f(p1, p2, p3)
        }
    }

val marryPpl = curryPeople(::modifyPeople)(::marryCouple)

fun peopleComposer(
    f1: (List<Person>) -> List<Person>,
    f2: (List<Person>) -> List<Person>)
        : (List<Person>) -> List<Person> =
    {f1(f2(it))}

val traditionalCouple = peopleComposer(::marryCouple, ::sameSurname)

val marryTraditionalPpl = curryPeople(::modifyPeople)(traditionalCouple)
