package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        val intList = inputList.map { it.toInt() }.asSequence()
        intList.forEach { a ->
            val b = intList.find { a + it == 2020 }
            if (b != null) {
                return a * b
            }
        }
        return -1
    }

    override fun partTwo(): Any {
        val intList = inputList.map { it.toInt() }.asSequence()
        intList.forEach { a ->
            intList.forEach { b ->
                val c = intList.find { a + b + it == 2020 }
                if (c != null) {
                    return a * b * c
                }
            }
        }
        return -1
    }
}