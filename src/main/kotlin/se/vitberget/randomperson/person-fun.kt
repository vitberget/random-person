package se.vitberget.randomperson

import java.time.Year
import kotlin.random.Random

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

typealias PPL2PPL = (List<Person>) -> List<Person>

fun modifyPeople(
    modderFn: PPL2PPL,
    filterFn: (Person) -> Boolean,
    persons: List<Person>,
    times: Int
): List<Person> {
    val modPersons = persons.toMutableList()
    val unModded = persons.filter(filterFn).toMutableList()
    val moddedByMe = mutableListOf<Person>()

    (1..times).forEach {
        if (it % 100 == 0)
            println("processed $it/$times")

        val p1 = unModded.random()
        unModded.remove(p1)
        val p2 = unModded.random()
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

fun makeBaby(parents: List<Person>): List<Person> {
    val adressParent = parents.random()

    val names = parents.flatMap { it.firstNames }.distinct()

    val baby = Person(
        parents = parents.map { it.pn },
        pn = PNGenerator.generate(),
        born = Year.now().value - Random.nextInt(0, 10),

        surName = parents.random().surName,
        firstNames = (1..Random.nextInt(1, Math.min(4, names.size)))
            .fold(listOf(), { acc, i -> acc + (names - acc).random() }),

        streetName = adressParent.streetName,
        streetNumber = adressParent.streetNumber,
        city = adressParent.city
    )

    return parents + baby
}

fun sameSurname(family: List<Person>) = family.map { it.copy(surName = family[0].surName) }

fun <PARAM1_TYPE, PARAM2_TYPE, PARAM3_TYPE, PARAM4_TYPE, RETURN_TYPE>
        curryPeople(theFunction: (PARAM1_TYPE, PARAM2_TYPE, PARAM3_TYPE, PARAM4_TYPE) -> RETURN_TYPE):
            (PARAM1_TYPE) -> (PARAM2_TYPE) -> (PARAM3_TYPE, PARAM4_TYPE) -> RETURN_TYPE =
    { param1 ->
        { param2 ->
            { param3, param4 ->
                theFunction(param1, param2, param3, param4)
            }
        }
    }


val marryPpl = curryPeople(::modifyPeople)(::marryCouple)(::isUnmarried)

fun isUnmarried(person: Person) = person.married == null
fun isParentMaterial(person: Person) = person.parents == null && person.born < Year.now().value - 20

fun peopleComposer(fun1: PPL2PPL, fun2: PPL2PPL): PPL2PPL = { fun1(fun2(it)) }

val traditionalCouple = peopleComposer(::marryCouple, ::sameSurname)

val marryTraditionalPpl = curryPeople(::modifyPeople)(traditionalCouple)(::isUnmarried)

val makeBabies = curryPeople(::modifyPeople)(::makeBaby)(::isParentMaterial)
