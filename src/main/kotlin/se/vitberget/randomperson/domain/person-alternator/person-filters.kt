package se.vitberget.randomperson.domain.`person-alternator`

import se.vitberget.randomperson.domain.Person
import java.time.Year

fun isUnmarried(person: Person) =
    person.married == null

fun isParentMaterial(person: Person) =
    person.parents == null && person.born < Year.now().value - 20
