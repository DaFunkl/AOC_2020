package days

class Day22 : Day(22) {
    var player1: MutableList<Int> = mutableListOf()
    var player2: MutableList<Int> = mutableListOf()

    override fun partOne(): Any {
        parse()
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val c1 = player1[0]; player1.removeAt(0)
            val c2 = player2[0]; player2.removeAt(0)
            if (c1 > c2) {
                player1.add(c1); player1.add(c2)
            } else {
                player2.add(c2); player2.add(c1)
            }
        }
        val winner = if (player1.isEmpty()) player2 else player1
        return winner.reversed().withIndex().sumBy { (idx, it) -> it * (idx + 1) }
    }

    val p1Set = mutableSetOf<List<Int>>(player1)
    val p2Set = mutableSetOf<List<Int>>(player2)
    val stack = mutableListOf<Int>()
    override fun partTwo(): Any {
        parse()
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val c1 = player1[0]; player1.removeAt(0)
            val c2 = player2[0]; player2.removeAt(0)
            stack.add(c1); stack.add(c2)
            var winner = 0

            if (p1Set.contains(player1) || p2Set.contains(player2)) winner = 1
            else if (c1 == player1.size || c2 == player2.size) winner = -1
            else winner = if (c1 > c2) 1 else 2

            p1Set.add(player1); p2Set.add(player2)

            when (winner) {
                1 -> player1.addAll(stack.sortedDescending())
                2 -> player2.addAll(stack.sortedDescending())

            }

        }

        val winner = if (player1.isEmpty()) player2 else player1
        return winner.reversed().withIndex().sumBy { (idx, it) -> it * (idx + 1) }
    }

    fun recGame(player1: MutableList<Int>, player2: MutableList<Int>): Int {
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val c1 = player1[0]; player1.removeAt(0)
            val c2 = player2[0]; player2.removeAt(0)
            stack.add(c1); stack.add(c2)
            var winner = 0

            if (p1Set.contains(player1) || p2Set.contains(player2)) winner = 1
            else if (c1 == player1.size || c2 == player2.size) {
                winner = recGame(mutableListOf(player1), mutableListOf(player2))
            } else winner = if (c1 > c2) 1 else 2

            p1Set.add(player1); p2Set.add(player2)

            when (winner) {
                1 -> player1.addAll(stack.sortedDescending())
                2 -> player2.addAll(stack.sortedDescending())
                else -> return winner
            }
        }
        if(stack.isEmpty()){
            val winner = if (player1.isEmpty()) player2 else player1
            return winner.reversed().withIndex().sumBy { (idx, it) -> it * (idx + 1) }
        } else {
            if(player1.isEmpty()) return 2
            else return 1
        }
    }

    fun parse() {
        player1 = mutableListOf()
        player2 = mutableListOf()
        var playerIdx = 1
        for (it in inputList) {
            if (it.startsWith("Player")) continue
            if (it.isBlank()) playerIdx++
            else when (playerIdx) {
                1 -> player1.add(it.toInt())
                2 -> player2.add(it.toInt())
            }
        }
    }
}