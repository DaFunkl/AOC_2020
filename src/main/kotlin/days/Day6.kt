package days

class Day6 : Day(6) {

    private val answers by lazy { solve() }

    override fun partOne(): Any = answers.first

    override fun partTwo(): Any = answers.second

    private fun solve(): Pair<Int, Int> {
        var groupAnswers = Pair(HashSet<Char>(), ArrayList<Set<Char>>())
        var count = Pair(0, 0)
        for (line in inputList) {
            if (line.isBlank()) {
                count = Pair(
                    count.first + groupAnswers.first.size,
                    count.second + getAdder(groupAnswers.second)
                )
                groupAnswers = Pair(HashSet(), ArrayList())
            } else {
                val hs = HashSet<Char>()
                line.forEach { groupAnswers.first.add(it); hs.add(it) }
                groupAnswers.second.add(hs)
            }
        }
        return Pair(
            count.first + groupAnswers.first.size,
            count.second + getAdder(groupAnswers.second)
        )
    }

    private fun getAdder(groupAnswers: List<Set<Char>>) =
        if (groupAnswers.size == 1) groupAnswers[0].size
        else groupAnswers.fold(groupAnswers.first()) { acc, i -> acc.intersect(i) }.size

}
