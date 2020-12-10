package days

class Day9 : Day(9) {

    private val chunkSize = 25
    private val data = inputList.map { it.toLong() }
    private val invalidNumber = findInvalid()

    override fun partOne(): Any = invalidNumber

    override fun partTwo(): Any {
        var end = 0
        var sum = data[0]
        val deQueue: MutableList<Long> = ArrayDeque()
        deQueue.add(data[0])
        while (sum != invalidNumber) {
            while (sum < invalidNumber) {
                sum += data[++end]
                deQueue.add(data[end])
            }
            while (sum > invalidNumber)
                sum -= deQueue.removeFirst()
        }
        return deQueue.minOrNull()!! + deQueue.maxOrNull()!!
    }

    private fun findInvalid(): Long {
        for (idx in chunkSize + 1 until data.size) {
            if (!hasSum(data, idx)) return data[idx]
        }
        return -1
    }

    private fun hasSum(list: List<Long>, target: Int): Boolean {
        val sum = list[target]
        val start = target - (chunkSize + 1)
        for (i1 in start until target) {
            for (i2 in i1 + 1 until target) {
                if (sum == list[i1] + list[i2]) return true
            }
        }
        return false
    }
}
