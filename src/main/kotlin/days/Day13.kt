package days


class Day13 : Day(13) {
    val data = inputList[1].split(",").asSequence()

    val departTime = inputList[0].toInt()
    val busIds = data.filter { it != "x" }.map { it.toInt() }
    val departSlots = data.withIndex().map { (idx, it) -> Pair(idx, if (it == "x") busIds else listOf(it.toInt())) }

    override fun partOne(): Any {
        val result = busIds.map { Pair(it, it - departTime % it) }.minByOrNull { it.second }!!
        return result.first * result.second
    }

    override fun partTwo(): Any {
        return -1
    }

}
