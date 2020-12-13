package days


class Day13 : Day(13) {
    val data = inputList[1].split(",")

    val departTime = inputList[0].toInt()
    val busIds = data.filter { it != "x" }.map { it.toInt() }

    override fun partOne(): Any {
        val result = busIds.map { Pair(it, it - departTime % it) }.minByOrNull { it.second }!!
        return result.first * result.second
    }

    override fun partTwo(): Any {
        val list = data.withIndex()
            .map { (idx, it) -> Pair(it, idx) }
            .filter { it.first != "x" }
            .map { Pair(it.first.toLong(), it.second.toLong()) }
            .sortedByDescending { it.first }

        var current = list.first().first - list.first().second
        var adder = list.first().first
        var finished = BooleanArray(list.size)
        finished[0] = true
        list.forEach { println(it.toString()) }
        while (finished.any { !it }) {
//            if (current >= 3500) break;
            current += adder
            println("todo[${finished.count { !it }}]: $current += $adder")
            for ((idx, done) in finished.withIndex()) {
                if (done) continue
                if (fits(current, list[idx])) {
                    finished[idx] = true
                    adder *= list[idx].first
                }
            }
            println(finished.joinToString())
            list.forEach { println(it.toString()) }
        }
        return current
    }

    fun fits(current: Long, bus: Pair<Long, Long>): Boolean {
//        println("($current % ${bus.first} (${current % bus.first})) == ${bus.first} - ${bus.second} (${bus.first - bus.second}) => ${(current % bus.first) == bus.first - bus.second}")
        return (current % bus.first) == (bus.first + bus.first  - bus.second) % bus.first
    }
}
