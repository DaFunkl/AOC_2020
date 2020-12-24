package days

class Day24 : Day(24) {
    val data = inputList.map { replaceDirs(it) }
    val set = mutableSetOf<Pair<Int, Int>>()
    val dirs = Direction.values().map { it.dir }

    override fun partOne(): Any {
        data.forEach { ot ->
            var pos = Pair(0, 0)
            ot.forEach {
                pos += when (it) {
                    'e' -> Direction.E.dir
                    'w' -> Direction.W.dir
                    'k' -> Direction.SE.dir
                    'm' -> Direction.SW.dir
                    'n' -> Direction.NW.dir
                    'p' -> Direction.NE.dir
                    else -> throw Exception("Unknown IT: $it")
                }
            }
            if (set.contains(pos)) set.remove(pos) else set.add(pos)
        }
        return set.size
    }

    override fun partTwo(): Any {
        var cState = mutableSetOf<Pair<Int, Int>>()
        cState.addAll(set)
        for (i in 1..100) {
            val nState = mutableSetOf<Pair<Int, Int>>()
            val todos = mutableSetOf<Pair<Int, Int>>()
            cState.forEach { todos.add(it); dirs.forEach { d -> todos.add(it + d) } }
            todos.forEach {
                val adj = countAdj(it, cState)
                if (
                    (cState.contains(it) && adj in 1..2) ||
                    (!cState.contains(it) && adj == 2)
                ) nState.add(it)
            }
            cState = nState
        }
        println("Day : ${cState.size}")
        return cState.size
    }

    fun countAdj(p: Pair<Int, Int>, s: Set<Pair<Int, Int>>): Int =
        dirs.map { it + p }.filter { s.contains(it) }.count()

    fun replaceDirs(str: String): String {
        return str.replace("se", "k")
            .replace("sw", "m")
            .replace("nw", "n")
            .replace("ne", "p")
    }

    operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>): Pair<Int, Int> =
        Pair(this.first + o.first, this.second + o.second)

    fun Pair<Int, Int>.plus(o: Direction): Pair<Int, Int> = this + o.dir

    enum class Direction(val dir: Pair<Int, Int>) {
        E(Pair(2, 0)),
        W(Pair(-2, 0)),
        SE(Pair(1, 1)), // k
        SW(Pair(-1, 1)), // m
        NW(Pair(-1, -1)), // n
        NE(Pair(1, -1)) // p
    }
}