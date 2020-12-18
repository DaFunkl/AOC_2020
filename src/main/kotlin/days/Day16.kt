package days

class Day16 : Day(16) {

    private var rules: MutableMap<String, IntArray> = mutableMapOf()
    private var myTicket: List<Int> = listOf()
    private var nearbyTickets: MutableList<List<Int>> = mutableListOf()
    private var invalid = BooleanArray(0)

    override fun partOne(): Any {
        parse()
        invalid = BooleanArray(nearbyTickets.size + 1)
        var count = 0
        nearbyTickets.withIndex().forEach { (idx, ticket) ->
            ticket.forEach {
                if (!isNrValid(it)) {
                    invalid[idx] = true
                    count += it
                }
            }
        }
        return count
    }

    private fun isNrValid(nr: Int): Boolean = rules.any { (_, r) -> isNrValid(nr, r) }

    private fun isNrValid(nr: Int, r: IntArray): Boolean = nr in r[0]..r[1] || nr in r[2]..r[3]

    override fun partTwo(): Any {
        val order = mutableMapOf<Int, String>()
        val todos: MutableList<String> = rules.keys.filter { it.startsWith("departure") } as MutableList

        nearbyTickets.add(myTicket)
        val map: MutableMap<Int, List<String>> = HashMap()
        for (i in myTicket.indices) {
            map[i] = rules.keys.toList()
        }

        // remove invalid rules
        for ((idx, ticket) in nearbyTickets.withIndex()) {
            if (invalid[idx]) continue
            for ((i, nr) in ticket.withIndex()) {
                map[i] = map[i]!!.filter { isNrValid(nr, rules[it]!!) }
            }
        }

        // filter known rules
        val removed = mutableSetOf<String>()
        while (todos.isNotEmpty()) {
            for ((k, v) in map) {
                if (v.size == 1) {
                    if (removed.contains(v[0])) continue
                    order[k] = v[0]
                    todos.remove(v[0])
                    removed.add(v[0])
                    map.forEach { (t, u) -> if (k != t) map[t] = u.filter { it != v[0] } }
                }
            }
        }

        var count = 1L
        order.forEach { (idx, it) ->
            if (it.startsWith("departure ")) {
                count *= myTicket[idx]
            }
        }
        return count
    }

    private fun parse() {
        var state = 0
        for (it in inputList) {
            if (it.isBlank()) continue
            if (it == "your ticket:" || it == "nearby tickets:") {
                state++
                continue
            }
            when (state) {
                0 -> {
                    var spl = it.split(": ")
                    val ruleClass = spl[0]
                    rules[ruleClass] = IntArray(4)
                    spl = spl[1].split(" or ")
                    rules[ruleClass]!![0] = spl[0].split("-")[0].toInt()
                    rules[ruleClass]!![1] = spl[0].split("-")[1].toInt()
                    rules[ruleClass]!![2] = spl[1].split("-")[0].toInt()
                    rules[ruleClass]!![3] = spl[1].split("-")[1].toInt()
                }
                1 -> myTicket = it.split(",").map { it.toInt() }
                2 -> nearbyTickets.add(it.split(",").map { it.toInt() })
                else -> throw Exception("Unknown State: $state")
            }
        }
    }
}
