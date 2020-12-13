package days


class Day13 : Day(13) {
    private val data = inputList[1].split(",")

    private val departTime = inputList[0].toInt()
    private val busIds = data.filter { it != "x" }.map { it.toInt() }

    override fun partOne(): Any {
        val result = busIds.map { Pair(it, it - departTime % it) }.minByOrNull { it.second }!!
        return result.first * result.second
    }

    override fun partTwo(): Any {
        val list = data.asSequence().withIndex()
            .map { (idx, it) -> Pair(it, idx) }
            .filter { it.first != "x" }
            .map { Pair(it.first.toLong(), it.second.toLong()) }
            .sortedByDescending { it.first }.toList()

        var current = list.first().first - list.first().second
        var adder = list.first().first
        val finished = BooleanArray(list.size)
        finished[0] = true
        while (finished.any { !it }) {
            current += adder

            for ((idx, done) in finished.withIndex()) {
                if (done) continue
                if (fits(current, list[idx])) {
                    finished[idx] = true
                    adder *= list[idx].first
                }
            }
        }
        return current
    }

    private fun fits(current: Long, bus: Pair<Long, Long>): Boolean {
        return (current % bus.first) == (bus.first + ((bus.first - bus.second) % bus.first)) % bus.first
    }
}
