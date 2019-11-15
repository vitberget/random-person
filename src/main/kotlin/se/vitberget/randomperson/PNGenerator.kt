package se.vitberget.randomperson

import kotlin.random.Random

object PNGenerator {
    private var generatedPNRs = mutableSetOf<Int>()

    fun generate(): Int {
        synchronized(generatedPNRs) {
            while (true) {
                val pnr = Random.nextInt(1_000_000, 9_999_999)

                if (!generatedPNRs.contains(pnr)) {
                    generatedPNRs.add(pnr)
                    return pnr
                }
            }
        }
    }

    fun pns() = generatedPNRs.toList()
}