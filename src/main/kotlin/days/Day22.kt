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

    val stack = mutableListOf<Int>()

    override fun partTwo(): Any {
        parse(); return spaceCards(player1, player2)
    }

    var game = 1
    fun spaceCards(player1: MutableList<Int>, player2: MutableList<Int>): Int {
        val p1Set = mutableSetOf<String>(player1.joinToString())
        val p2Set = mutableSetOf<String>(player2.joinToString())
        var round = 1
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            if(game == 2 && round == 6){
                println("asd")
            }

            println("Round ${round++}, Game $game")
            println("p1: ${player1.joinToString()}")
            println("p2: ${player2.joinToString()}")
            println()
            val c1 = player1[0]; player1.removeAt(0)
            val c2 = player2[0]; player2.removeAt(0)
            stack.add(Math.min(c1, c2)); stack.add(Math.max(c1, c2))
            var winner = 0


            if (p1Set.contains(player1.joinToString()) || p2Set.contains(player2.joinToString())) winner = 1
            else if (c1 <= player1.size && c2 <= player2.size) {
                val p1l = mutableListOf<Int>()
                for (i in 0 until c1) p1l.add(player1[i])
                val p2l = mutableListOf<Int>()
                for (i in 0 until c2) p2l.add(player2[i])
                game++
                winner = spaceCards(p1l, p2l)
            } else winner = if (c1 > c2) 1 else 2

            p1Set.add(player1.joinToString()); p2Set.add(player2.joinToString())

            val n1 = stack[stack.size - 1]
            val n2 = stack[stack.size - 2]
            when (winner) {
                1 -> {
                    player1.add(n1); player1.add(n2)
                }
                2 -> {
                    player2.add(n1); player2.add(n2)
                }
                else -> {
                    return winner
                }
            }
            stack.removeLast()
            stack.removeLast()
        }
        game--
        if (stack.isEmpty()) {
            val winner = if (player1.isEmpty()) player2 else player1
            return winner.reversed().withIndex().sumBy { (idx, it) -> it * (idx + 1) }
        } else {
            if (player1.isEmpty()) return 2
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