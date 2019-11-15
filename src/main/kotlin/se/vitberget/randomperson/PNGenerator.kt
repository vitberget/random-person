package se.vitberget.randomperson

import kotlin.random.Random

object PNGenerator {
    private var generatedPNRs = mutableSetOf<Int>()

    fun generate(): Int {
        synchronized(generatedPNRs) {
            while (true) {
                val pnr = Random.nextInt(100_000, 999_999)

                if (!generatedPNRs.contains(pnr)) {
                    generatedPNRs.add(pnr)
                    return pnr
                }
            }
        }
    }
}