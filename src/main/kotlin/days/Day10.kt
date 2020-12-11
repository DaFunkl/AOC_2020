package days

class Day10 : Day(10) {

    private val data = inputList.asSequence().map { it.toInt() }.sorted()

    override fun partOne(): Any {
        var prev = 0
        var count1 = 0
        var count3 = 1
        data.forEach {
            when (it - prev) {
                1 -> count1++
                3 -> count3++
            }
            prev = it
        }
        return count1 * count3
    }

    override fun partTwo(): Any {
        var continuesOnes = 0
        var prev = 0
        var result = 1L
        data.forEach {
            val delta = it - prev
            if (delta == 1) continuesOnes++
            else {
                if (continuesOnes > 1) result *= fetchMultiplier(continuesOnes)
                continuesOnes = 0
            }
            prev = it
        }
        if (continuesOnes > 1) result *= fetchMultiplier(continuesOnes)
        return result
    }

    private fun fetchMultiplier(continuesOnes: Int): Long = when (continuesOnes) {
        2 -> 2L
        3 -> 4L
        4 -> 7L
        else -> {
            println("unknown multiplier for: $continuesOnes")
            1
        }
    }
}