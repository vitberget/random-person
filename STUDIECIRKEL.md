# Studiecirkel Kotlin

## Övningar

### Kapitel 3

Övning 1:

1. Gå till [person-fun.kt][person-fun.kt]
1. modda `fun marryRandom(...)` så att den tar en funktion (som argument) som används istället för raderna 37 och 38.
1. döp om `fun marryRandom(...)` till `peopleModifier` (eller något bättre beskrivande :D)
1. skriv en funktion som "gifter" personer, `marryCouple`.
1. Skriv en metod `newMarryRandom` som använder currying och `peopleModifier` och `marryCouple` (så att man använder den på samma sätt som man gjorde `marryRandom`.

Övning 2:

1. Skriv en funktion som ger två personer samma efternamn enligt lämplig (eller arbiträr) regel, `sameSurname`.
1. Skriv en mha combining en funktion som gör `marryCouple` och sen `sameSurname`.
1. Skriv en funktion `marrySameSurnameRandom` som använder ovan combine-metod på "samma sätt" som `newMarryRandom`.


[person-fun.kt]: src/main/kotlin/se/vitberget/randomperson/person-fun.kt
