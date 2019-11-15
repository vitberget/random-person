package se.vitberget.randomperson

import kotlin.random.Random

object PNGenerator {
    private var generatedPNRs = mutableSetOf<String>()

    fun generate(): String {
        synchronized(generatedPNRs) {
            while (true) {
                val pnr = listOf(
                    Random.nextInt(1, 9),
                    Random.nextInt(0, 9),
                    Random.nextInt(0, 9),
                    Random.nextInt(0, 9),
                    Random.nextInt(0, 9),
                    Random.nextInt(0, 9)
                ).joinToString(separator = "")

                if (!generatedPNRs.contains(pnr)) {
                    generatedPNRs.add(pnr)
                    return pnr
                }
            }
        }
    }
}