package days

class Day6 : Day(6) {
    override fun partOne(): Any {
        var groupAnswers = HashSet<Char>()
        var count = 0
        for (line in inputList) {
            if (line.isBlank()) {
                count += groupAnswers.size
                groupAnswers = HashSet()
            } else {
                line.forEach { groupAnswers.add(it) }
            }
        }
        count += groupAnswers.size
        return count
    }

    override fun partTwo(): Any {
        var groupAnswers = ArrayList<Set<Char>>()
        var count = 0
        for (line in inputList) {
            if (line.isBlank()) {
                val adder = if (groupAnswers.size == 1) {
                    groupAnswers[0].size
                } else {
                    var intersection = groupAnswers[0]
                    groupAnswers.forEach { intersection = intersection.intersect(it) }
                    intersection.size
                }
                count += adder
                groupAnswers = ArrayList()
            } else {
                val hs = HashSet<Char>()
                line.forEach { hs.add(it) }
                groupAnswers.add(hs)
            }
        }
        val adder = if (groupAnswers.size == 1) {
            groupAnswers[0].size
        } else {
            var intersection = groupAnswers[0]
            groupAnswers.forEach { intersection = intersection.intersect(it) }
            intersection.size
        }
        count += adder
        return count
    }
}
