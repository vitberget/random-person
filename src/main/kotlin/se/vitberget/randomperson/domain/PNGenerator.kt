package se.vitberget.randomperson.domain

import kotlin.random.Random

object PNGenerator {
    private var generatedPNRs = mutableSetOf<Long>()

    fun generate(): Long {
        synchronized(generatedPNRs) {
            while (true) {
                val pnr = Random.nextLong(1_000_000_000, 9_999_999_999)

                if (!generatedPNRs.contains(pnr)) {
                    generatedPNRs.add(pnr)
                    return pnr
                }
            }
        }
    }

    fun pns() = generatedPNRs.toList()
}