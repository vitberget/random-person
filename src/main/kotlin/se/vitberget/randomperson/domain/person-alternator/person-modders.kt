package se.vitberget.randomperson.domain.`person-alternator`

import se.vitberget.randomperson.domain.PNGenerator
import se.vitberget.randomperson.domain.Person
import java.time.Year
import kotlin.random.Random

typealias PPL2PPL = (List<Person>) -> List<Person>

fun marryCouple(betrothed: List<Person>) =
    listOf(
        betrothed[0].copy(married = betrothed[1].pn),
        betrothed[1].copy(married = betrothed[0].pn)
    )

fun makeBaby(parents: List<Person>): List<Person> {
    val adressParent = parents.random()
    val names = parents.flatMap { it.firstNames }.distinct()

    val baby = Person(
        parents = parents.map { it.pn },
        pn = PNGenerator.generate(),
        born = Year.now().value - Random.nextInt(0, 10),

        surName = parents.random().surName,
        firstNames = (1..Random.nextInt(1, Math.min(4, names.size)))
            .fold(listOf(), { acc, _ -> acc + (names - acc).random() }),

        streetName = adressParent.streetName,
        streetNumber = adressParent.streetNumber,
        city = adressParent.city
    )

    return listOf(baby)
}

fun sameSurname(family: List<Person>) = family.map { it.copy(surName = family[0].surName) }

fun peopleComposer(fun1: PPL2PPL, fun2: PPL2PPL): PPL2PPL = { fun1(fun2(it)) }

val traditionalCouple =
    peopleComposer(::marryCouple, ::sameSurname)