package days

class Day22 : Day(22) {
    private var player1: MutableList<Int> = mutableListOf()
    private var player2: MutableList<Int> = mutableListOf()

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


    override fun partTwo(): Any {
        parse(); return spaceCards(player1, player2)
    }

    private var stack = mutableListOf<Int>()
    private fun spaceCards(player1: MutableList<Int>, player2: MutableList<Int>): Int {
        val set = mutableSetOf<String>()
        var p1Won = false
        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val hash = player1.joinToString(",") + ";" + player2.joinToString(",")
            if (set.contains(hash)) {
                p1Won = true
                break
            }
            set.add(hash)

            val c1 = player1[0]; player1.removeAt(0)
            val c2 = player2[0]; player2.removeAt(0)
            stack.add(c2); stack.add(c1)

            val winner: Int = if (c1 <= player1.size && c2 <= player2.size) {
                val p1l = mutableListOf<Int>(); for (i in 0 until c1) p1l.add(player1[i])
                val p2l = mutableListOf<Int>(); for (i in 0 until c2) p2l.add(player2[i])
                spaceCards(p1l, p2l)
            } else if (c1 > c2) 1 else 2

            when (winner) {
                1 -> {
                    player1.add(stack[stack.size - 1]); player1.add(stack[stack.size - 2])
                }
                2 -> {
                    player2.add(stack[stack.size - 2]); player2.add(stack[stack.size - 1])
                }
                else -> return winner
            }
            stack.removeLast()
            stack.removeLast()
        }

        return if (p1Won || stack.isNotEmpty()) {
            if (p1Won || player2.isEmpty()) 1 else 2
        } else {
            val winner = if (player1.isEmpty()) player2 else player1
            winner.reversed().withIndex().sumBy { (idx, it) -> it * (idx + 1) }
        }
    }

    private fun parse() {
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