package days

class Day7 : Day(7) {

    override fun partOne(): Any {
        val bags = HashSet<String>(); bags.add("shiny gold")
        var prevSize = 0
        while (prevSize != bags.size) {
            prevSize = bags.size
            data.forEach lit@{ (bag: String, contains: List<Pair<Int, String>?>) ->
                if (contains[0] == null) return@lit
                if (contains.any { bags.contains(it!!.second) }) {
                    bags.add(bag)
                }
            }
        }
        return prevSize - 1
    }

    override fun partTwo(): Any {
        val todos = HashMap<String, Int>()
        todos["shiny gold"] = 1
        var count = 0
        while (todos.isNotEmpty()) {
            val bag = Pair(todos.keys.first(), todos[todos.keys.first()])
            todos.remove(bag.first)
            count += bag.second!!
            data.forEach { (t, u) ->
                if (u[0] != null && t == bag.first) {
                    u.forEach {
                        if (it != null) {
                            val preVal: Int = if (todos[it.second] != null) todos[it.second]!! else 0
                            todos[it.second] = preVal + bag.second!! * it.first
                        }
                    }
                }
            }
        }
        return count-1
    }

    private val data = inputList.map { it.split(" bags contain ") }
        .associate {
            it[0] to it[1]
                .split(", ").map { it1 ->
                    val spl = it1.split(" ")
                    if (spl[0] == "no") null
                    else {
                        Pair(spl[0].toInt(), concatStrArray(spl, " ", 1, spl.size - 2))
                    }
                }
        }

    private fun concatStrArray(arr: List<String>, separator: String, from: Int, to: Int): String {
        var ret = ""
        for (i in from..to) {
            ret += arr[i]
            if (i != to) ret += separator
        }
        return ret
    }
}
