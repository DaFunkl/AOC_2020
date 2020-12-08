package days

class Day8 : Day(8) {

    enum class Operation { ACC, JMP, NOP }

    private val data = inputList.map {
        val spl = it.split(" ")
        Pair(Operation.valueOf(spl[0].toUpperCase()), spl[1].toInt())
    } as ArrayList<Pair<Operation, Int>>

    override fun partOne() = operate(data).first

    override fun partTwo(): Any {
        var pointer = 0
        var accumulate = operate(data)
        while (!accumulate.second) {
            while (!(data[pointer].first == Operation.JMP || data[pointer].first == Operation.NOP)) {
                pointer++
            }
            val oldOperation = data[pointer].first
            val newOperation = if (oldOperation == Operation.NOP) Operation.JMP else Operation.NOP
            data[pointer] = data[pointer].copy(first = newOperation)
            accumulate = operate(data)
            if (accumulate.second) {
                break
            }
            data[pointer] = data[pointer].copy(first = oldOperation)
            pointer++
        }
        return accumulate.first
    }

    private fun operate(instructions: ArrayList<Pair<Operation, Int>>): Pair<Int, Boolean> {
        val visited = mutableSetOf<Int>()
        var pointer = 0
        var accumulator = 0
        while (!visited.contains(pointer) && pointer < instructions.size) {
            visited.add(pointer)
            when (instructions[pointer].first) {
                Operation.ACC -> {
                    accumulator += instructions[pointer].second
                    pointer++
                }
                Operation.JMP -> pointer += instructions[pointer].second
                Operation.NOP -> pointer++
            }
        }
        return Pair(accumulator, pointer >= instructions.size)
    }
}
