package days

class Day16 : Day(16) {

    var rules: MutableMap<String, IntArray> = mutableMapOf()
    var myTicket: List<Int> = listOf()
    var nearbyTickets: MutableList<List<Int>> = mutableListOf()
    var invalid = BooleanArray(0)

    override fun partOne(): Any {
        parse()
        invalid = BooleanArray(nearbyTickets.size)
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

    fun isNrValid(nr: Int): Boolean {
        var valid = false
        rules.forEach { (_, r) ->
            if (isNrValid(nr, r)) {
                valid = true
            }
        }
        return valid
    }

    fun isNrValid(nr: Int, r: IntArray): Boolean = nr in r[0]..r[1] || nr in r[2]..r[3]

    override fun partTwo(): Any {
        val order = mutableMapOf<Int, String>()
        val todos: MutableSet<String> = rules.keys

        nearbyTickets.add(myTicket)


        val map: MutableMap<Int, List<String>> = HashMap()
        for (i in myTicket.indices) {
            map[i] = rules.keys.toList().filter { it.startsWith("departure") }
        }

        while (map.any { (_, v) -> v.size > 1 }) {
            for (ticket in nearbyTickets) {
                for ((idx, nr) in ticket.withIndex()) {
                    if (invalid[idx] || map[idx]!!.size == 1) continue
                    map[idx] = map[idx]!!.filter { isNrValid(nr, rules[it]!!) }

                    if (map[idx]!!.size == 1) {
                        todos.remove(map[idx]!![0])
                        order[idx] = map[idx]!![0]
                        println("Rule found: $idx -> ${map[idx]!![0]}")
                        order.forEach { k, v -> println("$k -> $v") }
                        break
                    }
                }
            }
            break
        }
        var count = 0

        map.forEach { k, v -> println("$k -> ${v.joinToString()}") }

        order.forEach { (idx, it) ->
            if (it.startsWith("departure ")) {
                count += myTicket[idx]
            }
        }
        return count
    }

    fun parse() {
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
                else -> throw Exception("Unkown State: $state")
            }
        }
    }
}
